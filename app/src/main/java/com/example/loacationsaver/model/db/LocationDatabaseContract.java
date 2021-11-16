package com.example.loacationsaver.model.db;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import androidx.annotation.Nullable;

public class LocationDatabaseContract extends SQLiteOpenHelper {

    public static final String TABLE_NAME="LocationSaverDatabase";
    public static final String COLUMN_SR_NO="SrNo";
    public static final String COLUMN_LATITUDE="latitude";
    public static final String COLUMN_LONGITUDE="longitude";
    private static  final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME + "("+
            COLUMN_SR_NO +" INTEGER PRIMARY KEY, " +
            COLUMN_LATITUDE +" TEXT, " +
            COLUMN_LONGITUDE +" TEXT )";
    private static final String DELETE_TABLE="DROP TABLE IF EXISTS "+ TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LocationSaver.db";

    public LocationDatabaseContract(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.d(TAG,"Inside onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }


}
