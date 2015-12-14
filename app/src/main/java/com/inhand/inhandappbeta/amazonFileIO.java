package com.inhand.inhandappbeta;

import java.net.URL;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import org.xml.sax.XMLReader;

import android.content.Context;
import org.xml.sax.InputSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class amazonFileIO {
    private static final String TAG = "Amazon File IO";
    private final String URL_STRING = "";
    private final String FILENAME = "amazon_search_results.xml";
    private Context context = null;


    public amazonFileIO(Context context) {
        this.context = context;
    }

    public void downloadFile(String keywordsString) {
        try{
            keywordsString = keywordsString.replaceAll(" ", "%20");

            // Need to concatenate keywordsString to URL_STRING. Researching Amazon request URL.
            URL url = new URL(URL_STRING); // + keywordsString

            // get the input stream
            InputStream in = url.openStream();

            // get the output stream
            FileOutputStream out =
                    context.openFileOutput(FILENAME, Context.MODE_PRIVATE);

            // read input and write output
            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            while (bytesRead != -1)
            {
                out.write(buffer, 0, bytesRead);
                bytesRead = in.read(buffer);
            }
            out.close();
            in.close();
        }
        catch (IOException e) {
            Log.e(TAG, e.toString());
            Log.i(TAG, FILENAME);
        }
    }

    public eBayURL readFile() {
        try {
            // get the XML reader
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader xmlreader = parser.getXMLReader();
//
            // set content handler
            eBayURLHandler theRssHandler = new eBayURLHandler();
            xmlreader.setContentHandler(theRssHandler);

            // read the file from internal storage
            FileInputStream in = context.openFileInput(FILENAME);

            // parse the data
            InputSource is = new InputSource(in);
            xmlreader.parse(is);

            // set the feed in the activity
            eBayURL feed = theRssHandler.getFeed();
            return feed;
        }
        catch (Exception e) {
            Log.e(TAG, e.toString());

            return null;
        }
    }
}