package com.example.loacationsaver.model.db;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import com.example.loacationsaver.model.LocationData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocationDBAdapter {
   private Context context;
   private SQLiteDatabase database;
   private static LocationDBAdapter instance;


   private LocationDBAdapter(Context context){
       this.context=context;
       database=new LocationDatabaseHelper(this.context, LocationDatabaseHelper.DATABASE_NAME,null, LocationDatabaseHelper.DATABASE_VERSION).getWritableDatabase();
   }
   public static LocationDBAdapter getLocationInstance(Context context){
       if(instance==null){
           instance=new LocationDBAdapter(context);
       }
       return instance;
   }

    public boolean insert(String lat, String lang) throws Exception{
       if(!CheckIfExitsData(lat,lang)){
           ContentValues contentValues=new ContentValues();
           contentValues.put(LocationDatabaseHelper.COLUMN_LATITUDE,lat);
           contentValues.put(LocationDatabaseHelper.COLUMN_LONGITUDE,lang);
           return database.insert(LocationDatabaseHelper.TABLE_NAME,null,contentValues)>0;
       }else{
           throw new Exception("Location Already Saved");
       }

    }
    public List<String> getSavedLocation(){
        List<String> locations=new ArrayList<>();
        Cursor cursor=database.query(LocationDatabaseHelper.TABLE_NAME,new String[]{LocationDatabaseHelper.COLUMN_LATITUDE, LocationDatabaseHelper.COLUMN_LONGITUDE},null,null,null,null,null);
        if(cursor!=null && cursor.getCount()>0){
            while(cursor.moveToNext()){
                LocationData data = new LocationData(cursor.getString(0),cursor.getString(1));
                locations.add(String.valueOf(data));
            }
        }
        Objects.requireNonNull(cursor).close();
        return locations;
    }

    public boolean Delete(String lat, String lang){
        return database.delete(LocationDatabaseHelper.TABLE_NAME,LocationDatabaseHelper.COLUMN_LATITUDE+" LIKE ? AND "+
                LocationDatabaseHelper.COLUMN_LONGITUDE+" LIKE ? ",new String[]{lat,lang})>0;
    }

    public boolean CheckIfExitsData(String lat,String lang){
        String Query = "Select * from " + LocationDatabaseHelper.TABLE_NAME + " WHERE " +
                LocationDatabaseHelper.COLUMN_LONGITUDE+ " = '" + lang+"' AND " +
                LocationDatabaseHelper.COLUMN_LATITUDE +" = '"+lat+"'";
            Cursor cursor = database.rawQuery(Query, null);
            if(cursor.getCount() <= 0){
                cursor.close();
                return false;
            }
            cursor.close();
            return true;
    }
}
