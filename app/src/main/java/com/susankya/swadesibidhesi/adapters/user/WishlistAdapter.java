package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.ProductDetailsActivity;
import com.susankya.swadesibidhesi.models.user.Wishlist;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ichha on 2/16/2018.
 */

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {
    private List<Wishlist> wishlists;
    private Context context;

    public WishlistAdapter(List<Wishlist> wishlists, Context context) {
        this.wishlists = wishlists;
        this.context = context;
    }

    @Override
    public WishlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_product_item, null);
        return new WishlistViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(WishlistViewHolder holder, int position) {
        final Wishlist wishlist = wishlists.get(position);
        holder.itemName.setText(wishlist.getProductItem().getTitle());
        if (wishlist.getProductItem().getImages().size() > 0)
            holder.itemImage.setImageURI(wishlist.getProductItem().getImages().get(0).getOriginal());
        else holder.itemImage.setImageURI("http://bit.ly/2FxkuxQ");
        holder.itemPrice.setText(wishlist.getProductItem().getPrice().getExcl_tax());
        holder.itemCategory.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("product_id", wishlist.getProductItem().getId());
                intent.putExtra("product_url", wishlist.getProductItem().getUrl());
                intent.putExtra("product_name", wishlist.getProductItem().getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishlists.size();
    }

    public static class WishlistViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.my_image_view)
        SimpleDraweeView itemImage;

        public WishlistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
