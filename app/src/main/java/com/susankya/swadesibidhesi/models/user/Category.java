package com.susankya.swadesibidhesi.models.user;

import java.util.List;

public class Category {
    private int id;
    private String name;
    private String description;
    private String image;
    private List<Object> children = null;
    private String url_for_products;

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public List<Object> getChildren() {
        return children;
    }

    public String getUrl_for_products() {
        return url_for_products;
    }
}
