package com.susankya.swadesibidhesi.activities.user;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.joanzapata.iconify.widget.IconTextView;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcCustomerAPI;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcProductCategoriesAPI;
import com.susankya.swadesibidhesi.BuildConfig;
import com.susankya.swadesibidhesi.Generic.Flavor;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.WooCommerce.NavCategoriesListAdapter;
import com.susankya.swadesibidhesi.adapters.user.NavRecyclerViewAdapter;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.fragments.user.HomeFragment;
import com.susankya.swadesibidhesi.models.WooCommerce.WcCustomer;
import com.susankya.swadesibidhesi.models.WooCommerce.WcCustomerResponse;
import com.susankya.swadesibidhesi.models.WooCommerce.WcNavCategoryResponse;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductCategory;
import com.susankya.swadesibidhesi.models.user.CatLevelOne;
import com.susankya.swadesibidhesi.notifications.NotificationCountSetClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.susankya.swadesibidhesi.Generic.Keys.CUSTOMER_ID;
import static com.susankya.swadesibidhesi.Generic.Keys.USER_LOGGED_IN;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawer;
    @BindView(R.id.nav_list)
    public RecyclerView recyclerView;
    @BindView(R.id.fragment_container)
    public FrameLayout frameLayout;
    @BindView(R.id.txtLogin)
    public TextView txtLoginView;
    //    @BindView(R.id.userName)
//    public TextView userName;
    @BindView(R.id.txtOnline)
    public TextView txtOnline;
    @BindView(R.id.appNameLayout)
    public LinearLayout appNameLayout;
    //    @BindView(R.id.loginLayout)
//    public LinearLayout loginLayout;
    @BindView(R.id.homeView)
    public TextView txtHomeView;
    @BindView(R.id.imageView)
    public ImageView profilePic;
    @BindView(R.id.txtSignUp)
    public TextView txtSignUp;
    @BindView(R.id.seeMyOrders)
    public IconTextView txtMyOrders;
    @BindView(R.id.orders)
    public IconTextView myOrders;
    @BindView(R.id.connectToFacebook)
    public IconTextView connectToFacebook;
    //    @BindView(R.id.settings)
//    public IconTextView logOut;
    @BindView(R.id.facebook)
    public CardView facebook;
    @BindView(R.id.instagram)
    public CardView instagram;
    @BindView(R.id.twitter)
    public CardView twitter;
    public List<CatLevelOne> catLevelOneList = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private NavRecyclerViewAdapter adapter;
    private ActionBarDrawerToggle toggle;
    private Gson mGson;
    private List<WcProductCategory> mCategories = new ArrayList<>();
    List<WcProductCategory> parentList = new ArrayList<>();
    List<WcProductCategory> childrenList = new ArrayList<>();
    private boolean isLoggedIn = false;
    private String phoneNo;
    private String fbLink, instaLink, twitterLink;
    private static TextView userName;
    private static LinearLayout loginLayout;
    private static IconTextView logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        userName = findViewById(R.id.userName);
        logOut = findViewById(R.id.settings);
        loginLayout = findViewById(R.id.loginLayout);
        addHomeFragment(); //adding fragment to this activity
        setUpToolbar();
        initFlavors();
        init(); // view initialization and listener
        updateNavHeader();
        fetchCategories();
//        Fabric.with(this, new Crashlytics());
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.mbsh_tb_icon);
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        toggle.syncState();
    }

    private void initFlavors() {
        switch (BuildConfig.FLAVOR) {
            case Flavor.MBSH:
                toolbar.setNavigationIcon(R.drawable.ic_hamburger_mbsh);
                profilePic.setImageResource(R.drawable.ic_mbsh);
                break;
            default:
                break;
        }
    }

    public static void updateNavHeader() {
        if (App.db().getBoolean(USER_LOGGED_IN)) {
            String fname = App.db().getObject(Keys.CUSTOMER, WcCustomer.class).first_name;
            String lastNAme = App.db().getObject(Keys.CUSTOMER, WcCustomer.class).last_name;
            userName.setVisibility(View.VISIBLE);
            userName.setText(fname + " " + lastNAme);
            loginLayout.setVisibility(View.GONE);
            logOut.setVisibility(View.VISIBLE);
        }
    }

    private void addHomeFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    // sets toolbar and listener
    public void init() {
        setTitle(getString(R.string.app_name));
        isLoggedIn = App.db().getBoolean(USER_LOGGED_IN);

        connectToFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.START);
                Toast.makeText(HomeActivity.this, "Opening up Facebook, Please wait...", Toast.LENGTH_SHORT).show();
                if (BuildConfig.FLAVOR.equals(Flavor.MBSH)) openMBSHFacebook();
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.START);
                Toast.makeText(HomeActivity.this, "Opening up Twitter, Please wait...", Toast.LENGTH_SHORT).show();
                openTwitter();
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.START);
                Toast.makeText(HomeActivity.this, "Opening up Instagram, Please wait...", Toast.LENGTH_SHORT).show();
                openInstagram();
            }
        });

        txtLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.START);
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.START);
                Intent intent = new Intent(HomeActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.START);
                Intent intent = new Intent(HomeActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.START);
                App.db().putBoolean(USER_LOGGED_IN, false);
                Toast.makeText(HomeActivity.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            }
        });
        txtHomeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.START);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new HomeFragment());
