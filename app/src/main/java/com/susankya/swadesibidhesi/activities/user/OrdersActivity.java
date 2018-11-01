package com.susankya.swadesibidhesi.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcOrdersAPI;
import com.susankya.swadesibidhesi.BuildConfig;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.Generic.Utilities;
import com.susankya.swadesibidhesi.ItemDecorations.VerticalSpaceItemDecoration;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.OrdersAdapter;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcOrderItem;
import com.susankya.swadesibidhesi.models.WooCommerce.WcOrderResponse;
import com.susankya.swadesibidhesi.models.user.OrderLineResult;
import com.susankya.swadesibidhesi.models.user.OrderResultItem;
import com.susankya.swadesibidhesi.models.user.ProductItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.susankya.swadesibidhesi.Generic.Keys.CUSTOMER_ID;
import static com.susankya.swadesibidhesi.Generic.Keys.PRODUCT_LIST_IN_WC_ORDERS;
import static com.susankya.swadesibidhesi.Generic.Keys.USER_LOGGED_IN;

public class OrdersActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.ordersRV)
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
    @BindView(R.id.loginLayout)
    LinearLayout loginLayout;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    private LinearLayoutManager layoutManager;
    private List<OrderResultItem> orderItemsList = new ArrayList<>();
    private List<OrderLineResult> orderLineResults = new ArrayList<>();
    private List<ProductItem> productItemList = new ArrayList<>();
    ProductItem productItem = new ProductItem();
    String productUrl;
    String splittedUrl[];
    private Gson mGson;
    private List<WcOrderItem> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);
        if(App.db().getBoolean(USER_LOGGED_IN)) initWc();
        else login();
//        init();
        setUpRecyclerView();
        setUpToolbar();

    }

    private void login() {
        loginLayout.setVisibility(View.VISIBLE);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(OrdersActivity.this,LoginActivity.class);
                intent.putExtra(Keys.FROM_ORDERS,true);
                startActivity(intent);
            }
        });
    }

    private void initWc() {
        if (Utilities.isConnectionAvailable(this)) {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            getWcOrders();
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            empty.setImageDrawable(getResources().getDrawable(R.drawable.ic_plug));
            emptyText.setText("OOPS, out of Connection");
        }
    }

    private void setUpRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));
    }

    private void init() {
        List<WcOrderItem> list = App.db().getOrderLists(PRODUCT_LIST_IN_WC_ORDERS);
        if (list.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            empty.setImageDrawable(getResources().getDrawable(R.drawable.ic_orders));
            emptyText.setText("There are no any orders right now.");
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            recyclerView.setAdapter(new OrdersAdapter(list, OrdersActivity.this));
        }
    }

    private void getWcOrders() {
        progressLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        WcOrdersAPI ordersAPI = App.getWcRetrofit().create(WcOrdersAPI.class);
        ordersAPI.getOrdersOfParticularUser(App.db().getInt(CUSTOMER_ID)).enqueue(new Callback<WcOrderResponse>() {
            @Override
            public void onResponse(Call<WcOrderResponse> call, Response<WcOrderResponse> response) {
                progressBar.setVisibility(View.GONE);
                progressLayout.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().orders.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                        empty.setImageDrawable(getResources().getDrawable(R.drawable.ic_orders));
                        emptyText.setText("You have no orders!");
                    } else {
                        orderList = response.body().orders;
                        recyclerView.setAdapter(new OrdersAdapter(orderList, OrdersActivity.this));
                        Log.d("retrofit", "" + response.body().orders.size());
                    }
                } else {
                    try {
                        Log.d("retrofit", "errorbody" + response.errorBody().string());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<WcOrderResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                progressLayout.setVisibility(View.GONE);
                Log.d("retrofit", "throwable" + t);
            }
        });
    }

    private void setUpToolbar() {
        toolbar.setTitle("Your Orders");
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarIcons));
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (BuildConfig.FLAVOR.equalsIgnoreCase("bhumis"))
            this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_bhumis);
        else this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up
        return false;
    }
}
