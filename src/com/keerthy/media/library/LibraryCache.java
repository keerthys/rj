package com.keerthy.media.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.keerthy.media.item.MediaItem;

public class LibraryCache {

    private final Map<Long, MediaItem> itemMapping;
    
    public LibraryCache() {
        itemMapping = new HashMap<Long, MediaItem>();
    }
    
    public void buildItems(List<MediaItem> mediaItems) {
        
    }
}
