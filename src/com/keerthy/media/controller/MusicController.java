package com.keerthy.media.controller;

import java.util.List;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.MediaController.MediaPlayerControl;

import com.keerthy.media.MediaApplication;
import com.keerthy.media.activities.NowPlayingActivity;
import com.keerthy.media.cache.MusicDetailsRetriever;
import com.keerthy.media.cache.MusicItem;
import com.keerthy.media.service.MusicService;
import com.keerthy.media.service.MusicService.MusicBinder;
import com.keerthy.media.widget.MediaControllerWidget;

/**
 * Controls and manipulates the UI for the {@link NowPlayingActivity}
 * 
 * @author keerthys
 * 
 */
public class MusicController extends BaseController implements MediaPlayerControl, IMediaPlaybackListener {

    private MusicService musicService;
    private boolean boundToService;
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
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
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
        musicControllerWidget = new MediaControllerWidget(context);
        musicControllerWidget.setMediaPlayer(this);
        musicControllerWidget.setAnchorView(view);
        musicControllerWidget.setEnabled(true);
        musicControllerWidget.setAlpha(70);
        musicControllerWidget.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              musicService.playNext();
            }
          }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              musicService.playPrevious();
              
            }
          });
    }
    
    @Override
    public void onTrackChange() {
        musicControllerWidget.show(0);        
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {
        if (musicServiceIntent == null) {
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
