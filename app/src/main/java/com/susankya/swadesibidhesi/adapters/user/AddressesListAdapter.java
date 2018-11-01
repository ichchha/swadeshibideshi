package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.CheckOutActivity;
import com.susankya.swadesibidhesi.fragments.user.FinalCheckOutPageFragment;
import com.susankya.swadesibidhesi.models.WooCommerce.WcBilling;
import com.susankya.swadesibidhesi.models.user.Basket;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.susankya.swadesibidhesi.Generic.FragmentKeys.WC_BILLING;
import static com.susankya.swadesibidhesi.Generic.Keys.FROM_QUOTE;

/**
 * Created by Ichha on 2/16/2018.
 */

public class AddressesListAdapter extends RecyclerView.Adapter<AddressesListAdapter.AddressViewHolder> {
    private List<WcBilling> notificationList;
    private Basket basket;
    private Context context;
    private Boolean fromQuote;

    public AddressesListAdapter(boolean fromQuote,List<WcBilling> notificationList, Context context) {
        this.notificationList = notificationList;
        this.fromQuote = fromQuote;
        this.context = context;
    }

    public AddressesListAdapter(List<WcBilling> notificationList, Basket basket, Context context) {
        this.notificationList = notificationList;
        this.basket = basket;
        this.context = context;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipping_detail_item, null);
        return new AddressViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int position) {
        final WcBilling shippingAddress = notificationList.get(position);
        holder.userName.setText(shippingAddress.first_name + " " + shippingAddress.last_name);
        holder.firstLineAddress.setText(shippingAddress.address_1);
        holder.cityName.setText(shippingAddress.city);
        holder.phoneNo.setText(shippingAddress.phone);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle details = new Bundle();
                details.putParcelable(WC_BILLING, shippingAddress);
                details.putBoolean(FROM_QUOTE,fromQuote);
                FinalCheckOutPageFragment fragment = new FinalCheckOutPageFragment();
                fragment.setArguments(details);
                ((CheckOutActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.checkout_container, fragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.userName)
        TextView userName;
        @BindView(R.id.firstLineAddress)
        TextView firstLineAddress;
        @BindView(R.id.cityName)
        TextView cityName;
        @BindView(R.id.phoneNo)
        TextView phoneNo;
        @BindView(R.id.useAddress)
        TextView useAddress;

        public AddressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
