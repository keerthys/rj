package com.keerthy.media;

import android.app.Application;
import android.content.Context;

public class MediaApplication extends Application {

    private static MediaApplication appInstance;

    public MediaApplication() {
        appInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context getAppContext() {
        return appInstance.getApplicationContext();
    }
}
