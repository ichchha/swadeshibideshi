package com.susankya.swadesibidhesi.models.user;

import java.util.List;

public class NavCategory {
    private int count;
    private String next;
    private String previous;
    private List<CatLevelOne> results;

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<CatLevelOne> getResults() {
        return results;
    }
}
