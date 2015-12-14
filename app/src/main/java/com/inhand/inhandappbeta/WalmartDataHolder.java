package com.inhand.inhandappbeta;

public class WalmartDataHolder {
    private walmartURL walmartData;

    public walmartURL getWalmartData() {return walmartData;}
    public void setWalmartData(walmartURL walmartData) {this.walmartData = walmartData;}

    private static final DataHolder walmartHolder = new DataHolder();
    public static DataHolder getWalmartInstance() {return walmartHolder;}
}
