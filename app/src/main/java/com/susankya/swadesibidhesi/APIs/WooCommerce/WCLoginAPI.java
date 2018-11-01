package com.susankya.swadesibidhesi.APIs.WooCommerce;

import com.susankya.swadesibidhesi.models.WooCommerce.WcLoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Ichha on 3/21/2018.
 */

public interface WCLoginAPI {
    @Headers({"Accept:application/json", "Content-Type:application/json;"})
    @POST("wp-json/jwt-auth/v1/token")
    Call<WcLoginResponse> userLogin(@Body WcLoginResponse login);

}
