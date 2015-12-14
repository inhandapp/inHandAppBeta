package com.inhand.inhandappbeta;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultsActivity extends AppCompatActivity implements OnItemClickListener, OnEditorActionListener{

    private eBayURL url;
    private eBayFileIO io;

    private EditText userEnteredSearchPhrase;
    private ListView itemsListView;

    public String userEnteredSearchString = "";
    private final String TAG = "ResultsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        itemsListView = (ListView) findViewById(R.id.listView);
        userEnteredSearchPhrase = (EditText) findViewById(R.id.search_bar);

        io = new eBayFileIO(getApplicationContext());

        itemsListView.setOnItemClickListener(this);

        //include toolbar
        /*
        try {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.logo_bar);
        setSupportActionBar(myToolbar);

            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (NullPointerException e){
            Log.v(TAG, "NullPointer");
        }*/
        url = DataHolder.getInstance().getData();
        updateDisplay(url);
    }

    public void updateDisplay(eBayURL url)
    {
        if (url == null) {
            Log.d(TAG, "No search results");
            return;
        }

        // get the items for the url
        ArrayList<eBayItem> items = url.getAllItems();

        // create a List of Map<String, ?> objects
        ArrayList<HashMap<String, String>> data =
                new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        for (eBayItem item : items) {
            map = new HashMap<String, String>();
            map.put("title", item.getTitle());
            map.put("currentPrice", item.getPrice());
            map.put("viewItemURL", item.getLink());
            eBayItem ebayitems = new eBayItem(item.getTitle(), item.getPrice(), item.getLink());
            //InHandDBHandler.addItems(ebayitems);
            data.add(map);
        }

        // create the resource, from, and to variables
        int resource = R.layout.listview_item;
        String[] from = {"title", "currentPrice", "viewItemURL"};
        int[] to = {R.id.titleTextView, R.id.currentPriceTextView, R.id.linkTextView};

        // create and set the adapter
        SimpleAdapter adapter =
                new SimpleAdapter(this, data, resource, from, to);
        itemsListView.setAdapter(adapter);

        Log.d(TAG, "Search results displayed");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {

        // get the item at the specified position
        eBayItem item = url.getItem(position);

        // create an intent
        Intent intent = new Intent(this, ItemActivity.class);

        intent.putExtra("title", item.getTitle());
        intent.putExtra("currentPrice", item.getPrice());
        intent.putExtra("viewItemURL", item.getLink());

        this.startActivity(intent);
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
            io.downloadFile(userEnteredSearchString);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d(TAG, "Search results downloaded");
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
            Log.d(TAG, "Search results read");

            // update the display for the activity
            Intent intent = new Intent (ResultsActivity.this, ResultsActivity.class);
            DataHolder.getInstance().setData(url);
            startActivity(intent);
        }
    }
}