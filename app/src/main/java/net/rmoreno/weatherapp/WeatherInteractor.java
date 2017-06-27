package net.rmoreno.weatherapp;


import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class WeatherInteractor {

    private WeatherRepository repo = new WeatherRepository();

    public WeatherInteractor(WeatherRepository repo) {
        this.repo = repo;
    }

    public void getWeatherData(float lat, float lng, final Presenter.WeatherCallback weatherCallback) {
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
