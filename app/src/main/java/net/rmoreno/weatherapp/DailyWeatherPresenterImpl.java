package net.rmoreno.weatherapp;

import net.rmoreno.weatherapp.models.DailyWeather;

public class DailyWeatherPresenterImpl implements DailyWeatherPresenter {

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void pause() {

    }

    @Override
    public DailyWeather getDailyWeather(float lat, float lng) {

        return new DailyWeather();
    }
}
