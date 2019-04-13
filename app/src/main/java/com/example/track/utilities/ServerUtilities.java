package com.example.track.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.track.user;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerUtilities {
    public static final String LONGITUDE="longitude";
    public static final String LATITUDE="latitude";
    public static final String IMEI="IMEI";
    public static final String TAG="ServerUtilities";
    public static final Float DEFAULT_COUNT=0.000f;
    public static final String DEFAULT_IMEI="none";

    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference("users");

    public static void sendToServer(double latitude,double longitude,String IMEI){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        myRef = database.getReference("users").child(IMEI);
        Date date = new Date();
        String time = formatter.format(date);
        try{
            user u= new user(latitude,longitude,IMEI,time);
            myRef.setValue(u);
        }catch (NullPointerException n){
            Log.i(TAG, "onClick: Data not Updated");
        }
    }
    public static void saveToLocal(Context context, double latitude, double longitude,String imei){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(LATITUDE, (float) latitude);
        editor.putFloat(LONGITUDE, (float) longitude);
        editor.putString(IMEI, imei);
        editor.apply();
        Log.i(TAG, "saveToLocal: Done");
    }
    public static double[] getFromLocal(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        double lat = prefs.getFloat(LATITUDE, DEFAULT_COUNT);
        double lng = prefs.getFloat(LONGITUDE, DEFAULT_COUNT);
        return new double[]{lat,lng};
    }
    public static void update(Context context, double latitude, double longitude,String imei){
        saveToLocal(context,latitude,longitude,imei);
    }
    public static void syncToServer(Context context){
        double d[]= getFromLocal(context);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String imei=prefs.getString(IMEI,DEFAULT_IMEI);
        sendToServer(d[0],d[1],imei);
    }

}
