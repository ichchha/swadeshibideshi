package com.susankya.swadesibidhesi.APIs.WooCommerce;

import com.susankya.swadesibidhesi.models.WooCommerce.WcOrderResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WcOrdersAPI {
    @GET("orders")
    Call<WcOrderResponse> getOrders();

    @GET("customers/{customer_id}/orders/")
    Call<WcOrderResponse> getOrdersOfParticularUser(@Path("customer_id") int customer_id);
}
