package com.susankya.swadesibidhesi.fragments.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susankya.swadesibidhesi.ItemDecorations.VerticalSpaceItemDecoration;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.NotificationsAdapter;
import com.susankya.swadesibidhesi.models.user.Notification;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsFragment extends Fragment {
    @BindView(R.id.notificationsRV)
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private static final int VERTICAL_ITEM_SPACE = 8;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new NotificationsAdapter(getNotificationItems(), getActivity()));
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        return view;
    }

    private List<Notification> getNotificationItems() {
        List<Notification> list = new ArrayList<>();
        list.add(new Notification("2 hours ago", "Flat 300 OFF just for You!", "Enjoy Free delivery | EMI | Easy Exchange", R.drawable.sale1));
        list.add(new Notification("2 hours ago", "Flat 300 OFF just for You!", "Enjoy Free delivery | EMI | Easy Exchange", R.drawable.sale3));
        list.add(new Notification("2 hours ago", "Flat 300 OFF just for You!", "Enjoy Free delivery | EMI | Easy Exchange", R.drawable.sale1));
        return list;
    }
}
