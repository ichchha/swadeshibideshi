package com.susankya.swadesibidhesi.APIs.WooCommerce;

import com.susankya.swadesibidhesi.models.WooCommerce.WcCustomerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Ichha on 3/21/2018.
 */

public interface WcCustomerAPI {
    @GET("customers/email/{email_address}")
    Call<WcCustomerResponse> fetchCustomerDetail(@Path("email_address") String email);

    @POST("customers")
    Call<WcCustomerResponse> createCustomer(@Body WcCustomerResponse customer);
}
