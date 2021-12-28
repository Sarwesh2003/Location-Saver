package com.example.loacationsaver.model.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import androidx.annotation.Nullable;

public class LocationDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME="LocationSaverDatabase";
    public static final String COLUMN_LATITUDE="latitude";
    public static final String COLUMN_LONGITUDE="longitude";
    public static final String COLUMN_ADDRESS="address";
    private static  final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME + "("+
            COLUMN_LATITUDE +" TEXT, " +
            COLUMN_LONGITUDE +" TEXT, " +
            COLUMN_ADDRESS + " TEXT "
            +")";
    private static final String DELETE_TABLE="DROP TABLE IF EXISTS "+ TABLE_NAME;
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "LocationSaver.db";

    public LocationDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.d("Success","Inside onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }
}
