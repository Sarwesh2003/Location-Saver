package com.example.loacationsaver.controller.MainActivityController;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.loacationsaver.View.MainAcitivtyView.ViewImplementor;
import com.example.loacationsaver.model.db.DatabaseModel;
import com.example.loacationsaver.model.locations.LocationModel;

public class ActivityController implements ActivityControllerInterface {

    DatabaseModel model;
    LocationModel locationModel;
    ViewImplementor view;



    public ActivityController(DatabaseModel model, ViewImplementor viewImplementor, LocationModel locationModel) {
        this.model=model;
        this.view=viewImplementor;
        this.locationModel=locationModel;
    }

    @Override
    public void onViewLoaded() {
        try{
            this.GetLocation();
            view.ShowAllLocations(model.getAllSavedLocation());
        } catch (Exception e) {
            view.ShowError(e.getMessage());
        }
    }

    @Override
    public void GetLocation() {
        locationModel.CheckPrerequisite();
    }

    public void GetLocationUpdates(){
        locationModel.getUpdates();
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
