package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.HomeActivity;
import com.susankya.swadesibidhesi.fragments.user.ProductsFragment;
import com.susankya.swadesibidhesi.models.user.CategoryData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.susankya.swadesibidhesi.Generic.FragmentKeys.CAT_ID;
import static com.susankya.swadesibidhesi.Generic.FragmentKeys.CAT_NAME;

public class CategoriesImagesAdapter extends RecyclerView.Adapter<CategoriesImagesAdapter.RecyclerViewHolder> {
    private List<CategoryData> categoriesList = new ArrayList<>();
    public Context context;
    private OnItemClickListener listener;

    public CategoriesImagesAdapter(List<CategoryData> itemList, Context context, OnItemClickListener listener) {
        this.categoriesList = itemList;
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(CategoryData item);
    }

    @Override
    public CategoriesImagesAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.categories_image, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(layoutView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoriesImagesAdapter.RecyclerViewHolder holder, final int position) {
        holder.categoryImage.setImageURI(categoriesList.get(position).getCategory().getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(CAT_NAME, categoriesList.get(position).getCategory().getName());
                bundle.putInt(CAT_ID, categoriesList.get(position).getCategory().getId());
                ProductsFragment fragment = new ProductsFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = ((HomeActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category_image)
        SimpleDraweeView categoryImage;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final CategoryData item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
