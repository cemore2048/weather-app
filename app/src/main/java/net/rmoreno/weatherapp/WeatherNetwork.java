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

import org.json.JSONException;

import java.io.IOException;

public class WeatherNetwork {

    public CurrentWeather getDailyWeather(float lat, float lng) {
        String URL = "https://api.forecast.io/forecast/5530508d3568e57848d53bf10cfade1f/" + lat + "," + lng;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                try {
                    String jsonData = response.body().string();
                    final String passingData = jsonData;

                    if(response.isSuccessful()) {
                        currentWeather = getCurrentWeatherData(jsonData);
                        //change to daily weather
                        dailyWeather = getDailyWeatherData(jsonData);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayDailyWeather(currentWeather, dailyWeather, mSweaterTemp);
                                Log.d(ACTIVITY, "onclick listener set");
                                cardView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(MainActivity.this, HourlyActivity.class);
                                        intent.putExtra("hourly", passingData);
                                    }
                                });
                            }
                        });
                    }
                } catch(JSONException e) {
                    Log.d(ACTIVITY + " JSONEXCEPTION", e.getMessage());
                } catch(IOException e){
                    Log.d(ACTIVITY + " IOEXCEPTION", e.getMessage());
                }
            }
        });
        return new CurrentWeather();
    }
}
