package net.rmoreno.weatherapp.repositories

import android.content.SharedPreferences
import com.squareup.okhttp.Response
import io.reactivex.Observable
import net.rmoreno.weatherapp.WeatherNetwork

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
