package com.susankya.swadesibidhesi.models.user;

import java.util.List;

public class Cart implements CartItem{
    public int id;
    public String owner;
    public String status;
    public String lines;
    public String url;
    public String total_excl_tax;
    public String total_excl_tax_excl_discounts;
    public String total_incl_tax;
    public String total_incl_tax_excl_discounts;
    public String total_tax;
    public String currency;
    public List<Object> voucher_discounts = null;
    public List<Object> offer_discounts = null;
    public Boolean is_tax_known;

    public Cart() {
    }

    public Cart(String total_excl_tax, String total_excl_tax_excl_discounts, String total_incl_tax, String total_incl_tax_excl_discounts, String total_tax) {
        this.total_excl_tax = total_excl_tax;
        this.total_excl_tax_excl_discounts = total_excl_tax_excl_discounts;
        this.total_incl_tax = total_incl_tax;
        this.total_incl_tax_excl_discounts = total_incl_tax_excl_discounts;
        this.total_tax = total_tax;
    }

    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getStatus() {
        return status;
    }

    public String getLines() {
        return lines;
    }

    public String getUrl() {
        return url;
    }

    public String getTotal_excl_tax() {
        return total_excl_tax;
    }

    public String getTotal_excl_tax_excl_discounts() {
        return total_excl_tax_excl_discounts;
    }

    public String getTotal_incl_tax() {
        return total_incl_tax;
    }

    public String getTotal_incl_tax_excl_discounts() {
        return total_incl_tax_excl_discounts;
    }

    public String getTotal_tax() {
        return total_tax;
    }

    public String getCurrency() {
        return currency;
    }

    public List<Object> getVoucher_discounts() {
        return voucher_discounts;
    }

    public List<Object> getOffer_discounts() {
        return offer_discounts;
    }

    public Boolean getIs_tax_known() {
        return is_tax_known;
    }
}