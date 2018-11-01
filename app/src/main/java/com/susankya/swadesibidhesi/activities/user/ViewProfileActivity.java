package com.susankya.swadesibidhesi.activities.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.user.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewProfileActivity extends AppCompatActivity {
    @BindView(R.id.email)
    EditText emailAddress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.saveDetails)
    Button btnSave;
    @BindView(R.id.mobile)
    EditText mobileNo;
    @BindView(R.id.fName)
    EditText firstName;
    @BindView(R.id.lName)
    EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        ButterKnife.bind(this);
        init();
        setUpToolbar();
    }
    private void init() {
        emailAddress.setText(App.db().getObject(Keys.USER_LOGIN, User.class).email);
        firstName.setText(App.db().getObject(Keys.USER_LOGIN, User.class).first_name);
        lastName.setText(App.db().getObject(Keys.USER_LOGIN, User.class).last_name);
        mobileNo.setText(App.db().getObject(Keys.USER_LOGIN, User.class).mobile_no);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewProfileActivity.this, "Your profile details have been saved!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setUpToolbar() {
        toolbar.setTitle("My Profile");
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarIcons));
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up
        return false;
    }
}
