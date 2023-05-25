package com.livewallk.livewallpapersfork;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;


public class ForK_SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livewall_splash);
        sendToForKWall_Main();
    }


    public void sendToForKWall_Main() {
        Intent intent_LiveWallForK = new Intent(ForK_SplashActivity.this,
                ForK_MainActivity.class);
        startActivity(intent_LiveWallForK);
        finish();
    }

}