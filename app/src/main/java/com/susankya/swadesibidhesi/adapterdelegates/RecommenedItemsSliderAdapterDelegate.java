package com.susankya.swadesibidhesi.adapterdelegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.susankya.swadesibidhesi.ItemDecorations.HorizontalSpaceItemDecoration;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.RecommendedSliderProductAdapter;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductSlide;
import com.susankya.swadesibidhesi.models.user.ProductDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommenedItemsSliderAdapterDelegate extends AdapterDelegate<List<ProductDetails>>{

    private LayoutInflater inflater;
    Context activity;
    private RecommendedSliderProductAdapter adapter;

    public RecommenedItemsSliderAdapterDelegate(Context activity) {
//        this.fromVariation = fromVariation;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @Override protected boolean isForViewType(@NonNull List<ProductDetails> items, int position) {
        return items.get(position) instanceof WcProductSlide;
    }

    @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new SliderViewHolder(inflater.inflate(R.layout.layout_horizontal_scrollable_grid, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull List<ProductDetails> items, int position,
                                           @NonNull RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {
        SliderViewHolder vh = (SliderViewHolder) holder;
        WcProductSlide productSlide = (WcProductSlide) items.get(position);
        if(productSlide.sliderList.isEmpty()) vh.sliderTitle.setVisibility(View.GONE);
        vh.sliderTitle.setText(productSlide.sliderTitle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        vh.recyclerView.setLayoutManager(layoutManager);
        vh.recyclerView.setHasFixedSize(true);
        vh.recyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(12));
        adapter = new RecommendedSliderProductAdapter(productSlide.fromVariation,productSlide.sliderList, activity, new RecommendedSliderProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WcProduct item) {
            }
        });
        vh.recyclerView.setAdapter(adapter);
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.slider_title)TextView sliderTitle;
        @BindView(R.id.horizontal_gridView)RecyclerView recyclerView;

        public SliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
