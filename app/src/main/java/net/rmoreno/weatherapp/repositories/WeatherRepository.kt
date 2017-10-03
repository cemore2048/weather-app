package net.rmoreno.weatherapp.repositories

import android.content.SharedPreferences
import io.reactivex.Observable
import net.rmoreno.weatherapp.WeatherNetwork
import okhttp3.Response

class WeatherRepository(internal var preferences: SharedPreferences) {

    internal var network = WeatherNetwork()

    fun getWeather(lat: Double, lng: Double): Observable<Response> {
        return network.getWeather(lat, lng)
    }

    val sweaterTemp: Int
        get() = preferences.getInt("sweater", 0)

    fun updateSweather(temperature: Int) {
        val edit = preferences.edit()

        edit.remove("sweater")
        edit.putInt("sweater", temperature)
        edit.apply()
        preferences.getInt("sweater", 0)
    }
}
