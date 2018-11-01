package com.susankya.swadesibidhesi.models.WooCommerce;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by SCIT on 3/10/2017.
 */

public class WcCategory implements Comparable {
    public int id;
    public String name;
    public String slug;
    public int parent;
    public String description;
    public List<WcCategory> children;
    public String display; //Available: default, products, subcategories and both
    public String image;
    public int count;


    @Override
    public int compareTo(@NonNull Object o) {
//        return (((WcProductCategory) o).slug).compareTo(this.slug);
        return  (this.count < ((WcCategory) o).count ? -1 : (this.count == ((WcCategory) o).count ? 0 : 1));
    }
}
