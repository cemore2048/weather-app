package net.rmoreno.weatherapp

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log


class MyLocationListener : LocationListener {
    override fun onLocationChanged(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude

        println("LATITUDE AND LONGITUDE$latitude   $longitude")
    }

    override fun onProviderEnabled(provider: String) {
        Log.d("Latitude", "enable")
    }

    override fun onProviderDisabled(provider: String) {
        Log.d("Latitude", "disable")
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        Log.d("Latitude", "status")
    }
}
