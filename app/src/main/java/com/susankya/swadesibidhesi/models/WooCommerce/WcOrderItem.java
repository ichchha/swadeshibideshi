package com.susankya.swadesibidhesi.models.WooCommerce;

import java.util.List;

public class WcOrderItem {
    public int id;
    public String order_number;
    public String order_key;
    public String status;
    public String currency;
    public String total;
    public String subtotal;
    public String total_line_items_quantity;
    public String total_tax;
    public String total_shipping;
    public String cart_tax;
    public String shipping_tax;
    public String shipping_methods;
    public PaymentDetails payment_details;
    public WcBilling billing_address;
    public WcShipping shipping_address;
    public List<WcLineItem> line_items = null;
    public List<ShippingLine> shipping_lines;
    public WcCustomer customer;
}
