package net.rmoreno.weatherapp;

import net.rmoreno.weatherapp.models.DailyWeather;

public interface DailyWeatherPresenter extends Presenter {

    DailyWeather getDailyWeather(float lat, float lng);

}