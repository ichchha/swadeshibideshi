package com.susankya.swadesibidhesi.models.WooCommerce;

import com.susankya.swadesibidhesi.models.user.HomeItem;
import com.susankya.swadesibidhesi.models.user.ProductDetails;

import java.util.List;

/**
 * Created by Ichha on 2/5/2018.
 */

public class WcProductSlide implements HomeItem, ProductDetails {
    public int categoryId;
    public String sliderTitle;
    public List<WcProduct> sliderList;
    public String category_slug;
    public Boolean fromVariation;

    public WcProductSlide(Boolean fromVariation,String sliderTitle, List<WcProduct> sliderList) {
        this.fromVariation = fromVariation;
        this.sliderTitle = sliderTitle;
        this.sliderList = sliderList;
    }

    public WcProductSlide(String category_slug, String sliderTitle, List<WcProduct> sliderList) {
        this.category_slug = category_slug;
        this.sliderTitle = sliderTitle;
        this.sliderList = sliderList;
    }
}
