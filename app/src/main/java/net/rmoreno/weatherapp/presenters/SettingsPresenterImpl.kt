package net.rmoreno.weatherapp.presenters

import net.rmoreno.weatherapp.SettingsInteractor

class SettingsPresenterImpl(private val interactor: SettingsInteractor) : SettingsPresenter {
    override fun resume() {}

    override fun destroy() {}

    override fun pause() {}

    override fun updateSweather(temperature: Int) {
        interactor.updateSweather(temperature)
    }
}
