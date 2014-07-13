package com.keerthy.media.library;

import java.util.HashMap;
import java.util.Map;

import com.keerthy.media.MediaApplication;

import android.content.Context;
import android.provider.MediaStore;

/**
 * Holds and manages the instance of library supported by this application.
 * 
 * @author keerthys
 * 
 */
public class LibraryManager {
    Map<LibraryType, Library> libraries;

    private LibraryManager() {
        libraries = new HashMap<LibraryType, Library>();
        warmup();
    }

    private static class SingletonHolder {
        private static final LibraryManager INSTANCE = new LibraryManager();
    }

    public static LibraryManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void warmup() {
        MediaContentObserver contentObserver = new MediaContentObserver(null);
        MediaApplication.getAppContext().getContentResolver().registerContentObserver(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, true, contentObserver);
        LibraryFactory libraryFactory = new LibraryFactory(contentObserver);
        for (LibraryType libraryType : LibraryType.values()) {
            Library library = libraryFactory.createLibrary(libraryType);
            libraries.put(libraryType, library);
            library.start();            
        }
    }
}
