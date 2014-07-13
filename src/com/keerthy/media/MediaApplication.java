package com.keerthy.media;

import com.keerthy.media.library.LibraryManager;

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
        
        // To warm up and load the library contents from a background thread.
        LibraryManager.getInstance();
    }

    public static Context getAppContext() {
        return appInstance.getApplicationContext();
    }
}
