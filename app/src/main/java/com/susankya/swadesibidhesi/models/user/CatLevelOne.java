package com.susankya.swadesibidhesi.models.user;

import com.google.gson.annotations.SerializedName;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class CatLevelOne extends ExpandableGroup<CatLevelTwo> {
    @SerializedName("id")
    public int id;
    public String name;
    public List<CatLevelTwo> children;
    public String url_for_products;
    public String description;


    public CatLevelOne(String title, List<CatLevelTwo> children, int id) {
        super(title, children);
        this.name = title;
        this.children = children;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CatLevelTwo> getChildren() {
        return children;
    }
}
