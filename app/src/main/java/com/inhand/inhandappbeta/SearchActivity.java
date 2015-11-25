package com.inhand.inhandappbeta;


import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView.OnEditorActionListener;

public class SearchActivity extends AppCompatActivity implements OnEditorActionListener {

    //Define variables for the widgets
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

    /******************* START ABOUT MENU METHODS*********************************/


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


/***************** END USER STRING LISTENER & OPERATION METHODS ********************/
}