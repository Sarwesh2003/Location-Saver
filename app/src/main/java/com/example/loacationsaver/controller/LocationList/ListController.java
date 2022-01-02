package com.example.loacationsaver.controller.LocationList;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.loacationsaver.model.adapters.LocationDBAdapter;
import com.example.loacationsaver.model.db.DatabaseModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ListController {
    Context mContext;
    DatabaseModel model;
    ArrayList<String> address;
    ArrayList<LatLng> latlang;

    public ListController(Context mContext) {
        this.mContext = mContext;
        this.model = new DatabaseModel(new LocationDBAdapter(mContext));
    }

    public ArrayList<String> GetAddressInList(){
        address=new ArrayList<>();
        Cursor c=model.GetCursorForAllData();
        if(c!=null){
            while(c.moveToNext()){
                address.add(c.getString(2));
            }
        }
        return address;
    }

    public ArrayList<LatLng> GetLatLngInList(){
        latlang=new ArrayList<>();
        Cursor c=model.GetCursorForAllData();
        if(c!=null){
            while(c.moveToNext()){
                double lt=Double.parseDouble(c.getString(0));
                double lng=Double.parseDouble(c.getString(1));
                LatLng lctn=new LatLng(lt,lng);
                latlang.add(lctn);
            }
        }
        return latlang;
    }
    public boolean DeleteLocations(String lat,String lang) {
        try {
            boolean isSuccess= model.Delete(lat, lang);
            if(isSuccess){
                Toast.makeText(mContext,"Deleted",Toast.LENGTH_SHORT).show();
                return isSuccess;
            }
        } catch (Exception e) {
            Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
