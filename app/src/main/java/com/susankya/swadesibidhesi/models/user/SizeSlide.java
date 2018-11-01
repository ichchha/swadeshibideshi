package com.susankya.swadesibidhesi.models.user;

import java.util.List;

/**
 * Created by Ichha on 2/5/2018.
 */

public class SizeSlide implements ProductDetails {

    List<CategoryItem> categories;

    public SizeSlide(List<CategoryItem> categories) {
        this.categories = categories;
    }

    public List<CategoryItem> getCategpriesList() {
        return categories;
    }
}
