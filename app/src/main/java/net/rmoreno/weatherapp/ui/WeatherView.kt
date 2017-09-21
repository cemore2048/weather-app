package net.rmoreno.weatherapp.ui

import net.rmoreno.weatherapp.models.CurrentWeather
import net.rmoreno.weatherapp.models.DailyWeather
import java.util.*

interface WeatherView {

    fun displayDailyWeather(dailyWeather: ArrayList<DailyWeather>, sweaterTemp: Int)

    fun displayCurrentWeather(currentWeather: CurrentWeather)
}
