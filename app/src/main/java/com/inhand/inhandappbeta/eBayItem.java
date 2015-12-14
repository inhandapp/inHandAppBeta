package com.inhand.inhandappbeta;

public class eBayItem {

    private String title = null; // title
    private String price = null; //price
    //private String image = null; //image url
    private String link = null; //item url

    public eBayItem() {
    }

    public eBayItem(String title, String price, String link) {
        this.title = title;
        this.price = price;
        //this.image = image;
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
