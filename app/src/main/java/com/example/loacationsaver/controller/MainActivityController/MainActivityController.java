package com.example.loacationsaver.controller.MainActivityController;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.loacationsaver.View.MainAcitivtyView.ViewImplementor;
import com.example.loacationsaver.model.db.DatabaseModel;
import com.example.loacationsaver.model.locations.LocationModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

public class MainActivityController implements ActivityControllerInterface {

    DatabaseModel model;
    LocationModel locationModel;
    ViewImplementor view;



    public MainActivityController(DatabaseModel model, ViewImplementor viewImplementor, LocationModel locationModel) {
        this.model=model;
        this.view=viewImplementor;
        this.locationModel=locationModel;
    }

    @Override
    public void onViewLoaded() {
        try{
            this.GetLocation();
            //view.ShowAllLocations(model.getAllSavedLocation());
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
    public void OnClickSaveLocation(LatLng latLng) {
        try {
            if(model.saveLocation(String.valueOf(latLng.latitude),String.valueOf(latLng.longitude), this.GetAddress(latLng))){
                view.ShowSuccess("Saved");
            }
        } catch (Exception e) {
            if(Objects.equals(e.getMessage(), "Location Already Exists")){
                view.ShowError("Location Already Exists");
            }
            else{
                view.ShowError("Something went wrong");
            }
        }
    }

    @Override
    public void DeleteLocations(String lat, String lang) {

    }

    /*@Override
    public void DeleteLocations(String lat,String lang) {
        try {
            boolean isSuccess= model.Delete(lat, lang);
            if(isSuccess){
                view.ShowSuccess("Successfully Deleted");
                //view.UpdateView(model.getAllSavedLocation());
            }
        } catch (Exception e) {
            view.ShowError(e.getMessage());
        }
    }*/

    @Override
    public LatLng GetLatLng() {
        return locationModel.GetLatLng();
    }

    public String GetAddress(LatLng latLng){
        return locationModel.GetAddress(latLng);
    }
}
