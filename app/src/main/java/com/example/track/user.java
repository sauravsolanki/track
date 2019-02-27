package com.example.track;


import java.io.Serializable;

public class user implements Serializable {
    double latitude;
    double longitude;
    String imei;
    String time;

    public user(double latitude, double longitude, String imei, String time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.imei = imei;
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getImei() {
        return imei;
    }

    public String getTime() {
        return time;
    }
}
