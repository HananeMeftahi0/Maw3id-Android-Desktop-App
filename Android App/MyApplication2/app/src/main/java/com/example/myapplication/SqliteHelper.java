package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "maw3id";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERS = "users";
    public static final String KEY_ID = "id";
    public static final String KEY_USER_NAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CONNECTED="connected";
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_PASSWORD + " TEXT,"
            + KEY_CONNECTED + " TEXT"
            + " ) ";
    public static final String TABLE_TEMP = "tempy";
    public static final String KEY_AT = "AT";
    public static final String KEY_TEMPY = "id_tempy";
    public static final String KEY_BT = "BT";
    public static final String KEY_CT="CT";
    public static final String KEY_DT="DT";
    public static final String SQL_TABLE_TEMP = " CREATE TABLE " + TABLE_TEMP
            + " ( "
            + KEY_TEMPY + " INTEGER PRIMARY KEY, "
            + KEY_AT + " TEXT, "
            + KEY_BT + " TEXT, "
            + KEY_CT + " TEXT, "
            + KEY_DT + " TEXT, "
            + KEY_ID + " TEXT"

            + " ) ";


    public static final String KEY_ID_APP="id_app";
    public static final String KEY_NAME="name";
    public static final String KEY_FULLNAME="fullname";
    public static final String TABLE_APPS="appointements";
    public static final String KEY_DATE="date";
    public static final String KEY_NUMBER="number";

    public static final String SQL_TABLE_APP = " CREATE TABLE " + TABLE_APPS
            + " ( "
            + KEY_ID_APP + " INTEGER PRIMARY KEY, "
            + KEY_FULLNAME + " TEXT, "
            + KEY_NAME + " TEXT, "
            + KEY_DATE + " TEXT, "
            + KEY_ID+" INTEGER, "
            + KEY_NUMBER + " TEXT"
            + " ) ";


    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TABLE_USERS);
        db.execSQL(SQL_TABLE_APP);
        db.execSQL(SQL_TABLE_TEMP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_APPS);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_TEMP);
        onCreate(db);


    }
    public boolean Exists(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEMP,// Selecting Table
                new String[]{KEY_AT},//Selecting columns want to query
                KEY_ID + "=?",
                new String[]{id},//Where clause
                null, null, null);
        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {

            return true;
        }
        return false;
    }
    public void updateTurn(String turn,String currentUserId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(this.Exists(currentUserId)){

        switch (turn){
            case "A":
                cv.put(KEY_AT,turn);
                break;
            case "B":
                cv.put(KEY_BT,turn);
                break;
            case "C":
                cv.put(KEY_CT,turn);
                break;
            case "D":
                cv.put(KEY_DT,turn);
                break;
        }
        db.update(TABLE_TEMP, cv, KEY_ID + "=?", new String[] {currentUserId});
        }else{
            switch (turn){
                case "A":
                    cv.put(KEY_AT,turn);
                    cv.put(KEY_BT,"no");
                    cv.put(KEY_CT,"no");
                    cv.put(KEY_DT,"no");
                    cv.put(KEY_ID,currentUserId);
                    Log.v("tag user2", currentUserId);
                    break;
                case "B":
                    cv.put(KEY_AT,"no");
                    cv.put(KEY_BT,turn);
                    cv.put(KEY_CT,"no");
                    cv.put(KEY_DT,"no");
                    cv.put(KEY_ID,currentUserId);
                    break;
                case "C":
                    cv.put(KEY_AT,"no");
                    cv.put(KEY_BT,"no");
                    cv.put(KEY_CT,turn);
                    cv.put(KEY_DT,"no");
                    cv.put(KEY_ID,currentUserId);
                    break;
                case "D":
                    cv.put(KEY_AT,"no");
                    cv.put(KEY_BT,"no");
                    cv.put(KEY_CT,"no");
                    cv.put(KEY_DT,turn);
                    cv.put(KEY_ID,currentUserId);
                    break;
            }

            long todo_id = db.insert(TABLE_TEMP, null, cv);

        }

    }
    public void update(String turn,String value,String current){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(this.Exists(current)){
            switch (turn){
                case "A":
                    cv.put(KEY_AT,value);

                    break;
                case "B":
                    cv.put(KEY_BT,value);
                    break;
                case "C":
                    cv.put(KEY_CT,value);
                    break;
                case "D":
                    cv.put(KEY_DT,value);
                    break;
            }

            db.update(TABLE_TEMP, cv, KEY_ID + "=?", new String[] {current});
            }


    }
    public List<String> getT(String currentUserId){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> list=new ArrayList<>();

        Cursor cursor = db.rawQuery("select "+KEY_AT+","+KEY_BT+","+KEY_CT+","+KEY_DT+" from " +
                        TABLE_TEMP + " where " + KEY_ID + " = ?",
                new String[] { currentUserId});
        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            do {
                list.add(cursor.getString(0));
                list.add(cursor.getString(1));
                list.add(cursor.getString(2));
                list.add(cursor.getString(3));

            }while (cursor.moveToNext());
        }
        return list;

    }
    public void addApp(String name,String fullname,String date,String currentUserId,String number){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_FULLNAME, fullname);
        values.put(KEY_NAME, name);
        values.put(KEY_DATE, date);
        values.put(KEY_ID, currentUserId);
        values.put(KEY_NUMBER, number);

        long todo_id = db.insert(TABLE_APPS, null, values);

    }
    public void deleteUser(String currentUserId)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_USERS, KEY_ID+"="+ currentUserId, null);
        sqLiteDatabase.delete(TABLE_APPS, KEY_ID+"="+ currentUserId, null);
        sqLiteDatabase.close();

    }
    public void changepassword(String currentUserId, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_PASSWORD,password);
        db.update(TABLE_USERS, cv, KEY_ID + "=?", new String[] {currentUserId});
    }
    public void addUser(User user,String connected) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getName());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_CONNECTED, connected);
        long todo_id = db.insert(TABLE_USERS, null, values);
    }
    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,//
                new String[]{KEY_ID, KEY_USER_NAME, KEY_PASSWORD},
                KEY_USER_NAME + "=?",
                new String[]{user.getName()},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2));


            if (user.getPassword().equalsIgnoreCase(user1.getPassword())) {
                return user1;
            }
        }

        return null;
    }
    public void isConnected(String connected,String currentUserId,String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID,currentUserId);
        cv.put(KEY_USER_NAME,username);
        cv.put(KEY_PASSWORD,password);
        cv.put(KEY_CONNECTED,connected);
        db.update(TABLE_USERS, cv, KEY_ID + "=?", new String[] {currentUserId});


    }
    public void logout(String connected,String currentUserId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID,currentUserId);

        cv.put(KEY_CONNECTED,connected);
        db.update(TABLE_USERS, cv, KEY_ID + "=?", new String[] {currentUserId});


    }
    public List areYouConnected(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> arrayList=new ArrayList<>();

        Cursor cursor = db.rawQuery("select "+KEY_USER_NAME+","+KEY_PASSWORD+" from " +
                        TABLE_USERS + " where " + KEY_CONNECTED + " = ?",
                new String[] {"yes"});
        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            do {
                arrayList.add(cursor.getString(0));
                arrayList.add(cursor.getString(1));

            } while (cursor.moveToNext());

        }
        if (arrayList.isEmpty()) {
            return null;
        } else {

            return arrayList;
        }

    }

    public List displayAllApps(String currentUserId){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> list=new ArrayList<>();

        Cursor cursor = db.rawQuery("select "+KEY_NAME+","+KEY_DATE+","+KEY_NUMBER+" from " +
                        TABLE_APPS + " where " + KEY_ID + " = ?",
                new String[] { currentUserId});
        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            do {
                list.add(cursor.getString(0) + "       " + cursor.getString(1)+ "       " + cursor.getString(2));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean isDateExists(String date, String currentUserId,String value) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select "+KEY_DATE+" from " +
                        TABLE_APPS + " where " + KEY_ID + " = ? AND " + KEY_DATE +
                        " = ? AND " + KEY_NAME +" = ?",
                new String[] { currentUserId, date,value });

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {

            return true;
        }
        return false;
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_PASSWORD},//Selecting columns want to query
                KEY_USER_NAME + "=?",
                new String[]{username},//Where clause
                null, null, null);
        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {

            return true;
        }
        return false;
    }
}
