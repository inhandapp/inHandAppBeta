package com.inhand.inhandappbeta;

public class DataHolder {
    private eBayURL ebayData;

    public eBayURL getEbayData() {return ebayData;}
    public void setEbayData(eBayURL ebayData) {this.ebayData = ebayData;}

    private static final DataHolder ebayHolder = new DataHolder();
    public static DataHolder getInstance() {return ebayHolder;}
}
