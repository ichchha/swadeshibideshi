package com.susankya.swadesibidhesi.models.user;

import java.util.List;

public class BasketResultItem implements CartItem {
    public int id;
    private String url;
    public String product;
    public int quantity;
    private List<Object> attributes = null;
    public String price_currency;
    public String price_excl_tax;
    public String price_incl_tax;
    private String price_incl_tax_excl_discounts;
    private String price_excl_tax_excl_discounts;
    private Boolean is_tax_known;
    private Object warning;
    public String basket;
    public String stockrecord;
    private String date_created;
    private String image;
    private String product_title;
    public String line_reference;

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getProduct_title() {
        return product_title;
    }

    public String getUrl() {
        return url;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<Object> getAttributes() {
        return attributes;
    }

    public String getPrice_currency() {
        return price_currency;
    }

    public String getPrice_excl_tax() {
        return price_excl_tax;
    }

    public String getPrice_incl_tax() {
        return price_incl_tax;
    }

    public String getPrice_incl_tax_excl_discounts() {
        return price_incl_tax_excl_discounts;
    }

    public String getPrice_excl_tax_excl_discounts() {
        return price_excl_tax_excl_discounts;
    }

    public Boolean getIs_tax_known() {
        return is_tax_known;
    }

    public Object getWarning() {
        return warning;
    }

    public String getBasket() {
        return basket;
    }

    public String getStockrecord() {
        return stockrecord;
    }

    public String getDate_created() {
        return date_created;
    }
}
