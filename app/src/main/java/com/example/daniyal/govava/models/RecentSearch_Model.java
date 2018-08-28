package com.example.daniyal.govava.models;

import java.sql.Timestamp;

/**
 * Created by Pc on 6/28/2018.
 */

public class RecentSearch_Model {

    String text_search;
    long timestamp;

    public RecentSearch_Model(String text_search, long timestamp) {
        this.text_search = text_search;
        this.timestamp = timestamp;
    }

    public String getText_search() {
        return text_search;
    }

    public void setText_search(String text_search) {
        this.text_search = text_search;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
