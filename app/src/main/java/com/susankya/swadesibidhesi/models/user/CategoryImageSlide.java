package com.susankya.swadesibidhesi.models.user;

import java.util.List;

/**
 * Created by Ichha on 2/5/2018.
 */

public class CategoryImageSlide implements HomeItem {
    List<CategoryData> categoryDataList;

    public List<CategoryData> getCategoryDataList() {
        return categoryDataList;
    }

    public CategoryImageSlide(List<CategoryData> categoryDataList) {
        this.categoryDataList = categoryDataList;
    }
}
