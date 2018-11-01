package com.susankya.swadesibidhesi.models.user;

import java.util.List;

/**
 * Created by Ichha on 3/21/2018.
 */

public class Search {
    private Integer count;
    private String next;
    private Object previous;
    private List<SearchResult> results = null;

    public Integer getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public Object getPrevious() {
        return previous;
    }

    public List<SearchResult> getResults() {
        return results;
    }
}
