package com.keerthy.media.library;

import android.os.HandlerThread;

import com.keerthy.media.item.MediaDetailsRetriever;
import com.keerthy.media.library.MediaContentObserver.MediaChangeListener;

/**
 * Library implementation providing the functionality of data loading and cache
 * maintenance.
 * 
 * @author keerthys
 * 
 */
public class Library implements MediaChangeListener {
    final LibraryHandler handler;
    final LibraryCache cache;
    private HandlerThread workerThread;
    private final MediaContentObserver contentObserver;

    public Library(MediaDetailsRetriever mediaDetailsRetriever, MediaContentObserver mediaContentObserver) {
        cache = new LibraryCache();
        this.workerThread = new HandlerThread(this.getClass().getName(),
            android.os.Process.THREAD_PRIORITY_DEFAULT + 1);
        this.contentObserver = mediaContentObserver;
        this.workerThread.start();
        handler = new LibraryHandler(mediaDetailsRetriever, cache, null);

    }
    
    public void start() {
        contentObserver.registerListener(this);
        reloadCache();
    }

    private void reloadCache() {
        handler.sendEmptyMessage(LibraryHandler.RUN_QUERY);
    }

    @Override
    public void onMediaChange() {
        reloadCache();
    }
}
