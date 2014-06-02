package com.keerthy.media.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.keerthy.media.controller.MusicController;
import com.keerthy.music.R;

public class NowPlayingActivity extends Activity {

    private final MusicController musicController = new MusicController();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);
        musicController.onCreate();
        View  view = findViewById(R.id.main);
        musicController.addMusicControllerWidget(view, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        musicController.onStart();

    }
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.now_playing, menu);
        return true;
    }

}
