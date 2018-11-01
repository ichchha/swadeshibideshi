package com.susankya.swadesibidhesi.models.WooCommerce;

import com.susankya.swadesibidhesi.models.user.CartItem;

public class WcLineItem implements CartItem {
    public int id;
    public String name;
    public int product_id;
    public int variation_id;
    public int quantity;
    public String subtotal;
    public String subtotal_tax;
    public String total;
    public String total_tax;
    public String price;

    public WcLineItem(int product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }
}
