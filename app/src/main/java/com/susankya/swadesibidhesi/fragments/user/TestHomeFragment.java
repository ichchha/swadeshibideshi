package com.susankya.swadesibidhesi.fragments.user;


import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcHomeAPI;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcProductCategoriesAPI;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.Generic.Utilities;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.HomeAdapter;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcNavCategoryResponse;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductCategory;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductResponse;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductSlide;
import com.susankya.swadesibidhesi.models.user.Banner;
import com.susankya.swadesibidhesi.models.user.HomeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

import static android.view.View.GONE;
import static com.susankya.swadesibidhesi.Generic.Utilities.goToViberMarket;
import static com.susankya.swadesibidhesi.Generic.Utilities.isViberClientInstalled;

public class TestHomeFragment extends Fragment {
    @BindView(R.id.full_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progressBarLayout)
    View progressLayout;
    @BindView(R.id.homeLayout)
    ScrollView homeLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressTV)
    TextView progressTextView;
    @BindView(R.id.emtpyTextview)
    TextView emptyText;
    @BindView(R.id.emptyTextLayout)
    View emptyView;
    @BindView(R.id.sliderLayout)
    BannerSlider bannerSlider;
    @BindView(R.id.empty_img)
    ImageView empty;
    @BindView(R.id.tabButtonsLayout)
    RadioGroup radioButtonsGroup;
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
    List<ss.com.bannerslider.banners.Banner> homeBanners = new ArrayList<>();
    private List<WcProductCategory> wcProductCategoryList;
    private List<HomeItem> homeItemList = new ArrayList<>();
    private List<HomeItem> homeItemMindList = new ArrayList<>();
    private List<HomeItem> homeItemBodyList = new ArrayList<>();
    private List<HomeItem> homeItemHeartList = new ArrayList<>();
    private List<HomeItem> homeItemSoulList = new ArrayList<>();
    int loopLength;
    private String viberLink, whatsAppLink, messengerLink;
    private HomeAdapter adapter;

    public TestHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_radio_home, container, false);
        ButterKnife.bind(this, view);
        setFabMenus();
        init();
        return (view);
    }

    private void init() {
        if (Utilities.isConnectionAvailable(getActivity())) {
            emptyView.setVisibility(GONE);
            homeLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            fetchCategories();
        } else {
            homeLayout.setVisibility(GONE);
            emptyView.setVisibility(View.VISIBLE);
            empty.setImageDrawable(getResources().getDrawable(R.drawable.ic_plug));
            emptyText.setText("OOPS, out of Connection");
        }
    }

    private void fetchCategories() {
        progressBar.setVisibility(View.VISIBLE);
        WcProductCategoriesAPI categoriesAPI = App.getWcRetrofit().create(WcProductCategoriesAPI.class);
        categoriesAPI.getNavCategories().enqueue(new Callback<WcNavCategoryResponse>() {
            @Override
            public void onResponse(Call<WcNavCategoryResponse> call, Response<WcNavCategoryResponse> response) {
                if (response.isSuccessful()) {
//                    Log.d("retrofit", "onResponse: here");
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
//                Log.d("retrofit", "throwable" + t);
            }
        });
    }

    private void checkGroups(List<WcProductCategory> wcCategories) {
        for (int i = 0; i < wcCategories.size(); i++)
//            Log.d("hey", "checkGroups: " + wcCategories.get(i).getTitle() + wcCategories.get(i).children);
        for (WcProductCategory productCategory : wcCategories) {
            if (productCategory.slug.equals(Keys.MIND)) {
//                mindList = productCategory.children;
                mindList = getItems();
//                mindList = getMindItemList();
                homeItemMindList = getNewWcHomeItems(mindList, false);
//                homeItemMindList = getMindItemList(false);
            } else if (productCategory.slug.equals(Keys.BODY)) {
                bodyList = productCategory.children;
                homeItemBodyList = getNewWcHomeItems(bodyList, false);
            } else if (productCategory.slug.equals(Keys.HEART)) {
                heartList = productCategory.children;
                homeItemHeartList = getNewWcHomeItems(heartList, false);
            } else if (productCategory.slug.equals(Keys.SOUL)) {
                soulList = productCategory.children;
                homeItemSoulList = getNewWcHomeItems(soulList, false);
            }
        }
//        Log.d("first", "checkGroups: " + homeItemMindList.size());
        getHomeItems(homeItemMindList);
    }

    private List<WcProductCategory> getItems() {
        List<WcProductCategory> list = new ArrayList<>();
        return list;
    }

    private List<HomeItem> getMindItemList(boolean isFirst) {
        List<HomeItem> list = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        titleList.add("Used Books");
        titleList.add("New Books");
        titleList.add("New Text Books");
        titleList.add("Used Text Books");
        titleList.add("Used HBR Magazine Issue");

        List<String> slugList = new ArrayList<>();
        slugList.add("used-books");
        slugList.add("new-books");
        slugList.add("new-text-books");
        slugList.add("used-text-books");
        slugList.add("used-hbr-magazine-issue");
        for (int i = 0; i < 5; i++) {
            String title = titleList.get(i);
            String slug = slugList.get(i);
            getProductsForThisCategory(list, title, slug, isFirst);
        }
//        progressBar.setVisibility(GONE);
        return list;
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
//                    checkFurtherChildrens(list);
                }
            }
            list.add(new WcProductCategory(title, list1, id, slug));
        }
