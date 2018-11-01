package com.susankya.swadesibidhesi.models.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ichha on 3/21/2018.
 */

public class OrderResultItem {
    public String number;
    public String basket;
    public String url;
    public String lines;
    public String owner;
    public Object billingAddress;
    public String currency;
    public String total_incl_tax;
    public String total_excl_tax;
    public String shipping_incl_tax;
    public String shipping_excl_tax;
    public ShippingAddress shipping_address;
    public String shipping_method;
    public String shipping_code;
    public String status;
    public String guest_email;
    public String date_placed;
    public String payment_url;
    public List<Object> offer_discounts = null;
    public List<Object> voucher_discounts = null;
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
