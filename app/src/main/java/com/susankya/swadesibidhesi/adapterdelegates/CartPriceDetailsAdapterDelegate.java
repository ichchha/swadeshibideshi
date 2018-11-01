package com.susankya.swadesibidhesi.adapterdelegates;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.models.user.Cart;
import com.susankya.swadesibidhesi.models.user.CartItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartPriceDetailsAdapterDelegate extends AdapterDelegate<List<CartItem>> {

    private LayoutInflater inflater;
    Activity activity;

    public CartPriceDetailsAdapterDelegate(Activity activity) {
        inflater = activity.getLayoutInflater();
        this.activity = activity;
    }

    @Override
    protected boolean isForViewType(@NonNull List<CartItem> items, int position) {
        return items.get(position) instanceof Cart;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new PriceViewHolder(inflater.inflate(R.layout.layout_price_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<CartItem> items, int position,
                                 @NonNull RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {
        PriceViewHolder vh = (PriceViewHolder) holder;
        Cart priceDetails = (Cart) items.get(position);
        vh.priceExc.setText(priceDetails.getTotal_excl_tax());
        vh.priceExcTaxExcDis.setText(priceDetails.getTotal_excl_tax_excl_discounts());
        vh.priceIncTaxExcDis.setText(priceDetails.getTotal_incl_tax_excl_discounts());
        vh.priceInc.setText(priceDetails.getTotal_incl_tax());
        vh.totalPayable.setText(priceDetails.getTotal_incl_tax_excl_discounts());
    }

    static class PriceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.price_ex)
        TextView priceExc;
        @BindView(R.id.price_ex_tax_ex_dis)
        TextView priceExcTaxExcDis;
        @BindView(R.id.price_inc_tax_ex_dis)
        TextView priceIncTaxExcDis;
        @BindView(R.id.price_inc_txt)
        TextView priceInc;
        @BindView(R.id.total_payable)
        TextView totalPayable;

        public PriceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
