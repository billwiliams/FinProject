package com.example.ndirangu.estiproject;

import com.example.ndirangu.estiproject.Login;
import com.example.ndirangu.estiproject.MySQLiteHelper;
import com.example.ndirangu.estiproject.Users;
import com.example.ndirangu.estiproject.util.SystemUiHider;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SaveCallback;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class StartfullActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    //time to wait then display the next screen
    private static final int DISPLAY_LENGTH=1000;
    protected ProgressBar mProgressBar;
    protected  boolean mBActive;
    protected int progress=0;
    protected  TextView firstscreentext;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(getBaseContext(), "lFMlSHevZuVxgqsDWayVIGXrfUfu95vjOitDmIXA", "lgTKFqyulSwLFPs85TvxONg6SETNqCxI4sUs6YFV");
        PushService.setDefaultPushCallback(this, StartfullActivity.class);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_startfull);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);
         firstscreentext=(TextView)findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //start another intent
        mProgressBar =(ProgressBar) findViewById(R.id.startProgressBar);
        final Thread timerThread= new Thread(){
            @Override
        public void run(){
                mBActive=true;
                try{

                    while((progress<100) ){
                        sleep(200);
                        if (mBActive){

                            updateProgress();

                        }
                    }
                    /*change the screen presented to another*/
                    runOnUiThread(new Runnable() {
                        public void run() {
                            onContinue();
                        }
                    });

                }catch (InterruptedException e){

                }finally {

                }
            }

        };
        timerThread.start();




      /*  */
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

    }
    /*Updating the progress bar based on time passed*/
    public  void updateProgress(  ){
        if (null !=mProgressBar){

           progress+=10;
            mProgressBar.setProgress(progress);

        }
    }
    /*Change the Intent Presented to the user*/

            public  void onContinue(){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String database="UserDB";
                        File file=getApplicationContext().getDatabasePath(database);
                        boolean db=doesDatabaseExist(file);
                        if(db==true) {
                            try {
                                MySQLiteHelper helpdb=new MySQLiteHelper(getApplication());
                                String Token=helpdb.findEmail();
                                if (Token!=null) {
                                    Intent mainIntent = new Intent(StartfullActivity.this, AllMenu.class);
                                    StartfullActivity.this.startActivity(mainIntent);

                                    StartfullActivity.this.finish();
                                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                }
                                else{
                                    Intent mainIntent=new Intent(StartfullActivity.this,Login.class);
                                    StartfullActivity.this.startActivity(mainIntent);

                                    StartfullActivity.this.finish();
                                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                }

                            }catch (Exception e){
                                Log.d("DATABASE","Unable to find Token");
                            }
                        }
                        else{
                            Intent mainIntent=new Intent(StartfullActivity.this,Login.class);
                            StartfullActivity.this.startActivity(mainIntent);

                            StartfullActivity.this.finish();
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                        }


                    }
                },DISPLAY_LENGTH);


            }
    private static boolean doesDatabaseExist(File file) {

        return file.exists();
    }





    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
