package com.keerthy.media.library;

import java.util.ArrayList;
import java.util.List;

import android.database.ContentObserver;
import android.os.Handler;

/**
 * Listens to media change and notifies registered library to update their cache.
 * 
 * @author keerthys
 * 
 */
public class MediaContentObserver extends ContentObserver {

    private List<MediaChangeListener> listeners;

    public interface MediaChangeListener {
        public void onMediaChange();
    }

    
    public MediaContentObserver(Handler handler) {
        super(handler);
        listeners = new ArrayList<MediaContentObserver.MediaChangeListener>();
    }

    public void registerListener(MediaChangeListener mediaChangeListener) {
        listeners.add(mediaChangeListener);
    }

    @Override
    public void onChange(boolean selfChange) {
        for (MediaChangeListener listener : listeners) {
            listener.onMediaChange();
        }
    }
}
