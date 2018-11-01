package com.susankya.swadesibidhesi.models.user;

public class Image {
    public Integer id;
    public String original;
    public String caption;
    public Integer display_order;
    public String date_created;
    public Integer product;

    public Image(String original) {
        this.original = original;
    }

    public Integer getId() {
        return id;
    }

    public String getOriginal() {
        return original;
    }

    public String getCaption() {
        return caption;
    }

    public Integer getProduct() {
        return product;
    }
}
