package com.susankya.swadesibidhesi.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.susankya.swadesibidhesi.Generic.FragmentKeys;
import com.susankya.swadesibidhesi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnterEmailActivity extends AppCompatActivity {
    @BindView(R.id.email_address)
    EditText emailAddress;
    @BindView(R.id.continur_btn)
    Button btnContinue;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_enter_email);
        ButterKnife.bind(this);
        setUpToolbar();
        init();
    }

    private void setUpToolbar() {
        toolbar.setTitle("Guest Checkout");
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarIcons));
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

    }
    private void init() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        Boolean emailErr = false;
        String email = emailAddress.getText().toString().trim();
        // email validation
        if (email.isEmpty()) {
            emailErr = true;
            emailAddress.setError("This field is required *");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailErr = true;
            emailAddress.setError("Invalid email address.");
        } else {
            emailAddress.setError(null);
        }
        if(!emailErr)
        {
            Bundle details = new Bundle();
            details.putString(FragmentKeys.BASKET_LINE,getIntent().getExtras().getString(FragmentKeys.BASKET_LINE));
            details.putString(FragmentKeys.BASKET_URL,getIntent().getExtras().getString(FragmentKeys.BASKET_URL));
            details.putString(FragmentKeys.BASKET_TOTAL, getIntent().getExtras().getString(FragmentKeys.BASKET_TOTAL));
            details.putString(FragmentKeys.CURRENCY, getIntent().getExtras().getString(FragmentKeys.CURRENCY));
            details.putString(FragmentKeys.GUEST_EMAIL,email);
            Intent intent = new Intent(EnterEmailActivity.this, CheckOutActivity.class);
            intent.putExtras(details);
            startActivity(intent);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up
        return false;
    }
}
