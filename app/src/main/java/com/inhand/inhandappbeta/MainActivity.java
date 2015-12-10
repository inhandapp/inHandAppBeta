package com.inhand.inhandappbeta;

import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends AppCompatActivity implements OnEditorActionListener{

    //Define widget variables
    private EditText userEnteredSearchPhrase;

    //Define instance variables
    public String userEnteredSearchString = "";
    private final String TAG = "Problem!";

    //Define the SharedPreferences object
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

}