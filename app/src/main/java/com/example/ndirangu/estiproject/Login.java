package com.example.ndirangu.estiproject;

import android.app.Activity;
import android.content.Intent;
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
import com.sromku.simple.fb.listeners.OnActionListener;
import com.sromku.simple.fb.listeners.OnLoginListener;

import java.util.List;


public class Login extends Activity {
    /*Permissions recquired to access usser facebook data*/
    Permission[] permissions = new Permission[] {
            Permission.USER_PHOTOS,
            Permission.EMAIL,

            Permission.USER_ACTIONS_MUSIC,
            Permission.PUBLISH_ACTION,
            Permission.USER_BIRTHDAY,
            //For facebook user Music
            Permission.USER_LIKES


    };
    SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
            .setAppId("222412067951023")
            .setNamespace("sromkuapp")
            .setPermissions(permissions)
            .build();
    private SimpleFacebook mSimpleFacebook;
    private String TAG="EstiProject";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();

        setContentView(R.layout.activity_login);
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
            //Get the music pages a user likes
            mSimpleFacebook.get("me", "music", null, new OnActionListener<List<Page>>() {

                @Override
                public void onComplete(List<Page> response) {
                    Log.i(TAG, "Number of music pages I like = " + response.size() );
                }

            });
            Intent mainIntent=new Intent(Login.this,AllMenu.class);
            Login.this.startActivity(mainIntent);

           Login.this.finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        }

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
    public void LoginFacebook(View view){
        if(view.getId()==R.id.loginButton){
        mSimpleFacebook.login(onLoginListener);
    }}
public void SkipLogin(View view){


        Intent mainIntent=new Intent(Login.this,AllMenu.class);
        Login.this.startActivity(mainIntent);

        Login.this.finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}
