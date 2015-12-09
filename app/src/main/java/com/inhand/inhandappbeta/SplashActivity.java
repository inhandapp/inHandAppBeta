package com.inhand.inhandappbeta;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.widget.ImageView;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    //Define instance variables
    private static final int STOPSPLASH = 0;
    private static final long SPLASHTIME = 3000;

    //Define widget variables
    private ImageView splash;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        splash = (ImageView) findViewById(R.id.splash_screen);
        Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }

    /******************* START SPLASH SCREEN HANDLER*********************************/
    private Handler splashHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOPSPLASH:
                    launchSearchActivity();
                    break;
            }
            super.handleMessage(msg);
        }

    };
    /******************* END SPLASH SCREEN HANDLER*********************************/

    /******************* START MAIN_ACTIVITY INTENT*********************************/
    public void launchSearchActivity(){
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
    }
    /******************* END MAIN_ACTIVITY INTENT*********************************/
}