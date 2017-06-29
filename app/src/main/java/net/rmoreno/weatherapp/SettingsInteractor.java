package net.rmoreno.weatherapp;

import net.rmoreno.weatherapp.repositories.WeatherRepository;

public class SettingsInteractor {

    private WeatherRepository repo;

    public SettingsInteractor(WeatherRepository repo) {
        this.repo = repo;
    }

    public void updateSweather(int temperature) {
        repo.updateSweather(temperature);
    }
}
