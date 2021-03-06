package com.example.ndirangu.estiproject;
import com.example.ndirangu.estiproject.MySQLiteHelper;
import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Page;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.entities.User;
import com.sromku.simple.fb.listeners.OnActionListener;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Login extends Activity {
    /*Permissions recquired to access usser facebook data*/
    Permission[] permissions = new Permission[] {
            //get user photos
            Permission.USER_PHOTOS,
            //get user email
            Permission.EMAIL,
            //get user actions for music

            Permission.USER_ACTIONS_MUSIC,
            //allow us to post on behalf of the user
            Permission.PUBLISH_ACTION,
            //get user birthday
            Permission.USER_BIRTHDAY,
            //For facebook user Music
            Permission.USER_LIKES


    };
    SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
            .setAppId("820703371314246")
            .setNamespace("sromkuapp")
            .setPermissions(permissions)
            .build();
    private SimpleFacebook mSimpleFacebook;
    private String TAG="EstiProject";

    public  String Birthday,Email,Username,MusicLikes,Token;
    int Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //request for actionbar and hide it before loading the activity
requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();

        setContentView(R.layout.activity_login);
        //set the configuration for the facebook login
        SimpleFacebook.setConfiguration(configuration);







    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    OnLoginListener onLoginListener = new OnLoginListener() {
        @Override
        public void onLogin() {
            // change the state of the button or do whatever you want
            Log.i(TAG, "Logged in");

 //get the facebook access token form the current session
 Token =mSimpleFacebook.getSession().getAccessToken();
           Log.i(TAG,Token);



            //Get the music pages a user likes

            mSimpleFacebook.get("me", "music", null, new OnActionListener<List<Page>>() {

                @Override
                public void onComplete(List<Page> response) {
                    Log.i(TAG, "Number of music pages I like = " + response.size());
                    if (response.size() > 0) {
                        //populate the Musiclikes with user's artist preferences
                        MusicLikes=response.get(0).getName();
                        for (int i = 1; i < response.size(); i++) {
                            Log.i(TAG, response.get(i).getName());
                            //populate all artists a user likes
                            MusicLikes +=","+response.get(i).getName();
                        }

                    }
                    else
                    {
                        //else set the music likes to unknown if user doesnt have any likes on artists
                        MusicLikes="Unknown ";
                    }
                    Log.i(TAG,MusicLikes);
                }


            });

            mSimpleFacebook.getProfile(onProfileListener);









//start a new intent
            Intent mainIntent=new Intent(Login.this,AllMenu.class);
            Login.this.startActivity(mainIntent);

           Login.this.finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        }
        //allows items to be added to the database failure to which no item will be added to the database



        @Override
        public void onNotAcceptingPermissions(Permission.Type type) {
            // user didn't accept READ or WRITE permission
            Log.w(TAG, String.format("You didn't accept %s permissions", type.name()));
        }

        @Override
        public void onThinking() {

        }

        @Override
        public void onException(Throwable throwable) {

        }

        @Override
        public void onFail(String reason) {

        }

    /*
     * You can override other methods here:
     * onThinking(), onFail(String reason), onException(Throwable throwable)
     */
    };
    OnProfileListener onProfileListener = new OnProfileListener() {
        @Override
        public void onComplete(Profile profile) {
            Log.i(TAG, "My profile id = " + profile.getId());
            Email=profile.getEmail();
            Log.i(TAG,Email);
            Birthday=profile.getBirthday();
            Log.i(TAG,Birthday);
           /* Id=Integer.parseInt(profile.getId());
            Username=profile.getFirstName();
            Log.i(TAG,profile.getFirstName());
            //Create a database and store users info
            Users user=new Users();

            user.setBirthday(Birthday);
            user.setToken(Token);
            Log.i(TAG, Token);
            user.setMusicLikes(MusicLikes);
            Log.i(TAG,MusicLikes);
            user.setEmail(Email);*/
            //create a new instance of mysqlhelper and add the user to the database
            MySQLiteHelper mysqlhelper= new MySQLiteHelper(getApplicationContext());
            mysqlhelper.AddUser(Email,Token,MusicLikes,Birthday);
            Log.i(TAG,"added User");
        }

    /*
     * You can override other methods here:
     * onThinking(), onFail(String reason), onException(Throwable throwable)
     */
    };

    public void LoginFacebook(View view){
        if(view.getId()==R.id.loginButton){
            //enable facebook login on clicking the login with facebook login button
        mSimpleFacebook.login(onLoginListener);





        }}
    //on clicking the skip login button change the intent to the allmenu class
public void SkipLogin(View view){


        Intent mainIntent=new Intent(Login.this,AllMenu.class);
        Login.this.startActivity(mainIntent);

        Login.this.finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }


}
