package net.rmoreno.weatherapp.repositories

import android.content.SharedPreferences
import io.reactivex.Observable
import net.rmoreno.weatherapp.WeatherNetwork
import net.rmoreno.weatherapp.models.ForecastResponse
import okhttp3.Response

class WeatherRepository(internal var preferences: SharedPreferences) {

    internal var network = WeatherNetwork.Factory.create()

    fun getForecast(lat: Double, lng: Double): Observable<ForecastResponse> {
        return network.getForecast(lat, lng)
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
