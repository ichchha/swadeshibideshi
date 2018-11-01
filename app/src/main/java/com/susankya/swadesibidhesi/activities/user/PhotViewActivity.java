package com.susankya.swadesibidhesi.activities.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.joanzapata.iconify.widget.IconTextView;
import com.squareup.picasso.Picasso;
import com.susankya.swadesibidhesi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotViewActivity extends AppCompatActivity {
    @BindView(R.id.photo_view)
    PhotoView photoView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.faClose)
    IconTextView closeViewPager;
    private String productUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phot_view);
        ButterKnife.bind(this);
        init();
        Intent i= getIntent();
        productUrl= i.getStringExtra("photo");
        Uri uri;
        if (getIntent() != null) {
            uri = Uri.parse(productUrl);
            try {
                Picasso.with(PhotViewActivity.this).load(uri).into(photoView);
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
    }

    private void init() {
     //   viewPager.setAdapter(new CustomPagerAdapter(this,));
        viewPager.setAdapter(new SamplePagerAdapter());
        closeViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static class SamplePagerAdapter extends PagerAdapter {
        private static final int[] sDrawables = { R.drawable.sale1, R.drawable.sale2, R.drawable.sale3,
                R.drawable.denim_jacket, R.drawable.pp, R.drawable.product_placeholder };

        @Override
        public int getCount() {
            return sDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setImageResource(sDrawables[position]);

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
