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
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcHomeAPI;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcProductCategoriesAPI;
import com.susankya.swadesibidhesi.BuildConfig;
import com.susankya.swadesibidhesi.Generic.Flavor;
import com.susankya.swadesibidhesi.Generic.Utilities;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.HomeActivity;
import com.susankya.swadesibidhesi.adapters.user.HomeAdapter;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcCategory;
import com.susankya.swadesibidhesi.models.WooCommerce.WcCategoryResponse;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductResponse;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductSlide;
import com.susankya.swadesibidhesi.models.user.Banner;
import com.susankya.swadesibidhesi.models.user.BannerSlide;
import com.susankya.swadesibidhesi.models.user.HomeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.susankya.swadesibidhesi.Generic.Utilities.goToViberMarket;
import static com.susankya.swadesibidhesi.Generic.Utilities.isViberClientInstalled;

public class HomeFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBarLayout)
    View progressLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressTV)
    TextView progressTextView;
    @BindView(R.id.outOfStockImage)
    ImageView outOfStock;
    @BindView(R.id.emtpyTextview)
    TextView emptyText;
    @BindView(R.id.emptyTextLayout)
    View emptyView;
    @BindView(R.id.empty_img)
    ImageView empty;
    @BindView(R.id.fabMessage)
    FloatingActionMenu fabMessage;
    @BindView(R.id.viber)
    FloatingActionButton viber;
    @BindView(R.id.messenger)
    FloatingActionButton messenger;
    @BindView(R.id.whatsApp)
    FloatingActionButton whatsApp;
    private LinearLayoutManager layoutManager;
    private HomeAdapter homeAdapter;
    private LayoutInflater inflater;
    private List<HomeItem> homeItemsList;
    private String viberLink, whatsAppLink, messengerLink;
    int loopLength;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        init();
        initFlavors();
        return (view);
    }

    private void initFlavors() {
    }

    private void init() {
        ((HomeActivity) getActivity()).setTitle(getString(R.string.app_name));
        if (Utilities.isConnectionAvailable(getActivity())) {
            emptyView.setVisibility(GONE);
            recyclerView.setVisibility(View.VISIBLE);
            checkWCRetrofit();
//            getLatestAddedProducts();
            setFabMenus();
        } else {
            recyclerView.setVisibility(GONE);
            emptyView.setVisibility(View.VISIBLE);
            empty.setImageDrawable(getResources().getDrawable(R.drawable.ic_plug));
            emptyText.setText("OOPS, out of Connection");
        }
    }

    private void setFabMenus() {
        viberLink=getResources().getString(R.string.viber);
        messengerLink=getResources().getString(R.string.messenger);
        whatsAppLink=getResources().getString(R.string.whatsApp);
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

    private void checkWCRetrofit() {
        progressBar.setVisibility(View.VISIBLE);
        WcProductCategoriesAPI categoriesAPI = App.getWcRetrofit().create(WcProductCategoriesAPI.class);
        categoriesAPI.getCategories().enqueue(new Callback<WcCategoryResponse>() {
            @Override
            public void onResponse(Call<WcCategoryResponse> call, Response<WcCategoryResponse> response) {
                progressBar.setVisibility(GONE);
                if (response.isSuccessful()) {
                    if (response.body().product_categories.isEmpty()) {
                    }
//                        Toast.makeText(getActivity(), "There are no any product categories!", Toast.LENGTH_SHORT).show();
                    else {
                        List<WcCategory> list = response.body().product_categories;
                        layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        if (getContext() != null) {
                            Log.d("retrofit", "onResponse:reached here "+response.body().product_categories);
                            homeAdapter = new HomeAdapter(getActivity(), getWcHomeItems(Utilities.getCategoryListSortedByCount(list)));
                            recyclerView.setAdapter(homeAdapter);
                        }
//                        for (WcCategory category : list)
//                            Log.d("retrofit", "Reversed students list: " + category.count + "slug" + category.slug);
                    }
                } else {
                    try {
//                        Log.d("retrofit", "errorbody" + response.errorBody().string());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<WcCategoryResponse> call, Throwable t) {
                progressBar.setVisibility(GONE);
//                Log.d("retrofit", "throwable" + t);
            }
        });
    }

    private List<HomeItem> getWcHomeItems(List<WcCategory> product_categories) {
        List<HomeItem> list = new ArrayList<>();
        if (BuildConfig.FLAVOR.equals(Flavor.MBSH))
            list.add(new BannerSlide(getBhumisBanner()));

        if (product_categories.size() > 5) loopLength = 5;
        else loopLength = product_categories.size();

        for (int i = 0; i < loopLength; i++) {
            Log.d("Home", product_categories.get(i).slug);
            String title = product_categories.get(i).name;
            String slug = product_categories.get(i).slug;
            getProductsForThisCategory(list, title, slug);
        }
        return list;
    }


    private List<Banner> getBhumisBanner() {
        List<Banner> list = new ArrayList<>();
        list.add(new Banner("https://bhumis.com/wp-content/uploads/2017/12/b2-1.jpg"));
        list.add(new Banner("https://bhumis.com/wp-content/uploads/2017/12/b4.jpg"));
        list.add(new Banner("https://bhumis.com/wp-content/uploads/2017/12/b3.jpg"));
        list.add(new Banner("https://bhumis.com/wp-content/uploads/2017/12/b1-1.jpg"));
        return list;
    }

    //fetching list of products from woocommerce api based on category
    private List<WcProduct> getProductsForThisCategory(final List<HomeItem> list, final String title, final String slug) {
        progressBar.setVisibility(View.VISIBLE);
        Log.d("reach", "getProductsForThisCategory: herein retrofit");
        final List<WcProduct> productList = new ArrayList<>();
        WcHomeAPI homeAPI = App.getWcRetrofit().create(WcHomeAPI.class);
        homeAPI.getProductsOfTheParticularCategory(slug).enqueue(new Callback<WcProductResponse>() {
            @Override
            public void onResponse(Call<WcProductResponse> call, Response<WcProductResponse> response) {
                progressBar.setVisibility(GONE);
                if (response.isSuccessful()) {
                    Log.d("reach", "onResponse: ponSuccess");
                    if (response.body().products.isEmpty()) {
                    }
//                        Toast.makeText(getActivity(), "There are no any product categories!", Toast.LENGTH_SHORT).show();
                    else {
                        list.add(new WcProductSlide(slug, title, response.body().products));
                    }
                } else {
                    try {
                        Log.d("reach", "onResponse:here on error ");
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<WcProductResponse> call, Throwable t) {
                progressBar.setVisibility(GONE);
                Log.d("reach", "throwable" + t);
            }
        });
        return productList;
    }
}
