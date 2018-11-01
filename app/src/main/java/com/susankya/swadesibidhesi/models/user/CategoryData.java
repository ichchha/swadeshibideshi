package com.susankya.swadesibidhesi.models.user;

import java.util.List;

public class CategoryData {
    public Category category;
    public String id;
    public List<ProductItem> category_products;

    public Category getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public List<ProductItem> getCategory_products() {
        return category_products;
    }
}
