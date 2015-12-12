package com.inhand.inhandappbeta;

/**
 * Created by Brogrammer on 12/11/2015.
 */
public class DataHolder {
    private eBayURL data;
    public eBayURL getData() {return data;}
    public void setData(eBayURL data) {this.data = data;}

    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}
}
