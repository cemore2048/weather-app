package net.rmoreno.weatherapp


import com.squareup.okhttp.Callback
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response

import net.rmoreno.weatherapp.presenters.Presenter
import net.rmoreno.weatherapp.repositories.WeatherRepository
import org.json.JSONObject

import java.io.IOException

class WeatherInteractor(var repo: WeatherRepository) {

    fun getWeatherData(lat: Double, lng: Double, weatherCallback: Presenter.WeatherCallback) {
        repo.getWeather(lat, lng, weatherCallback)
    }

    val sweaterWeather: Int
        get() = repo.sweaterTemp


}
