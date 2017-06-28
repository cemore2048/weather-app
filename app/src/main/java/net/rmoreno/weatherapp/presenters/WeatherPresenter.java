package net.rmoreno.weatherapp.presenters;

public interface WeatherPresenter extends Presenter {

    void getDailyWeather(double lat, double lng);

    void getCurrentWeather(double lat, double lng);

    boolean firstTime();
}