package com.inhand.inhandappbeta;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView.OnEditorActionListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;



public class SearchActivity extends AppCompatActivity implements OnEditorActionListener {
    //Logging purposes
    private static final String TAG = "SearchActivity";

    //Define widget variables
    private EditText userEnteredSearchPhrase;

    //Define instance variables
    private String userEnteredSearchString;

    //Define the SharedPreferences object
    private SharedPreferences savedValues;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.search_activity);

        //Get references to widgets
        userEnteredSearchPhrase = (EditText) findViewById(R.id.searchbar);

        //Set listener for EditText widget
        userEnteredSearchPhrase.setOnEditorActionListener(this);

        //Get SharedPreferences object
        savedValues = getSharedPreferences("userEnteredSearchString", MODE_PRIVATE);

    }

    /*******************
     * START ABOUT MENU METHODS
     *********************************/

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

    /******************
     * START USER STRING LISTENER & OPERATION METHODS
     ************/

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
                            //TODO process search
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

    /*****************
     * END USER STRING LISTENER & OPERATION METHODS
     ********************/

    /******************
     * READ EBAY URL
     ************/

    public void readURL(String keywords) {

        try {
            URL oracle = new URL("http://svcs.ebay.com/services/search/FindingService/v1" +
                    "?OPERATION-NAME=findItemsByKeywords" +
                    "&SERVICE-VERSION=1.0.0" +
                    "&SECURITY-APPNAME=inHanda34-8e86-4e05-9e5b-1fdeb7f3cab" +
                    "&RESPONSE-DATA-FORMAT=XML" +
                    "&REST-PAYLOAD" +
                    "&keywords=computer");
                    //"&keywords=harry%20potter%20phoenix");
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                Log.i(TAG, "SearchActivity");
                //System.out.println(inputLine);
            in.close();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }
}