package com.example.loacationsaver.controller;

import android.content.Context;

import com.example.loacationsaver.View.MainActivityView;
import com.example.loacationsaver.model.LocationModel;

public interface LocationControllerInterface {
    void OnClickSaveLocation(String lat, String lang);

    void DeleteLocations(String lat, String lang);
    void onViewLoaded();
}
