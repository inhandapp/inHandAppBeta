package com.inhand.inhandappbeta;

public class walmartItem {

    private String title = null;
    private String price = null;
    private String image = null;
    private String link = null;

    public walmartItem() {
    }

    public walmartItem(String title, String price, String link) {
        this.title = title;
        this.price = price;
        this.link = link;
    }

    public void setTitle(String title)     {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
