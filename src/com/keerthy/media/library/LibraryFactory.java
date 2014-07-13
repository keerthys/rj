package com.keerthy.media.library;

import com.keerthy.media.item.AlbumDetailsRetriever;
import com.keerthy.media.item.ArtistDetailsRetriever;
import com.keerthy.media.item.MusicDetailsRetriever;

/**
 * Responsible for creating library instance base on the type.
 * 
 * @author keerthys
 * 
 */
public class LibraryFactory {

    private final MediaContentObserver mediaContentObserver;

    public LibraryFactory(MediaContentObserver contentObserver) {
        this.mediaContentObserver = contentObserver;
    }

    Library createLibrary(LibraryType libraryType) {
        switch (libraryType) {
        case MUSIC:
            return new Library(new MusicDetailsRetriever(), mediaContentObserver);
        case ALBUM:
            return new Library(new AlbumDetailsRetriever(), mediaContentObserver);
        case ARTIST:
            return new Library(new ArtistDetailsRetriever(), mediaContentObserver);
        default:
            throw new UnsupportedOperationException("Libray does not existd");
        }

    }
}
