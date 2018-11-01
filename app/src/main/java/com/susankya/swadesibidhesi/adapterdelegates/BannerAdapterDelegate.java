package com.susankya.swadesibidhesi.adapterdelegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.models.user.BannerSlide;
import com.susankya.swadesibidhesi.models.user.HomeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

/**
 * Created by Ichha on 2/4/2018.
 */

public class BannerAdapterDelegate extends  AdapterDelegate<List<HomeItem>> {

    private LayoutInflater inflater;
    Context activity;
    List<Banner> banners = new ArrayList<>();
    public BannerAdapterDelegate(Context activity) {
        this.activity = activity;
        this.inflater= LayoutInflater.from(activity);
    }

    @Override protected boolean isForViewType(@NonNull List<HomeItem> items, int position) {
        return items.get(position) instanceof BannerSlide;
    }

    @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new BannerViewHolder(inflater.inflate(R.layout.layout_banner, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull List<HomeItem> items, int position,
                                           @NonNull RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {
        BannerViewHolder vh = (BannerViewHolder) holder;
        BannerSlide bannerSlide = (BannerSlide) items.get(position);
        for (int i = 0; i < (bannerSlide.getBannerList().size()); i++) {
            banners.add(new RemoteBanner((bannerSlide.getBannerList().get(i).image)));
            //Glide.with(activity).load(bannerSlide.getBannerList().get(i).getImage()).into(imageView);
        }
        //keeping the banner image in center
        for(int i=0;i<banners.size();i++) banners.get(i).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        vh.bannerSlider.setBanners(banners); //attaching the banner images into banner slider
        vh.bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
                String msg = String.valueOf(position);
                Log.d("banner","msg");
               // Toast.makeText(activity, "Banner with position " + String.valueOf(position) + " clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {

       @BindView(R.id.banner_slider1)BannerSlider bannerSlider;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
