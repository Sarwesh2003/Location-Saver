package com.example.loacationsaver.model;

public class LocationData {
    int srno;
    String lat,lang;

    public LocationData(int srno, String lat, String lang) {
        this.srno = srno;
        this.lat = lat;
        this.lang = lang;
    }

    public int getSrno() {
        return srno;
    }

    public void setSrno(int srno) {
        this.srno = srno;
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

    @Override
    public String toString() {
        return "(" + srno +", " + lat + ", " + lang + ')';
    }
}
