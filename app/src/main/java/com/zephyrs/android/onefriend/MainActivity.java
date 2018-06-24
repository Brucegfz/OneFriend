package com.zephyrs.android.onefriend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private OutputStream os;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){

                preferences = getSharedPreferences("phone", Context.MODE_PRIVATE);

                if (preferences.getBoolean("firststart", true)) {
                    editor = preferences.edit();
                    editor.putBoolean("firststart", false);
                    editor.commit();
                    Intent homeIntent = new Intent(MainActivity.this,IntroductionPage.class);
                    startActivity(homeIntent);
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                    finish();
                } else{
                    Intent homeIntent = new Intent(MainActivity.this,BottomBar.class);
                    startActivity(homeIntent);
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                    finish();
                }

            }
        },SPLASH_TIME_OUT);
    }
}
