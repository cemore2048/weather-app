package net.rmoreno.weatherapp

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

class LocationSensor(val context: Context) {

    private var connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var networkInfo: NetworkInfo
    private var lm: LocationManager

    init {
        networkInfo = connectivityManager.activeNetworkInfo
        lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    fun requestLocationUpates() {
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, MyLocationListener())
    }

    private fun isLocationEnabled(lm: LocationManager): Boolean {
        var gpsEnabled = false
        var networkEnabled = false

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }

        return gpsEnabled && networkEnabled
    }

    fun getLocation(): Pair<Double, Double>? {
        return if (isLocationEnabled(lm)) {
            val location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            Log.d("LOCATION", location.latitude.toString())
            Pair(location.latitude, location.longitude)
        } else {
            null
        }
    }
}