package com.susankya.swadesibidhesi.adapters.WooCommerce;

import android.app.Activity;

import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;
import com.susankya.swadesibidhesi.adapterdelegates.ProductDescAdapterDelegate;
import com.susankya.swadesibidhesi.adapterdelegates.ProductPhotosAdapterDelegate;
import com.susankya.swadesibidhesi.adapterdelegates.RecommenedItemsSliderAdapterDelegate;
import com.susankya.swadesibidhesi.models.user.ProductDetails;

import java.util.List;

public class ProductDetailsAdapter extends ListDelegationAdapter<List<ProductDetails>> {
    public ProductDetailsAdapter(Activity activity, List<ProductDetails> items) {

        // DelegatesManager is a protected Field in ListDelegationAdapter
        delegatesManager
                .addDelegate(new ProductPhotosAdapterDelegate(activity))
                .addDelegate(new ProductDescAdapterDelegate(activity))
                //.addDelegate(new SizeSliderAdapterDelegate(activity));
                .addDelegate(new RecommenedItemsSliderAdapterDelegate(activity));

        // Set the items from super class.
        setItems(items);
    }
}

