package net.rmoreno.weatherapp.repositories

import android.content.SharedPreferences
import io.reactivex.schedulers.Schedulers
import net.rmoreno.weatherapp.WeatherNetwork
import io.reactivex.android.schedulers.AndroidSchedulers;
import net.rmoreno.weatherapp.presenters.Presenter

class WeatherRepository(internal var preferences: SharedPreferences) {

    internal var network = WeatherNetwork()

    fun getWeather(lat: Double, lng: Double, weatherCallback: Presenter.WeatherCallback) {
        network.getWeather(lat, lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    weatherCallback.onWeatherRetrieved(response)
                }
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
