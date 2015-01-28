package com.example.mk.rcwindy.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//

/**
 * Created by MK on 30.12.2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "usersData";

    //  tables names
    private static final String TABLE_USER = "users";
    private static final String TABLE_SPOT = "spots";

    // Users Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "name";
    private static final String KEY_PASSWORD = "password";

    //Spots Table Colums names
    private static final String SPOT_ID= "id";
    private static final String SPOT_USER_ID = "spot_user";
    private static final String SPOT_NAME="Spot_name";
    private static final String SPOT_LATITUDE = "Latitude";
    private static final String SPOT_LONGITUDE = "Longitude";
    private static final String SPOT_WEATHER_ICON = "Spot_weather_icon";
    private static final String SPOT_WIND_SPEED ="Wind_speed";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT" + ")";

        String CREATE_SPOTS_TABLE = "CREATE TABLE " + TABLE_SPOT +
                "(" +   SPOT_ID + " INTEGER PRIMARY KEY," +
                        SPOT_USER_ID + " INTEGER," +
                        SPOT_NAME + " TEXT," +
                        SPOT_LATITUDE + " TEXT," +
                        SPOT_LONGITUDE + " TEXT," +
                        SPOT_WIND_SPEED + " TEXT," +
                        SPOT_WEATHER_ICON + " TEXT"+")";
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_SPOTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPOT);

        // Create tables again
        onCreate(db);
    }



    // Adding new User
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getUserName()); // Username
        values.put(KEY_PASSWORD, user.getPassword()); // Password›

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }


    //GETTING USER

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID,
                        KEY_USERNAME, KEY_PASSWORD }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return user;
    }

    //GETTING USER ID

    public User userIdGetter(String userName){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID,
                        KEY_USERNAME, KEY_PASSWORD }, KEY_USERNAME + "=?",
                new String[] { String.valueOf(userName) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return user;

    }


    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUserID(Integer.parseInt(cursor.getString(0)));
                user.setUserName(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                // Adding User to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // return User list
        return userList;
    }
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getUserID()) });

        db.close();
    }
    public int getUsersCount() {
        String countQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    /*żeby usunąć całą Tablicę Userów
    db.delete(TABLE_USER,null,null);

        db.execSQL("delete from "+ TABLE_USER);
        db.execSQL("vacuum") */



    //TABLICA SPOTÓW

    //ADD SPOT

    public void addSpot(Spot spot){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SPOT_USER_ID,spot.getUser_id_fk());
        values.put(SPOT_NAME,spot.getSpotName());
        values.put(SPOT_LATITUDE,spot.getSpotLatitude());
        values.put(SPOT_LONGITUDE,spot.getSpotLongitude());
        values.put(SPOT_WIND_SPEED,spot.getWindSpeed());
        values.put(SPOT_WEATHER_ICON,spot.getSpotWeatherIcon());

        db.insert(TABLE_SPOT, null, values);
        db.close(); // Closing database connection
        Log.d("Dodano do Bazy","kolejny spot");

    }

    //Get Current User Spot List

    public ArrayList<Spot> getUserSpotList(int id){    //było samo List a nie ArrayList i w tym wersie nizej tez
        ArrayList<Spot> spotList = new ArrayList<Spot>();
        String selectQuery = "SELECT " +SPOT_ID+","+ SPOT_NAME + ","+ SPOT_LATITUDE + ","+SPOT_LONGITUDE+","+SPOT_WIND_SPEED +","+SPOT_WEATHER_ICON+
                " FROM " + TABLE_SPOT + " WHERE "+SPOT_USER_ID + "="+id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Spot spot = new Spot();
                spot.setSpot_id(Integer.parseInt(cursor.getString(0)));
                spot.setSpotName(cursor.getString(1));
                spot.setSpotLatitude(cursor.getString(2));
                spot.setSpotLongitude(cursor.getString(3));
                spot.setWindSpeed(Double.parseDouble(cursor.getString(4)));
                spot.setSpotWeatherIcon(cursor.getString(5));
                // Adding User to list
                spotList.add(spot);
            } while (cursor.moveToNext());
        }

    return spotList;
    }


    //UPDATE SPOTu
    public int updateSpot(Spot spot){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SPOT_WIND_SPEED, spot.getWindSpeed());
        values.put(SPOT_WEATHER_ICON, spot.getSpotWeatherIcon());

        int i = db.update(TABLE_SPOT,
                values,
                SPOT_ID+" = ?",
                new String[] { String.valueOf(spot.getSpot_id()) });

        //  close
        db.close();

        return i;
    }
}
