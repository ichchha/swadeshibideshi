package com.susankya.swadesibidhesi.models.user;

import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;

import java.util.ArrayList;
import java.util.List;

public class BasketLine implements CartItem {
    public String totalPrice;
    public int count;
    private Object next;
    private Object previous;
    private List<BasketResultItem> results ;
    public ArrayList<WcProduct> cartResults;

    public BasketLine(ArrayList<WcProduct> cartResults) {
        this.cartResults = cartResults;
    }

    public BasketLine(String totalPrice, List<BasketResultItem> results) {
        this.totalPrice = totalPrice;
        this.results = results;
    }

    public BasketLine(String totalPrice, int count, List<BasketResultItem> results) {
        this.totalPrice = totalPrice;
        this.count = count;
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public Object getNext() {
        return next;
    }

    public Object getPrevious() {
        return previous;
    }

    public List<BasketResultItem> getResults() {
        return results;
    }
}
