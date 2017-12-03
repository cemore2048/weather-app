package net.rmoreno.weatherapp

import io.reactivex.Observable
import net.rmoreno.weatherapp.models.ForecastResponse
import net.rmoreno.weatherapp.repositories.WeatherRepository

class WeatherInteractor(var repo: WeatherRepository, var locationSensor: LocationSensor) {
    fun getWeatherData(lat: Double, lng: Double): Observable<ForecastResponse> {
        return repo.getForecast(lat, lng)
    }

    fun getCurrentLocation(): Pair<Double, Double>? {
        return locationSensor.getLocation()
    }

    fun startLocationService() {
        locationSensor.requestLocationUpates()
    }

    val sweaterWeather: Int
        get() = repo.sweaterTemp
}
