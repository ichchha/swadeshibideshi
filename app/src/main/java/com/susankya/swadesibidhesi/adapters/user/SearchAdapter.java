package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.ProductDetailsActivity;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.RecyclerViewHolder> {
    private List<WcProduct> productList;

    public Context context;
    private final OnItemClickListener listener;

    public SearchAdapter(List<WcProduct> itemList, Context context, OnItemClickListener listener) {
        this.productList = itemList;
        this.context = context;
        this.listener = listener;

    }

    public interface OnItemClickListener {
        void onItemClick(WcProduct item);
    }

    @Override
    public SearchAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_item, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(layoutView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.RecyclerViewHolder holder, int position) {
        final WcProduct searchResult = productList.get(position);
        holder.itemTitle.setText(productList.get(position).title);
        holder.itemPrice.setText(searchResult.price);
        holder.originalPrice.setText(searchResult.regular_price);
        holder.discountOffer.setText(searchResult.sale_price);
        if (productList.get(holder.getAdapterPosition()).images != null) {
            holder.draweeView.setImageURI(productList.get(holder.getAdapterPosition()).images.get(0).src);
        } else {
            holder.draweeView.setImageResource(R.drawable.product_placeholder);
//            getProduct(searchResult.id, holder.draweeView, holder.offerLayout, holder.discountOffer, holder.originalPrice, holder.getAdapterPosition());

        }
        holder.itemPrice.setText("Rs. " + String.valueOf(productList.get(position).price));
        if (searchResult.categories != null) {
            if (searchResult.categories.size() > 0) {
                holder.itemCategory.setVisibility(View.VISIBLE);
                String category =searchResult.categories.get(0);
                    holder.itemCategory.setText(Html.fromHtml(category));
            }
        } else holder.itemCategory.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                if(searchResult.categories!=null) intent.putExtra("sub_cat", searchResult.categories.get(0));
                intent.putExtra("product_id", searchResult.id);
                intent.putExtra("product_url", searchResult.permalink);
                intent.putExtra("product_name", searchResult.title);
                intent.putExtra(Keys.WCPRODUCT,searchResult);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemName)
        TextView itemTitle;
        @BindView(R.id.itemCategory)
        TextView itemCategory;
        @BindView(R.id.itemPrice)
        TextView itemPrice;
        @BindView(R.id.originalPrice)
        TextView originalPrice;
        @BindView(R.id.discountOffer)
        TextView discountOffer;
        @BindView(R.id.originalPriceLayout)
        RelativeLayout originalPriceLayout;
        @BindView(R.id.offerLayout)
        LinearLayout offerLayout;
        @BindView(R.id.discount_layout)
        RelativeLayout discountLayout;
        @BindView(R.id.itemImage)
        RelativeLayout itemImageLayout;
        @BindView(R.id.bottomLayout)
        LinearLayout bottomLayout;
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
