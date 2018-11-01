package com.susankya.swadesibidhesi.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.Generic.Utilities;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.ProfileAdapter;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.user.Profile;
import com.susankya.swadesibidhesi.models.user.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileActivity extends AppCompatActivity {
    @BindView(R.id.profileRV)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnLogout)
    IconTextView btnLogout;
    @BindView(R.id.userName)
    TextView userName;
    private LinearLayoutManager layoutManager;
    private DividerItemDecoration mDividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setUpToolbar();
        init();
        setUpProfile();

    }

    private void setUpProfile() {
        if(Utilities.isLoggedIn(UserProfileActivity.this)){
            userName.setText(App.db().getObject(Keys.USER_LOGIN, User.class).username);
        }
    }

    private void setUpToolbar() {
        toolbar.setTitle("Profile");
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

    private void init() {

        if(Utilities.isLoggedIn(UserProfileActivity.this)) btnLogout.setVisibility(View.VISIBLE);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utilities.isConnectionAvailable(UserProfileActivity.this)){
                App.db().putBoolean(Keys.USER_LOGGED_IN,false);
                Toast.makeText(UserProfileActivity.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserProfileActivity.this,HomeActivity.class));}
                else Toast.makeText(UserProfileActivity.this, "There is no internet connection available. Please try again later!", Toast.LENGTH_SHORT).show();
            }
        });

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ProfileAdapter(getDetails(), this));
        mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);
    }

    private List<Profile> getDetails() {
        List<Profile> list = new ArrayList<>();
        list.add(new Profile("Orders", "Check your order status", R.drawable.ic_orders_24dp));
        list.add(new Profile("Collections & Wishlist", "All your curated product collections", R.drawable.ic_border_all_black_24dp));
//        list.add(new Profile("Liked Products", "All products liked by you", R.drawable.ic_favorite_black_24dp));
//        list.add(new Profile("Profile Details", "Change your profile details & password", R.drawable.ic_person_details_24dp));
        list.add(new Profile("Profile Details", "View your profile details", R.drawable.ic_person_details_24dp));
//        list.add(new Profile("Settings", "Manage notifications & app settings", R.drawable.ic_phonelink_setup_black_24dp));
        return list;
    }

}
