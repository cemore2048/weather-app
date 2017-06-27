package net.rmoreno.weatherapp;


import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.rmoreno.weatherapp.presenters.Presenter;
import net.rmoreno.weatherapp.repositories.WeatherRepository;

import java.io.IOException;

public class WeatherInteractor {

    private WeatherRepository repo = new WeatherRepository();

    public WeatherInteractor(WeatherRepository repo) {
        this.repo = repo;
    }

    public void getWeatherData(double lat, double lng, final Presenter.WeatherCallback weatherCallback) {
        repo.getCurrentWeather(lat, lng, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                weatherCallback.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                weatherCallback.onWeatherRetrieved(response);
            }
        });
    }


}
