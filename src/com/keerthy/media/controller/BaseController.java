package com.keerthy.media.controller;

/**
 * Base class for controlling / manipulating the UI.
 * 
 * @author keerthys
 * 
 */
public abstract class BaseController {

    /**
     * Called when a new instance of the activity have been created.
     */
    public abstract void onCreate();

    /**
     * Called when the activity becomes visible.
     */
    public abstract void onStart();

    /**
     * Called when the activity goes to background and becomes invisible.
     */
    public abstract void onStop();
}
