package com.example.loacationsaver.model;

import androidx.annotation.NonNull;

public class LocationData {
    String lat,lang;

    public LocationData(String lat, String lang) {
        this.lat = lat;
        this.lang = lang;
    }



    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @NonNull
    @Override
    public String toString() {
        return lat + "," + lang;
    }
}
