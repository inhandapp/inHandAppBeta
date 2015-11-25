package com.inhand.inhandappbeta;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Handshake {
    public static void main(String[] args) throws Exception {
        URL oracle = new URL("http://svcs.ebay.com/services/search/FindingService/v1" +
            "?OPERATION-NAME=findItemsByKeywords" +
            "&SERVICE-VERSION=1.0.0" +
            "&SECURITY-APPNAME=inHanda34-8e86-4e05-9e5b-1fdeb7f3cab" +
            "&RESPONSE-DATA-FORMAT=XML" +
            "&REST-PAYLOAD" +
            "&keywords=harry%20potter%20phoenix");
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
}

