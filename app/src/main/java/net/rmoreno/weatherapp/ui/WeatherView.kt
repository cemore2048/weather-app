package net.rmoreno.weatherapp.ui

import net.rmoreno.weatherapp.models.CurrentWeather
import net.rmoreno.weatherapp.models.DailyWeather

interface WeatherView {

    fun displayDailyWeather(dailyWeather: ArrayList<DailyWeather>, sweaterTemp: Int)

    fun displayCurrentWeather(currentWeather: CurrentWeather)

    fun setLoading(isLoading: Boolean)

    fun displayNetworkError()

}
