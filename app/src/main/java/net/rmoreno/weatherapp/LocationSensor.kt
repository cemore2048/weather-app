package net.rmoreno.weatherapp

import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class LocationSensor(val context: Context) {
    val fusedClient = LocationServices.getFusedLocationProviderClient(context)

    fun getLastLocation(): Task<Location> {
        return fusedClient.lastLocation
    }
}
