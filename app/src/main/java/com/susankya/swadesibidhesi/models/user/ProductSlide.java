package com.susankya.swadesibidhesi.models.user;

import java.util.List;

/**
 * Created by Ichha on 2/5/2018.
 */

public class ProductSlide implements HomeItem,ProductDetails {
    int categoryId;
    String sliderTitle;
    List<ProductItem> sliderList;

    public ProductSlide(String sliderTitle, List<ProductItem> sliderList) {
        this.sliderTitle = sliderTitle;
        this.sliderList = sliderList;
    }

    public ProductSlide(int categoryId, String sliderTitle, List<ProductItem> sliderList) {
        this.categoryId = categoryId;
        this.sliderTitle = sliderTitle;
        this.sliderList = sliderList;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getSliderTitle() {
        return sliderTitle;
    }

    public List<ProductItem> getSliderList() {
        return sliderList;
    }
}
