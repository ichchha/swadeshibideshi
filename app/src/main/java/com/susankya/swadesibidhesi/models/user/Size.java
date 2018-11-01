package com.susankya.swadesibidhesi.models.user;

/**
 * Created by Ichha on 3/19/2018.
 */

public class Size implements ProductDetails{
    public Size(String size) {
        this.size = size;
    }

    public String size;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
