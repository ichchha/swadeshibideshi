package com.susankya.swadesibidhesi.activities.user;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcProductsAPI;
import com.susankya.swadesibidhesi.Config.Url;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.Generic.Utilities;
import com.susankya.swadesibidhesi.ItemDecorations.GridViewItemDecoration;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.Task.GetDataTask;
import com.susankya.swadesibidhesi.adapters.user.SearchAdapter;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.listeners.EndlessRecyclerGridScrollListener;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductResponse;
import com.susankya.swadesibidhesi.models.user.ProductItem;
import com.susankya.swadesibidhesi.models.user.SearchResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchableActivity extends AppCompatActivity {
    ProgressDialog pd;
    @BindView(R.id.searchTitle)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.progressBarLayout)
    View progressLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressTV)
    TextView progressTextView;
    @BindView(R.id.emtpyTextview)
    TextView emptyText;
    @BindView(R.id.emptyTextLayout)
    View emptyView;
    @BindView(R.id.empty_img)
    ImageView empty;
    SearchAdapter productsAdapter;
    List<SearchResult> products = new ArrayList<>();
    String nextProductURL, searchTitle;
    private GridLayoutManager layoutManager;
    private Boolean first = true;
    private ProductItem productItem;
    private String searchQuery = "";
    private Gson mGson;
    private List<WcProduct> productList = new ArrayList<>();
    private EndlessRecyclerGridScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        ButterKnife.bind(this);
        setToolbar();
        //   setUpRecyclerView();
        init();


    }

    private void setUpRecyclerView() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                title.setVisibility(View.GONE); // when user scrolls the recyclerview it hides the search title
            }
        });

    }


    private void init() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.addItemDecoration(new GridViewItemDecoration(this));
        recyclerView.setLayoutManager(layoutManager);
//            Log.d("nextPageAPISearch","called");
        scrollListener = new EndlessRecyclerGridScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Log.d("nextpage", "" + nextProductURL+" productsize:"+products.size());
                //  Toast.makeText(getApplicationContext(),"called"+nextProductURL,Toast.LENGTH_LONG).show();
                if (nextProductURL != null) {
                    String[] nextPageTitle = Utilities.getNextPageNo(nextProductURL);
//                    fetchMoreProducts(nextPageTitle);
                }

            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        if (Utilities.isConnectionAvailable(this)) {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
//            searchWcProducts(searchQuery);
            searchProductsInWC(searchQuery);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            empty.setImageDrawable(getResources().getDrawable(R.drawable.ic_plug));
            emptyText.setText("OOPS, out of Connection");
        }

    }

    private void searchProductsInWC(String searchQuery) {
        progressBar.setVisibility(View.VISIBLE);
        WcProductsAPI productsAPI = App.getWcRetrofit().create(WcProductsAPI.class);
        productsAPI.searchProducts(searchQuery).enqueue(new Callback<WcProductResponse>() {
            @Override
            public void onResponse(Call<WcProductResponse> call, Response<WcProductResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    productList = response.body().products;
                    if (productList.isEmpty()) {
                    } else {
                        recyclerView.setAdapter(new SearchAdapter(productList, SearchableActivity.this, new SearchAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(WcProduct item) {
                                Intent intent = new Intent(SearchableActivity.this, ProductDetailsActivity.class);
                                if (item.categories != null)
                                    intent.putExtra("sub_cat", item.categories.get(0));
                                intent.putExtra("product_id", item.id);
                                intent.putExtra("product_url", item.permalink);
                                intent.putExtra("product_name", item.title);
                                intent.putExtra(Keys.WCPRODUCT, item);
                                startActivity(intent);
                            }
                        }));
                    }

                } else {
                    try {
                        Log.d("retrofit", "errorbody" + response.errorBody().string());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<WcProductResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        menu.findItem(R.id.action_search).setEnabled(false);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setBackgroundColor(getResources().getColor(R.color.white));
        searchViewAndroidActionBar.findViewById(android.support.v7.appcompat.R.id.search_close_btn).setEnabled(true);
        searchViewAndroidActionBar.setMaxWidth(Integer.MAX_VALUE);
        searchViewAndroidActionBar.setIconified(false);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                Utilities.hideSoftKeyboard(SearchableActivity.this);
                searchProductsInWC(query);// closes keyboard after user submits
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null) {
                    Log.d("OOPS", newText);
                    // searchTitle = newText;
                    searchProductsInWC(newText);
                }
                return false;

            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("StaticFieldLeak")
    private void searchWcProducts(String queryText) {
        mGson = new Gson();
        searchQuery = queryText;
        if (Utilities.isConnectionAvailable(SearchableActivity.this)) {
            new GetDataTask(GetDataTask.METHOD_GET) {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                protected void onPostExecute(String[] result) {
                    super.onPostExecute(result);
                    progressBar.setVisibility(View.GONE);
                    progressLayout.setVisibility(View.GONE);
                    if (result.length != 0) Log.d("woocommerce", "products" + result[0]);
                    JsonElement jsonCategories = new JsonParser().parse(result[0]).getAsJsonObject().get("products");
                    productList = new ArrayList<>();
                    productList.addAll((List<WcProduct>) mGson.fromJson(jsonCategories, new TypeToken<List<WcProduct>>() {
                    }.getType()));
                    if (productList.size() == 0) {
                        progressTextView.setVisibility(View.VISIBLE);
                        progressTextView.setText(getResources().getString(R.string.product_empty));
                        progressTextView.setTextSize(20);
                        progressTextView.setTextColor(getResources().getColor(R.color.colorAccent));
                    } else {
                        recyclerView.setAdapter(new SearchAdapter(productList, SearchableActivity.this, new SearchAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(WcProduct item) {
                                Intent intent = new Intent(SearchableActivity.this, ProductDetailsActivity.class);
                                if (item.categories != null)
                                    intent.putExtra("sub_cat", item.categories.get(0));
                                intent.putExtra("product_id", item.id);
                                intent.putExtra("product_url", item.permalink);
                                intent.putExtra("product_name", item.title);
                                intent.putExtra(Keys.WCPRODUCT, item);
                                startActivity(intent);
                            }
                        }));
                    }
                }

            }.execute(Url.searchProducts(searchQuery));
        } else {
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}


