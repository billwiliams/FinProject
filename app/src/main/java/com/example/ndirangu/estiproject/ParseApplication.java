package com.example.ndirangu.estiproject;

import com.parse.Parse;
import com.parse.ParseACL;

import com.parse.ParseUser;
import com.parse.PushService;

import android.app.Application;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.initialize(getBaseContext(), "lFMlSHevZuVxgqsDWayVIGXrfUfu95vjOitDmIXA", "lgTKFqyulSwLFPs85TvxONg6SETNqCxI4sUs6YFV");
        PushService.setDefaultPushCallback(this, StartfullActivity.class);

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }

}
