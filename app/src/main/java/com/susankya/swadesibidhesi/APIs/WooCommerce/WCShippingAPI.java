package com.susankya.swadesibidhesi.APIs.WooCommerce;

import com.susankya.swadesibidhesi.models.WooCommerce.WcShippingZoneMethod;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ichha on 3/21/2018.
 */

public interface WCShippingAPI {

    @GET("wp-json/wc/v2/shipping/zones/1/methods")
    Call<List<WcShippingZoneMethod>> getShippingCostDetails();
}
