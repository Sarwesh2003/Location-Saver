package com.example.loacationsaver.model;


import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.example.loacationsaver.model.db.LocationDBAdapter;

import java.util.ArrayList;
import java.util.List;

public class LocationModel  {
    LocationDBAdapter db;
    List<String> locations;
    public LocationModel(LocationDBAdapter adapter){
        this.db=adapter;
        locations=db.getSavedLocation();
    }
    public boolean saveLocation(String lat,String lang) throws Exception{
        boolean isSuccess=db.insert(lat,lang);
        if(!isSuccess){
            throw new Exception("Error Saving");
        }
        else{
            refresh();
        }
        return isSuccess;
    }
    public List<String> getAllSavedLocation()throws Exception{
        if(this.locations!=null && this.locations.size()>0){
            return this.locations;
        }
        else{
            refresh();
            throw new Exception("No Records Found");
        }
    }
    public boolean Delete(String lat, String lang) throws  Exception{
        boolean isSuccess= db.Delete(lat,lang);
        if(!isSuccess){
            throw new Exception("Error while deleting");
        }
        else{
            refresh();
        }
        return isSuccess;
    }

    public void refresh(){
        locations=this.db.getSavedLocation();
    }

}
