package com.susankya.swadesibidhesi.APIs.WooCommerce;

import com.susankya.swadesibidhesi.models.WooCommerce.WcProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WcHomeAPI {
    @GET("products")
    Call<WcProductResponse> getProductsOfTheParticularCategory(@Query("filter[category]") String category_slug);
}
