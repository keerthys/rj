package com.keerthy.media.controller;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;

import com.keerthy.media.MediaApplication;
import com.keerthy.media.activities.HomeActivity;
import com.keerthy.media.cache.MusicDetailsRetriever;
import com.keerthy.media.cache.MusicItem;
import com.keerthy.media.service.MusicService;
import com.keerthy.media.service.MusicService.MusicBinder;
import com.keerthy.media.widget.MediaControllerWidget;
import com.keerthy.media.widget.MediaControllerWidget.IMediaPlayerController;

/**
 * Controls and manipulates the UI for the {@link HomeActivity}
 * 
 * @author keerthys
 * 
 */
public class MusicController extends BaseController implements IMediaPlayerController,
    IMediaPlaybackListener {

    private MusicService musicService;
    // Static instance is needed as we should not establish a new service
    // connection when the activity comes to front.
    private static boolean boundToService;
    private Intent musicServiceIntent;
    private List<MusicItem> musicItems;
    private MediaControllerWidget musicControllerWidget;

    public MusicController() {
        musicItems = new MusicDetailsRetriever().getMediaItems();
    }

    @Override
    public void start() {
        musicService.startMediaPlayer();
    }

    @Override
    public void playNext() {
        musicService.playNext();
    }

    @Override
    public void playPrevious() {
        musicService.playPrevious();
    }

    @Override
    public void pause() {
        musicService.pauseMediaPlayer();
    }

    @Override
    public int getDuration() {
        return musicService.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return musicService.getCurrentPostion();
    }

    @Override
    public void seekTo(int pos) {
        musicService.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return musicService.isPlaying();
    }

    /**
     * Callback that gets invoked when the connection with the
     * {@link MusicService} has been established / disconnected.
     */
    private ServiceConnection musicServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            MusicBinder musicBinder = (MusicBinder) binder;
            musicService = musicBinder.getService();
            musicService.setList(musicItems);
            musicService.setListener(MusicController.this);
            musicService.playMusic(3);
            musicControllerWidget.show();
            boundToService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            boundToService = false;
        }

    };

    /**
     * Adds the music controller widget on top of the provided view.
     * 
     * @param view
     */
    public void addMusicControllerWidget(View view, Context context) {
        musicControllerWidget = new MediaControllerWidget(context, view, this);
    }

    @Override
    public void onTrackChange() {
        musicControllerWidget.show();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {
        if (!boundToService) {
            musicServiceIntent = new Intent(MediaApplication.getAppContext(), MusicService.class);
            MediaApplication.getAppContext().bindService(musicServiceIntent,
                musicServiceConnection, Context.BIND_AUTO_CREATE);
            MediaApplication.getAppContext().startService(musicServiceIntent);
        }
    }

    @Override
    public void onStop() {

    }

}
