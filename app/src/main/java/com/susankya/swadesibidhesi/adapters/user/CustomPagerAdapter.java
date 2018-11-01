package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.PhotViewActivity;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductImage;

import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {
    private Context context;
    private List<WcProductImage> dataObjectList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public CustomPagerAdapter(Context context, List<WcProductImage> dataObjectList) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(this.context.LAYOUT_INFLATER_SERVICE);
        this.dataObjectList = dataObjectList;
    }

    @Override
    public int getCount() {
        return dataObjectList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = this.layoutInflater.inflate(R.layout.pager_gallery_item, container, false);
        SimpleDraweeView imageSlider = view.findViewById(R.id.sliderImage);
        final String imageUri = this.dataObjectList.get(position).src;
        if (imageUri.isEmpty()) {
            imageSlider.setImageURI("http://bit.ly/2FxkuxQ");
        } else {
            try {
                imageSlider.setImageURI(imageUri);
            } catch (Exception e) {
                e.printStackTrace();
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
//                MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
//                long maxMemory = heapUsage.getMax() / MEGABYTE;
//                long usedMemory = heapUsage.getUsed() / MEGABYTE;
//                System.out.println(i+ " : Memory Use :" + usedMemory + "M/" + maxMemory + "M");
            }
        }
        container.addView(view);
        imageSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PhotViewActivity.class);
                // intent.putExtra("WcProduct Id",productId);
                if (dataObjectList.get(position).src.isEmpty()) {
                    intent.putExtra("photo", "http://bit.ly/2FxkuxQ");
                } else {
                    intent.putExtra("photo", imageUri);
                }
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
