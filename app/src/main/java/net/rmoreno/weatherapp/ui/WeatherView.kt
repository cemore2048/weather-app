package net.rmoreno.weatherapp.ui

import net.rmoreno.weatherapp.models.Currently
import net.rmoreno.weatherapp.models.DailyDetail

interface WeatherView {
    fun displayDailyWeather(dailyWeather: List<DailyDetail>, sweaterTemp: Int, timezone: String)

    fun displayCurrentWeather(currentWeather: Currently, timezone: String)

    fun setLoading(isLoading: Boolean)

    fun displayNetworkError()

    fun goToIntroActivity()

}
