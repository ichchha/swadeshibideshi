package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.models.user.ProductItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.RecyclerViewHolder> {
    private List<ProductItem> productList;
    public Context context;
    private final OnItemClickListener listener;
    private String currency = "Rs. ";

    public ProductsAdapter(List<ProductItem> itemList, Context context, OnItemClickListener listener) {
        this.productList = itemList;
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ProductItem item);
    }

    @Override
    public ProductsAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_item, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(layoutView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductsAdapter.RecyclerViewHolder holder, int position) {
        holder.itemTitle.setText(productList.get(position).getTitle());
        ProductItem productItem = productList.get(position);
        holder.itemPrice.setText(productItem.price.currency+productItem.price.excl_tax);
        holder.itemCategory.setText(productList.get(position).getCategories().get(0).split(">")[1].trim());
        holder.bind(productList.get(position), listener);
        if(productItem.getAvailability().getIs_available_to_buy()) holder.outOfStock.setVisibility(View.GONE);
        else holder.outOfStock.setVisibility(View.VISIBLE);
        if (productList.get(position).getImages().isEmpty()) {
            holder.draweeView.setImageURI("http://bit.ly/2FxkuxQ");
        } else {
            holder.draweeView.setImageURI(productList.get(position).getImages().get(0).getOriginal());
        }
        if(productItem.price.discount_percentage.equals("0"))holder.offerLayout.setVisibility(View.GONE);
        else{holder.offerLayout.setVisibility(View.VISIBLE);
//        String s[] = productItem.price.price_before_discount.split(".");
//            holder.originalPrice.setText(s[1]);
            holder.originalPrice.setText(productItem.price.price_before_discount);
        holder.discountOffer.setText(productItem.price.discount_percentage+"% Off");}
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

        public void bind(final ProductItem item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
