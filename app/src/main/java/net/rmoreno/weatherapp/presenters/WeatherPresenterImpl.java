package net.rmoreno.weatherapp.presenters;

import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.rmoreno.weatherapp.WeatherInteractor;
import net.rmoreno.weatherapp.ui.MainActivity;
import net.rmoreno.weatherapp.ui.WeatherView;
import net.rmoreno.weatherapp.models.CurrentWeather;
import net.rmoreno.weatherapp.models.DailyWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class WeatherPresenterImpl implements WeatherPresenter {

    public WeatherView view;
    public WeatherInteractor weatherInteractor;

    public WeatherPresenterImpl(MainActivity view, WeatherInteractor weatherInteractor) {
        this.view = view;
        this.weatherInteractor = weatherInteractor;
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void pause() {

    }

    @Override
    public boolean firstTime() {
        return weatherInteractor.getSweaterWeather() == 0 ? true : false;
    }
    @Override
    public void getCurrentWeather(double lat, double lng) {
        weatherInteractor.getWeatherData(lat, lng, new WeatherCallback() {
            @Override
            public void onWeatherRetrieved(Response response) {
                try{
                    String jsonData = response.body().string();

                    CurrentWeather currentWeather = getCurrentWeatherData(jsonData);
                    view.displayCurrentWeather(currentWeather);
                } catch (JSONException e) {
                    Log.d("Present JSON Exception", e.getMessage());
                } catch (IOException e) {
                    Log.d("Present IO Exception", e.getMessage());
                }

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });
    }
    @Override
    public void getDailyWeather(double lat, double lng) {
        weatherInteractor.getWeatherData(lat, lng, new WeatherCallback() {
            @Override
            public void onWeatherRetrieved(Response response) {
                try{
                    String jsonData = response.body().string();

                    ArrayList<DailyWeather> dailyWeather = getDailyWeatherData(jsonData);
                    int sweaterWeather = weatherInteractor.getSweaterWeather();
                    view.displayDailyWeather(dailyWeather, sweaterWeather);
                } catch (JSONException e) {
                    Log.d("Present JSON Exception", e.getMessage());
                } catch (IOException e) {
                    Log.d("Present IO Exception", e.getMessage());
                }

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });
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
