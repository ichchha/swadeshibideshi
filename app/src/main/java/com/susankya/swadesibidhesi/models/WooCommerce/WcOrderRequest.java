package com.susankya.swadesibidhesi.models.WooCommerce;

import java.util.List;

public class WcOrderRequest {
    public int customer_id;
    public PaymentDetails payment_details;
    public WcBilling billing_address;
    public WcShipping shipping_address;
    public List<WcLineItem> line_items;
    public List<ShippingLine> shipping_lines;
}
