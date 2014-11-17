package com.example.ndirangu.estiproject;

import android.util.Log;

/**
 * Created by NDIRANGU on 10/2/2014.
 */
public class Users {
    private int Id;
    private String Email;
    private String Token;
    private String Birthday;
    private String MusicLikes;

    public Users() {
    }

        public Users(int Id,String Email,String Token,String Birthday,String MusicLikes){
            this.Id = Id;
            this.Birthday = Birthday;
            this.Token = Token;
            this.Email = Email;
            this.MusicLikes = MusicLikes;
        }

    public void setId(int Id){
        this.Id=Id;
    }

public void setEmail(String Email){
    this.Email=Email;
}

    public void setBirthday(String birthday) {
        this.Birthday = birthday;
    }

    public void setToken(String token) {
       this.Token = token;
    }

    public void setMusicLikes(String musicLikes) {
       this.MusicLikes = musicLikes;
    }
    public int getId(){
        return this.Id;
    }

    public String getBirthday() {
        return this.Birthday;
    }

    public String getEmail() {
        Log.i("DB",Token);
        return this.Email;
    }

    public String getToken() {
        Log.i("DB",Token);
        return this.Token;

    }

    public String getMusicLikes() {
        return this.MusicLikes;
    }
}
