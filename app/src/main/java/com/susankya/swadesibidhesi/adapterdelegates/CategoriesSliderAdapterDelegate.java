package com.susankya.swadesibidhesi.adapterdelegates;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.CategoriesAdapter;
import com.susankya.swadesibidhesi.models.user.Category;
import com.susankya.swadesibidhesi.models.user.CategorySlide;
import com.susankya.swadesibidhesi.models.user.HomeItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesSliderAdapterDelegate extends AdapterDelegate<List<HomeItem>> {

    private LayoutInflater inflater;
    Activity activity;
    private CategoriesAdapter adapter;

    public CategoriesSliderAdapterDelegate(Activity activity) {
        this.activity = activity;
        inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<HomeItem> items, int position) {
        return items.get(position) instanceof CategorySlide;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new CatSliderViewHolder(inflater.inflate(R.layout.layout_horizontal_scrollable, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<HomeItem> items, int position,
                                 @NonNull RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {
        final CatSliderViewHolder vh = (CatSliderViewHolder) holder;
        CategorySlide categorySlide = (CategorySlide) items.get(position);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        vh.recyclerView.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categorySlide.getCategoryDataList(), activity, new CategoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category item) {
                Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show();
                Log.d("category","clicked");
            }
        });
        //Toast.makeText(activity, ""+categorySlide.getCatLevelOneList().get(0).getName(), Toast.LENGTH_SHORT).show();
        vh.recyclerView.setAdapter(adapter);
        vh.recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "clicked"+vh.recyclerView.getChildAt(0), Toast.LENGTH_SHORT).show();
                Log.d("categorySlide","clicked");
            }
        });
    }

    static class CatSliderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.horizontal_categories)
        RecyclerView recyclerView;

        public CatSliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
