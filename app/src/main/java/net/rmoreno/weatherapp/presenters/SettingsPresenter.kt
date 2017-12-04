package net.rmoreno.weatherapp.presenters

import net.rmoreno.weatherapp.SettingsInteractor

class SettingsPresenter(private val interactor: SettingsInteractor) {

    fun updateSweather(temperature: Int) {
        interactor.updateSweather(temperature)
    }
}
