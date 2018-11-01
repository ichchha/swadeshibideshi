package com.susankya.swadesibidhesi.activities.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.susankya.swadesibidhesi.APIs.WooCommerce.WCShippingAPI;
import com.susankya.swadesibidhesi.BuildConfig;
import com.susankya.swadesibidhesi.Generic.Flavor;
import com.susankya.swadesibidhesi.Generic.FragmentKeys;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.fragments.user.AddShippingAddressFragment;
import com.susankya.swadesibidhesi.models.WooCommerce.WcShippingZoneMethod;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

import static com.susankya.swadesibidhesi.Generic.Keys.FROM_QUOTE;

public class CheckOutActivity extends AppCompatActivity {
    public static String checkout_email = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        ButterKnife.bind(this);
        setUpToolbar();
        //Toast.makeText(this,""+checkoutDetails.get(0).toString(),Toast.LENGTH_SHORT).show();
        init();
    }

    private void setUpToolbar() {
        toolbar.setTitle(getResources().getString(R.string.checkout));
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarIcons));
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (BuildConfig.FLAVOR.equalsIgnoreCase("bhumis"))
            this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_bhumis);
        else this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    private void init() {
        if(BuildConfig.FLAVOR.equals(Flavor.MBSH)) getShippingCharge();  //only show shipping charge on wowgrabus for now;
        Bundle details = new Bundle();
        details.putString(FragmentKeys.BASKET_LINE, getIntent().getExtras().getString(FragmentKeys.BASKET_LINE));
        details.putString(FragmentKeys.BASKET_URL, getIntent().getExtras().getString(FragmentKeys.BASKET_URL));
        details.putString(FragmentKeys.BASKET_TOTAL, getIntent().getExtras().getString(FragmentKeys.BASKET_TOTAL));
        details.putString(FragmentKeys.CURRENCY, getIntent().getExtras().getString(FragmentKeys.CURRENCY));
        details.putString(FragmentKeys.GUEST_EMAIL, getIntent().getExtras().getString(FragmentKeys.GUEST_EMAIL));
        details.putBoolean(FROM_QUOTE, getIntent().getExtras().getBoolean(Keys.FROM_QUOTE));

        Log.d("fromQuote", "init:checkout "+getIntent().getExtras().getBoolean(Keys.FROM_QUOTE));
        AddShippingAddressFragment fragment = new AddShippingAddressFragment();
        fragment.setArguments(details);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.checkout_container, fragment)
                .commit();
    }

    public static void getShippingCharge() {
        WCShippingAPI shippingAPI = App.getShippingRetrofit().create(WCShippingAPI.class);
        retrofit2.Call<List<WcShippingZoneMethod>> call = shippingAPI.getShippingCostDetails();
        call.enqueue(new Callback<List<WcShippingZoneMethod>>() {
            @Override
            public void onResponse(retrofit2.Call<List<WcShippingZoneMethod>> call, Response<List<WcShippingZoneMethod>> response) {
                if (response.isSuccessful()) {
                    Log.d("LoginFA", "reached here" );
                    if (!response.body().isEmpty()) {
                        App.db().putString(Keys.COST, response.body().get(0).settings.cost.value);
                        App.db().putBoolean(Keys.COST_FETCHED, true);
                        App.db().putBoolean(Keys.SHIPPING_COST_ENABLED,response.body().get(0).enabled);
                    }
                } else {
                    try {
                        Log.d("LoginFA", "" + response.errorBody().string());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<WcShippingZoneMethod>> call, Throwable t) {
                Log.d("LoginFA", "" + t.toString());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}
