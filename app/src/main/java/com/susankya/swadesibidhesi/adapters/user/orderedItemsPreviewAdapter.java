package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcProductsAPI;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcLineItem;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ichha on 2/16/2018.
 */

public class orderedItemsPreviewAdapter extends RecyclerView.Adapter<orderedItemsPreviewAdapter.PreviewItemViewHolder> {
    private List<WcLineItem> orderLineResultList = new ArrayList<>();
    private Context context;
    int orderLineID;
    private Gson mGson;

    public orderedItemsPreviewAdapter(Context context, int orderLineID) {
        this.context = context;
        this.orderLineID = orderLineID;
    }

    public orderedItemsPreviewAdapter(List<WcLineItem> notificationList, Context context) {
        this.orderLineResultList = notificationList;
        this.context = context;
    }

    @Override
    public PreviewItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.preview_order_item, null);
        return new PreviewItemViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(PreviewItemViewHolder holder, int position) {
        WcLineItem orderLineResult = orderLineResultList.get(position);
        getWcProduct(orderLineResult.product_id,holder.itemImage);
        holder.itemName.setText(orderLineResult.name);
        holder.itemQty.setText(String.valueOf(orderLineResult.quantity));
    }

    // get product detail from api using product id
    private void getWcProduct(int product_id, final SimpleDraweeView itemImage) {
        WcProductsAPI productsAPI = App.getWcRetrofit().create(WcProductsAPI.class);
        productsAPI.getProductDetail(product_id).enqueue(new Callback<WcProductResponse>() {
            @Override
            public void onResponse(Call<WcProductResponse> call, Response<WcProductResponse> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    WcProduct product = response.body().product;
                    if(!product.images.isEmpty())
                    itemImage.setImageURI(product.images.get(0).src);

                } else {
                    try {
                        Log.d("retrofit", "errorbody" + response.errorBody().string());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<WcProductResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderLineResultList.size();
    }

    public static class PreviewItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemName)
        TextView itemName;
        @BindView(R.id.qtyLayout)
        LinearLayout qtyLayout;
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
