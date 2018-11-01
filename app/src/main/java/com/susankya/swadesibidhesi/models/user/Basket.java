package com.susankya.swadesibidhesi.models.user;

import java.util.List;

/**
 * Created by Ichha on 3/20/2018.
 */

public class Basket {
    public String url;
    public int quantity;
    private String owner;
    private String status;
    private String lines;
    private String total_excl_tax;
    private String total_excl_tax_excl_discounts;
    private String total_incl_tax;
    private String total_incl_tax_excl_discounts;
    private String total_tax;
    private String currency;
    private List<Object> voucher_discounts = null;
    private List<Object> offer_discounts = null;
    private Boolean is_tax_known;

    public Basket() {
    }

    public Basket(String url, String lines, String totalInclTaxExclDiscounts, String currency) {
        this.url = url;
        this.lines = lines;
        this.total_incl_tax_excl_discounts = totalInclTaxExclDiscounts;
        this.currency = currency;
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

    public String getTotalExclTax() {
        return total_excl_tax;
    }

    public String getTotalExclTaxExclDiscounts() {
        return total_excl_tax_excl_discounts;
    }

    public String getTotalInclTax() {
        return total_incl_tax;
    }

    public String getTotalInclTaxExclDiscounts() {
        return total_incl_tax_excl_discounts;
    }

    public String getTotalTax() {
        return total_tax;
    }

    public String getCurrency() {
        return currency;
    }

    public List<Object> getVoucherDiscounts() {
        return voucher_discounts;
    }

    public List<Object> getOfferDiscounts() {
        return offer_discounts;
    }

    public Boolean getTaxKnown() {
        return is_tax_known;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
