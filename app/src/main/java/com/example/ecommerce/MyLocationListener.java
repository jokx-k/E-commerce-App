package com.example.ecommerce;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyLocationListener implements LocationListener {
    private Context activityContext;;

    public MyLocationListener(Context context) {
        this.activityContext = context;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        System.out.println("a");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(activityContext,"on Status Change",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Toast.makeText(activityContext,"GPS Enabled",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(activityContext,"GPS Disable",Toast.LENGTH_LONG).show();
    }
}
