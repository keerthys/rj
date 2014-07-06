package com.keerthy.media.controller;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.keerthy.media.MediaApplication;
import com.keerthy.media.activities.HomeActivity;
import com.keerthy.music.R;

/**
 * Adds notification to the notification and lock screen widget with information about
 * currently playing song.
 * 
 * @author keerthys
 * 
 */
public class NotificationController extends AppWidgetProvider {

    public static final String ACTION_MEDIA_PLAYER_PLAY_PAUSE = "com.keerthy.media.service.MediaPlayerPlayPause";
    public static final String ACTION_MEDIA_PLAYER_PLAY_PREVIOUS = "com.keerthy.media.service.MediaPlayerPlayPrevious";
    public static final String ACTION_MEDIA_PLAYER_PLAY_NEXT = "com.keerthy.media.service.MediaPlayerPlayNext";

    private static final int NOTIFICATION_ID = 1;
    private static Intent INTENT_PLAY_NEXT = new Intent(ACTION_MEDIA_PLAYER_PLAY_NEXT);
    private static Intent INTENT_PLAY_PREVIOUS = new Intent(ACTION_MEDIA_PLAYER_PLAY_PREVIOUS);
    private static Intent INTENT_PLAY_PAUSE = new Intent(ACTION_MEDIA_PLAYER_PLAY_PAUSE);

    public void sendNotification(Service service) {

        Notification.Builder builder = new Notification.Builder(MediaApplication.getAppContext());
        RemoteViews notificationView = constructNotificationView();

        builder.setSmallIcon(R.drawable.ic_launcher).setOngoing(true).setContent(notificationView);
        Notification notification = builder.build();
        service.startForeground(NOTIFICATION_ID, notification);
    }

    private RemoteViews constructNotificationView() {
        Intent activityIntent = new Intent(MediaApplication.getAppContext(), HomeActivity.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent activityPendingIntent = PendingIntent.getActivity(
            MediaApplication.getAppContext(), 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews notificationView = new RemoteViews(MediaApplication.getAppContext()
            .getPackageName(), R.layout.notification_media_controller);
        notificationView.setOnClickPendingIntent(R.id.notificationHolder, activityPendingIntent);

        PendingIntent servicePendingIntent = PendingIntent.getService(
            MediaApplication.getAppContext(), 0, INTENT_PLAY_PAUSE,
            PendingIntent.FLAG_UPDATE_CURRENT);
        notificationView.setOnClickPendingIntent(R.id.btnPlay, servicePendingIntent);

        servicePendingIntent = PendingIntent.getService(MediaApplication.getAppContext(), 0,
            INTENT_PLAY_PREVIOUS, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationView.setOnClickPendingIntent(R.id.btnPrevious, servicePendingIntent);

        servicePendingIntent = PendingIntent.getService(MediaApplication.getAppContext(), 0,
            INTENT_PLAY_NEXT, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationView.setOnClickPendingIntent(R.id.btnNext, servicePendingIntent);

        if (MusicController.getInstance().isPlaying()) {
            notificationView.setImageViewResource(R.id.btnPlay, R.drawable.btn_pause);
        }
        else {
            notificationView.setImageViewResource(R.id.btnPlay, R.drawable.btn_play);
        }
        notificationView.setTextViewText(R.id.songTitle, MusicController.getInstance().getCurrentItem().getDisplayName());
        return notificationView;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        RemoteViews remoteViews = constructNotificationView();
        for (int i = 0; i < N; i++) {
            appWidgetManager.updateAppWidget(i, remoteViews);
        }
    }
}
