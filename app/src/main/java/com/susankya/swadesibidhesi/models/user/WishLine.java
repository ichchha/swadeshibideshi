package com.susankya.swadesibidhesi.models.user;

import java.util.List;

public class WishLine{
    private int count;
    private Object next;
    private Object previous;
    public List<WishLineItem> results = null;

    public WishLine(List<WishLineItem> results) {
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

    public List<WishLineItem> getResults() {
        return results;
    }
}
