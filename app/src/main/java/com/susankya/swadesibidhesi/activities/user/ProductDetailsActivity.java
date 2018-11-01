package com.susankya.swadesibidhesi.activities.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.joanzapata.iconify.widget.IconTextView;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcProductsAPI;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.Generic.Utilities;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.WooCommerce.ProductDetailsAdapter;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductImage;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductResponse;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductSlide;
import com.susankya.swadesibidhesi.models.user.Basket;
import com.susankya.swadesibidhesi.models.user.ProductDetails;
import com.susankya.swadesibidhesi.models.user.ProductPhotoSlide;
import com.susankya.swadesibidhesi.notifications.NotificationCountSetClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhan.transparent.widget.TransparentFrameLayout;

import static com.susankya.swadesibidhesi.Generic.Keys.FROM_QUOTE;

public class ProductDetailsActivity extends AppCompatActivity {

    @BindView(R.id.pfdRecyclerView)
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProductDetailsAdapter adapter;
    @BindView(R.id.txtCart)
    IconTextView btnAddToCart;
    @BindView(R.id.buyNow)
    IconTextView buyNowBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.emtpyTextview)
    TextView emptyText;
    @BindView(R.id.emptyTextLayout)
    View emptyView;
    @BindView(R.id.icontText)
    IconTextView iconTextView;
    @BindView(R.id.bottom_layout)
    View bottomLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tool_bar)
    TransparentFrameLayout transparentToolBar;
    private int mDy;
    private String productUrl;
    public static int productID;
    private Boolean isAddedToWishlist;
    private String SUB_CAT = "sub_cat", PRD_NAME = "product_name";
    private int initialPos;
    private boolean addedToCart = false;
    private boolean addedToQuotation = false;
    public int maxQty;
    public String toolbarTitle;
    private boolean first;
    private boolean fromBuyNow = false;
    private boolean isAvailableToBuy;
    //    private boolean gotBasketId = false;
    ArrayList<Integer> productIDListOfCartItems = new ArrayList<>();
    private Gson mGson;
    public static WcProduct wcProduct;
    private MenuItem cart, quotation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        initialPos = recyclerView.getTop();
        Intent i = getIntent();
        toolbarTitle = i.getStringExtra("sub_cat");
        productUrl = i.getStringExtra("product_url");
        wcProduct = i.getParcelableExtra(Keys.WCPRODUCT);
        productID = wcProduct.id;
        Log.d("productId", "" + productID);
        isAddedToWishlist = i.getBooleanExtra("is_added_to_wishlist", false);
        checkAddedToCart();
        setToolbar();
        getNewWCProduct();
        initViews();
    }

    private void getNewWCProduct() {
        progressBar.setVisibility(View.VISIBLE);
        WcProductsAPI productsAPI = App.getWcRetrofit().create(WcProductsAPI.class);
        productsAPI.getProductDetail(productID).enqueue(new Callback<WcProductResponse>() {
            @Override
            public void onResponse(Call<WcProductResponse> call, Response<WcProductResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    wcProduct = response.body().product;
                    toolbarTitle = wcProduct.title;
                    isAvailableToBuy = wcProduct.in_stock;
//                    Log.d("stock", "" + isAvailableToBuy);
                    adapter = new ProductDetailsAdapter(ProductDetailsActivity.this, getWcItems(wcProduct));
                    recyclerView.setAdapter(adapter);

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
            }
        });
    }

    private void checkAddedToCart() {
        productIDListOfCartItems = App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_BASKET);
        for (Integer i : productIDListOfCartItems) {
            if (i == productID) {
                btnAddToCart.setText(R.string.addedToCart);
                addedToCart = true;
            }
        }
    }

    // set the toolbar
    public void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // view initialization , listener
    public void initViews() {

        if (Utilities.isConnectionAvailable(ProductDetailsActivity.this)) {
            first = true;
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            iconTextView.setText("{mdi-wifi-off}");
            emptyText.setText("OOPS, out of connection");
        }

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        transparentToolBar.setMaxOffset(getResources().getDimension(R.dimen.offset));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mDy += dy;
                transparentToolBar.updateTop(mDy);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (mDy > 40) {
                        transparentToolBar.setElevation(5f);
                        transparentToolBar.setColorToBackGround(getResources().getColor(R.color.white));
                        if (mDy > getResources().getDimension(R.dimen.offset)) {
                            toolbar.setTitleTextColor(getResources().getColor(R.color.colorGreyLight));
                            cart.setVisible(true);
                            //toolbar.setTitle(getIntent().getStringExtra(PRD_NAME));
                            toolbar.setTitle(Html.fromHtml(toolbarTitle));
                        } else {
                            cart.setVisible(false);
                            toolbar.setTitle(null);
                        }
                    } else if (mDy < getResources().getDimension(R.dimen.offset)) {
                        cart.setVisible(false);
                        transparentToolBar.setBackground(getDrawable(R.drawable.toolbar_transparent));
                        transparentToolBar.setElevation(0f);
                    }
                }
            }
        });

        final Basket basket = new Basket();
        basket.quantity = 1;//auto quantity=1
        basket.url = productUrl;

        if (addedToCart) {
            btnAddToCart.setEnabled(false);  //to not allow user to click again\
        }
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addedToCart)
                    Toast.makeText(ProductDetailsActivity.this, "Already added to cart!", Toast.LENGTH_SHORT).show();
                fromBuyNow = false;
                if (Utilities.isConnectionAvailable(ProductDetailsActivity.this)) {
                    if (isAvailableToBuy) {
                        addToWcBasket(wcProduct, fromBuyNow);
                    } else
                        Toast.makeText(ProductDetailsActivity.this, "Sorry, the product is currently not in stock!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ProductDetailsActivity.this, "There is no internet connection available. Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromBuyNow = true;
                if (Utilities.isConnectionAvailable(ProductDetailsActivity.this)) {
                    if (isAvailableToBuy) {
                        if (addedToCart) {
                            Intent intent = new Intent(ProductDetailsActivity.this, AddToCartActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            addToWcBasket(wcProduct, fromBuyNow);
                        }
                    } else
                        Toast.makeText(ProductDetailsActivity.this, "Sorry, the product is currently not in stock!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ProductDetailsActivity.this, "There is no internet connection available. Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

    }

    private void checkAddedToCartEarlier(int productID, WcProduct wcProduct) {
        Boolean isAdded = false;
        btnAddToCart.setText(R.string.addedToCart);
        addedToCart = true;
        ArrayList<Integer> productIdList = App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_BASKET);
        ArrayList<WcProduct> productList = App.db().getListCartItem(Keys.PRODUCT_LIST_IN_WC_BASKET);
        for (int id : productIdList) {
            if (id == productID) {
                isAdded = true;
            } else {
                isAdded = false;
            }
        }
        if (isAdded) {
            for (WcProduct product : productList) {
                if (product.id == productID) {
                    product.quantity = product.quantity++;
                    Log.d("added", "checkAddedToCartEarlier: ");
                    productList.remove(product);
                    Log.d("added", "checkAddedToCartEarlier: removed");
                    productList.add(product);
                    App.db().putListCartItems(Keys.PRODUCT_LIST_IN_WC_BASKET, productList);
                } else {
                }
            }
        } else {
            Utilities.addProductToWcCart(ProductDetailsActivity.this, productID, wcProduct);
            App.wcCartProducts.add(wcProduct);
        }
        Log.d("cart", "addedToCartEarlier: " + isAdded);
//        return isAdded;
    }

    private void checkAddedToQuotationEarlier(int productID, WcProduct wcProduct) {
        Boolean isAdded = false;
        buyNowBtn.setText(R.string.addedToQuote);
        addedToQuotation = true;
        ArrayList<Integer> productIdList = App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_Quotation);
        ArrayList<WcProduct> productList = App.db().getListCartItem(Keys.PRODUCT_LIST_IN_Quotation);
        for (int id : productIdList) {
            if (id == productID) {
                isAdded = true;
            } else {
                isAdded = false;
            }
        }
        if (isAdded) {
            for (WcProduct product : productList) {
                if (product.id == productID) {
                    product.quantity = product.quantity++;
                    Log.d("added", "checkAddedToCartEarlier: ");
                    productList.remove(product);
                    Log.d("added", "checkAddedToCartEarlier: removed");
                    productList.add(product);
                    App.db().putListCartItems(Keys.PRODUCT_LIST_IN_Quotation, productList);
                } else {
                }
            }
        } else {
            Utilities.addProductToQuotation(ProductDetailsActivity.this, productID, wcProduct);
        }
        Log.d("cart", "addedToCartEarlier: " + isAdded);
//        return isAdded;
    }

    private void openMailInbox() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.email)});
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(emailIntent, "Send your email in:"));
    }

    private void addToWcBasket(WcProduct product, boolean fromBuyNow) {
        wcProduct = product;
        wcProduct.quantity = 1;
        checkAddedToCartEarlier(productID, wcProduct);
        Log.d("testy", "addToWcBasket: " + wcProduct.id);
//        if (checkAddedToCartEarlier(productID)) {

        btnAddToCart.setEnabled(false);
        Toast.makeText(ProductDetailsActivity.this, "Item added to cart successfully.", Toast.LENGTH_SHORT).show();
        if (!App.wcCartProducts.isEmpty()) Log.d("stock", "" + App.wcCartProducts.size());
//        Utilities.addProductToCart(ProductDetailsActivity.this, productID); //to store the product id
        if (fromBuyNow) {
            startActivity(new Intent(ProductDetailsActivity.this, AddToCartActivity.class));
            finish();
        }
    }

    private List<ProductDetails> getWcItems(WcProduct productItem) {
        List<ProductDetails> list = new ArrayList<>();
        list.add(new ProductPhotoSlide(getImages(productItem)));
        list.add(productItem);
//        if (!productItem.variations.isEmpty())
//            list.add(new WcProductSlide(true,"Related Items", productItem.variations));
        if (!productItem.related_ids.isEmpty())
            list.add(new WcProductSlide(false, "Suggested For You", getRecommendedProducts(productItem)));
        return list;
    }

    private List<WcProduct> getRecommendedProducts(WcProduct productItem) {
        final List<WcProduct> list = new ArrayList<>();
        if (!productItem.related_ids.isEmpty()) {
            for (int i = 0; i < productItem.related_ids.size(); i++) {
                WcProductsAPI productsAPI = App.getWcRetrofit().create(WcProductsAPI.class);
                productsAPI.getProductDetail(productItem.related_ids.get(i)).enqueue(new Callback<WcProductResponse>() {
                    @Override
                    public void onResponse(Call<WcProductResponse> call, Response<WcProductResponse> response) {
                        progressBar.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null) {
                            list.add(response.body().product);
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
        }
        return list;
    }


    private List<WcProductImage> getImages(WcProduct productItem) {
        List<WcProductImage> list = new ArrayList<>();
        for (int i = 0; i < productItem.images.size(); i++) {
            list.add(new WcProductImage(productItem.images.get(i).src));
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_details_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        cart = menu.findItem(R.id.cart);
        quotation = menu.findItem(R.id.quotation);
        cart.setVisible(true);
        NotificationCountSetClass.setAddToCart(ProductDetailsActivity.this, cart, App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_BASKET).size());
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.cart:
                Intent intent1 = new Intent(ProductDetailsActivity.this, AddToCartActivity.class);
                intent1.putExtra(FROM_QUOTE, false);
                startActivity(intent1);
                break;
            case R.id.quotation:
                Intent intent2 = new Intent(ProductDetailsActivity.this, AddToCartActivity.class);
                intent2.putExtra(FROM_QUOTE, true);
                startActivity(intent2);
                break;
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}
