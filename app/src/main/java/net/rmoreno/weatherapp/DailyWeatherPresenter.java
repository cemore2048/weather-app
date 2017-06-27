package net.rmoreno.weatherapp;

public interface DailyWeatherPresenter extends Presenter {

    void getDailyWeather(float lat, float lng);

    void getCurrentWeather(float lat, float lng);

}