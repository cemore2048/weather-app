package net.rmoreno.weatherapp.ui;

import net.rmoreno.weatherapp.models.CurrentWeather;
import net.rmoreno.weatherapp.models.DailyWeather;

import java.util.ArrayList;

public interface WeatherView {

    void displayDailyWeather(ArrayList<DailyWeather> dailyWeather);

    void displayCurrentWeather(CurrentWeather currentWeather);
}
