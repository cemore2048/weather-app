package net.rmoreno.weatherapp;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

/**
 * Created by Rafa on 12/12/15.
 */
public class MyLocationListener implements LocationListener{


    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }
}
