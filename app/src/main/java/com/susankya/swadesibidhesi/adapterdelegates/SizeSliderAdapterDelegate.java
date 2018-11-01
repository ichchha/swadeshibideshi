package com.susankya.swadesibidhesi.adapterdelegates;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.susankya.swadesibidhesi.ItemDecorations.HorizontalSpaceItemDecoration;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.ProductVariationAdapter;
import com.susankya.swadesibidhesi.models.user.ProductDetails;
import com.susankya.swadesibidhesi.models.user.SizeSlide;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ichha on 2/4/2018.
 */

public class SizeSliderAdapterDelegate extends AdapterDelegate<List<ProductDetails>> {

    private LayoutInflater inflater;
    Activity activity;
    private ProductVariationAdapter adapter;

    public SizeSliderAdapterDelegate(Activity activity) {
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<ProductDetails> items, int position) {
        return items.get(position) instanceof SizeSlide;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new SizeSliderViewHolder(inflater.inflate(R.layout.horizontal_scrollable_size, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<ProductDetails> items, final int position,
                                 @NonNull RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {
        final SizeSliderViewHolder vh = (SizeSliderViewHolder) holder;
        SizeSlide categorySlide = (SizeSlide) items.get(position);
        final int size = categorySlide.getCategpriesList().size();
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        vh.recyclerView.setLayoutManager(layoutManager);
        vh.recyclerView.setHasFixedSize(true);
//        vh.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(activity, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                try {
//                   // adapter.setSelected(position);
//                    Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }));
//        vh.recyclerView.setAdapter(new ProductVariationAdapter(categorySlide.getCategpriesList(), activity));
        vh.recyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(14));
    }

    static class SizeSliderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.horizontal_categories)
        RecyclerView recyclerView;
        @BindView(R.id.itemSizeLayout)
        FlowLayout itemSizeLayout;

        public SizeSliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
