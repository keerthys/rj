package com.keerthy.media.service;

/**
 * Interface specifying the different media player controls.
 * 
 * @author keerthys
 * 
 */
public interface IMediaPlayerControls {

    /**
     * Plays the specified song index.
     * 
     * @param songIndex
     */
    public void playMusic(int songIndex);

    /**
     * Returns the current play back position.
     * 
     * @return current position of the song
     */
    public int getCurrentPostion();

    /**
     * Pauses the media player.
     */
    public void pauseMediaPlayer();

    /**
     * Resumes / starts the media player.
     */
    public void startMediaPlayer();

    /**
     * Seeks the player to the specified position.
     * 
     * @param position
     *            - the offset in milliseconds from the start to seek to
     */
    public void seekTo(int position);
    
    /**
     * Plays the next song.
     */
    public void playNext();
    
    /**
     * Plays the previous song.
     */
    public void playPrevious();
    
    /**
     * Returns whether the music player is playing anything currently.
     * @return true / false
     */
    public boolean isPlaying();
    
    /**
     * Returns the total duration of the song.
     * @return
     */
    public int getDuration();
}
