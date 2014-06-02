package com.keerthy.media.cache;

import java.util.LinkedList;
import java.util.List;

import com.keerthy.media.MediaApplication;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

public abstract class MediaDetailsRetriever<T> {

    abstract String[] getProjection();

    abstract String getSelection();

    abstract Uri getUri();

    abstract T constructMediaItem(Cursor cursor);

    public List<T> getMediaItems() {
        ContentResolver contentResolver = MediaApplication.getAppContext().getContentResolver();
        Cursor cursor = contentResolver
            .query(getUri(), getProjection(), getSelection(), null, null);
        List<T> mediaList = new LinkedList<T>();
        while (cursor.moveToNext()) {
            mediaList.add(constructMediaItem(cursor));
        }
        return mediaList;

    }

}
