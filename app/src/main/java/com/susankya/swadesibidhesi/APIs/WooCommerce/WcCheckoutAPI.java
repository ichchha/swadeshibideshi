package com.susankya.swadesibidhesi.APIs.WooCommerce;

import com.susankya.swadesibidhesi.models.WooCommerce.WcCheckOut;
import com.susankya.swadesibidhesi.models.WooCommerce.WcOrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WcCheckoutAPI {
    @POST("orders")
    Call<WcOrderResponse> postOrderForCheckout(@Body WcCheckOut checkOut);
}
