package com.example.loacationsaver.model.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.loacationsaver.model.db.LocationData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocationDBAdapter {
    private final SQLiteDatabase database;
   private static LocationDBAdapter instance;


   public LocationDBAdapter(Context context){
       database=new LocationDatabaseHelper(context, LocationDatabaseHelper.DATABASE_NAME,null, LocationDatabaseHelper.DATABASE_VERSION).getWritableDatabase();
   }
   public static LocationDBAdapter getLocationInstance(Context context){
       if(instance==null){
           instance=new LocationDBAdapter(context);
       }
       return instance;
   }

    public boolean insert(String lat, String lang, String address) throws Exception{
       if(!CheckIfExitsData(lat,lang,address)){
           ContentValues contentValues=new ContentValues();
           contentValues.put(LocationDatabaseHelper.COLUMN_LATITUDE,lat);
           contentValues.put(LocationDatabaseHelper.COLUMN_LONGITUDE,lang);
           contentValues.put(LocationDatabaseHelper.COLUMN_ADDRESS,address);
           return database.insert(LocationDatabaseHelper.TABLE_NAME,null,contentValues)>0;
       }else{
           throw new Exception("Location Already Saved");
       }

    }
    /*public List<String> getSavedLocation(){
        List<String> locations=new ArrayList<>();
        Cursor cursor=database.query(LocationDatabaseHelper.TABLE_NAME,new String[]{LocationDatabaseHelper.COLUMN_ADDRESS},null,null,null,null,null);
        if(cursor!=null && cursor.getCount()>0){
            while(cursor.moveToNext()){
                String data = cursor.getString(2);
                locations.add(String.valueOf(data));
            }
        }
        Objects.requireNonNull(cursor).close();
        return locations;
    }*/

    public boolean Delete(String lat, String lang){
        return database.delete(LocationDatabaseHelper.TABLE_NAME,LocationDatabaseHelper.COLUMN_LATITUDE+" LIKE ? AND "+
                LocationDatabaseHelper.COLUMN_LONGITUDE+" LIKE ? ",new String[]{lat,lang})>0;
    }
    public boolean CheckIfExitsData(String lat,String lang, String address){
        String Query = "SELECT * FROM "+LocationDatabaseHelper.TABLE_NAME+ " WHERE "+
                LocationDatabaseHelper.COLUMN_LATITUDE +" = " + lat +" AND " +
                LocationDatabaseHelper.COLUMN_LONGITUDE +" = " + lang +" AND "+
                LocationDatabaseHelper.COLUMN_ADDRESS +" = '" + address +"'";
            Cursor cursor = database.rawQuery(Query, null);
            if(cursor.getCount() <= 0){
                cursor.close();
                return false;
            }
            cursor.close();
            return true;
    }

    public Cursor GetCursor(){
       String query="SELECT * FROM "+LocationDatabaseHelper.TABLE_NAME;
       Cursor cursor=null;
       if(database!=null){
           cursor=database.rawQuery(query,null);
       }
       return cursor;
    }
}
