package com.inhand.inhandappbeta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private final String TAG = "Problem!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = (EditText) findViewById(R.id.search_bar);
        searchEditText.setVisibility(View.GONE);

        //include toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.logo_bar);
        setSupportActionBar(myToolbar);
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (NullPointerException e){
            Log.v(TAG,"NullPointer");
        }
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

        if(searchEditText.getVisibility() == View.GONE){
            searchEditText.setVisibility(View.VISIBLE);
        }
        else {
            searchEditText.setVisibility(View.GONE);
        }
    }
}