//        checkFurtherChildrens(list);
        checkGroups(list);
    }

    private void getHomeItems(final List<HomeItem> homeItemMindList) {
        setUpBanners(getBanners(), homeItemMindList);
//        getNewWcHomeItems(mindList, true);
        getMindItemList(true);
        radioButtonsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btnMind:
                        if (homeItemMindList.size() > 0) {
                            emptyView.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(new HomeAdapter(getActivity(), TestHomeFragment.this.homeItemMindList));
                        } else showEmptyMessage();
                        progressBar.setVisibility(GONE);
                        break;
                    case R.id.btnBody:
                        if (homeItemBodyList.size() > 0) {
                            emptyView.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(new HomeAdapter(getActivity(), homeItemBodyList));
                        } else showEmptyMessage();
                        progressBar.setVisibility(GONE);
                        break;
                    case R.id.btnHeart:
                        if (homeItemHeartList.size() > 0) {
                            emptyView.setVisibility(GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(new HomeAdapter(getActivity(), homeItemHeartList));
                        } else showEmptyMessage();
                        progressBar.setVisibility(GONE);
                        break;
                    case R.id.btnSoul:
//                        Log.d("size", "onCheckedChanged: soul"+homeItemSoulList.size());
                        if (homeItemSoulList.size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                            recyclerView.setAdapter(new HomeAdapter(getActivity(), homeItemSoulList));
                        } else showEmptyMessage();
                        progressBar.setVisibility(GONE);
                        break;
                    default:
                        recyclerView.setAdapter(new HomeAdapter(getActivity(), TestHomeFragment.this.homeItemMindList));
                        progressBar.setVisibility(GONE);
                        break;
                }
            }
        });
    }

    private void showEmptyMessage() {
        recyclerView.setVisibility(GONE);
        emptyView.setVisibility(View.VISIBLE);
        empty.setImageDrawable(getResources().getDrawable(R.drawable.ic_coming_soon));
        emptyText.setText("Products Coming Soon");
    }

    private void setFabMenus() {
        viberLink = getResources().getString(R.string.viber);
        messengerLink = getResources().getString(R.string.messenger);
        whatsAppLink = getResources().getString(R.string.whatsApp);
        fabMessage.setVisibility(View.VISIBLE);
        messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Opening up Facebook Messenger, Please wait...", Toast.LENGTH_SHORT).show();
                openMessenger(messengerLink);
            }
        });
        viber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Opening up Viber, Please wait...", Toast.LENGTH_SHORT).show();
                viberCall(viberLink, getContext());
            }
        });
        whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Opening up What's App, Please wait...", Toast.LENGTH_SHORT).show();
                openWhatsApp(whatsAppLink);
            }
        });
    }

    private void openMessenger(String messengerLink) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_VIEW);
        sendIntent.setPackage("com.facebook.orca");
        sendIntent.setData(Uri.parse("https://m.me/" + messengerLink));
        try {
            startActivity(sendIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "Please Install Facebook Messenger", Toast.LENGTH_LONG).show();
        }
    }

    private void openWhatsApp(String id) {
        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(id) + "@s.whatsapp.net");
        try {
            startActivity(sendIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "Please Install WhatsApp", Toast.LENGTH_LONG).show();
        }
    }

    private void viberCall(String contact, Context context) {
        try {
            if (!isViberClientInstalled(context)) {
                //goToMarket(context,"\"https://play.google.com/store/apps/details?id=com.viber.voip\"");
                goToViberMarket(context);
                return;
            }

            Uri uri = Uri.parse("tel:" + Uri.encode(contact));
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setClassName("com.viber.voip", "com.viber.voip.WelcomeActivity");
            intent.setData(uri);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "Please Install Viber Messenger", Toast.LENGTH_LONG).show();
        }
    }


    private void setUpBanners(List<Banner> banners, List<HomeItem> homeItemMindList) {
        for (int i = 0; i < banners.size(); i++)
            homeBanners.add(new RemoteBanner((banners.get(i).image)));
        //keeping the banner image in center
        for (int i = 0; i < homeBanners.size(); i++)
            homeBanners.get(i).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        bannerSlider.setBanners(homeBanners); //attaching the banner images into banner slider
    }

    private List<com.susankya.swadesibidhesi.models.user.Banner> getBanners() {
        List<com.susankya.swadesibidhesi.models.user.Banner> list = new ArrayList<>();
//        list.add(new com.susankya.wcbookstore.models.user.Banner("https://mbshnepal.com/wp-content/uploads/2012/06/book-collage-4x3-1024x768.png"));
        list.add(new com.susankya.swadesibidhesi.models.user.Banner("https://mbshnepal.com/wp-content/uploads/2012/06/banner-body.jpg"));
        list.add(new com.susankya.swadesibidhesi.models.user.Banner("https://mbshnepal.com/wp-content/uploads/2012/06/soul-banner.jpg"));
        list.add(new com.susankya.swadesibidhesi.models.user.Banner("https://mbshnepal.com/wp-content/uploads/2012/06/heart-banner.jpg"));
        return list;
    }

    private List<HomeItem> getNewWcHomeItems(List<WcProductCategory> wcProductCategoryList, boolean isFirst) {
        List<HomeItem> list = new ArrayList<>();
        if (wcProductCategoryList.size() > 5) loopLength = 5;
        else loopLength = wcProductCategoryList.size();

        for (int i = 0; i < loopLength; i++) {
            Log.d("Home", wcProductCategoryList.get(i).slug);
            String title = wcProductCategoryList.get(i).name;
            String slug = wcProductCategoryList.get(i).slug;
            getProductsForThisCategory(list, title, slug, isFirst);
        }
        return list;
    }

    //fetching list of products from woocommerce api based on category
    private List<HomeItem> getProductsForThisCategory(final List<HomeItem> list, final String title, final String slug, final boolean isFirst) {
        Log.d("reach", "getProductsForThisCategory: herein retrofit");
        final List<WcProduct> productList = new ArrayList<>();
        WcHomeAPI homeAPI = App.getWcRetrofit().create(WcHomeAPI.class);
        homeAPI.getProductsOfTheParticularCategory(slug).enqueue(new Callback<WcProductResponse>() {
            @Override
            public void onResponse(Call<WcProductResponse> call, Response<WcProductResponse> response) {
                progressBar.setVisibility(GONE);
                if (response.isSuccessful()) {
                    if (response.body().products.isEmpty()) {
                    } else {
                        list.add(new WcProductSlide(slug, title, response.body().products));
                        if (isFirst) {
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(new HomeAdapter(getActivity(), list));
                        } else {
                        }
                    }
                } else {
                    try {
//                        Log.d("reach", "onResponse:here on error ");
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<WcProductResponse> call, Throwable t) {
                progressBar.setVisibility(GONE);
//                Log.d("reach", "throwable" + t);
            }
        });
        return list;
    }

    @Override
    public void onResume() {
//        getNewWcHomeItems(mindList, true);
        getMindItemList(true);
        super.onResume();
    }
}
