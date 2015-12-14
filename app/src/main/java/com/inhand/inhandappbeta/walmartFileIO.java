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

public class walmartFileIO {
    private static final String TAG = "Walmart File IO";
    private final String URL_STRING = "http://api.walmartlabs.com/v1/search?apiKey=vy4y6fjwhy4kvpenemwacmfg&numItems=5&sort=bestseller&query=";
    private final String FILENAME = "walmart_search_results.xml";
    private Context context = null;


    public walmartFileIO(Context context) {
        this.context = context;
    }

    public void downloadFile(String keywordsString) {
        try{
            keywordsString = keywordsString.replaceAll(" ", "%20");

            // get the URL
            URL url = new URL(URL_STRING + keywordsString); // + keywordsString

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

    public walmartURL readFile() {
        try {
            // get the XML reader
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader xmlreader = parser.getXMLReader();

            // set content handler
            walmartURLHandler theRssHandler = new walmartURLHandler();
            xmlreader.setContentHandler(theRssHandler);

            // read the file from internal storage
            FileInputStream in = context.openFileInput(FILENAME);

            // parse the data
            InputSource is = new InputSource(in);
            xmlreader.parse(is);

            // set the feed in the activity
            walmartURL feed = theRssHandler.getFeed();
            return feed;
        }
        catch (Exception e) {
            Log.e(TAG, e.toString());

            return null;
        }
    }
}