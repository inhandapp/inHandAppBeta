package com.inhand.inhandappbeta;

import java.util.ArrayList;

public class walmartURL {
    private ArrayList<walmartItem> items;

    public walmartURL() {
        items = new ArrayList<walmartItem>();
    }

    public int addItem(walmartItem item) {
        items.add(item);
        return items.size();
    }

    public walmartItem getItem(int index) {
        return items.get(index);
    }

    public ArrayList<walmartItem> getAllItems() {
        return items;
    }
}