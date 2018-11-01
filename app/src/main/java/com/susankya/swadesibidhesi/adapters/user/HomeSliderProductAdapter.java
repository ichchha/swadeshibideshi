package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.ProductDetailsActivity;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeSliderProductAdapter extends RecyclerView.Adapter<HomeSliderProductAdapter.RecyclerViewHolder> {
    private List<WcProduct> productList = new ArrayList<>();
    public Context context;
    private final OnItemClickListener listener;
    private String currency = "Rs. ";

    public HomeSliderProductAdapter(List<WcProduct> itemList, Context context, OnItemClickListener listener) {
        this.productList = itemList;
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(WcProduct item);
    }

    @Override
    public HomeSliderProductAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_product_item, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(layoutView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(HomeSliderProductAdapter.RecyclerViewHolder holder, int position) {
        final WcProduct productItem = productList.get(position);
        holder.priceLayout.setVisibility(View.VISIBLE);

        if (!productItem.categories.isEmpty())
            holder.itemCategory.setText(productItem.categories.get(0));
        holder.itemName.setText(Html.fromHtml(productItem.title));
        if (productItem.images.size() > 0)
            holder.itemImage.setImageURI(productItem.images.get(0).src);
        else holder.itemImage.setImageURI("http://bit.ly/2FxkuxQ");

        if (productItem.price == "") holder.itemPrice.setVisibility(View.GONE);
        else holder.itemPrice.setText(currency + productItem.price);

        holder.itemCategory.setVisibility(View.GONE);

        if (productItem.in_stock) holder.outOfStock.setVisibility(View.GONE);
        else holder.outOfStock.setVisibility(View.VISIBLE);
        Log.d("homeSlider", "productID" + productItem.id);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, ""+productItem.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("product_id", productItem.id);
                intent.putExtra("product_name", productItem.title);
                intent.putExtra(Keys.WCPRODUCT, productItem);
                context.startActivity(intent);
            }
        });

        if (productItem.on_sale && productItem.price != "") {
            holder.originalPrice.setVisibility(View.VISIBLE);
            holder.originalPrice.setText(currency + productItem.regular_price);
            holder.originalPrice.setPaintFlags(holder.originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.originalPrice.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.priceLayout)
        RelativeLayout priceLayout;
        @BindView(R.id.itemName)
        TextView itemName;
        @BindView(R.id.itemPrice)
        TextView itemPrice;
        @BindView(R.id.originalPrice)
        TextView originalPrice;
        @BindView(R.id.off_txt)
        TextView discountOffer;
        @BindView(R.id.itemCategory)
        TextView itemCategory;
        @BindView(R.id.outOfStock)
        TextView outOfStock;
        @BindView(R.id.my_image_view)
        SimpleDraweeView itemImage;

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
