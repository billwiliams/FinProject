package com.example.ndirangu.estiproject;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.parse.Parse;
import com.parse.ParseACL;

import com.parse.ParseUser;
import com.parse.PushService;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import java.util.List;

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
