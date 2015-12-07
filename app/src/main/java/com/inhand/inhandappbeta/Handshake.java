// FOR REFERENCE PURPOSES ONLY
package com.inhand.inhandappbeta;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Handshake {

    public void readURL(String keywords) {
        try {
            keywords = keywords.replaceAll(" ", "%20");

            URL oracle = new URL("http://svcs.ebay.com/services/search/FindingService/v1" +
                    "?OPERATION-NAME=findItemsByKeywords" +
                    "&SERVICE-VERSION=1.0.0" +
                    "&SECURITY-APPNAME=inHanda34-8e86-4e05-9e5b-1fdeb7f3cab" +
                    "&RESPONSE-DATA-FORMAT=XML" +
                    "&REST-PAYLOAD" +
                    "&keywords=" + keywords);
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
        }
        catch(IOException e) {
            Log.d("Handshake", e.toString());
        }
    }
}