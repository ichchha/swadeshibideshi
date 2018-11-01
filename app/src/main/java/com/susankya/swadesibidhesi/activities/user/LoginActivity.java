package com.susankya.swadesibidhesi.activities.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.morphingbutton.MorphingButton;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WCLoginAPI;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcCustomerAPI;
import com.susankya.swadesibidhesi.BuildConfig;
import com.susankya.swadesibidhesi.Generic.Flavor;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.Generic.Utilities;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcCustomer;
import com.susankya.swadesibidhesi.models.WooCommerce.WcCustomerResponse;
import com.susankya.swadesibidhesi.models.WooCommerce.WcLoginResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.susankya.swadesibidhesi.Generic.Keys.CUSTOMER_ID;
import static com.susankya.swadesibidhesi.Generic.Keys.FROM_CHECKOUT;
import static com.susankya.swadesibidhesi.Generic.Keys.FROM_ORDERS;
import static com.susankya.swadesibidhesi.Generic.Keys.FROM_QUOTE;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.signUp)
    TextView signUp;
    @BindView(R.id.skip)
    TextView skip;
    @BindView(R.id.new_to_app)
    TextView newToApp;
    @BindView(R.id.backArrow)
    ImageView backArrow;
    @BindView(R.id.pasalXIcon)
    ImageView appIcon;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.email_layout)
    TextInputLayout emailLayout;
    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.emailAddress)
    EditText emailAddress;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText txtPassword;
    @BindView(R.id.btnMorph)
    MorphingButton btnMorph;
    private Boolean fromOrders = false, fromCheckOut = false, fromQuote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up
        return false;
    }

    private void init() {
        newToApp.setText("New to " + getResources().getString(R.string.app_name) + " app? ");
        if (getIntent().getExtras() != null) {
            fromCheckOut = getIntent().getBooleanExtra(FROM_CHECKOUT, false);
            fromOrders = getIntent().getBooleanExtra(FROM_ORDERS, false);
            fromQuote = getIntent().getBooleanExtra(FROM_QUOTE, false);
        }
        if (BuildConfig.FLAVOR.equals(Flavor.MBSH))
            appIcon.setImageResource(R.drawable.ic_mbsh);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        Boolean emailErr = false, passwordErr = false;
        String email = username.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        if (email.isEmpty()) {
            emailErr = true;
            username.setError("Please enter your username*");
        } else username.setError(null);
        if (password.isEmpty()) {
            passwordErr = true;
            txtPassword.setError("Please enter password*");
        } else txtPassword.setError(null);

        if (!emailErr && !passwordErr) {
            if (Utilities.isConnectionAvailable(this))
                loginUser(email, password);
            else
                Toast.makeText(this, "There is no internet connection. Please try again later!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser(String email, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        WcLoginResponse loginData = new WcLoginResponse();
        loginData.username = email;
        loginData.password = password;
        WCLoginAPI loginAPI = App.getRetrofit().create(WCLoginAPI.class);
        retrofit2.Call<WcLoginResponse> call = loginAPI.userLogin(loginData);
        call.enqueue(new Callback<WcLoginResponse>() {
            @Override
            public void onResponse(retrofit2.Call<WcLoginResponse> call, Response<WcLoginResponse> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    fetchCustomerDetail(response.body().user_email);
                    App.db().putString(Keys.CUSTOMER_EMAIL, response.body().user_email);
                    App.db().putString(Keys.USERNAME, response.body().user_nicename);
                } else {
                    try {
//                        Log.d("LoginError", response.errorBody().string());
                        Toast.makeText(getApplicationContext(), "Please check your email and password and try again", Toast.LENGTH_SHORT).show();
//                            clearFields();
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<WcLoginResponse> call, Throwable t) {
                dialog.dismiss();
//                Log.d("LoginFA", "" + t.toString());
            }
        });
    }

    private void fetchCustomerDetail(String user_email) {
        WcCustomerAPI customerAPI = App.getWcRetrofit().create(WcCustomerAPI.class);
        customerAPI.fetchCustomerDetail(user_email).enqueue(new Callback<WcCustomerResponse>() {
            @Override
            public void onResponse(Call<WcCustomerResponse> call, Response<WcCustomerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WcCustomer customer = response.body().customer;
                    App.db().putObject(Keys.CUSTOMER, customer);
                    App.db().putBoolean(Keys.USER_LOGGED_IN, true);
                    App.db().putInt(CUSTOMER_ID, customer.id);
                    HomeActivity.updateNavHeader();
                    if (fromCheckOut) {
                        Intent intent= new Intent(LoginActivity.this, AddToCartActivity.class);
                        intent.putExtra(FROM_QUOTE,fromQuote);
                        startActivity(intent);
                    } else if (fromOrders)
                        startActivity(new Intent(LoginActivity.this, OrdersActivity.class));
                    else
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                    Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                    finish();
//                    Log.d("customer", "onResponse: " + customer.id);
                } else {
                    try {
                        Toast.makeText(getApplicationContext(), "There was a problem connecting to network.Please try again later!", Toast.LENGTH_SHORT).show();
//                        Log.d("customer", "errorbody" + response.errorBody().string());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<WcCustomerResponse> call, Throwable t) {
//                Log.d("customer", "onFailure: " + t);
            }
        });
    }
}
