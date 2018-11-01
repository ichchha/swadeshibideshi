package com.susankya.swadesibidhesi.models.user;

import java.util.List;

public class NewWishList {
    private int count;
    private Object next;
    private Object previous;
    private List<WishListItem> results = null;

    public NewWishList(List<WishListItem> results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public Object getNext() {
        return next;
    }

    public Object getPrevious() {
        return previous;
    }

    public List<WishListItem> getResults() {
        return results;
    }
}
