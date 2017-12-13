package net.rmoreno.weatherapp

import android.location.Location
import com.google.android.gms.tasks.Task
import io.reactivex.Observable
import net.rmoreno.weatherapp.models.ForecastResponse
import net.rmoreno.weatherapp.repositories.WeatherRepository

class WeatherInteractor(var repo: WeatherRepository, var locationSensor: LocationSensor) {
    fun getWeatherData(lat: Double, lng: Double): Observable<ForecastResponse> {
        return repo.getForecast(lat, lng)
    }

    fun getCurrentLocation(): Task<Location> {
        return locationSensor.getLastLocation()
    }

    val sweaterWeather: Int
        get() = repo.sweaterTemp
}
