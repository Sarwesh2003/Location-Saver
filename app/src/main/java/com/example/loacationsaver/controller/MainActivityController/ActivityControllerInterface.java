package com.example.loacationsaver.controller.MainActivityController;

import com.google.android.gms.maps.model.LatLng;

public interface ActivityControllerInterface {
    void OnClickSaveLocation(LatLng latLngs);
    void DeleteLocations(String lat, String lang);
    LatLng GetLatLng();
    void onViewLoaded();
    void GetLocation();
}
