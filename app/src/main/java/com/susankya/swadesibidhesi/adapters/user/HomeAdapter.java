package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;

import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;
import com.susankya.swadesibidhesi.adapterdelegates.SliderAdapterDelegate;
import com.susankya.swadesibidhesi.models.user.HomeItem;

import java.util.List;

public class HomeAdapter extends ListDelegationAdapter<List<HomeItem>> {
    public HomeAdapter(Context activity, List<HomeItem> items) {
        // DelegatesManager is a protected Field in ListDelegationAdapter
        delegatesManager
//                .addDelegate(new BannerAdapterDelegate(activity))
                .addDelegate(new SliderAdapterDelegate(activity));

        // Set the items from super class.
        setItems(items);
    }
}

