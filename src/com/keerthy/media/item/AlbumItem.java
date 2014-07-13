package com.keerthy.media.item;

import java.util.ArrayList;
import java.util.List;

public final class AlbumItem extends MediaItem {

    private long id;
    private List<MusicItem> musicItems;

    public AlbumItem() {
        musicItems = new ArrayList<MusicItem>();
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addMusicItem(MusicItem musicItem) {
        musicItems.add(musicItem);
    }
    
    public List<MusicItem> getMusicItems() {
        return musicItems;
    }
    
    

}
