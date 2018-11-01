package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susankya.swadesibidhesi.Generic.FragmentKeys;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ichha on 2/16/2018.
 */

public class finalItemsPreviewAdapter extends RecyclerView.Adapter<finalItemsPreviewAdapter.PreviewItemViewHolder> {
    private List<WcProduct> notificationList;
    private Context context;
    private float totalPrice = 0, payable;
    private TextView orderTotal, payableTotal, deliveryCharge;
//    private Float delivery = Float.valueOf(App.db().getString(Keys.COST));
    private Boolean shippingChargeEnabled = App.db().getBoolean(Keys.SHIPPING_COST_ENABLED);
    private Boolean shippingChargeFetched = App.db().getBoolean(Keys.COST_FETCHED);

    public finalItemsPreviewAdapter(TextView deliveryCharge, TextView order_total, TextView total_payable, List<WcProduct> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
        this.orderTotal = order_total;
        this.payableTotal = total_payable;
        this.deliveryCharge = deliveryCharge;
    }

    @Override
    public PreviewItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.preview_product_item, null);
        return new PreviewItemViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(PreviewItemViewHolder holder, int position) {
        float productPrice;
        Log.d("totalPrice", notificationList.size() + "");
        //Log.d("check",notificationList.get(position).getProduct_title()+notificationList.get(position).getQuantity());
        WcProduct product = notificationList.get(position);
        if (product.price == "") productPrice = 0;
        else productPrice = Float.valueOf(product.price) * product.quantity;
        totalPrice = totalPrice + productPrice;

        holder.itemName.setText(product.title);
        holder.itemQty.setText(String.valueOf(product.quantity));

        if (notificationList.isEmpty()) {
        } else {
            Log.d("totalPrice", position + "");
            if (position == notificationList.size() - 1)
                Log.d("last", "onBindViewHolder: " + position + String.valueOf(totalPrice));
        }

        orderTotal.setText("Rs. " + String.valueOf(totalPrice));
        if (shippingChargeEnabled && shippingChargeFetched) {
            payable = totalPrice + Float.valueOf(App.db().getString(Keys.COST));
            deliveryCharge.setText("Rs. " + App.db().getString(Keys.COST));
            payableTotal.setText("Rs. " + String.valueOf(payable));
            App.db().putFloat(FragmentKeys.WC_ORDER_TOTAL, payable);
        } else {
            payableTotal.setText("Rs. " + String.valueOf(totalPrice));
            App.db().putFloat(FragmentKeys.WC_ORDER_TOTAL, totalPrice);
        }
//        total_payable.setText(String.valueOf(App.db().getInt(FragmentKeys.WC_ORDER_TOTAL)));
        if (!product.images.isEmpty()) holder.itemImage.setImageURI(product.images.get(0).src);
        else {
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class PreviewItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemName)
        TextView itemName;
        @BindView(R.id.itemQty)
        TextView itemQty;
        @BindView(R.id.itemImage)
        SimpleDraweeView itemImage;

        public PreviewItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
