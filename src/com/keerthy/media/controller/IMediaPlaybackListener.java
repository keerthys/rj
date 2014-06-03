package com.keerthy.media.controller;

/**
 * Interface between the music service and music controller.
 * 
 * @author keerthys
 * 
 */
public interface IMediaPlaybackListener {
    /**
     * To notify the controller to update the UI.
     */
    public void onTrackChange();
}
