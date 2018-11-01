package com.susankya.swadesibidhesi.models.user;

import java.util.List;

public class WishListItem {
    public int id;
    public List<WishLineItem> lines = null;
    public String name;
    public String key;
    public String visibility;
    public String date_created;
    public int owner;

    public int getId() {
        return id;
    }

    public List<WishLineItem> getLines() {
        return lines;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getDate_created() {
        return date_created;
    }

    public int getOwner() {
        return owner;
    }
}
