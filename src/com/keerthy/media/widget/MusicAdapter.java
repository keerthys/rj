package com.keerthy.media.widget;

import java.util.List;

import com.keerthy.media.MediaApplication;
import com.keerthy.media.cache.MusicItem;
import com.keerthy.music.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MusicAdapter extends BaseAdapter {

    List<MusicItem> musicItems;
    LayoutInflater layoutInflater;

    public MusicAdapter(List<MusicItem> musicItems) {
        this.musicItems = musicItems;
        this.layoutInflater = LayoutInflater.from(MediaApplication.getAppContext());
    }

    @Override
    public int getCount() {
        return musicItems.size();
    }

    @Override
    public Object getItem(int position) {
        return musicItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.music_item, parent);
        TextView titleView = (TextView) view.findViewById(R.id.song_title);
        TextView artistView = (TextView) view.findViewById(R.id.song_artist);
        return view;
    }

}
