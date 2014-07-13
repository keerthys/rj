package com.keerthy.media.item;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class MusicDetailsRetriever extends MediaDetailsRetriever {

    private static final String SELECTION = MediaStore.Audio.Media.IS_MUSIC + " != 0";

    private static final String[] PROJECTION = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.COMPOSER,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION
    };
    
    @Override
    String[] getProjection() {
        return PROJECTION;
    }

    @Override
    String getSelection() {
        return SELECTION;
    }

    @Override
    MediaItem constructMediaItem(Cursor cursor) {
        MusicItem musicItem = new MusicItem();
        musicItem.setId(cursor.getLong(0));
        musicItem.setArtist(cursor.getString(1));
        musicItem.setAlbum(cursor.getString(2));
        musicItem.setComposer(cursor.getString(3));
        musicItem.setTitle(cursor.getString(4));
        musicItem.setData(cursor.getString(5));
        musicItem.setDisplayName(cursor.getString(6));
        musicItem.setDuration(cursor.getLong(7));
        Log.i("Sample", musicItem.toString());
        return musicItem;
    }

    @Override
    Uri getUri() {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }
    

}
