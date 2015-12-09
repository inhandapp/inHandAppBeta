package com.inhand.inhandappbeta;

import java.util.ArrayList;

public class eBayURL {
    private ArrayList<eBayItem> items;

    public eBayURL() {
        items = new ArrayList<eBayItem>();
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