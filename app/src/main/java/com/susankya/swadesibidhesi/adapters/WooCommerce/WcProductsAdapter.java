package com.susankya.swadesibidhesi.adapters.WooCommerce;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WcProductsAdapter extends RecyclerView.Adapter<WcProductsAdapter.RecyclerViewHolder> {
    private List<WcProduct> productList;
    public Context context;
    private final OnItemClickListener listener;
    private String currency = "Rs. ";

    public WcProductsAdapter(List<WcProduct> itemList, Context context, OnItemClickListener listener) {
        this.productList = itemList;
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(WcProduct item);
    }

    @Override
    public WcProductsAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_item, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(layoutView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(WcProductsAdapter.RecyclerViewHolder holder, int position) {
        WcProduct productItem = productList.get(position);
        holder.itemTitle.setText(productItem.title);

        if (productItem.price == "") holder.itemPrice.setVisibility(View.GONE);
        else holder.itemPrice.setText(currency + productItem.price);
//        holder.itemPrice.setText(currency + productItem.price);
        holder.priceLayout.setVisibility(View.VISIBLE);

        if (productItem.categories != null)
            holder.itemCategory.setText(Html.fromHtml(productItem.categories.get(0)));
        holder.bind(productList.get(position), listener);
        if (productItem.in_stock) holder.outOfStock.setVisibility(View.GONE);
        else holder.outOfStock.setVisibility(View.VISIBLE);
        if (productItem.images.isEmpty()) {
            holder.draweeView.setImageURI("http://bit.ly/2FxkuxQ");
        } else {
            holder.draweeView.setImageURI(productItem.images.get(0).src);
        }

        if (productItem.on_sale && productItem.price != "") {
            holder.discountLayout.setVisibility(View.VISIBLE);
            holder.originalPrice.setText(currency + productItem.regular_price);
            holder.originalPrice.setPaintFlags(holder.originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            String salePrice = productItem.sale_price;
            String regularPrice = productItem.regular_price;
            Log.d("test", salePrice + "rp" + regularPrice);
            if (productItem.regular_price == "" || productItem.sale_price == null) {
                holder.discountOffer.setText(currency + productItem.sale_price);
            } else {
                float rp = Float.valueOf(regularPrice);
                float sp = Float.valueOf(salePrice);
                float sub = rp - sp;
                float discountPercentage = (sub / rp) * 100;
                String discount = String.format("%,.0f", discountPercentage);
                holder.discountOffer.setText("( " + discount + "% OFF" + " )");
            }

        } else {
            holder.discountLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemName)
        TextView itemTitle;
        @BindView(R.id.priceLayout)
        RelativeLayout priceLayout;
        @BindView(R.id.itemCategory)
        TextView itemCategory;
        @BindView(R.id.itemPrice)
        TextView itemPrice;
        @BindView(R.id.originalPrice)
        TextView originalPrice;
        @BindView(R.id.discountOffer)
        TextView discountOffer;
        @BindView(R.id.discount_layout)
        RelativeLayout discountLayout;
        @BindView(R.id.offerLayout)
        LinearLayout offerLayout;
        @BindView(R.id.originalPriceLayout)
        RelativeLayout originalPriceLayout;
        @BindView(R.id.outOfStock)
        TextView outOfStock;
        @BindView(R.id.my_image_view)
        SimpleDraweeView draweeView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final WcProduct item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
