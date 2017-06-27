package net.rmoreno.weatherapp.repositories;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.rmoreno.weatherapp.WeatherNetwork;

import java.io.IOException;

public class WeatherRepository {

    WeatherNetwork network = new WeatherNetwork();

    public void getCurrentWeather(double lat, double lng, final Callback callback) {
        network.getDailyWeather(lat, lng, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try{
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {
                        callback.onResponse(response);
                    }
                }  catch(IOException e){
                    Log.d("DWPresent" + " IOEXCEPTION", e.getMessage());
                }

            }
        });
    }

    public void getDailyWeather(float lat, float lng, final Callback callback) {
        network.getCurrentWeather(lat, lng, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try{
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {
                        callback.onResponse(response);
                    }
                } catch(IOException e){
                    Log.d("DWPresent" + " IOEXCEPTION", e.getMessage());
                }

            }
        });
    }


}
