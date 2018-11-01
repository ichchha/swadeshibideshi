package com.susankya.swadesibidhesi.adapterdelegates;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.susankya.swadesibidhesi.ItemDecorations.VerticalSpaceItemDecoration;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.CartProductListAdapter;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.user.BasketLine;
import com.susankya.swadesibidhesi.models.user.CartItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartItemsAdapterDelegate extends AdapterDelegate<List<CartItem>>{

    private LayoutInflater inflater;
    Activity activity;
    private CartProductListAdapter adapter;

    public CartItemsAdapterDelegate(Activity activity) {
        this.activity = activity;
        inflater = activity.getLayoutInflater();
    }

    @Override protected boolean isForViewType(@NonNull List<CartItem> items, int position) {
        return items.get(position) instanceof BasketLine;
    }

    @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new SliderViewHolder(inflater.inflate(R.layout.layout_cart_recycler_view, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull List<CartItem> items, int position,
                                           @NonNull RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {
        SliderViewHolder vh = (SliderViewHolder) holder;
        BasketLine cartItems = (BasketLine) items.get(position);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        vh.total_items.setText(String.valueOf(cartItems.count));
        vh.total_price.setText(cartItems.totalPrice);
        vh.recyclerView.setLayoutManager(layoutManager);
        vh.recyclerView.setHasFixedSize(true);
        if(cartItems.cartResults.isEmpty()) Log.d("WcBasket","Empty");
        else Log.d("WcBasket",""+cartItems.cartResults.size());
       //Log.d("CartDelegate",cartItems.get);
        vh.recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(12));
        adapter = new CartProductListAdapter(cartItems.cartResults,false, activity, new CartProductListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WcProduct item) {
            }
        });
        vh.recyclerView.setAdapter(adapter);
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.horizontal_categories)RecyclerView recyclerView;
        @BindView(R.id.total_items)TextView total_items;
        @BindView(R.id.total_price)TextView total_price;

        public SliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
