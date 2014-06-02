package com.keerthy.media.service;

import java.util.List;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;

import com.keerthy.media.activities.NowPlayingActivity;
import com.keerthy.media.cache.MusicItem;
import com.keerthy.music.R;

/**
 * Talks with {@link MediaPlayer} and provides the implementation for the
 * different play back options specified in {@link MediaPlayerControls}
 * 
 * @author keerthys
 * 
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayerControls {

    private static final int NOTIFY_ID = 1;
    private final IBinder musicBinder = new MusicBinder();

    private MediaPlayer mediaPlayer;
    private List<MusicItem> musicItems;
    private int currentIndex;

    @Override
    public void onCreate() {
        super.onCreate();
        currentIndex = 0;
        initializeMediaPlayer();
    }

    private void initializeMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    public void setList(List<MusicItem> musicItems) {
        this.musicItems = musicItems;
        this.currentIndex = 0;
    }

    @Override
    public void playMusic(int songIndex) {
        currentIndex = songIndex;
        playMusic();
    }

    @Override
    public int getCurrentPostion() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void pauseMediaPlayer() {
        mediaPlayer.pause();
    }

    @Override
    public void startMediaPlayer() {
        mediaPlayer.start();
    }

    @Override
    public void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }

    @Override
    public void playNext() {
        currentIndex++;
        if (currentIndex > musicItems.size()) {
            currentIndex = 0;
        }
        playMusic();
    }

    @Override
    public void playPrevious() {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = musicItems.size() - 1;
        }
        playMusic();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        // playNext();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        MusicItem musicItem = musicItems.get(currentIndex);
        // TODO - Re factor notification part.
        Intent notIntent = new Intent(this, NowPlayingActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0, notIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendInt).setSmallIcon(R.drawable.ic_launcher)
            .setTicker(musicItem.getTitle()).setOngoing(true).setContentTitle("Playing")
            .setContentText(musicItem.getDisplayName());
        Notification not = builder.build();

        startForeground(NOTIFY_ID, not);
    }

    private void playMusic() {
        MusicItem musicItem = musicItems.get(currentIndex);
        long id = musicItem.getId();
        Uri musicUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(getApplicationContext(), musicUri);
            mediaPlayer.prepare();
        }
        catch (Exception e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
        
        
    }

    /**
     * Binder instance returned when establishing connection with this service.
     * 
     * @author keerthys
     * 
     */
    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

}
