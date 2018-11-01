package com.susankya.swadesibidhesi.models.user;

/**
 * Created by mdiwa on 2/14/2018.
 */

public class ProductPrice {
    public String currency;
    public String excl_tax;
    public String incl_tax;
    public String tax;
    public String test;
    public String price_before_discount;
    public String discount_percentage;

    public String getCurrency() {
        return currency;
    }

    public String getExcl_tax() {
        return excl_tax;
    }

    public String getIncl_tax() {
        return incl_tax;
    }

    public String getTax() {
        return tax;
    }

    public String getTest() {
        return test;
    }

    public String getPrice_before_discount() {
        return price_before_discount;
    }

    public String getDiscount_percentage() {
        return discount_percentage;
    }
}
