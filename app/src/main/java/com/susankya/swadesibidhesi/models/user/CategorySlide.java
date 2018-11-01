package com.susankya.swadesibidhesi.models.user;

import java.util.List;

/**
 * Created by Ichha on 2/5/2018.
 */

public class CategorySlide implements HomeItem {
    List<Category> categoryList;

    public List<Category> getCategoryDataList() {
        return categoryList;
    }

    public CategorySlide(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
