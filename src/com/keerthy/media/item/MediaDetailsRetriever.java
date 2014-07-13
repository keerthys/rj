package com.keerthy.media.item;

import java.util.LinkedList;
import java.util.List;

import com.keerthy.media.MediaApplication;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

public abstract class MediaDetailsRetriever {

    abstract String[] getProjection();

    abstract String getSelection();

    abstract Uri getUri();

    abstract MediaItem constructMediaItem(Cursor cursor);

    public List<MediaItem> getMediaItems() {
        ContentResolver contentResolver = MediaApplication.getAppContext().getContentResolver();
        Cursor cursor = contentResolver
            .query(getUri(), getProjection(), getSelection(), null, null);
        List<MediaItem> mediaList = new LinkedList<MediaItem>();
        while (cursor.moveToNext()) {
            mediaList.add(constructMediaItem(cursor));
        }
        return mediaList;

    }

}
