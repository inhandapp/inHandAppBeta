package com.inhand.inhandappbeta;

import java.util.ArrayList;

public class amazonURL {
    private ArrayList<amazonItem> items;

    public amazonURL() {
        items = new ArrayList<amazonItem>();
    }

    public int addItem(amazonItem item) {
        items.add(item);
        return items.size();
    }

    public amazonItem getItem(int index) {
        return items.get(index);
    }

    public ArrayList<amazonItem> getAllItems() {
        return items;
    }
}