package com.keerthy.media.activities;

import java.util.ArrayList;
import java.util.List;

import com.keerthy.media.controller.BaseController;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

    List<BaseController> controllers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controllers = new ArrayList<BaseController>();
    }

    /**
     * Adds the controller that would be interested in the activity life cycle.
     * 
     * @param controller
     */
    public void addController(BaseController controller) {
        controllers.add(controller);
        controller.onCreate();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        for (BaseController controller : controllers) {
            controller.onStart();
        }
    }
    
    @Override
    protected void onStop() {
        super.onStart();
        for (BaseController controller : controllers) {
            controller.onStop();
        }
    }

}
