package com.example.ndirangu.estiproject;

/**
 * Created by NDIRANGU on 10/2/2014.
 * database class thats exxtends the sqlite database
 * Helps create a database
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "UserDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create users table
        String CREATE_USER_TABLE = "CREATE TABLE Users ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "token TEXT, "+
                "email TEXT ,"+
                "musiclikes TEXT ,"+
                "birthday TEXT )";

        // create books table
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older users table if existed
        db.execSQL("DROP TABLE IF EXISTS USERS");

        // create fresh books table
        this.onCreate(db);
    }
    /*
    *CRUD
    * OPS
     */
    // user table name
    private static final String TABLE_USERS = "users";

    // user Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MUSICLIKES = "musiclikes";
    private static final String KEY_BIRTHDAY = "birthday";

    private static final String[] COLUMNS = {KEY_ID,KEY_TOKEN,KEY_EMAIL,KEY_MUSICLIKES,KEY_BIRTHDAY};
    public void AddUser(Users user){
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_TOKEN, user.getToken());
        values.put(KEY_MUSICLIKES,user.getMusicLikes());
        values.put(KEY_BIRTHDAY,user.getBirthday());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_USERS, null, values);
        db.close();
    }
    /*Returns User Token from facebook */
    public String findUser(String EmailUser){
        String query = "Select * FROM " + TABLE_USERS+ " WHERE " + KEY_EMAIL + " =  \"" + EmailUser + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        String userToken = new String();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            userToken=cursor.getString(3);
            cursor.close();
        } else {
            userToken = null;
        }
        db.close();
        return userToken;
    }
    public String findEmail(){
        String query = "Select * FROM " + TABLE_USERS+ " WHERE " + KEY_EMAIL + " IS NOT NULL" ;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        String userToken = new String();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            userToken=cursor.getString(2);
            cursor.close();
        } else {
            userToken = null;
        }
        db.close();
        return userToken;

    }
    public String findBirthday(String EmailUser){
        String query = "Select * FROM " + TABLE_USERS+ " WHERE " + KEY_EMAIL + " =  \"" + EmailUser + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        String userToken = new String();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            userToken=cursor.getString(4);
            cursor.close();
        } else {
            userToken = null;
        }
        db.close();
        return userToken;
    }

}
