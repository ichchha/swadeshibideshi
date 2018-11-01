package com.susankya.swadesibidhesi.adapterdelegates;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.susankya.swadesibidhesi.ItemDecorations.HorizontalSpaceItemDecoration;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.HomeActivity;
import com.susankya.swadesibidhesi.adapters.user.HomeSliderProductAdapter;
import com.susankya.swadesibidhesi.fragments.user.ProductsFragment;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductSlide;
import com.susankya.swadesibidhesi.models.user.HomeItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.susankya.swadesibidhesi.Generic.FragmentKeys.CAT_NAME;
import static com.susankya.swadesibidhesi.Generic.FragmentKeys.CAT_SLUG;
import static com.susankya.swadesibidhesi.Generic.FragmentKeys.SUb_CAT_NAME;

public class SliderAdapterDelegate extends AdapterDelegate<List<HomeItem>> {

    private LayoutInflater inflater;
    Context context;
    private HomeSliderProductAdapter adapter;

    public SliderAdapterDelegate(Context context) {
//        inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.context = context;
        this.inflater = LayoutInflater.from(context);
//        inflater = context.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<HomeItem> items, int position) {
        return items.get(position) instanceof WcProductSlide;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new SliderViewHolder(inflater.inflate(R.layout.layout_horizontal_scrollable_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<HomeItem> items, int position,
                                 @NonNull RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {
        SliderViewHolder vh = (SliderViewHolder) holder;
        final WcProductSlide productSlide = (WcProductSlide) items.get(position);

        vh.sliderTitle.setText(Html.fromHtml(productSlide.sliderTitle));
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        vh.recyclerView.setLayoutManager(layoutManager);
        vh.recyclerView.setHasFixedSize(true);
        vh.recyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(12));

        adapter = new HomeSliderProductAdapter(productSlide.sliderList, context, new HomeSliderProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WcProduct item) {
            }
        });
        vh.recyclerView.setAdapter(adapter);
        vh.viewAll.setVisibility(View.VISIBLE);
        vh.viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(activity, "Al clicked"+ productSlide.getCategoryId(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString(SUb_CAT_NAME, productSlide.sliderTitle);
                bundle.putString(CAT_NAME, "" + Html.fromHtml(productSlide.sliderTitle));
                bundle.putString(CAT_SLUG, productSlide.category_slug);
                ProductsFragment fragment = new ProductsFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = ((HomeActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.slider_title)
        TextView sliderTitle;
        @BindView(R.id.view_all)
        TextView viewAll;
        @BindView(R.id.horizontal_gridView)
        RecyclerView recyclerView;

        public SliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
