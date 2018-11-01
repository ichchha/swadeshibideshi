package com.susankya.swadesibidhesi.fragments.user;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.common.internal.ImmutableMap;
import com.google.gson.Gson;
import com.joanzapata.iconify.widget.IconTextView;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcHomeAPI;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcProductsAPI;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.Generic.Utilities;
import com.susankya.swadesibidhesi.ItemDecorations.GridViewItemDecoration;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.HomeActivity;
import com.susankya.swadesibidhesi.activities.user.ProductDetailsActivity;
import com.susankya.swadesibidhesi.adapters.WooCommerce.WcProductsAdapter;
import com.susankya.swadesibidhesi.adapters.user.ProductsAdapter;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.listeners.EndlessRecyclerViewScrollListener;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductResponse;
import com.susankya.swadesibidhesi.models.user.ProductItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.susankya.swadesibidhesi.Generic.FragmentKeys.CAT_NAME;
import static com.susankya.swadesibidhesi.Generic.FragmentKeys.CAT_SLUG;

public class ProductsFragment extends Fragment {

    @BindView(R.id.products_rv)
    RecyclerView recyclerView;
    @BindView(R.id.txtSort)
    IconTextView txtSort;
    @BindView(R.id.txtFilter)
    IconTextView txtFilter;
    @BindView(R.id.verticalLine)
    View verticalLine;
    @BindView(R.id.progressBarLayout)
    View progressLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.bottomLayout)
    View bottomView;
    @BindView(R.id.emptyTextLayout)
    View emptyView;
    @BindView(R.id.empty_img)
    ImageView empty;
    @BindView(R.id.emtpyTextview)
    TextView emptyText;
    @BindView(R.id.progressTV)
    TextView progressTextView;
    @BindView(R.id.outOfStockImage)
    ImageView outOfStock;
    private GridLayoutManager layoutManager;
    private ProductsAdapter adapter;
    private WcProductsAdapter wcProductsAdapter;
    List<ProductItem> productItems = new ArrayList<>();
    String nextProductURL;
    private EndlessRecyclerViewScrollListener scrollListener;
    private Gson mGson;
    private List<WcProduct> productList = new ArrayList<>();
    private String category_slug;
    private int productsCount, pagesCount;
    private int nextPage = 1;
    private boolean isNextPageAvailable = true;

    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        if (getArguments() != null) {
            setTitle(getArguments().getString(CAT_NAME));
            category_slug = getArguments().getString(CAT_SLUG);
        }
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        GridViewItemDecoration gridItemDivider = new GridViewItemDecoration(getActivity());
        recyclerView.addItemDecoration(gridItemDivider);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d("check",""+isNextPageAvailable+nextPage);
                if (isNextPageAvailable && (productList.size() > 0)) {
                    fetchMoreWCProducts(String.valueOf(nextPage));
                }
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        if (Utilities.isConnectionAvailable(getActivity())) {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            countNoOfProducts();
            getProductsByCategorySlug();
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            empty.setImageDrawable(getResources().getDrawable(R.drawable.ic_plug));
            emptyText.setText("OOPS, out of Connection");
        }

        txtSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Sort", Toast.LENGTH_SHORT).show();
            }
        });
        txtFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Filter", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void countNoOfProducts() {
        WcProductsAPI productsAPI = App.getWcRetrofit().create(WcProductsAPI.class);
        productsAPI.countNoOfProductsInTheCategpry(category_slug).enqueue(new Callback<WcProductResponse>() {
            @Override
            public void onResponse(Call<WcProductResponse> call, Response<WcProductResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    productsCount = response.body().count;
                    int quotient = productsCount / 10;
                    int remainder = productsCount % 10;

                    if (remainder > 0) pagesCount = quotient + 1;
                    else pagesCount = quotient;
                    Log.d("math", pagesCount + "");
                } else {
                    try {
                        Log.d("retrofit", "errorbody" + response.errorBody().string());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<WcProductResponse> call, Throwable t) {
            }
        });
    }

    private void getProductsByCategorySlug() {
        progressLayout.setVisibility(View.VISIBLE);
        bottomView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        WcHomeAPI homeAPI = App.getWcRetrofit().create(WcHomeAPI.class);
        homeAPI.getProductsOfTheParticularCategory(category_slug).enqueue(new Callback<WcProductResponse>() {
            @Override
            public void onResponse(Call<WcProductResponse> call, Response<WcProductResponse> response) {
                progressBar.setVisibility(View.GONE);
                progressLayout.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().products.isEmpty()) {
                        isNextPageAvailable = false;
                        emptyView.setVisibility(View.VISIBLE);
                        empty.setImageDrawable(getResources().getDrawable(R.drawable.ic_online_shopping));
                        emptyText.setText("Products will be added soon!");
                        emptyText.setTextSize(20);
                        emptyText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    } else {
                        isNextPageAvailable = true;
                        nextPage++;
                        productList = response.body().products;
                        wcProductsAdapter =new WcProductsAdapter(productList, getActivity(), new WcProductsAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(WcProduct item) {
                                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                                if (item.categories != null)
                                    intent.putExtra("sub_cat", item.categories.get(0));
                                intent.putExtra("product_id", item.id);
                                intent.putExtra("product_url", item.permalink);
                                intent.putExtra("product_name", item.title);
                                intent.putExtra(Keys.WCPRODUCT, item);
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(wcProductsAdapter);
                    }
                } else {
                    try {
//                        Log.d("retrofit", "errorbody" + response.errorBody().string());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<WcProductResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                progressLayout.setVisibility(View.GONE);
//                Log.d("retrofit", "throwable" + t);
            }
        });
    }

    private void fetchMoreWCProducts(String page) {
        Log.d("page", page);
        WcProductsAPI productsAPI = App.getWcRetrofit().create(WcProductsAPI.class);
        productsAPI.fetchProductsBasedOnPages(ImmutableMap.of("filter[category]", category_slug, "page", page))
                .enqueue(new Callback<WcProductResponse>() {
                    @Override
                    public void onResponse(Call<WcProductResponse> call, Response<WcProductResponse> response) {
                        progressBar.setVisibility(View.GONE);
                        progressLayout.setVisibility(View.GONE);
                        bottomView.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().products.isEmpty()) isNextPageAvailable = false;
                            else {
                                isNextPageAvailable = true;
                                int listSize = productList.size();
                                nextPage++;
                                productList.addAll(response.body().products);
                                wcProductsAdapter.notifyItemRangeInserted(listSize, response.body().products.size());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WcProductResponse> call, Throwable t) {
                        Log.d("ProductFailure", t.toString());
                    }
                });
    }
    // sets title
    private void setTitle(String title) {
        ((HomeActivity) getActivity()).setTitle(title);
    }
}
