package com.susankya.swadesibidhesi.fragments.user;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.susankya.swadesibidhesi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ichha on 3/11/2018.
 */

public class ChangePasswordDialogFragment extends DialogFragment {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.changePassword)
    public Button changePassword;
    @BindView(R.id.old_pw_et)
    public EditText oldPassword;
    Boolean error;

    public static final String TAG = "ChangePasswordDialogFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
        super.onCreateView(inflater, parent, state);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_change_password, parent, false);
        ButterKnife.bind(this, view);
        setUpToolbar();
        init();
        return view;
    }

    private void init() {
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        String old_password = oldPassword.getText().toString().trim();
        if (old_password.isEmpty()) {
            // error = true;
            //oldPassword.setHint("Hwello");
            oldPassword.setError("Please enter your current password*");
        } else oldPassword.setError(null);
    }

    private void setUpToolbar() {
        toolbar.setTitle(R.string.change_password);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarIcons));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

    }


//    @Override
//    public boolean onSupportNavigateUp() {
//        finish(); // close this activity as oppose to navigating up
//        return false;
//    }
}