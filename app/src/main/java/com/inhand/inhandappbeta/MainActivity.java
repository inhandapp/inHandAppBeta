package com.inhand.inhandappbeta;

import android.view.View;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

public class MainActivity extends Activity {

    //Define instance variables
    private static final int STOPSPLASH = 0;
    private static final long SPLASHTIME = 3000;

    //Define widget variables
    private ImageView splash;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        splash = (ImageView) findViewById(R.id.splashscreen);
        Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }

    /******************* START SPLASH SCREEN METHODS*********************************/

    private  Handler splashHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOPSPLASH:
                    //remove SplashScreen from view
                    splash.setVisibility(View.GONE);
                    launchSearchActivity();
                    break;
            }
            super.handleMessage(msg);
        }

    };

    public void launchSearchActivity(){
        Intent i= new Intent(this,SearchActivity.class);
        startActivity(i);
    }

    /******************* END SPLASH SCREEN METHODS*********************************/

}