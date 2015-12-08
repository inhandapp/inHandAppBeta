package com.inhand.inhandappbeta;

import java.net.URL;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;
import android.os.Bundle;
import android.view.View;
import java.io.IOException;
import java.io.InputStream;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.MenuItem;
import android.view.KeyEvent;
import java.io.BufferedReader;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView.OnEditorActionListener;

public class SearchActivity extends AppCompatActivity
implements OnEditorActionListener {
//implements OnEditorActionListener, OnItemClickListener, OnClickListener {

    //Define class variables
    private eBayURL url;
    private eBayFileIO io;

    //Define widget variables
    private EditText userEnteredSearchPhrase;
    private TextView titleTextView;
    private EditText searchFieldEditTextView;
    private ListView itemsListView;
    private Button submitButton;

    //Define instance variables
    private String userEnteredSearchString = "";

    //Define the SharedPreferences object
    private SharedPreferences savedValues;

    //Logging purposes
    private static final String TAG = "inHandAppBeta";

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.search_activity);

        io = new eBayFileIO(getApplicationContext());

        //Get references to widgets
        userEnteredSearchPhrase = (EditText) findViewById(R.id.searchbar);
        //searchFieldEditTextView = (EditText) findViewById(R.id.searchFieldEditText);
        //titleTextView = (TextView) findViewById(R.id.titleTextView);
        //itemsListView = (ListView) findViewById(R.id.itemsListView);
        //submitButton = (Button) findViewById(R.id.submitButton);

        //Set listener for EditText widget
        userEnteredSearchPhrase.setOnEditorActionListener(this);
        //submitButton.setOnClickListener(this);
        //itemsListView.setOnItemClickListener(this);

        //Get SharedPreferences object
        savedValues = getSharedPreferences("userEnteredSearchString", MODE_PRIVATE);

    }

    /******************* START ABOUT MENU METHODS*********************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onAboutClick(MenuItem item) {

        // Inflate the about message contents
        View messageView = getLayoutInflater().inflate(R.layout.about, null, false);

        // When linking text, force to always use default color. This works
        // around a pressed color state bug.
        TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
        int defaultColor = textView.getTextColors().getDefaultColor();
        textView.setTextColor(defaultColor);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setIcon(R.drawable.logo);
        builder.setTitle(R.string.app_name);
        builder.setView(messageView);
        builder.create();
        builder.show();

    }

    public void onHelpClick(MenuItem item) {
        String aboutMessage = this.getResources().getString(R.string.helpMessage);
        Toast.makeText(getBaseContext(), aboutMessage, Toast.LENGTH_LONG).show();
    }

    /***************** END ABOUT MENU METHODS ************************************/

    /****************** START USER STRING LISTENER & OPERATION METHODS************/



    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        //Parses String from EditText widget
        userEnteredSearchString = String.valueOf(userEnteredSearchPhrase);

        userEnteredSearchPhrase.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        return false;
    }

    /*@Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitButton:
                keywordsString = searchFieldEditTextView.getText().toString();
                new DownloadURL().execute();
                break;
        }
    }*/

    class DownloadURL extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            //io.downloadFile();
            io.downloadFile(userEnteredSearchString);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("eBay", "Search results downloaded");
            new ReadURL().execute();
        }
    }

    class ReadURL extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            url = io.readFile();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("eBay", "Search results read");

            // update the display for the activity
            //ItemsActivity.this.updateDisplay();
        }
    }

    public void updateDisplay()
    {
        if (url == null) {
            titleTextView.setText("Unable to get eBay search results");
            return;
        }

        // set the title for the url
        //titleTextView.setText(url.getTitle());

        // get the items for the url
        ArrayList<eBayItem> items = url.getAllItems();

        // create a List of Map<String, ?> objects
        ArrayList<HashMap<String, String>> data =
                new ArrayList<HashMap<String, String>>();
        for (eBayItem item : items) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("title", item.getTitle());
            map.put("currentPrice", item.getPrice());
            data.add(map);
        }

        // create the resource, from, and to variables
        int resource = R.layout.listview_item;
        String[] from = {"title", "currentPrice"};
        int[] to = {R.id.titleTextView, R.id.currentPriceTextView};

        // create and set the adapter
        SimpleAdapter adapter =
                new SimpleAdapter(this, data, resource, from, to);
        itemsListView.setAdapter(adapter);

        Log.d("eBay", "Search results displayed");
    }

    //@Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {

        // get the item at the specified position
        eBayItem item = url.getItem(position);

        // create an intent
        Intent intent = new Intent(this, ItemActivity.class);

        intent.putExtra("title", item.getTitle());
        intent.putExtra("currentPrice", item.getPrice());
        intent.putExtra("link", item.getLink());

        this.startActivity(intent);
    }

/***************** END USER STRING LISTENER & OPERATION METHODS ********************/



/****************** START USER STRING LISTENER & OPERATION METHODS************/


    public void readEbayUrl(String keywords) {
        try {
            keywords = keywords.replaceAll(" ", "%20");

            URL eBayUrl = new URL("http://svcs.ebay.com/services/search/FindingService/v1" +
                "?OPERATION-NAME=findItemsByKeywords" +
                "&SERVICE-VERSION=1.0.0" +
                "&SECURITY-APPNAME=inHanda34-8e86-4e05-9e5b-1fdeb7f3cab" +
                "&RESPONSE-DATA-FORMAT=XML" +
                "&REST-PAYLOAD" +
                "&keywords=" + keywords);
        BufferedReader in = new BufferedReader(new InputStreamReader(eBayUrl.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
       }
        catch(IOException e) {
            Log.d("TAG", "SearchActivity");
        }
    }

    public void readEbayUrlAlternate(String keywords) {
        final String FILENAME = "eBay_feed.xml";
        try {
            // get input stream
            URL eBayUrl = new URL("http://svcs.ebay.com/services/search/FindingService/v1" +
                    "?OPERATION-NAME=findItemsByKeywords" +
                    "&SERVICE-VERSION=1.0.0" +
                    "&SECURITY-APPNAME=inHanda34-8e86-4e05-9e5b-1fdeb7f3cab" +
                    "&RESPONSE-DATA-FORMAT=XML" +
                    "&REST-PAYLOAD" +
                    "&keywords=" + keywords);
            InputStream in = eBayUrl.openStream();

            //get the output stream
            FileOutputStream out = openFileOutput(FILENAME, Context.MODE_PRIVATE);

            //read input and write output
            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            while (bytesRead != -1) {
                out.write(buffer, 0, bytesRead);
                bytesRead = in.read(buffer);
            }
            out.close();
            in.close();
        }
        catch(IOException e) {
            Log.d("TAG", e.toString());
        }
    }
    /****************** START USER STRING LISTENER & OPERATION METHODS************/
}