package com.example.loacationsaver.controller.MainActivityController;

public interface ActivityControllerInterface {
    void OnClickSaveLocation(String lat, String lang);
    void DeleteLocations(String lat, String lang);
    void onViewLoaded();
    void GetLocation();
}
