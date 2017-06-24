package net.rmoreno.weatherapp;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.rmoreno.weatherapp.models.CurrentWeather;
import net.rmoreno.weatherapp.models.DailyWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DailyWeatherPresenterImpl implements DailyWeatherPresenter {

    WeatherNetwork network;
    DailyWeatherView view;

    DailyWeatherPresenterImpl(DailyWeatherView view, WeatherNetwork network) {
        this.network = network;
        this.view = view;
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
                        view.displayDailyWeather();
                    }
                }  catch(JSONException e) {
                    Log.d("DWPresen" + " JSONEXCEPTION", e.getMessage());
                } catch(IOException e){
                    Log.d("DWPresent" + " IOEXCEPTION", e.getMessage());
                }

            }
        });
        return new DailyWeather();
    }

    public CurrentWeather getCurrentWeatherData(String jsonData) throws JSONException{

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

    public ArrayList<DailyWeather> getDailyWeatherData(String jsonData) throws JSONException {
        ArrayList<DailyWeather> dailyWeatherList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject hourly = jsonObject.getJSONObject("daily");
        JSONArray data = hourly.getJSONArray("data");

        Log.d("DWPresent", String.valueOf(data.getJSONObject(1).getDouble("precipProbability")));
        for (int i = 0; i < data.length(); i++) {
            DailyWeather dailyWeather = new DailyWeather();

            dailyWeather.setTime(data.getJSONObject(i).getLong("time"));
            dailyWeather.setMinTemp(data.getJSONObject(i).getDouble("temperatureMin"));
            dailyWeather.setMaxTemp(data.getJSONObject(i).getDouble("temperatureMax"));
            dailyWeather.setTimeZone(jsonObject.getString("timezone"));
            dailyWeather.setPrecip(data.getJSONObject(i).getDouble("precipProbability"));
            dailyWeatherList.add(dailyWeather);
        }

        return dailyWeatherList;
    }

}
