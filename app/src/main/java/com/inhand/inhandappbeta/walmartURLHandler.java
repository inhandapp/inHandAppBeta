package com.inhand.inhandappbeta;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class walmartURLHandler extends DefaultHandler {
    private walmartURL feed;
    private walmartItem item;

    //private boolean feedTitleHasBeenRead = false;

    private boolean isTitle = false;
    private boolean isLink = false;
    private boolean isMSRP = false;

    public walmartURL getFeed() {
        return feed;
    }

    public void startDocument() throws SAXException {
        feed = new walmartURL();
        item = new walmartItem();
    }

    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts) throws SAXException {

        if (qName.equals("item")) {
            item = new walmartItem();
            return;
        }
        else if (qName.equals("name")) {
            isTitle = true;
            return;
        }
        else if (qName.equals("msrp")) {
            isMSRP = true;
            return;
        }
        else if (qName.equals("productUrl")) {
            isLink = true;
            return;
        }
    }

    public void endElement(String namespaceURI, String localName,
                           String qName) throws SAXException
    {
        if (qName.equals("item")) {
            feed.addItem(item);
            return;
        }
    }

    public void characters(char ch[], int start, int length)
    {
        String s = new String(ch, start, length);
        /*if (isTitle) {
            if (feedTitleHasBeenRead == false) {
                feed.setTitle(s);
                feedTitleHasBeenRead = true;
            }
            else {
                item.setTitle(s);
            }
            isTitle = false;
        }*/
        //else if (isCurrentPrice) {
        if (isMSRP) {
            item.setPrice(s);
            isMSRP = false;
        }
        else if (isLink) {
            item.setLink(s);
            isLink = false;
        }
    }
}