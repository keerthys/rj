package com.keerthy.media.library;

import java.util.HashMap;
import java.util.Map;

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
        LibraryFactory libraryFactory = new LibraryFactory();
        for (LibraryType libraryType : LibraryType.values()) {
            libraries.put(libraryType, libraryFactory.createLibrary(libraryType));
        }
    }
}
