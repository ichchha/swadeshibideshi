package com.susankya.swadesibidhesi.models.WooCommerce;

public class WcProductImage {
    public int id;
    public String created_at;
    public String updated_at;
    public String src;
    public String title;
    public String alt;
    public Integer position;

    public WcProductImage(String src) {
        this.src = src;
    }
}
