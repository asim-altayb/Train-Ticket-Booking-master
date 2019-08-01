package com.example.fashkl.im;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(splash.this);
       final boolean islog = preferences.getBoolean("islogined", false);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                if(!islog){
                Intent mainIntent = new Intent(splash.this,HomePage.class);
                splash.this.startActivity(mainIntent);}
                else
                {
                    Intent ntent = new Intent(splash.this,Grid_Home.class);
                    splash.this.startActivity(ntent);
                }
                splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
