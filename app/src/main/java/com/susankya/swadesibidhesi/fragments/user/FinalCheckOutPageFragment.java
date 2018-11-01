package com.susankya.swadesibidhesi.fragments.user;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.susankya.swadesibidhesi.APIs.WooCommerce.WcCheckoutAPI;
import com.susankya.swadesibidhesi.BuildConfig;
import com.susankya.swadesibidhesi.Generic.Flavor;
import com.susankya.swadesibidhesi.Generic.FragmentKeys;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.Generic.Utilities;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.CheckOutActivity;
import com.susankya.swadesibidhesi.activities.user.HomeActivity;
import com.susankya.swadesibidhesi.activities.user.OrdersActivity;
import com.susankya.swadesibidhesi.adapters.user.finalItemsPreviewAdapter;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.ShippingLine;
import com.susankya.swadesibidhesi.models.WooCommerce.WcBilling;
import com.susankya.swadesibidhesi.models.WooCommerce.WcCheckOut;
import com.susankya.swadesibidhesi.models.WooCommerce.WcLineItem;
import com.susankya.swadesibidhesi.models.WooCommerce.WcOrderRequest;
import com.susankya.swadesibidhesi.models.WooCommerce.WcOrderResponse;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.WooCommerce.WcShipping;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.susankya.swadesibidhesi.Generic.Keys.COST;
import static com.susankya.swadesibidhesi.Generic.Keys.CUSTOMER_ID;
import static com.susankya.swadesibidhesi.Generic.Keys.FROM_QUOTE;
import static com.susankya.swadesibidhesi.Generic.Keys.USER_LOGGED_IN;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinalCheckOutPageFragment extends Fragment {

    @BindView(R.id.parent_layout)
    RelativeLayout parentLayout;
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
    @BindView(R.id.order_total)
    TextView order_total;
    @BindView(R.id.total_payable)
    TextView total_payable;
    @BindView(R.id.deliverTo)
    TextView deliverTo;
    @BindView(R.id.payment_layout)
    LinearLayout paymentLayout;
    @BindView(R.id.topLayout)
    LinearLayout topLayout;
    @BindView(R.id.shippingAddress_layout)
    LinearLayout shippingLayout;
    @BindView(R.id.emptyTextLayout)
    View emptyView;
    @BindView(R.id.total_items)
    TextView total_items;
    @BindView(R.id.empty_img)
    ImageView empty;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.firstLineAddress)
    TextView firstLineAddress;
    @BindView(R.id.cityName)
    TextView cityName;
    @BindView(R.id.delivery)
    TextView deliveryCharge;
    @BindView(R.id.phoneNo)
    TextView phoneNo;
    @BindView(R.id.btnPlaceOrder)
    Button btnPlaceOrder;
    private LinearLayoutManager layoutManager;
    int basketLineId;
    String basketLine;
    private WcBilling wcBilling;
    private Gson mGson;
    private Float lineTotal = 0.0f;
    private List<ShippingLine> shippinLines;
    private boolean fromQuote = false;

    public FinalCheckOutPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final_check_out_page, container, false);
        ButterKnife.bind(this, view);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
        getWcBasketItems();
        return view;
    }

    private void getWcBasketItems() {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        total_items.setText(String.valueOf(App.db().getListCartItem(Keys.PRODUCT_LIST_IN_WC_BASKET).size()));
        recyclerView.setAdapter(new finalItemsPreviewAdapter(deliveryCharge, order_total, total_payable, App.db().getListCartItem(Keys.PRODUCT_LIST_IN_WC_BASKET), getActivity()));
    }

    private void init() {
        if (getArguments() != null) {
            fromQuote = getArguments().getBoolean(FROM_QUOTE);
            wcBilling = getArguments().getParcelable(FragmentKeys.WC_BILLING);
        }
        if (BuildConfig.FLAVOR.equals(Flavor.MBSH)) checkIfShippingChargeFetched();
        userName.setText(wcBilling.first_name + " " + wcBilling.last_name);
        phoneNo.setText(wcBilling.phone);
        firstLineAddress.setText(wcBilling.address_1);
        cityName.setText(wcBilling.city);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utilities.isConnectionAvailable(getActivity()))
                    performWcCheckout();
                else
                    Toast.makeText(getActivity(), "There is no internet connection. Please try again later!", Toast.LENGTH_SHORT).show();
            }
        });

        List<WcProduct> cartItems = App.db().getListCartItem(Keys.PRODUCT_LIST_IN_WC_BASKET);
        Float price, quantity, totalPrice;
        for (int i = 0; i < cartItems.size(); i++) {
            quantity = Float.valueOf(cartItems.get(i).quantity);
            if (cartItems.get(i).price != "") {
                price = Float.valueOf(cartItems.get(i).price);
                totalPrice = quantity * price;
                lineTotal = lineTotal + totalPrice;
            }
        }


    }

    private void checkIfShippingChargeFetched() {
        if (!App.db().getBoolean(Keys.COST_FETCHED)) CheckOutActivity.getShippingCharge();
        else {
            ShippingLine shippingLine = new ShippingLine();
            shippingLine.method_id = "flat_rate";
            shippingLine.method_title = "Flat Rate";
            shippingLine.total = Float.valueOf(App.db().getString(COST));
            shippinLines = new ArrayList<>();
            shippinLines.add(shippingLine);
        }
    }

    private void performWcCheckout() {
        if (fromQuote)
            wcBilling.last_name = wcBilling.last_name + getContext().getResources().getString(R.string.quotation_from_app);
        else
            wcBilling.last_name = wcBilling.last_name + getContext().getResources().getString(R.string.order_from_app);
        WcShipping wcShipping = new WcShipping();
        wcShipping.address_1 = wcBilling.address_1;
        wcShipping.address_2 = wcBilling.address_2;
        wcShipping.first_name = wcBilling.first_name;
        wcShipping.last_name = wcBilling.last_name;
        wcShipping.city = wcBilling.city;
        wcShipping.country = wcBilling.country;
        wcShipping.postcode = wcBilling.postcode;
        wcShipping.state = wcBilling.state;

        List<WcLineItem> lineItems = new ArrayList<>();
        for (int i = 0; i < App.db().getListCartItem(Keys.PRODUCT_LIST_IN_WC_BASKET).size(); i++) {
            lineItems.add(new WcLineItem(App.db().getListCartItem(Keys.PRODUCT_LIST_IN_WC_BASKET).get(i).id, App.db().getListCartItem(Keys.PRODUCT_LIST_IN_WC_BASKET).get(i).quantity));
        }

        WcOrderRequest orderRequest = new WcOrderRequest();
        orderRequest.billing_address = wcBilling;
        orderRequest.shipping_address = wcShipping;
        orderRequest.line_items = lineItems;
        orderRequest.shipping_lines = shippinLines;
        if (App.db().getBoolean(USER_LOGGED_IN))
            orderRequest.customer_id = App.db().getInt(CUSTOMER_ID);

        WcCheckOut checkOut = new WcCheckOut();
        checkOut.order = orderRequest;

        postWcCheckoutData(checkOut);
    }

    private void postWcCheckoutData(WcCheckOut checkOut) {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        WcCheckoutAPI checkoutAPI = App.getWcRetrofit().create(WcCheckoutAPI.class);
        checkoutAPI.postOrderForCheckout(checkOut).enqueue(new Callback<WcOrderResponse>() {
            @Override
            public void onResponse(Call<WcOrderResponse> call, Response<WcOrderResponse> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Utilities.addToOrdersList(response.body().order);
                    showSuccess();
                } else {
                    try {
                        Toast.makeText(getActivity(), "There was an error connecting to network. Please try again later!", Toast.LENGTH_SHORT).show();
                        Log.d("retrofit", "checkout error" + response.errorBody().string());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<WcOrderResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "There was an error connecting to network. Please try again later!", Toast.LENGTH_SHORT).show();
                Log.d("retrofit", "checkout throwable" + t);
            }
        });
    }

    private void showSuccess() {
        if (fromQuote) {
            App.db().putListInt(Keys.PRODUCT_ID_LIST_IN_Quotation, Utilities.emptyProductIDsQuotationList(getActivity()));
            App.db().putListCartItems(Keys.PRODUCT_LIST_IN_Quotation, Utilities.emptyProductsFromQuotationList(getActivity()));
            Toast.makeText(getActivity(), "" + getResources().getString(R.string.quotation_success), Toast.LENGTH_SHORT).show();
        } else {
            App.db().putListInt(Keys.PRODUCT_ID_LIST_IN_BASKET, Utilities.emptyProductIDsFromBasketList(getActivity()));
            App.db().putListCartItems(Keys.PRODUCT_LIST_IN_WC_BASKET, Utilities.emptyProductsFromBasketList(getActivity()));
            Toast.makeText(getActivity(), "" + getResources().getString(R.string.orders_success), Toast.LENGTH_SHORT).show();
        }
        getActivity().finish();
        if (App.db().getBoolean(USER_LOGGED_IN))
            startActivity(new Intent(getActivity(), OrdersActivity.class));
        else startActivity(new Intent(getActivity(), HomeActivity.class));
    }
}
