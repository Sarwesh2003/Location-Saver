package com.example.loacationsaver.model;


import android.database.Cursor;
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
            throw new Exception("Something went wrong");
        }
        return isSuccess;
    }
    public List<String> getAllSavedLocation()throws Exception{
        if(this.locations!=null && this.locations.size()>0){
            return this.locations;
        }
        else{
            throw new Exception("No Records Found");
        }
    }
    public boolean DeleteLocation(int SrNo) throws  Exception{
        boolean isSuccess= db.Delete(SrNo);
        if(!isSuccess){
            throw new Exception("Something went wrong");
        }
        return isSuccess;
    }


}
