package com.susankya.swadesibidhesi.fragments.user;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.susankya.swadesibidhesi.Generic.FragmentKeys;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.Generic.Utilities;
import com.susankya.swadesibidhesi.ItemDecorations.ItemOffsetDecoration;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.AddressesListAdapter;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcBilling;
import com.susankya.swadesibidhesi.models.WooCommerce.WcCustomer;
import com.susankya.swadesibidhesi.models.user.Basket;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.susankya.swadesibidhesi.Generic.Keys.CUSTOMER;
import static com.susankya.swadesibidhesi.Generic.Keys.FROM_QUOTE;
import static com.susankya.swadesibidhesi.Generic.Keys.USER_LOGGED_IN;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddShippingAddressFragment extends Fragment {
    @BindView(R.id.firstname_layout)
    TextInputLayout firstNameLayout;
    @BindView(R.id.lastname_layout)
    TextInputLayout lastNameLayout;
    @BindView(R.id.first_address_layout)
    TextInputLayout firstAddressLayout;
    @BindView(R.id.email_address_layout)
    TextInputLayout emailAddressLayout;
    @BindView(R.id.city_layout)
    TextInputLayout cityLayout;
    @BindView(R.id.addressListLayout)
    LinearLayout addressListLayout;
    @BindView(R.id.addressesRV)
    RecyclerView recyclerView;
    @BindView(R.id.phone_layout)
    TextInputLayout phoneLayout;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.first_address)
    EditText firstAddress;
    @BindView(R.id.second_address)
    EditText secondAddress;
    @BindView(R.id.email_address)
    EditText emailAddress;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.phone)
    EditText phoneNo;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.paymentLayout)
    LinearLayout paymentLayout;
    @BindView(R.id.cashOnDelivery)
    TextView cashOnDelivery;
    private int MAX_LENGTH = 10;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private String basketUrl, basketTotal, currency, basketLine, guestEmail;
    private Basket basket;
    private boolean fromQuote;

    public AddShippingAddressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_shipping_address, container, false);
        ButterKnife.bind(this, view);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
        setUpToolbar();
        return view;
    }

    private void setUpToolbar() {
        getActivity().setTitle(R.string.shipping_address);
    }

    private void init() {
        checkLoggedIn();
        basketUrl = getArguments().getString(FragmentKeys.BASKET_URL);
        basketTotal = getArguments().getString(FragmentKeys.BASKET_TOTAL);
        basketLine = getArguments().getString(FragmentKeys.BASKET_LINE);
        currency = getArguments().getString(FragmentKeys.CURRENCY);
        guestEmail = getArguments().getString(FragmentKeys.GUEST_EMAIL);
        fromQuote = getArguments().getBoolean(Keys.FROM_QUOTE);
        basket = new Basket(basketUrl, basketLine, basketTotal, currency);
        getWcAddressList();
//        if (Utilities.isConnectionAvailable(getActivity()) && Utilities.isLoggedIn(getActivity())) getWcAddressList();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkLoggedIn() {
        if (App.db().getBoolean(USER_LOGGED_IN)) fillUpData();
    }

    private void fillUpData() {
        WcCustomer customer = App.db().getObject(CUSTOMER, WcCustomer.class);
        firstName.setText(customer.billing_address.first_name);
        String lastNAme = customer.billing_address.last_name;
        String last[];
        if (lastNAme.contains(" ")) {
            last = lastNAme.split(" ");
            lastName.setText(last[0]);
        } else lastName.setText(customer.billing_address.last_name);
        firstAddress.setText(customer.billing_address.address_1);
        secondAddress.setText(customer.billing_address.address_2);
        emailAddress.setText(customer.billing_address.email);
        city.setText(customer.billing_address.city);
        phoneNo.setText(customer.billing_address.phone);
    }

    private void getWcAddressList() {
        if (!App.db().getBillingAddressList(FragmentKeys.WC_BILLING_ADDRESS_LIST).isEmpty()) {
            addressListLayout.setVisibility(View.VISIBLE);
            gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.billing_address_space));
            recyclerView.setAdapter(new AddressesListAdapter(fromQuote, App.db().getBillingAddressList(FragmentKeys.WC_BILLING_ADDRESS_LIST), getActivity()));
        }
    }

    private void checkValidation() {
        Boolean fnameErr = false, lnameErr = false, emailErr = false, firstlineaddressErr = false, cityErr = false, phoneErr = false;
        String fname = firstName.getText().toString().trim();
        String lname = lastName.getText().toString().trim();
        String firstLineAddress = firstAddress.getText().toString().trim();
        String secondLineAddress = secondAddress.getText().toString().trim();
        String email_address = emailAddress.getText().toString().trim();
        String cityName = city.getText().toString().trim();
        String phone = phoneNo.getText().toString().trim();

        if (fname.isEmpty()) {
            fnameErr = true;
            firstNameLayout.setError("Please enter your first name*");
        } else firstNameLayout.setError(null);
        if (lname.isEmpty()) {
            lnameErr = true;
            lastNameLayout.setError("Please enter your last name*");
        } else lastNameLayout.setError(null);
        if (firstLineAddress.isEmpty()) {
            firstlineaddressErr = true;
            firstAddressLayout.setError("Please enter first line of address*");
        } else firstAddressLayout.setError(null);
        if (email_address.isEmpty()) {
            emailErr = true;
            emailAddressLayout.setError("Please enter your email address*");
        } else emailAddressLayout.setError(null);
        if (cityName.isEmpty()) {
            cityErr = true;
            cityLayout.setError("Please enter the city name*");
        } else cityLayout.setError(null);
        // mobile validation
        if (phone.isEmpty()) {
            phoneErr = true;
            phoneLayout.setError("Please enter phone no *");
        } else if (phone.length() < MAX_LENGTH) {
            phoneErr = true;
            phoneLayout.setError("Number should not be less than " + MAX_LENGTH + ".");
        } else if (phone.length() > MAX_LENGTH) {
            phoneErr = true;
            phoneLayout.setError("Number should not be more than " + MAX_LENGTH + ".");
        } else {
            phoneLayout.setError(null);
        }

        if (!fnameErr && !lnameErr && !firstlineaddressErr && !cityErr && !phoneErr && !emailErr) {
            WcBilling billing = new WcBilling();
            billing.first_name = fname;
            billing.last_name = lname;
            billing.address_1 = firstLineAddress;
            billing.address_2 = secondLineAddress;
            billing.city = cityName;
            billing.phone = "+977 " + phone;
            billing.email = email_address;
            billing.country = "Nepal";
            Bundle details = new Bundle();
            details.putParcelable(FragmentKeys.WC_BILLING, billing);
            details.putBoolean(FROM_QUOTE, fromQuote);
            Utilities.addWcBillingAddress(getActivity(), billing);

            Log.d("fromQuote", "checkValidation: shipping" + fromQuote);

            FinalCheckOutPageFragment fragment = new FinalCheckOutPageFragment();
            fragment.setArguments(details);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.checkout_container, fragment)
                    .commit();
        }
    }
}
