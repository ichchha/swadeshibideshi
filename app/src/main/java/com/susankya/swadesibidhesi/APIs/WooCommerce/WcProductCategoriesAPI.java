package com.susankya.swadesibidhesi.APIs.WooCommerce;

import com.susankya.swadesibidhesi.models.WooCommerce.WcCategoryResponse;
import com.susankya.swadesibidhesi.models.WooCommerce.WcNavCategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WcProductCategoriesAPI {
    @GET("products/categories")
    Call<WcCategoryResponse> getCategories();

    @GET("products/categories")
    Call<WcNavCategoryResponse> getNavCategories();
}
