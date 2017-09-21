package net.rmoreno.weatherapp

import net.rmoreno.weatherapp.repositories.WeatherRepository

class SettingsInteractor(private val repo: WeatherRepository) {

    fun updateSweather(temperature: Int) {
        repo.updateSweather(temperature)
    }
}
