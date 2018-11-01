package com.susankya.swadesibidhesi.models.user;

import com.susankya.swadesibidhesi.models.WooCommerce.WcProductImage;

import java.util.List;

public class ProductPhotoSlide implements ProductDetails {
    List<WcProductImage> productImages;

    public ProductPhotoSlide(List<WcProductImage> productImages) {
        this.productImages = productImages;
    }

    public List<WcProductImage> getProductImages() {
        return productImages;
    }
}
