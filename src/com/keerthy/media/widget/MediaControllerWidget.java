package com.keerthy.media.widget;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.keerthy.media.item.MusicItem;
import com.keerthy.music.R;

/**
 * Widget that controls that takes user input and controls playback.
 * 
 * @author keerthys
 * 
 */
public class MediaControllerWidget implements SeekBar.OnSeekBarChangeListener {

    private ImageButton btnPlay;
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private ImageButton btnPlaylist;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    private SeekBar songProgressBar;
    private TextView songTitleLabel;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;

    // TODO Have this configurable
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds

    private final Handler mHandler = new Handler();
    private final IMediaPlayerController mediaPlayerController;

    /**
     * Interface to communicate the action performed on the media controller.
     * 
     * @author keerthys
     * 
     */
    public interface IMediaPlayerController {
        void start();

        void pause();

        int getDuration();

        int getCurrentPosition();

        void seekTo(int pos);

        void playNext();

        void playPrevious();

        boolean isPlaying();
        
        MusicItem getCurrentItem();
        
    }

    public MediaControllerWidget(Context context, View view,
        IMediaPlayerController mediaPlayerController) {
        this.mediaPlayerController = mediaPlayerController;
        btnPlay = (ImageButton) view.findViewById(R.id.btnPlay);
        /**
         * Play button click event plays a song and changes button to pause
         * image pauses a song and changes button to play image
         * */
        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check for already playing
                if (MediaControllerWidget.this.mediaPlayerController.isPlaying()) {
                    MediaControllerWidget.this.mediaPlayerController.pause();
                    // Changing button image to play button
                    btnPlay.setImageResource(R.drawable.btn_play);
                }
                else {
                    // Resume song
                    MediaControllerWidget.this.mediaPlayerController.start();
                    // Changing button image to pause button
                    btnPlay.setImageResource(R.drawable.btn_pause);
                }

            }
        });
        btnForward = (ImageButton) view.findViewById(R.id.btnForward);
        /**
         * Forward button click event Forwards song specified seconds
         * */
        btnForward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // get current song position
                final int currentPosition = MediaControllerWidget.this.mediaPlayerController
                    .getCurrentPosition();
                final int duration = MediaControllerWidget.this.mediaPlayerController.getDuration();
                // check if seekForward time is lesser than song duration
                if (currentPosition + seekForwardTime <= duration) {
                    // forward song
                    MediaControllerWidget.this.mediaPlayerController.seekTo(currentPosition
                        + seekForwardTime);
                }
                else {
                    // forward to end position
                    MediaControllerWidget.this.mediaPlayerController.seekTo(duration);
                }
            }
        });
        btnBackward = (ImageButton) view.findViewById(R.id.btnBackward);
        /**
         * Backward button click event Backward song to specified seconds
         * */
        btnBackward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // get current song position
                int currentPosition = MediaControllerWidget.this.mediaPlayerController
                    .getCurrentPosition();
                // check if seekBackward time is greater than 0 sec
                if (currentPosition - seekBackwardTime >= 0) {
                    // forward song
                    MediaControllerWidget.this.mediaPlayerController.seekTo(currentPosition
                        - seekBackwardTime);
                }
                else {
                    // backward to starting position
                    MediaControllerWidget.this.mediaPlayerController.seekTo(0);
                }

            }
        });
        btnNext = (ImageButton) view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                MediaControllerWidget.this.mediaPlayerController.playNext();
            }
        });
        btnPrevious = (ImageButton) view.findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                MediaControllerWidget.this.mediaPlayerController.playPrevious();
            }
        });

        btnRepeat = (ImageButton) view.findViewById(R.id.btnRepeat);
        btnShuffle = (ImageButton) view.findViewById(R.id.btnShuffle);
        songProgressBar = (SeekBar) view.findViewById(R.id.songProgressBar);
        songProgressBar.setOnSeekBarChangeListener(this);
        songTitleLabel = (TextView) view.findViewById(R.id.songTitle);
        songCurrentDurationLabel = (TextView) view.findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) view.findViewById(R.id.songTotalDurationLabel);

    }

    public void show() {
        updateProgressBar();
        if (MediaControllerWidget.this.mediaPlayerController.isPlaying()) {
            // Changing button image to pause button
            btnPlay.setImageResource(R.drawable.btn_pause);
        }
        else {
            // Changing button image to play button
            btnPlay.setImageResource(R.drawable.btn_play);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // TODO Auto-generated method stub

    }

    /**
     * When user starts moving the progress handler
     * */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    /**
     * When user stops moving the progress handler
     * */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mediaPlayerController.getDuration();
        int currentPosition = progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mediaPlayerController.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    /**
     * Update timer on seekbar
     * */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mediaPlayerController.getDuration();
            long currentDuration = mediaPlayerController.getCurrentPosition();

            // Displaying Total Duration time
            songTotalDurationLabel.setText("" + milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText("" + milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int) (getProgressPercentage(currentDuration, totalDuration));
            // Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    /**
     * Function to convert milliseconds time to Timer Format
     * Hours:Minutes:Seconds
     * */
    private String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        }
        else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Function to get Progress percentage
     * 
     * @param currentDuration
     * @param totalDuration
     * */
    private int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     * 
     * @param progress
     *            -
     * @param totalDuration
     *            returns current duration in milliseconds
     * */
    private int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

}
