package com.inhand.inhandappbeta;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SearchActivity extends AppCompatActivity implements OnEditorActionListener {

    //Define variables for the widgets
    private EditText userEnteredSearchPhrase;
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

    /***************** START ABOUT MENU METHODS ********************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onAboutClick(MenuItem item) {

        // Inflate the about message contents
        View messageView = getLayoutInflater().inflate(R.layout.about_menu, null, false);

        // When linking text, force to always use default color. This works
        // around a pressed color state bug.
        TextView textView = (TextView) messageView.findViewById(R.id.aboutMenu);
        int defaultColor = textView.getTextColors().getDefaultColor();
        textView.setTextColor(defaultColor);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.logo);
        builder.setTitle(R.string.app_name);
        builder.setView(messageView);
        builder.create();
        builder.show();

    }

    /***************** END ABOUT MENU METHODS ********************/

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //Parses String from EditText widget
        userEnteredSearchString = String.valueOf(userEnteredSearchString);
        return false;
    }
}
