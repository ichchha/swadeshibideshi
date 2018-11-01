package com.susankya.swadesibidhesi.adapterdelegates;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.susankya.swadesibidhesi.ItemDecorations.HorizontalSpaceItemDecoration;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.CustomPagerAdapter;
import com.susankya.swadesibidhesi.adapters.user.ThumbViewAdapter;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductImage;
import com.susankya.swadesibidhesi.models.user.ProductDetails;
import com.susankya.swadesibidhesi.models.user.ProductPhotoSlide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductPhotosAdapterDelegate extends AdapterDelegate<List<ProductDetails>> {

    private ThumbViewAdapter adapter;
    private LayoutInflater inflater;
    Activity activity;
    private Boolean isFirst = true;
    private int selectedPostion = 0;

    public ProductPhotosAdapterDelegate(Activity activity) {
        this.activity = activity;
        inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<ProductDetails> items, int position) {
        return items.get(position) instanceof ProductPhotoSlide;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new PhotoSliderViewHolder(inflater.inflate(R.layout.layout_product_image_slider, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<ProductDetails> items, int position,
                                 @NonNull final RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {
        final PhotoSliderViewHolder vh = (PhotoSliderViewHolder) holder;
        ProductPhotoSlide productImages = (ProductPhotoSlide) items.get(position);
        final int size = productImages.getProductImages().size();
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        vh.recyclerView.setLayoutManager(manager);
        //Toast.makeText(activity, ""+vh.recyclerView.getChildAt(0), Toast.LENGTH_SHORT).show();
        vh.recyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(8));
        adapter = new ThumbViewAdapter(productImages.getProductImages(), activity, new ThumbViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WcProductImage images, int position) {
                vh.viewPager.setCurrentItem(position, true);
                if(isFirst){position=selectedPostion;
                vh.recyclerView.getChildAt(position).setBackground(activity.getResources().getDrawable(R.drawable.thumb_pressed));
                isFirst=false;}
                else {
                    for (int i = 0; i < size; i++) {
                        isFirst=false;
                        if (i == position) {
                            vh.recyclerView.getChildAt(i).setBackground(activity.getResources().getDrawable(R.drawable.thumb_pressed));
                        } else {
                            vh.recyclerView.getChildAt(i).setBackground(activity.getResources().getDrawable(R.drawable.thumb_button));
                        }
                    }
                }
            }
        });

        vh.recyclerView.setAdapter(adapter);
        vh.viewPager.setAdapter(new CustomPagerAdapter(activity, productImages.getProductImages()));
        vh.viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"ViewPager Clicked",Toast.LENGTH_SHORT).show();
            }
        });
        vh.viewPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        // vh.recyclerView.getChildAt(0).setBackground(activity.getResources().getDrawable(R.drawable.thumb_pressed));
                        for (int i = 0; i < size; i++) {
                            if (i == position) {
                                vh.recyclerView.getChildAt(i).setBackground(activity.getResources().getDrawable(R.drawable.thumb_pressed));
                            } else {
                                vh.recyclerView.getChildAt(i).setBackground(activity.getResources().getDrawable(R.drawable.thumb_button));
                            }
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                }
        );
    }

    static class PhotoSliderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pager)
        ViewPager viewPager;
        @BindView(R.id.thumbnailsRV)
        RecyclerView recyclerView;

        public PhotoSliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}