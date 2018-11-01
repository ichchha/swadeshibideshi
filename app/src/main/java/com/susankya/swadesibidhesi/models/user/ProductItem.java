package com.susankya.swadesibidhesi.models.user;

import java.util.List;

public class ProductItem implements ProductDetails {

    private String url;
    private int id;
    private String title;
    private String description;
    private String date_created;
    private String date_updated;
    private List<ProductItem> recommended_products = null;
    public boolean is_added_to_wishlist;
    private String review;
    private List<Attribute> attributes = null;
    private List<String> categories = null;
    private String product_class;
    private List<Image> images = null;
    private Object rating;
    public ProductPrice price;
    private Availability availability;
    public int no_of_reviews;
    public String web_url;
    private List<Object> options = null;


    public ProductItem() {
    }

    public ProductItem(String itemTitle) {
        this.title = itemTitle;
    }

    public ProductItem(String title, String itemPrice, String itemCategory) {
        this.title = title;
//        this.itemPrice = itemPrice;
//        this.itemCategory = itemCategory;
    }

    public ProductItem(String itemTitle, String itemPrice, String itemCategory, List<Image> images) {
        this.title = itemTitle;
//        this.itemPrice = itemPrice;
//        this.itemCategory = itemCategory;
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public List<ProductItem> getRecommended_products() {
        return recommended_products;
    }

    public String getReview() {
        return review;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getProduct_class() {
        return product_class;
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

    public int getNo_of_reviews() {
        return no_of_reviews;
    }

    public List<Object> getOptions() {
        return options;
    }
}