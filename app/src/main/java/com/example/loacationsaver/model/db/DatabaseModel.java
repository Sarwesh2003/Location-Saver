package com.example.loacationsaver.model.db;


import android.database.Cursor;

import com.example.loacationsaver.model.adapters.LocationDBAdapter;

import java.util.List;
import java.util.Objects;

public class DatabaseModel {
    LocationDBAdapter db;
    List<String> locations;
    public DatabaseModel(LocationDBAdapter adapter){
        this.db=adapter;
        //locations=db.getSavedLocation();
    }
    public boolean saveLocation(String lat,String lang, String address) throws Exception{

        try{
            if(db.insert(lat,lang,address)){
                return true;
            }
        }catch (Exception e){
            if(Objects.equals(e.getMessage(), "Location Already Saved")){
                throw new Exception("Location Already Exists");
            }
            else{
                throw new Exception("Something went wrong");
            }
        }
        return false;
    }

    public boolean Delete(String lat, String lang) throws  Exception{
        boolean isSuccess= db.Delete(lat,lang);
        if(!isSuccess){
            throw new Exception("Error while deleting");
        }
        return isSuccess;
    }

    public Cursor GetCursorForAllData(){
        return db.GetCursor();
    }

}
