package com.susankya.swadesibidhesi.adapterdelegates;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.susankya.swadesibidhesi.ItemDecorations.VerticalSpaceItemDecoration;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.CategoriesImagesAdapter;
import com.susankya.swadesibidhesi.models.user.CategoryData;
import com.susankya.swadesibidhesi.models.user.CategoryImageSlide;
import com.susankya.swadesibidhesi.models.user.HomeItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesImagesAdapterDelegate extends AdapterDelegate<List<HomeItem>> {

    private LayoutInflater inflater;
    Activity activity;
    private CategoriesImagesAdapter adapter;

    public CategoriesImagesAdapterDelegate(Activity activity) {
        this.activity = activity;
        inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<HomeItem> items, int position) {
        return items.get(position) instanceof CategoryImageSlide;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new CatImageSliderViewHolder(inflater.inflate(R.layout.layout_horizontal_scrollable_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<HomeItem> items, int position,
                                 @NonNull RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {
        final CatImageSliderViewHolder vh = (CatImageSliderViewHolder) holder;
        CategoryImageSlide categorySlide = (CategoryImageSlide) items.get(position);
        vh.sliderTitle.setText("Featured Categories");
        vh.recyclerView.setLayoutManager(new GridLayoutManager(activity,3));
        vh.recyclerView.setHasFixedSize(true);
        adapter = new CategoriesImagesAdapter(categorySlide.getCategoryDataList(), activity, new CategoriesImagesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CategoryData item) {
            }
        });
        vh.recyclerView.setAdapter(adapter);
        vh.recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));
    }

    static class CatImageSliderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.slider_title)TextView sliderTitle;
        @BindView(R.id.horizontal_gridView)RecyclerView recyclerView;

        public CatImageSliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
