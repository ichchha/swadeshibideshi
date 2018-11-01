package com.susankya.swadesibidhesi.fragments.user;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcProductCategoriesAPI;
import com.susankya.swadesibidhesi.BuildConfig;
import com.susankya.swadesibidhesi.Generic.Flavor;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.viewpager.ViewPagerAdapter;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcNavCategoryResponse;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductCategory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;

import static com.susankya.swadesibidhesi.Generic.Keys.BODY;
import static com.susankya.swadesibidhesi.Generic.Keys.HEART;
import static com.susankya.swadesibidhesi.Generic.Keys.SOUL;

//import com.bumptech.glide.request.RequestOptions;
//import com.glide.slider.library.Animations.DescriptionAnimation;
//import com.glide.slider.library.SliderLayout;
//import com.glide.slider.library.SliderTypes.DefaultSliderView;

public class NewHomeFragment extends Fragment {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
//    @BindView(R.id.banner_slider1)
//    BannerSlider bannerSlider;
    @BindView(R.id.fabMessage)
    FloatingActionMenu fabMessage;
    @BindView(R.id.viber)
    FloatingActionButton viber;
    @BindView(R.id.messenger)
    FloatingActionButton messenger;
    @BindView(R.id.whatsApp)
    FloatingActionButton whatsApp;
    private List<WcProductCategory> mCategories = new ArrayList<>();
    List<WcProductCategory> parentList = new ArrayList<>();
    List<WcProductCategory> childrenList = new ArrayList<>();
    List<WcProductCategory> mindList = new ArrayList<>();
    List<WcProductCategory> bodyList = new ArrayList<>();
    List<WcProductCategory> soulList = new ArrayList<>();
    List<WcProductCategory> heartList = new ArrayList<>();
    List<Banner> homeBanners = new ArrayList<>();

    public NewHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_home, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
//        setUpBanners(getBanners());
        fetchCategories();
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        if (BuildConfig.FLAVOR.equals(Flavor.MBSH))
            tabLayout.setTabTextColors(Color.parseColor("#233D4D"), Color.parseColor("#F0963A"));
    }

    private void setUpBanners(List<com.susankya.swadesibidhesi.models.user.Banner> banners) {
        for (int i = 0; i < banners.size(); i++) {
            homeBanners.add(new RemoteBanner((banners.get(i).image)));
            //Glide.with(activity).load(bannerSlide.getBannerList().get(i).getImage()).into(imageView);
        }
        //keeping the banner image in center
        for (int i = 0; i < homeBanners.size(); i++)
            homeBanners.get(i).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        bannerSlider.setBanners(homeBanners); //attaching the banner images into banner slider
    }

    private List<com.susankya.swadesibidhesi.models.user.Banner> getBanners() {
        List<com.susankya.swadesibidhesi.models.user.Banner> list = new ArrayList<>();
//        list.add(new com.susankya.wcbookstore.models.user.Banner("https://mbshnepal.com/wp-content/uploads/2012/06/book-collage-4x3-1024x768.png"));
        list.add(new com.susankya.swadesibidhesi.models.user.Banner("https://mbshnepal.com/wp-content/uploads/2012/06/banner-body.jpg"));
        list.add(new com.susankya.swadesibidhesi.models.user.Banner("https://mbshnepal.com/wp-content/uploads/2012/06/soul-banner.jpg"));
        list.add(new com.susankya.swadesibidhesi.models.user.Banner("https://mbshnepal.com/wp-content/uploads/2012/06/heart-banner.jpg"));
        return list;
    }


    private void fetchCategories() {
        WcProductCategoriesAPI categoriesAPI = App.getWcRetrofit().create(WcProductCategoriesAPI.class);
        categoriesAPI.getNavCategories().enqueue(new Callback<WcNavCategoryResponse>() {
            @Override
            public void onResponse(Call<WcNavCategoryResponse> call, Response<WcNavCategoryResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().product_categories != null) {
                        mCategories = response.body().product_categories;
                        getWcCategories(mCategories);
                    }
                } else {
                    try {
//                        Log.d("retrofit", "errorbody" + response.errorBody().string());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<WcNavCategoryResponse> call, Throwable t) {
                Log.d("retrofit", "throwable" + t);
            }
        });
    }

    private void checkGroups(List<WcProductCategory> wcCategories) {
        for (int i = 0; i < wcCategories.size(); i++)
            Log.d("hey", "checkGroups: " + wcCategories.get(i).getTitle() + wcCategories.get(i).children);
        for (WcProductCategory productCategory : wcCategories) {
            if (productCategory.slug.equals(Keys.MIND)) {
                mindList = productCategory.children;
            } else if (productCategory.slug.equals(Keys.BODY)) {
                bodyList = productCategory.children;
            } else if (productCategory.slug.equals(Keys.HEART)) {
                heartList = productCategory.children;
            } else if (productCategory.slug.equals(Keys.SOUL)) {
                soulList = productCategory.children;
            }
        }
        setUpViewPager(viewPager, mindList, bodyList, heartList, soulList);
    }

    //    private List<WcProductCategory> getWcCategories(List<WcProductCategory> mCategories) {
    private void getWcCategories(List<WcProductCategory> mCategories) {
        List<WcProductCategory> list = new ArrayList<>();
        for (WcProductCategory category : mCategories) {
            int parent = category.parent;
            if (parent == 0) parentList.add(category);
            else {
                childrenList.add(category);
            }
        }
        for (int i = 0; i < parentList.size(); i++) {
            List<WcProductCategory> list1 = new ArrayList<>();
            String title = parentList.get(i).name;
            int id = parentList.get(i).id;
            String slug = parentList.get(i).slug;
            for (WcProductCategory category1 : childrenList) {
                if (id == category1.parent) {
                    list1.add(category1);
                }
            }
            list.add(new WcProductCategory(title, list1, id, slug));
        }
        checkGroups(list);
//        return list;
    }

    private void setUpViewPager(ViewPager viewPager, List<WcProductCategory> mindList, List<WcProductCategory> bodyList, List<WcProductCategory> heartList, List<WcProductCategory> soulList) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(HomeTabsFragment.newInstance(Keys.MIND, mindList), Keys.MIND);
        adapter.addFrag(HomeTabsFragment.newInstance(BODY, bodyList), BODY);
        adapter.addFrag(HomeTabsFragment.newInstance(Keys.SOUL, soulList), SOUL);
        adapter.addFrag(HomeTabsFragment.newInstance(Keys.HEART, heartList), HEART);
        viewPager.setAdapter(adapter);
    }
}
