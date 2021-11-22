package com.example.loacationsaver.controller;

import android.util.Log;

import com.example.loacationsaver.View.ViewImplementor;
import com.example.loacationsaver.model.LocationModel;

public class LocationController implements LocationControllerInterface{

    LocationModel model;
    ViewImplementor view;



    public LocationController(LocationModel model, ViewImplementor viewImplementor) {
        this.model=model;
        this.view=viewImplementor;
    }

    @Override
    public void onViewLoaded() {
        try{
            view.ShowAllLocations(model.getAllSavedLocation());
        } catch (Exception e) {
            view.ShowError(e.getMessage());
        }
    }

    @Override
    public void OnClickSaveLocation(String lat, String lang) {
        try {
            boolean isSuccess=model.saveLocation(lat,lang);
            if(isSuccess){
                view.UpdateView(model.getAllSavedLocation());
                view.ShowSuccess("Successful");
            }
        } catch (Exception e) {
            view.ShowError(e.getMessage());
        }
    }

    @Override
    public void DeleteLocations(String lat,String lang) {
        try {
            boolean isSuccess= model.Delete(lat, lang);
            if(isSuccess){
                view.ShowSuccess("Successfully Deleted");
                view.UpdateView(model.getAllSavedLocation());
            }
        } catch (Exception e) {
            view.ShowError(e.getMessage());
        }
    }
}
