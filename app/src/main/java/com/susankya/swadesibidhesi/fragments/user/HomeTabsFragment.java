package com.susankya.swadesibidhesi.fragments.user;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcHomeAPI;
import com.susankya.swadesibidhesi.BuildConfig;
import com.susankya.swadesibidhesi.Generic.Flavor;
import com.susankya.swadesibidhesi.Generic.Utilities;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.HomeAdapter;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcCategory;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductCategory;
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
import static com.susankya.swadesibidhesi.Generic.Keys.CATEGORIES_LIST;
import static com.susankya.swadesibidhesi.Generic.Keys.SLUG;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTabsFragment extends Fragment {

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
    com.github.clans.fab.FloatingActionButton viber;
    @BindView(R.id.messenger)
    com.github.clans.fab.FloatingActionButton messenger;
    @BindView(R.id.whatsApp)
    com.github.clans.fab.FloatingActionButton whatsApp;
    private LinearLayoutManager layoutManager;
    private HomeAdapter homeAdapter;
    private LayoutInflater inflater;
    private List<HomeItem> homeItemsList;
    //    private List<WcCategory> mindList, bodyList, soulList, heartList;
    private List<WcCategory> mindList = new ArrayList<>();
    private List<WcCategory> bodyList = new ArrayList<>();
    private List<WcCategory> soulList = new ArrayList<>();
    private List<WcCategory> heartList = new ArrayList<>();
    private List<WcProductCategory> wcProductCategoryList;
    private String slug, whatsAppLink, messengerLink;
    int loopLength;

    public HomeTabsFragment() {
        // Required empty public constructor
    }

    public static HomeTabsFragment newInstance(String slug, List<WcProductCategory> categories) {
        HomeTabsFragment fragment = new HomeTabsFragment();
        Bundle args = new Bundle();
        args.putString(SLUG, slug);
        args.putParcelableArrayList(CATEGORIES_LIST, (ArrayList<? extends Parcelable>) categories);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            slug = getArguments().getString(SLUG);
            wcProductCategoryList = getArguments().getParcelableArrayList(CATEGORIES_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        if (Utilities.isConnectionAvailable(getActivity())) {
            emptyView.setVisibility(GONE);
            recyclerView.setVisibility(View.VISIBLE);
            setUpRecyclerView();
//            checkWCRetrofit();
//            getLatestAddedProducts();
//            setFabMenus();
        } else {
            recyclerView.setVisibility(GONE);
            emptyView.setVisibility(View.VISIBLE);
            empty.setImageDrawable(getResources().getDrawable(R.drawable.ic_plug));
            emptyText.setText("OOPS, out of Connection");
        }
    }

    private void setUpRecyclerView() {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        homeAdapter = new HomeAdapter(getActivity(), getNewWcHomeItems(wcProductCategoryList));
        recyclerView.setAdapter(homeAdapter);
    }

    private List<HomeItem> getNewWcHomeItems(List<WcProductCategory> wcProductCategoryList) {
        List<HomeItem> list = new ArrayList<>();
        if (BuildConfig.FLAVOR.equals(Flavor.MBSH))
            list.add(new BannerSlide(getBhumisBanner()));

        if (wcProductCategoryList.size() > 5) loopLength = 5;
        else loopLength = wcProductCategoryList.size();

        for (int i = 0; i < loopLength; i++) {
            Log.d("Home", wcProductCategoryList.get(i).slug);
            String title = wcProductCategoryList.get(i).name;
            String slug = wcProductCategoryList.get(i).slug;
            getProductsForThisCategory(list, title, slug);
        }
        return list;
    }
    private List<Banner> getBhumisBanner() {
        List<Banner> list = new ArrayList<>();
            list.add(new Banner("https://mbshnepal.com/wp-content/uploads/2012/06/book-collage-4x3-1024x768.png"));
            list.add(new Banner("https://mbshnepal.com/wp-content/uploads/2012/06/banner-body.jpg"));
            list.add(new Banner("https://mbshnepal.com/wp-content/uploads/2012/06/soul-banner.jpg"));
            list.add(new Banner("https://mbshnepal.com/wp-content/uploads/2012/06/heart-banner.jpg"));
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        if (BuildConfig.FLAVOR.equals("unique"))
//            inflater.inflate(R.menu.sort_attendance_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
