package net.rmoreno.weatherapp;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

public class WeatherNetwork {

    public void getWeather(double lat, double lng, Callback callback) {
        String URL = "https://api.forecast.io/forecast/5530508d3568e57848d53bf10cfade1f/" + lat + "," + lng;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
