package com.inhand.inhandappbeta;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ItemActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        
        // get references to widgets
        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        TextView currentPriceTextView = (TextView) findViewById(R.id.currentPriceTextView);
        TextView linkTextView = (TextView) findViewById(R.id.linkTextView);
        
        // get the intent
        Intent intent = getIntent();
        
        // get data from the intent
        String title = intent.getStringExtra("title");
        String price = intent.getStringExtra("currentPrice");

        // display data on the widgets
        titleTextView.setText(title);
        currentPriceTextView.setText(price);

        // set listener
        linkTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // get the intent
        Intent intent = getIntent();
        
        // get the Uri for the link
        String link = intent.getStringExtra("link");
        Uri viewUri = Uri.parse(link);
        
        // create the intent and start it
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, viewUri);
        startActivity(viewIntent);
    }
}