//                transaction.addToBackStack("Home");
                transaction.commit();
            }
        });

    }

    private void openMBSHFacebook() {
        try {
            getApplicationContext().getPackageManager().getPackageInfo("com.facebook.katana", 0);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/mbshnepal>")));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/mbshnepal")));
        }
    }

    private void openInstagram() {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (getApplicationContext().getPackageManager().getPackageInfo("com.instagram.android", 0) != null) {
                if (instaLink.endsWith("/")) {
                    instaLink = instaLink.substring(0, instaLink.length() - 1);
                }
                final String username = instaLink.substring(instaLink.lastIndexOf("/") + 1);
                // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
                intent.setData(Uri.parse("http://instagram.com/_u/" + username));
                intent.setPackage("com.instagram.android");
//                return intent;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        intent.setData(Uri.parse(instaLink));
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "Please Install Instagram", Toast.LENGTH_LONG).show();
        }
//        startActivity(intent);
    }

    private void openTwitter() {
        Intent intent = null;
        try {
            // get the Twitter app if possible
            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            if (BuildConfig.FLAVOR.equals(Flavor.MBSH))
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=1045981356868726784"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterLink));
        }
        startActivity(intent);
    }

    private void fetchCategories() {
        WcProductCategoriesAPI categoriesAPI = App.getWcRetrofit().create(WcProductCategoriesAPI.class);
        categoriesAPI.getNavCategories().enqueue(new Callback<WcNavCategoryResponse>() {
            @Override
            public void onResponse(Call<WcNavCategoryResponse> call, Response<WcNavCategoryResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().product_categories != null) {
                        mCategories = response.body().product_categories;
                        layoutManager = new LinearLayoutManager(HomeActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(new NavCategoriesListAdapter(getWcCategories(mCategories), HomeActivity.this, getWcCategories(mCategories)));
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

    private List<WcProductCategory> getWcCategories(List<WcProductCategory> mCategories) {
        List<WcProductCategory> list = new ArrayList<>();
        for (WcProductCategory category : mCategories) {
            int parent = category.parent;
            int count = category.count;
            String slug = category.slug;
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
        return list;
    }

    private void fetchCustomerDetail(String user_email) {
        WcCustomerAPI customerAPI = App.getWcRetrofit().create(WcCustomerAPI.class);
        customerAPI.fetchCustomerDetail(user_email).enqueue(new Callback<WcCustomerResponse>() {
            @Override
            public void onResponse(Call<WcCustomerResponse> call, Response<WcCustomerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WcCustomer customer = response.body().customer;
                    App.db().putObject(Keys.CUSTOMER, customer);
                    App.db().putBoolean(Keys.USER_LOGGED_IN, true);
                    App.db().putInt(CUSTOMER_ID, customer.id);
                    Log.d("customer", "onResponse: " + customer.id);
                } else {
                    try {
                        Log.d("customer", "errorbody" + response.errorBody().string());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<WcCustomerResponse> call, Throwable t) {
                Log.d("customer", "onFailure: " + t);
            }
        });
    }


    //sets title
    public void setTitle(String title) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarIcons));
    }

    public void performNoBackStackTransaction(String tag, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        final int newBackStackLength = fragmentManager.getBackStackEntryCount() + 1;

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem cart = menu.findItem(R.id.cart);
        MenuItem search = menu.findItem(R.id.search);
        MenuItem fav = menu.findItem(R.id.fav);
        MenuItem quotation = menu.findItem(R.id.quotation);
        MenuItem call = menu.findItem(R.id.call);
        fav.setVisible(false);
        NotificationCountSetClass.setAddToCart(HomeActivity.this, cart, App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_BASKET).size());
//        NotificationCountSetClass.setAddToQuotation(HomeActivity.this, quotation, App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_Quotation).size());
        if (cart != null) {
            tintMenuIcon(HomeActivity.this, cart, R.color.toolbarIcons);
        }
        if (search != null) {
            tintMenuIcon(HomeActivity.this, search, R.color.toolbarIcons);
        }
        if (call != null) {
            tintMenuIcon(HomeActivity.this, call, R.color.toolbarIcons);
        }
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    public static void tintMenuIcon(Context context, MenuItem item, @ColorRes int color) {
        Drawable normalDrawable = item.getIcon();
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
        DrawableCompat.setTint(wrapDrawable, context.getResources().getColor(color));
        item.setIcon(wrapDrawable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.search:
                Intent intent = new Intent(HomeActivity.this, SearchableActivity.class);
                startActivity(intent);
                break;
            case R.id.cart:
                Intent intent1 = new Intent(HomeActivity.this, AddToCartActivity.class);
                intent1.putExtra(Keys.FROM_QUOTE, false);
                startActivity(intent1);
                break;
            case R.id.call:
                Toast.makeText(getApplicationContext(), "Please wait", Toast.LENGTH_SHORT).show();
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.fromParts("tel", getResources().getString(R.string.phone), null));
                startActivity(phoneIntent);
                break;
            case R.id.fav:
                Intent intent2 = new Intent(HomeActivity.this, WishlistActivity.class);
                startActivity(intent2);
                break;
            case R.id.quotation:
                Intent intent3 = new Intent(HomeActivity.this, AddToCartActivity.class);
                intent3.putExtra(Keys.FROM_QUOTE, true);
                startActivity(intent3);
                break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (fragment instanceof HomeFragment) {
                    if (doubleBackToExitPressedOnce) {
                        super.onBackPressed();
                        return;
                    }

                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);
                } else {
                    super.onBackPressed();
                }
            }
        }
    }
}