package com.example.loacationsaver.model.db;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

public class LocationData {
    LatLng latlang;

    public LocationData(LatLng lang) {
        this.latlang = lang;
    }

    public LatLng getLatlang() {
        return latlang;
    }

    public void setLatlang(LatLng latlang) {
        this.latlang = latlang;
    }

    @NonNull
    @Override
    public String toString() {
        return latlang.latitude + "," + latlang.longitude;
    }
}
