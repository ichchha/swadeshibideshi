package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
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

public class RecommendedSliderProductAdapter extends RecyclerView.Adapter<RecommendedSliderProductAdapter.RecyclerViewHolder> {
    private List<WcProduct> productList = new ArrayList<>();
    public Context context;
    private final OnItemClickListener listener;
    private Boolean fromVariation;
    private String currency = "Rs. ";

    public RecommendedSliderProductAdapter(Boolean fromVariation, List<WcProduct> itemList, Context context, OnItemClickListener listener) {
        this.productList = itemList;
        this.fromVariation = fromVariation;
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(WcProduct item);
    }

    @Override
    public RecommendedSliderProductAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_product_item, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(layoutView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecommendedSliderProductAdapter.RecyclerViewHolder holder, int position) {

        final int productID;
        final String productUrl;
        final WcProduct productItem = productList.get(position);

        if (fromVariation) setPhoto(productItem, holder.itemImage);
        holder.priceLayout.setVisibility(View.VISIBLE);
        if(productItem.price=="") holder.productPrice.setVisibility(View.GONE);
        else holder.productPrice.setText(currency+productItem.price);
//        holder.productPrice.setText(productItem.price);
        holder.itemName.setText(productItem.title);
        holder.itemCategory.setVisibility(View.GONE);
        if (productItem.images.isEmpty())
            holder.itemImage.setImageURI("http://bit.ly/2FxkuxQ");
        else {
            try {
                holder.itemImage.setImageURI(productItem.images.get(0).src);
            } catch (Exception e) {
                e.printStackTrace();
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        productID = productItem.id;
        productUrl = productItem.permalink;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                if (productItem.categories != null)
                    intent.putExtra("sub_cat", productItem.categories.get(0));
                intent.putExtra("product_id", productID);
                intent.putExtra("product_url", productUrl);
                intent.putExtra(Keys.WCPRODUCT, productItem);
                context.startActivity(intent);
                ((ProductDetailsActivity) context).finish();
            }
        });

        if (productItem.on_sale&& productItem.price != "") {
            holder.originalPrice.setVisibility(View.VISIBLE);
            holder.originalPrice.setText(currency + productItem.regular_price);
            holder.originalPrice.setPaintFlags(holder.originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.originalPrice.setVisibility(View.GONE);
        }
    }

    private void setPhoto(WcProduct productItem, SimpleDraweeView itemImage) {
        if (!productItem.image.isEmpty())
            itemImage.setImageURI(productItem.image.get(0).src);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemName)
        TextView itemName;
        @BindView(R.id.priceLayout)
        RelativeLayout priceLayout;
        @BindView(R.id.itemPrice)
        TextView productPrice;
        @BindView(R.id.originalPrice)
        TextView originalPrice;
        @BindView(R.id.off_txt)
        TextView discountOffer;
        @BindView(R.id.itemCategory)
        TextView itemCategory;
        @BindView(R.id.my_image_view)
        SimpleDraweeView itemImage;
        @BindView(R.id.outOfStock)
        TextView outOfStock;

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
