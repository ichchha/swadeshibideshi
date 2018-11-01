package com.susankya.swadesibidhesi.adapters.user;

import android.app.Activity;

import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;
import com.susankya.swadesibidhesi.adapterdelegates.CartItemsAdapterDelegate;
import com.susankya.swadesibidhesi.models.user.CartItem;

import java.util.List;

public class AddToCartAdapter extends ListDelegationAdapter<List<CartItem>> {
    public AddToCartAdapter(Activity activity, List<CartItem> items) {
        delegatesManager
                .addDelegate(new CartItemsAdapterDelegate(activity));
               // .addDelegate(new CartPriceDetailsAdapterDelegate(activity));

        // Set the items from super class.
        setItems(items);
    }
}

