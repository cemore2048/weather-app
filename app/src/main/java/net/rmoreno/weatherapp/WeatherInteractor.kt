package net.rmoreno.weatherapp

import io.reactivex.Observable
import net.rmoreno.weatherapp.repositories.WeatherRepository
import okhttp3.Response

class WeatherInteractor(var repo: WeatherRepository) {

    fun getWeatherData(lat: Double, lng: Double): Observable<Response> {
        return repo.getWeather(lat, lng)
    }

    val sweaterWeather: Int
        get() = repo.sweaterTemp
}
