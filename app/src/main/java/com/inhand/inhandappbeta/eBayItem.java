package com.inhand.inhandappbeta;

public class eBayItem {

    private  int _id;
    private String title = null; // title
    private String price = null; //price
    private String link = null; //item url

    public eBayItem() {
    }

    public eBayItem(int _id, String title, String price, String link) {
        this._id = _id;
        this.title = title;
        this.price = price;
        this.link = link;
    }

    public eBayItem(String title, String price, String link) {
        this.title = title;
        this.price = price;
        //this.image = image;
        this.link = link;
    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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
