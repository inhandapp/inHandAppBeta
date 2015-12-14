package com.inhand.inhandappbeta;

import android.util.Log;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.KeyEvent;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.content.SharedPreferences;
import android.view.inputmethod.EditorInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends AppCompatActivity implements OnEditorActionListener {

    //Define class variables
    private static eBayURL ebayUrl;
    private eBayFileIO ebayIo;
    private walmartURL walmartUrl;
    private walmartFileIO walmartIo;
    private EbayDataHolder ebayDataHolder;

    //Define widget variables
    private EditText userEnteredSearchPhrase;
    private TextView titleTextView;
    private ListView itemsListView;
    private Toolbar header_toolbar;

    //Define instance variables
    public String userEnteredSearchString = "";
    private final String TAG = "MainActivity";

    //Define the SharedPreferences object
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ebayIo = new eBayFileIO(getApplicationContext());
        //walmartIo = new walmartFileIO(getApplicationContext());

        userEnteredSearchPhrase = (EditText) findViewById(R.id.search_bar);

        //Set listener for EditText widget
        userEnteredSearchPhrase.setOnEditorActionListener(this);

        //include toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.logo_bar);
        setSupportActionBar(myToolbar);
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (NullPointerException e){
            Log.v(TAG,"NullPointer");
        }

        //Get SharedPreferences object
        savedValues = getSharedPreferences("userEnteredSearchString", MODE_PRIVATE);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        int keyCode = -1;
        if (event != null) {
            keyCode = event.getKeyCode();
        }
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED ||
                keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
                keyCode == KeyEvent.KEYCODE_ENTER) {

            userEnteredSearchString = userEnteredSearchPhrase.getText().toString();

            new DownloadURL().execute();
        }
        return false;
    }

    class DownloadURL extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            ebayIo.downloadFile(userEnteredSearchString);
            //walmartIo.downloadFile(userEnteredSearchString);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("MainActivity", "Search results downloaded");

            new ReadURL().execute();
        }
    }

    class ReadURL extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            ebayUrl = ebayIo.readFile();
            //walmartUrl = walmartIo.readFile();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("MainActivity", "Search results read");

            // update the display for the activity
            Intent intent = new Intent (getApplicationContext(), ResultsActivity.class);
            DataHolder.getInstance().setEbayData(ebayUrl);
            //ebayDataHolder.setEbayData(ebayUrl);
            startActivity(intent);
        }

    }
/***************** END USER STRING LISTENER & OPERATION METHODS ********************/

}
