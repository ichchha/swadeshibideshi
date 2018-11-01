package com.susankya.swadesibidhesi.fragments.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.NotificationsAdapter;
import com.susankya.swadesibidhesi.models.user.Notification;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WishlistFragment extends Fragment {

    @BindView(R.id.wishlistRV)
    RecyclerView recyclerView;
    @BindView(R.id.progressBarLayout)
    View progressLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressTV)
    TextView progressTextView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private LinearLayoutManager layoutManager;

    public WishlistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        ButterKnife.bind(this, view);
        setUpToolbar();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new NotificationsAdapter(getNotificationItems(), getActivity()));
        return view;
    }

    private void setUpToolbar() {
        toolbar.setTitle(getResources().getString(R.string.fragment_wishlist));
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarIcons));
    }


    private List<Notification> getNotificationItems() {
        List<Notification> list = new ArrayList<>();
        list.add(new Notification("2 hours ago", "Flat 300 OFF just for You!", "Enjoy Free delivery | EMI | Easy Exchange", R.drawable.sale1));
        list.add(new Notification("2 hours ago", "Flat 300 OFF just for You!", "Enjoy Free delivery | EMI | Easy Exchange", R.drawable.sale3));
        list.add(new Notification("2 hours ago", "Flat 300 OFF just for You!", "Enjoy Free delivery | EMI | Easy Exchange", R.drawable.sale1));
        return list;
    }
}
