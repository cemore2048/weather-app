package net.rmoreno.weatherapp;

import net.rmoreno.weatherapp.models.CurrentWeather;
import net.rmoreno.weatherapp.models.DailyWeather;

import java.util.ArrayList;

public interface WeatherRepository {

    public CurrentWeather getCurrentWeather();

    public ArrayList<DailyWeather> getDailyWeather();
}
