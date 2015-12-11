package net.rmoreno.weatherapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {

    String ACTIVITY = "MAIN ACTIVITY";

    CurrentWeather mCurrentWeather;
    ArrayList<HourlyWeather> mHourlyWeather;
    RecyclerView mRecyclerView;

    TextView mTemperature;
    TextView mSummary;
    TextView mTime;

    ImageView mIcon;


    //take in
    String mURL = "https://api.forecast.io/forecast/5530508d3568e57848d53bf10cfade1f/37.8267,-122.423";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient client = new OkHttpClient();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTemperature = (TextView) findViewById(R.id.temperature);
//        mSummary = (TextView) findViewById(R.id.summary);
        mTime = (TextView) findViewById(R.id.time);
//        mIcon = (ImageView) findViewById(R.id.icon);

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

                    try{
                        String jsonData = response.body().string();

                        if(response.isSuccessful()) {

                            mCurrentWeather = getCurrentWeatherData(jsonData);
                            mHourlyWeather = getHourlyWeatherData(jsonData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    setUIValues(mCurrentWeather, mHourlyWeather);
                                }
                            });
                        }

                    } catch(JSONException e){
                        Log.d(ACTIVITY + " JSONEXCEPTION", e.getMessage());
                    } catch(IOException e){
                        Log.d(ACTIVITY + " IOEXCEPTION", e.getMessage());
                    }

                }
        });
    }

    public CurrentWeather getCurrentWeatherData(String jsonData) throws JSONException{

        CurrentWeather currentWeather = new CurrentWeather();
        JSONObject jsonObject = new JSONObject(jsonData);

        JSONObject currently = jsonObject.getJSONObject("currently");

        Log.d(ACTIVITY + "currently", currently.toString());

        currentWeather.setTemp(currently.getDouble("temperature"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setTimeZone(jsonObject.getString("timezone"));


        //currentWeather.setIcon(currently.getString("icon"));

        return currentWeather;
    }

    public ArrayList<HourlyWeather> getHourlyWeatherData(String jsonData) throws JSONException{
        ArrayList<HourlyWeather> hourlyWeatherList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject hourly = jsonObject.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        for(int i = 0; i< data.length(); i++){
            HourlyWeather hourlyWeather = new HourlyWeather();

            hourlyWeather.setTime(data.getJSONObject(i).getLong("time"));
            hourlyWeather.setTemp(data.getJSONObject(i).getDouble("temperature"));
            hourlyWeather.setTimeZone(jsonObject.getString("timezone"));
            hourlyWeatherList.add(hourlyWeather);
        }


        return hourlyWeatherList;
    }

    public void setUIValues(CurrentWeather current, ArrayList<HourlyWeather> hourly){
        mTemperature.setText(current.getTemp() + "°");
        mTime.setText("At " + current.getFormatedTime());


        //the hourly data is set inside the adapter class
        HourlyAdapter adapter = new HourlyAdapter(hourly);
        mRecyclerView.setAdapter(adapter);

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
