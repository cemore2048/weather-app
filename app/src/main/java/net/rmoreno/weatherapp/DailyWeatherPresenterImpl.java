package net.rmoreno.weatherapp;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.rmoreno.weatherapp.models.DailyWeather;

import org.json.JSONException;

import java.io.IOException;

public class DailyWeatherPresenterImpl implements DailyWeatherPresenter {

    WeatherNetwork network;

    DailyWeatherPresenterImpl(WeatherNetwork network) {
        this.network = network;
    }
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
        String URL = "https://api.forecast.io/forecast/5530508d3568e57848d53bf10cfade1f/" + lat + "," + lng;
        network.getDailyWeather();
        return new DailyWeather();
    }


}
