package com.susankya.swadesibidhesi.activities.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.fragments.user.ChangePasswordDialogFragment;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class EditProfileActivity extends AppCompatActivity {
    @BindView(R.id.changePassword)
    Button changePassword;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.editCoverPic)
    ImageView editCoverPic;
    @BindView(R.id.editProfilePic)
    ImageView editProfilePic;
    @BindView(R.id.coverPic)
    ImageView coverPic;
    @BindView(R.id.profilePic)
    ImageView profilePic;
    @BindView(R.id.fullName)
    EditText fullName;
    @BindView(R.id.emailAddress)
    EditText emailAddress;
    @BindView(R.id.password)
    EditText txtPassword;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.userName)
    EditText username;
    private int changed = 0;
    private List<Uri> mSelected = new ArrayList<>();
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 125;
    public static final int CHOOSE_PROFILE_PIC = 23;
    public static final int CHOOSE_COVER_PIC = 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_user_profile);
        ButterKnife.bind(this);
        init();
        setUpToolbar();
    }

    private void init() {

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordDialogFragment dialog = new ChangePasswordDialogFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                dialog.show(ft, ChangePasswordDialogFragment.TAG);
            }
        });

        editCoverPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto(CHOOSE_COVER_PIC);
            }
        });

        editProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto(CHOOSE_PROFILE_PIC);
            }
        });
    }

    @AfterPermissionGranted(REQUEST_CODE_ASK_PERMISSIONS)
    private void getPhoto(int requestCode) {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Matisse.from(EditProfileActivity.this)
                    .choose(MimeType.allOf())
                    .theme(R.style.Matisse_Dracula)
                    .countable(false)
                    .maxSelectable(1)
                    .gridExpectedSize(this.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new PicassoEngine())
                    .forResult(requestCode);
        } else {
            EasyPermissions.requestPermissions(this, "Before you can select photos, please grant Permissions. Click OK to proceed.",
                    REQUEST_CODE_ASK_PERMISSIONS, perms);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_COVER_PIC && resultCode == RESULT_OK) {
            showPhoto(data,coverPic);
        }
        if (requestCode == CHOOSE_PROFILE_PIC && resultCode == RESULT_OK) {
           showPhoto(data,profilePic);
        }
    }

    private void showPhoto(Intent data, ImageView imageView) {
        if (changed > 1)
            mSelected.set(0, Matisse.obtainResult(data).get(0));
        else
            mSelected = Matisse.obtainResult(data);
        Picasso.with(EditProfileActivity.this).load(mSelected.get(0)).fit().centerInside().into(imageView);
    }

    private void setUpToolbar() {
        toolbar.setTitle("Edit Profile");
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
