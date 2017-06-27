package net.rmoreno.weatherapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.rmoreno.weatherapp.R;
import net.rmoreno.weatherapp.adapters.HourlyAdapter;
import net.rmoreno.weatherapp.models.HourlyWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HourlyActivity extends Activity {
    String ACTIVITY = "HOURLY ACTIVITY";

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();


        //converted to json
        String hourlyString = extras.getString("hello");
        try{

            ArrayList<HourlyWeather> hourlyWeatherArrayList = getHourlyWeatherData(hourlyString);
            setUIValues(hourlyWeatherArrayList);
        } catch(JSONException e){
            Log.d(ACTIVITY, "JSON EXCEPTION");
        }
    }



    public ArrayList<HourlyWeather> getHourlyWeatherData(String jsonData) throws JSONException {
        ArrayList<HourlyWeather> hourlyWeatherList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject hourly = jsonObject.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        for (int i = 0; i < data.length(); i++) {
            HourlyWeather hourlyWeather = new HourlyWeather();

            hourlyWeather.setTime(data.getJSONObject(i).getLong("time"));
            hourlyWeather.setTemp(data.getJSONObject(i).getDouble("temperature"));
            hourlyWeather.setTimeZone(jsonObject.getString("timezone"));
            hourlyWeatherList.add(hourlyWeather);
        }

        return hourlyWeatherList;
    }

    public void setUIValues(ArrayList<HourlyWeather> hourly){

        //the hourly data is get inside the adapter class
        HourlyAdapter adapter = new HourlyAdapter(HourlyActivity.this, hourly);
        mRecyclerView.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daily, menu);
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
