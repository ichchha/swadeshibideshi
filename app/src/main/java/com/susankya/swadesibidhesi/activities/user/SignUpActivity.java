package com.susankya.swadesibidhesi.activities.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.susankya.swadesibidhesi.APIs.WooCommerce.WcCustomerAPI;
import com.susankya.swadesibidhesi.BuildConfig;
import com.susankya.swadesibidhesi.Generic.Flavor;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.Generic.Utilities;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcCustomer;
import com.susankya.swadesibidhesi.models.WooCommerce.WcCustomerResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.btnConfirm)
    Button createAccount;
    @BindView(R.id.userName_layout)
    TextInputLayout userNameLayput;
    @BindView(R.id.firstname_layout)
    TextInputLayout fNameLayout;
    @BindView(R.id.lastname_layout)
    TextInputLayout lNameLayout;
    @BindView(R.id.email_layout)
    TextInputLayout emailLayout;
    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.re_password_layout)
    TextInputLayout re_password_layout;
    @BindView(R.id.re_password)
    EditText re_password;
    @BindView(R.id.emailAddress)
    EditText emailAddress;
    @BindView(R.id.password)
    EditText txtPassword;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.userName)
    EditText username;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.logIn)
    TextView logIn;
    @BindView(R.id.pasalXIcon)
    ImageView appIcon;
    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    Boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        setUpToolbar();
        init();
    }

    private void setUpToolbar() {
        toolbar.setTitle("Sign Up");
        toolbar.setTitleMarginStart(150);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarIcons));
        setSupportActionBar(toolbar);
        getScreenSize();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    public void getScreenSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;

        // Toast.makeText(this, "" + String.valueOf(deviceWidth), Toast.LENGTH_SHORT).show();
//        one.setText(String.valueOf(deviceWidth));
//        two.setText(String.valueOf(deviceHeight));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up

        return false;
    }


    private void init() {
        if (BuildConfig.FLAVOR.equals(Flavor.MBSH))
            appIcon.setImageResource(R.drawable.ic_mbsh);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        Boolean userNameErr = false, passwordErr = false, emailErr = false, fNameErr = false, lNameErr = false, passwordMatchingErr = false;
        String user_name = username.getText().toString().trim();
        String email = emailAddress.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String rePassword = re_password.getText().toString().trim();
        String first_name = firstName.getText().toString().trim();
        String last_name = lastName.getText().toString().trim();
        boolean checkPassword = password.equalsIgnoreCase(rePassword);

        if (user_name.isEmpty()) {
            userNameErr = true;
            userNameLayput.setError("Please enter your user name*");
        } else userNameLayput.setError(null);

        if (first_name.isEmpty()) {
            fNameErr = true;
            fNameLayout.setError("Please enter your first name*");
        } else fNameLayout.setError(null);

        if (last_name.isEmpty()) {
            lNameErr = true;
            lNameLayout.setError("Please enter your last name*");
        } else lNameLayout.setError(null);
        if (email.isEmpty()) {
            emailErr = true;
            emailLayout.setError("This field is required *");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailErr = true;
            emailLayout.setError("Invalid email address.");
        } else {
            emailLayout.setError(null);
        }
        if (password.isEmpty()) {
            passwordErr = true;
            passwordLayout.setError("Please enter password*");
        } else passwordLayout.setError(null);

        if (!checkPassword) {
            passwordMatchingErr = true;
            re_password_layout.setError("Password doesn't match! ");
        } else re_password_layout.setError(null);

        WcCustomer customer = new WcCustomer();
        customer.username = user_name;
        customer.password = password;
        customer.first_name = first_name;
        customer.last_name = last_name;
        customer.email = email;
        WcCustomerResponse customerResponse = new WcCustomerResponse();
        customerResponse.customer = customer;
        if (!userNameErr && !passwordErr && !emailErr && !fNameErr && !lNameErr && !passwordMatchingErr) {
            if (Utilities.isConnectionAvailable(this))
                signUpUser(customerResponse);
            else
                Toast.makeText(this, "There is no internet connection. Please try again later!", Toast.LENGTH_SHORT).show();
        }
    }

    private void signUpUser(WcCustomerResponse customerResponse) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        WcCustomerAPI customerAPI = App.getWcRetrofit().create(WcCustomerAPI.class);
        retrofit2.Call<WcCustomerResponse> call = customerAPI.createCustomer(customerResponse);
        call.enqueue(new Callback<WcCustomerResponse>() {
            @Override
            public void onResponse(retrofit2.Call<WcCustomerResponse> call, Response<WcCustomerResponse> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
//                    Log.d("signup", "onResponse:mathi "+response.body());
                    App.db().putInt(Keys.CUSTOMER_ID, response.body().customer.id);
                    App.db().putObject(Keys.CUSTOMER, response.body().customer);
                    App.db().putBoolean(Keys.USER_LOGGED_IN, true);
                    HomeActivity.updateNavHeader();
                    Toast.makeText(SignUpActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        String error = response.errorBody().string();
                        if(error.contains("registration-error-email-exists"))
                            Toast.makeText(getApplicationContext(), " An account is already registered with your email address. Please log in.", Toast.LENGTH_SHORT).show();
                        else if(error.contains("registration-error-username-exists"))
                            Toast.makeText(getApplicationContext(), " An account is already registered with that username. Please choose another.", Toast.LENGTH_SHORT).show();
//                        if (error.contains("message")) {
//                            String splitted[] = error.split("message");
//                            String a = splitted[1];
//                            if (a.contains(":")) {
//                                String[] b = a.split(":");
//                                b[1].replaceAll("}","");
//                                Log.d("signup", "onResponse: splitted" + b[1]);
//                                    Toast.makeText(getApplicationContext(), "" + b[1], Toast.LENGTH_LONG).show();
//                            }
//                        }
                         else Toast.makeText(SignUpActivity.this, "Sorry, please try again.."+response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }

                }
            }

            @Override
            public void onFailure(retrofit2.Call<WcCustomerResponse> call, Throwable t) {
//                Log.d("signup", "onResponse: reached here");
                dialog.dismiss();
                Log.d("SignupFA", t.toString());
            }
        });
    }
}
