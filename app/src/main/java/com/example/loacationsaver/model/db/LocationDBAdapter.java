package com.example.loacationsaver.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.loacationsaver.model.LocationData;

import java.util.ArrayList;
import java.util.List;

public class LocationDBAdapter {
   private Context context;
   private SQLiteDatabase database;
   private static LocationDBAdapter instance;


   private LocationDBAdapter(Context context){
       this.context=context;
       database=new LocationDatabaseContract(this.context,LocationDatabaseContract.DATABASE_NAME,null,LocationDatabaseContract.DATABASE_VERSION).getWritableDatabase();
   }
   public static LocationDBAdapter getLocationInstance(Context context){
       if(instance==null){
           instance=new LocationDBAdapter(context);
       }
       return instance;
   }

    public boolean insert(String lat, String lang){
        ContentValues contentValues=new ContentValues();
        contentValues.put(LocationDatabaseContract.COLUMN_LATITUDE,lat);
        contentValues.put(LocationDatabaseContract.COLUMN_LONGITUDE,lang);

        return database.insert(LocationDatabaseContract.TABLE_NAME,null,contentValues)>0;
    }
    public List<String> getSavedLocation(){
        List<String> locations=new ArrayList<>();
        Cursor cursor=database.query(LocationDatabaseContract.TABLE_NAME,new String[]{LocationDatabaseContract.COLUMN_SR_NO,
        LocationDatabaseContract.COLUMN_LATITUDE,LocationDatabaseContract.COLUMN_LONGITUDE},null,null,null,null,null);
        if(cursor!=null && cursor.getCount()>0){
            while(cursor.moveToNext()){
                LocationData data = new LocationData(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
                locations.add(String.valueOf(data));
            }
        }
        cursor.close();
        return locations;
    }

    public boolean Delete(int SrNo){
       return database.delete(LocationDatabaseContract.TABLE_NAME,LocationDatabaseContract.COLUMN_SR_NO+" = "+SrNo,null)>0;
    }
}
