package com.keerthy.media.library;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.keerthy.media.item.MediaDetailsRetriever;

/**
 * Handler to which we will post messages to retrieve the meta data about
 * everything related to music player.
 * 
 * @author keerthys
 * 
 */
public class LibraryHandler extends Handler {

    public static final int RUN_QUERY = 0;
    public static final int ADD_TO_FAVORITES = 1;

    private final MediaDetailsRetriever mediaDetailsRetriever;
    private final LibraryCache cache;

    public LibraryHandler(MediaDetailsRetriever mediaDetailsRetriever, LibraryCache cache,
        HandlerThread workerThread) {
        super(workerThread.getLooper());
        this.mediaDetailsRetriever = mediaDetailsRetriever;
        this.cache = cache;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
        case RUN_QUERY:
            loadCache();
            break;

        default:
            break;
        }
    }

    private void loadCache() {
        this.cache.buildItems(mediaDetailsRetriever.getMediaItems());
    }
}
