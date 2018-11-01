package com.susankya.swadesibidhesi.models.WooCommerce;

public class WcCustomer {
    public int id;
    public String email;
    public String first_name;
    public String last_name;
    public String password;
    public String username;
    public String role;
    public int last_order_id;
    public String last_order_date;
    public int orders_count;
    public String total_spent;
    public String avatar_url;
    public WcBilling billing_address;
    public WcShipping shipping_address;
}
