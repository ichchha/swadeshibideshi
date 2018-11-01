package com.susankya.swadesibidhesi.models.WooCommerce;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by SCIT on 3/10/2017.
 */

public class WcProductCategory extends ExpandableGroup<WcProductCategory> implements Comparable {
    @SerializedName("id")
    public int id;
    public String name;
    public String slug;
    public int parent;
    public String description;
    public List<WcProductCategory> children;
    public String display; //Available: default, products, subcategories and both
    public String image;
    public int count;

    public WcProductCategory(String title, List<WcProductCategory> items, int id,String slug) {
        super(title, items);
        this.name = title;
        this.children = items;
        this.id = id;
        this.slug = slug;
    }

    @Override
    public int compareTo(@NonNull Object o) {
//        return (((WcProductCategory) o).slug).compareTo(this.slug);
        return  (this.count < ((WcProductCategory) o).count ? -1 : (this.count == ((WcProductCategory) o).count ? 0 : 1));
    }
}
