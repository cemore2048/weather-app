package net.rmoreno.weatherapp.ui

import net.rmoreno.weatherapp.models.CurrentWeather
import net.rmoreno.weatherapp.models.Currently
import net.rmoreno.weatherapp.models.DailyDetail
import net.rmoreno.weatherapp.models.DailyWeather

interface WeatherView {

    fun displayDailyWeather(dailyWeather: List<DailyDetail>, sweaterTemp: Int, timezone: String)

    fun displayCurrentWeather(currentWeather: Currently)

    fun setLoading(isLoading: Boolean)

    fun displayNetworkError()

    fun goToIntroActivity()

}
