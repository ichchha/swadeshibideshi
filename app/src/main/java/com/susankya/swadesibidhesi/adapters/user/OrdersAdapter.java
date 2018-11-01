package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcOrderItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ichha on 2/18/2018.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {
    private List<WcOrderItem> orderList;
    private Context context;

    public OrdersAdapter(List<WcOrderItem> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order, null);
        return new OrderViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, int position) {
        final WcOrderItem orderResultItem = orderList.get(position);
        // getOrderLine(orderResultItem.shipping_address.id);

        holder.order_id.setText(orderResultItem.order_number);
        holder.billingAddress.setText(orderResultItem.billing_address.address_1 + ", " + orderResultItem.billing_address.city);
        holder.pdPrice.setText(orderResultItem.currency + " " + orderResultItem.total);
        holder.shippingMethod.setText(orderResultItem.shipping_methods);
        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderList.remove(orderResultItem);
                notifyDataSetChanged();
                //removing products list from tinydb
                ArrayList<WcOrderItem> orderLists = App.db().getOrderLists(Keys.PRODUCT_LIST_IN_WC_ORDERS);
                for (WcOrderItem product : orderLists) {
                    if (product.id == orderResultItem.id) orderLists.remove(product);
                    App.db().putListOrders(Keys.PRODUCT_LIST_IN_WC_ORDERS, orderLists);
                    Toast.makeText(context, "Order removed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        String orderStatus = orderResultItem.status;
        switch (orderStatus){
            case "pending":
            case "processing":
            case "completed":
                default:
        }
        holder.orderStatus.setText(orderResultItem.status);
        if (orderResultItem.line_items.isEmpty()) holder.viewProducts.setVisibility(View.GONE);
        else {
            holder.viewProducts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialDialog mDialog = new MaterialDialog.Builder(context)
                            .titleColor(Color.rgb(228, 0, 70))
                            .title(R.string.orderedItems)
                            .adapter(new orderedItemsPreviewAdapter(orderResultItem.line_items, context), new LinearLayoutManager(context))
                            .build();
                    mDialog.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_id)
        TextView order_id;
        @BindView(R.id.billingAddress)
        TextView billingAddress;
        @BindView(R.id.shippingMethod)
        TextView shippingMethod;
        @BindView(R.id.itemPrice)
        TextView pdPrice;
        @BindView(R.id.removeItem)
        ImageView removeItem;
        @BindView(R.id.txtOrderStatus)
        TextView orderStatus;
        @BindView(R.id.viewProducts)
        TextView viewProducts;

        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
