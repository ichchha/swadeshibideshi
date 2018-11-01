package com.susankya.swadesibidhesi.models.user;

/**
 * Created by Ichha on 2/5/2018.
 */

public class CategoryItem {
    private String categoryName;

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    private Boolean selected;


    public CategoryItem(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
