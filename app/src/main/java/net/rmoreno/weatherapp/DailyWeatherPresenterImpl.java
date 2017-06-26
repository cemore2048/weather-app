package net.rmoreno.weatherapp;

import android.util.Log;

import net.rmoreno.weatherapp.models.DailyWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DailyWeatherPresenterImpl implements DailyWeatherPresenter {

    DailyWeatherView view;

    DailyWeatherPresenterImpl(DailyWeatherView view) {
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

        return new DailyWeather();
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
