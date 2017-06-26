package net.rmoreno.weatherapp;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.rmoreno.weatherapp.models.CurrentWeather;
import net.rmoreno.weatherapp.models.DailyWeather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class WeatherRepository {

    WeatherNetwork network = new WeatherNetwork();

    public CurrentWeather getCurrentWeather(float lat, float lng) {
        network.getDailyWeather(lat, lng, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try{
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {
                        CurrentWeather currentWeather = getCurrentWeatherData(jsonData);

                    }
                }  catch(JSONException e) {
                    Log.d("DWPresent" + " JSONEXCEPTION", e.getMessage());
                } catch(IOException e){
                    Log.d("DWPresent" + " IOEXCEPTION", e.getMessage());
                }

            }
        });
    }

    public ArrayList<DailyWeather> getDailyWeather();

    private CurrentWeather getCurrentWeatherData(String jsonData) throws JSONException{

        CurrentWeather currentWeather = new CurrentWeather();
        JSONObject jsonObject = new JSONObject(jsonData);

        JSONObject currently = jsonObject.getJSONObject("currently");

        currentWeather.setTemp(currently.getDouble("temperature"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setTimeZone(jsonObject.getString("timezone"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setPrecip(currently.getInt("precipProbability"));
        currentWeather.setFeels(currently.getDouble(("apparentTemperature")));
        currentWeather.setWind(currently.getDouble(("windSpeed")));

        return currentWeather;
    }
}
