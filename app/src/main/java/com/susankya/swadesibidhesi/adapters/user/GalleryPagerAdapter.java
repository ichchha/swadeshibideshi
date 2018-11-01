package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.models.user.BannerItem;

import java.util.List;

/**
 * Created by Ichha on 2/14/2018.
 */

public class GalleryPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    List<BannerItem> images;

    public GalleryPagerAdapter(Context context, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
    }

    public GalleryPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.pager_gallery_item, container, false);
        container.addView(itemView);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 260);
        params.setMargins(3, 3, 3, 3);

        final ImageView thumbView = new ImageView(context);
        thumbView.setTag(position);
        thumbView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbView.setLayoutParams(params);
        thumbView.setMinimumHeight(260);

        return itemView;

    }
}
