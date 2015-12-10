package net.rmoreno.weatherapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends Activity {

    String ACTIVITY = "MAIN ACTIVITY";

    OkHttpClient mClient;
    CurrentWeather mWeather;

    //take in
    String mURL = "https://api.forecast.io/forecast/5530508d3568e57848d53bf10cfade1f/37.8267,-122.423";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(mURL)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                    Log.d(ACTIVITY, response.body().string());
                    try{
                        if(response.isSuccessful()) {
                            String jsonData = response.body().string();

                            Log.d(ACTIVITY + "jsonObject", jsonData);

                            mWeather = getWeatherData(jsonData);

                            Log.d(ACTIVITY + "Temperature", mWeather.getTemp());
                        }

                    } catch(JSONException e){
                        Log.d(ACTIVITY + " JSONEXCEPTION", e.getMessage());
                    } catch(IOException e){
                        Log.d(ACTIVITY + " IOEXCEPTION", e.getMessage());
                    }

                }
        });






    }

    public CurrentWeather getWeatherData(String jsonData) throws JSONException{
        CurrentWeather currentWeather = new CurrentWeather();
        JSONObject jsonObject = new JSONObject(jsonData);

        JSONObject hourly = jsonObject.getJSONObject("hourly");

        currentWeather.setTemp(hourly.getString("temperature"));

        return new CurrentWeather();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
