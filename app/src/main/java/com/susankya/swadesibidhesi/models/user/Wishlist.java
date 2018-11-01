package com.susankya.swadesibidhesi.models.user;

/**
 * Created by Ichha on 2/18/2018.
 */

public class Wishlist {
    private ProductItem productItem;
    private String pdName,pdPrice;
    private String pdImage;
    private int pdId;

    public Wishlist(ProductItem productItem) {
        this.productItem = productItem;
    }

    public ProductItem getProductItem() {
        return productItem;
    }

    public Wishlist(String pdName, String pdPrice, String pdImage, int pdId) {
        this.pdName = pdName;
        this.pdPrice = pdPrice;
        this.pdImage = pdImage;
        this.pdId = pdId;
    }

    public Wishlist(String pdName, String pdPrice, String pdImage) {
        this.pdName = pdName;
        this.pdPrice = pdPrice;
        this.pdImage = pdImage;
    }

    public String getPdName() {
        return pdName;
    }

    public String getPdPrice() {
        return pdPrice;
    }

    public String getPdImage() {
        return pdImage;
    }

    public int getPdId() {
        return pdId;
    }
}
