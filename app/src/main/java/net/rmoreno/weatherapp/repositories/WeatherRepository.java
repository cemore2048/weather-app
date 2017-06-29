package net.rmoreno.weatherapp.repositories;

import android.content.SharedPreferences;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.rmoreno.weatherapp.WeatherNetwork;

import java.io.IOException;

public class WeatherRepository {

    WeatherNetwork network = new WeatherNetwork();
    SharedPreferences preferences;

    public WeatherRepository (SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void getWeather(double lat, double lng, final Callback callback) {
        network.getWeather(lat, lng, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try{
                    if (response.isSuccessful()) {
                        callback.onResponse(response);
                    }
                }  catch(IOException e){
                    Log.d("DWPresent" + " IOEXCEPTION", e.getMessage());
                }

            }
        });
    }

    public int getSweaterTemp() {
        return preferences.getInt("sweater", 0);
    }
}
