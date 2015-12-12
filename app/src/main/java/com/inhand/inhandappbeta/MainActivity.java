package com.inhand.inhandappbeta;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView.OnEditorActionListener;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        //implements OnKeyListener {
        implements OnEditorActionListener {

    //Define class variables
    public eBayURL url;
    private eBayFileIO io;

    //Define widget variables
    private EditText userEnteredSearchPhrase;
    private TextView titleTextView;
    private ListView itemsListView;
    private Toolbar header_toolbar;

    //Define instance variables
    public String userEnteredSearchString = "";
    private final String TAG = "MainAct Problem!";

    //Define the SharedPreferences object
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        io = new eBayFileIO(getApplicationContext());

        userEnteredSearchPhrase = (EditText) findViewById(R.id.search_bar);

        userEnteredSearchPhrase.setVisibility(View.GONE);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                launchSearchActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void launchSearchActivity(){

        if(userEnteredSearchPhrase.getVisibility() == View.GONE){
            userEnteredSearchPhrase.setVisibility(View.VISIBLE);
        }
        else {
            userEnteredSearchPhrase.setVisibility(View.GONE);
        }
    }

    /*
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        userEnteredSearchPhrase.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:

                            //Parses String from EditText widget
                            userEnteredSearchString = String.valueOf(userEnteredSearchPhrase);

                            return true;

                        default:
                            break;
                    }
                }
                return false;
            }
        });
        return false;
    }*/

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
            Intent intent = new Intent (getApplicationContext(), ResultsActivity.class);
            DataHolder.getInstance().setData(url);
            startActivity(intent);
        }
    }
/***************** END USER STRING LISTENER & OPERATION METHODS ********************/

}
