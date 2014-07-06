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
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

/**
 * Singleton instance which controls and manipulates the UI for the {@link HomeActivity}
 * 
 * @author keerthys
 * 
 */
public class MusicController extends BaseController implements IMediaPlayerController,
    IMediaPlaybackListener, PanelSlideListener {

    private MusicService musicService;
    private boolean boundToService;
    private Intent musicServiceIntent;
    private List<MusicItem> musicItems;
    private MediaControllerWidget musicControllerWidget;

    private MusicController() {
        musicItems = new MusicDetailsRetriever().getMediaItems();
    }
    
    public static MusicController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder { 
        private static final MusicController INSTANCE = new MusicController();
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
    
    @Override
    public MusicItem getCurrentItem() {
        return musicService.getCurrentItem();
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

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        musicControllerWidget.show();
    }

    @Override
    public void onPanelCollapsed(View panel) {
        
    }

    @Override
    public void onPanelExpanded(View panel) {
        musicControllerWidget.show();
    }

    @Override
    public void onPanelAnchored(View panel) {
        
    }

    @Override
    public void onPanelHidden(View panel) {
        
    }

}
