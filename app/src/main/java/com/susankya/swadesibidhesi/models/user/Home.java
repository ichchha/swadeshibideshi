package com.susankya.swadesibidhesi.models.user;

import java.util.List;

public class Home implements HomeItem {
    public List<Banner> banner;
    public List<CategoryData> category_data;

    public List<CategoryData> getCategory_data() {
        return category_data;
    }

    public List<Banner> getBanner() {
        return banner;
    }
}
