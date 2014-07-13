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

    Library createLibrary(LibraryType libraryType) {
        switch (libraryType) {
        case MUSIC:
            return new Library(new MusicDetailsRetriever());
        case ALBUM:
            return new Library(new AlbumDetailsRetriever());
        case ARTIST:
            return new Library(new ArtistDetailsRetriever());
        default:
            throw new UnsupportedOperationException("Libray does not existd");
        }

    }
}
