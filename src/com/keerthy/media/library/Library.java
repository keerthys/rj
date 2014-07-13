package com.keerthy.media.library;

import android.os.HandlerThread;

import com.keerthy.media.item.MediaDetailsRetriever;

/**
 * Library implementation providing the functionality of data loading and cache
 * maintenance.
 * 
 * @author keerthys
 * 
 */
public class Library {
    final LibraryHandler handler;
    final LibraryCache cache;
    private HandlerThread workerThread;

    public Library(MediaDetailsRetriever mediaDetailsRetriever) {
        cache = new LibraryCache();
        this.workerThread = new HandlerThread(this.getClass().getName(),android.os.Process.THREAD_PRIORITY_DEFAULT+1);
        this.workerThread.start();
        handler = new LibraryHandler(mediaDetailsRetriever, cache, null);
        handler.sendEmptyMessage(LibraryHandler.RUN_QUERY);
    }
}
