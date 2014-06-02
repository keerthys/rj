package com.keerthy.media.activities;

import com.keerthy.music.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MediaListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_media_list);
        ListView listView = (ListView) findViewById(R.id.media_list);
        
        super.onCreate(savedInstanceState);
    }
}
