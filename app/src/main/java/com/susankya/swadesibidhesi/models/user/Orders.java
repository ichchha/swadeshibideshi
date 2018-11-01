package com.susankya.swadesibidhesi.models.user;

/**
 * Created by Ichha on 2/18/2018.
 */

public class Orders {
    private String order_id,product_name,product_cat,product_size,product_qty,product_price,order_status;
    public ProductItem productItem;

    public Orders(ProductItem productItem) {
        this.productItem = productItem;
    }

    public Orders(String order_status, ProductItem productItem) {
        this.order_status = order_status;
        this.productItem = productItem;
    }

    public Orders(String order_id, String product_name, String product_cat, String product_size, String product_qty, String product_price, String order_status) {this.order_id = order_id;
        this.product_name = product_name;
        this.product_cat = product_cat;
        this.product_size = product_size;
        this.product_qty = product_qty;
        this.product_price = product_price;
        this.order_status = order_status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_cat() {
        return product_cat;
    }

    public String getProduct_size() {
        return product_size;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getOrder_status() {
        return order_status;
    }
}
