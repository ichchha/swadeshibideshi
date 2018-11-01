package com.susankya.swadesibidhesi.models.user;

import java.util.List;

public class CategoryProduct {
    private String url;
    private int id;
    private String title;
    private List<Image> images = null;
    private Object rating;
    private ProductPrice price;
    private Availability availability;

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Image> getImages() {
        return images;
    }

    public Object getRating() {
        return rating;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public Availability getAvailability() {
        return availability;
    }
}
