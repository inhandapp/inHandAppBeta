package com.inhand.inhandappbeta;

import com.inhand.inhandappbeta.eBayItem;

import java.util.ArrayList;

public class eBayURL {
    private String title = null;
    private ArrayList<eBayItem> items;

    public eBayURL() {
        items = new ArrayList<eBayItem>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int addItem(eBayItem item) {
        items.add(item);
        return items.size();
    }

    public eBayItem getItem(int index) {
        return items.get(index);
    }

    public ArrayList<eBayItem> getAllItems() {
        return items;
    }
}