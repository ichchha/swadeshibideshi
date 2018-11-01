package com.susankya.swadesibidhesi.APIs.WooCommerce;

import com.susankya.swadesibidhesi.models.WooCommerce.WcProductResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface WcProductsAPI {
    @GET("products/")
    Call<WcProductResponse> getProducts();

    @GET("products/{id}")
    Call<WcProductResponse> getProductDetail(@Path("id") int id);

    @GET("products/")
    Call<WcProductResponse> searchProducts(@Query("filter[q]") String query);

    //to count the no of prpducts in the category
    @GET("products/count")
    Call<WcProductResponse> countNoOfProductsInTheCategpry(@Query("filter[category]") String category_slug);

    //to fetch products based on page no
    @GET("products/")
    Call<WcProductResponse> fetchProductsBasedOnPages(@QueryMap Map<String, String> filters);
}
