package net.rmoreno.weatherapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.rmoreno.weatherapp.adapters.DailyAdapter;
import net.rmoreno.weatherapp.models.CurrentWeather;
import net.rmoreno.weatherapp.models.DailyWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {

    String ACTIVITY = "MAIN ACTIVITY";
    String SHARED_PREFERENCES = "MyPrefs";
    ArrayList<DailyWeather> mDailyWeather;
    CurrentWeather mCurrentWeather;
    CardView mCardView;

    RecyclerView mRecyclerView;

    TextView mTemperature;
    TextView mSummary;
    TextView mTime;
    TextView mPrecipitation;
    TextView mWind;
    TextView mFeels;

    ImageView mIcon;

    int mSweaterTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //set shared preferences to detect if user has a 'sweater weather'

        SharedPreferences sweaterWeather = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mSweaterTemp = sweaterWeather.getInt("sweater", 0);

        Log.d(ACTIVITY + "SWEATER", String.valueOf(mSweaterTemp));

        if(mSweaterTemp == 0) {
            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
            startActivity(intent);

            Toast.makeText(MainActivity.this, "sweater is 0", Toast.LENGTH_SHORT).show();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCardView = (CardView) findViewById(R.id.currentData);

        mTemperature = (TextView) findViewById(R.id.temperature);
        mSummary = (TextView) findViewById(R.id.summary_text);
        mTime = (TextView) findViewById(R.id.time);
        mPrecipitation = (TextView) findViewById(R.id.precipitation);
        mFeels = (TextView) findViewById(R.id.feels);
        mWind = (TextView) findViewById(R.id.wind);

        mIcon = (ImageView) findViewById(R.id.current_icon);

        if(isNetworkAvailible()) {
            getLocation();
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        getLocation();
    }

    public void getWeather(double latitude, double longitude){

        Log.d(ACTIVITY, String.valueOf(longitude) + " " + String.valueOf(latitude));
        String URL = "https://api.forecast.io/forecast/5530508d3568e57848d53bf10cfade1f/" + latitude + "," + longitude;
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

                try{
                    String jsonData = response.body().string();
                    final String passingData = jsonData;

                    if(response.isSuccessful()) {

                        mCurrentWeather = getCurrentWeatherData(jsonData);
                        //change to daily weather
                        mDailyWeather = getDailyWeatherData(jsonData);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                setUIValues(mCurrentWeather, mDailyWeather, mSweaterTemp);
                                Log.d(ACTIVITY, "onclick listener set");
                                mCardView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(MainActivity.this, HourlyActivity.class);
                                        intent.putExtra("hourly", passingData);
                                    }
                                });
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

        Log.d(ACTIVITY, String.valueOf(data.getJSONObject(1).getDouble("precipProbability")));
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

    public void setUIValues(CurrentWeather current, ArrayList<DailyWeather> daily, int sweaterTemp) {
        mTemperature.setText(current.getTemp() + "°");
        mTime.setText("At " + current.getFormatedTime());
        mIcon.setImageResource(current.getIconId());
        mSummary.setText(current.getSummary());
        mPrecipitation.setText(String.valueOf(current.getPrecip())+"%");
        mFeels.setText(String.valueOf(current.getFeels())+ "°");
        mWind.setText(String.valueOf(current.getWind()) + "mph");

        DailyAdapter adapter = new DailyAdapter(MainActivity.this, daily, sweaterTemp);
        mRecyclerView.setAdapter(adapter);
    }

    private boolean isNetworkAvailible() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    public boolean isLocationEnabled(Context context, LocationManager lm){
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            return false;
        } else{
            return true;
        }
    }

    public void buildDialog(Context context){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("GPS network is not enabled");
        dialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
                //get gps
            }
        });
        dialog.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub

            }
        });
        dialog.show();
    }

    public void getLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0, locationListener);
        if(isLocationEnabled(MainActivity.this, lm)) {
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.d("LOCATION",String.valueOf(location.getLatitude()));
            getWeather(location.getLatitude(), location.getLongitude());

        } else {
            buildDialog(MainActivity.this);
        }
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