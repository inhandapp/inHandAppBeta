package com.inhand.inhandappbeta;

import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultsActivity extends AppCompatActivity implements OnItemClickListener{

    private eBayURL url;

    //private TextView titleTextView;
    private ListView itemsListView;

    private final String TAG = "ResultsAct Problem!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //titleTextView = (TextView) findViewById(R.id.titleTextView);
        itemsListView = (ListView) findViewById(R.id.listView);

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
            Log.d(TAG, "No eBay search results?");
            //titleTextView.setText("Unable to get eBay search results");
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
            //map.put("link", item.getLink());
            data.add(map);
        }

        // create the resource, from, and to variables
        int resource = R.layout.listview_item;
        String[] from = {"title", "currentPrice"};
        //String[] from = {"title", "currentPrice", "link"};
        int[] to = {R.id.titleTextView, R.id.currentPriceTextView};
        //int[] to = {R.id.titleTextView, R.id.currentPriceTextView, R.id.linkTextView};

        // create and set the adapter
        SimpleAdapter adapter =
                new SimpleAdapter(this, data, resource, from, to);
        itemsListView.setAdapter(adapter);

        Log.d("eBay", "Search results displayed");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {

        // get the item at the specified position
        eBayItem item = url.getItem(position);

        // create an intent
        Intent intent = new Intent(this, ResultsActivity.class);

        intent.putExtra("title", item.getTitle());
        intent.putExtra("currentPrice", item.getPrice());
        intent.putExtra("link", item.getLink());

        this.startActivity(intent);
    }
}