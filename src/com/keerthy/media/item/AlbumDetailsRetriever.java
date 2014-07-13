package com.keerthy.media.item;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class AlbumDetailsRetriever extends MediaDetailsRetriever {

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
        return  new AlbumItem();
    }

    @Override
    Uri getUri() {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }
    

}
