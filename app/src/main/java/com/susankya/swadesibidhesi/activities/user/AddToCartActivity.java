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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.susankya.swadesibidhesi.BuildConfig;
import com.susankya.swadesibidhesi.Generic.FragmentKeys;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.Generic.Utilities;
import com.susankya.swadesibidhesi.ItemDecorations.VerticalSpaceItemDecoration;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.AddToCartAdapter;
import com.susankya.swadesibidhesi.adapters.user.CartProductListAdapter;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductCategory;
import com.susankya.swadesibidhesi.models.user.BasketResultItem;
import com.susankya.swadesibidhesi.models.user.Cart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.susankya.swadesibidhesi.Generic.Keys.FROM_CHECKOUT;
import static com.susankya.swadesibidhesi.Generic.Keys.FROM_QUOTE;
import static com.susankya.swadesibidhesi.Generic.Keys.USER_LOGGED_IN;

public class AddToCartActivity extends AppCompatActivity {
    @BindView(R.id.cartRecyclerView)
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnPlaceOrder)
    Button btnPlaceOrder;
    private LinearLayoutManager layoutManager;
    private AddToCartAdapter adapter;
    List<BasketResultItem> basketResultItemList = new ArrayList<>();
    Cart cart = new Cart();
    private String basket_url, guest_email = "", total_incl_tax, currency, basket_line;
    private int noOfBasketItems;
    ArrayList<Integer> productIDListOfWishlistItems = new ArrayList<>();
    private Gson mGson;
    private List<WcProductCategory> mCategories = new ArrayList<>();
    private boolean fromQuote = false;
    private List<WcProduct> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        ButterKnife.bind(this);
        init();
        setUpToolbar();
        Log.d("wcbasket", App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_BASKET).size() + "");

    }

    private void setUpToolbar() {
        if (fromQuote) {
            btnPlaceOrder.setText("Get Quotation");
            toolbar.setTitle(getResources().getString(R.string.quotation));
        } else toolbar.setTitle(getResources().getString(R.string.add_to_cart));
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarIcons));
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (BuildConfig.FLAVOR.equalsIgnoreCase("bhumis"))
            this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_bhumis);
        else this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }


    private void init() {
        if (getIntent() != null) fromQuote = getIntent().getBooleanExtra(FROM_QUOTE, false);

        if (Utilities.isConnectionAvailable(this)) {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            getWcBasket();

        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            empty.setImageDrawable(getResources().getDrawable(R.drawable.ic_plug));
            emptyText.setText("OOPS, out of Connection");
        }

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.db().getBoolean(USER_LOGGED_IN)) {
                    Bundle details = new Bundle();
                    details.putString(FragmentKeys.BASKET_LINE, basket_line);
                    details.putString(FragmentKeys.BASKET_URL, basket_url);
                    details.putString(FragmentKeys.BASKET_TOTAL, total_incl_tax);
                    details.putString(FragmentKeys.CURRENCY, currency);
                    details.putBoolean(Keys.FROM_QUOTE, fromQuote);
                    Intent intent = new Intent(AddToCartActivity.this, CheckOutActivity.class);
                    intent.putExtras(details);
                    startActivity(intent);
                    finish();
                } else {
                    showDialog();
                }
            }
        });
    }

    public void showDialog() {
        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Guest Checkout")
                .titleColor(getResources().getColor(R.color.colorBlack))
                .content("Please Log in to view your order details in future. Order will made as Guest if not logged in.")
                .contentColor(getResources().getColor(R.color.colorBlack))
                .canceledOnTouchOutside(false)
                .positiveText("Proceed")
                .positiveColor(getResources().getColor(R.color.colorPrimaryDark))
                .negativeText("Login")
                .negativeColor(getResources().getColor(R.color.colorBlack))
                .show();
        View positiveButton = dialog.getActionButton(DialogAction.POSITIVE);
        View negativeBtn = dialog.getActionButton(DialogAction.NEGATIVE);
        positiveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Bundle details = new Bundle();
                        details.putString(FragmentKeys.BASKET_LINE, basket_line);
                        details.putString(FragmentKeys.BASKET_URL, basket_url);
                        details.putString(FragmentKeys.BASKET_TOTAL, total_incl_tax);
                        details.putString(FragmentKeys.CURRENCY, currency);
                        details.putBoolean(Keys.FROM_QUOTE, fromQuote);
                        Intent intent = new Intent(AddToCartActivity.this, CheckOutActivity.class);
                        intent.putExtras(details);
                        startActivity(intent);
                        finish();
                    }
                });
        negativeBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(AddToCartActivity.this, LoginActivity.class);
                        intent.putExtra(FROM_CHECKOUT, true);
                        intent.putExtra(FROM_QUOTE, fromQuote);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void getWcBasket() {

        if (fromQuote) list = App.db().getListCartItem(Keys.PRODUCT_LIST_IN_Quotation);
        else list = App.db().getListCartItem(Keys.PRODUCT_LIST_IN_WC_BASKET);
        if (list.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyText.setText("Sorry, there are no items.");
            empty.setImageResource(R.drawable.empty_basket);
        } else {
            Log.d("TestWc", "Basket Size" + App.db().getListCartItem(Keys.PRODUCT_LIST_IN_WC_BASKET).size());
            btnPlaceOrder.setVisibility(View.VISIBLE);
            layoutManager = new LinearLayoutManager(AddToCartActivity.this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(12));
            if (fromQuote) {
                recyclerView.setAdapter(new CartProductListAdapter(App.db().getListCartItem(Keys.PRODUCT_LIST_IN_Quotation), fromQuote, AddToCartActivity.this, new CartProductListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(WcProduct item) {
                        Toast.makeText(AddToCartActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                    }
                }));
            } else {
                recyclerView.setAdapter(new CartProductListAdapter(App.db().getListCartItem(Keys.PRODUCT_LIST_IN_WC_BASKET), false, AddToCartActivity.this, new CartProductListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(WcProduct item) {
                        Toast.makeText(AddToCartActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                    }
                }));
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}
