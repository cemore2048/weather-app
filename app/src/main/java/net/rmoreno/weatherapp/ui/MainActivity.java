package net.rmoreno.weatherapp.ui;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.rmoreno.weatherapp.MyLocationListener;
import net.rmoreno.weatherapp.R;
import net.rmoreno.weatherapp.WeatherInteractor;
import net.rmoreno.weatherapp.adapters.DailyAdapter;
import net.rmoreno.weatherapp.models.CurrentWeather;
import net.rmoreno.weatherapp.models.DailyWeather;
import net.rmoreno.weatherapp.presenters.WeatherPresenter;
import net.rmoreno.weatherapp.presenters.WeatherPresenterImpl;
import net.rmoreno.weatherapp.repositories.WeatherRepository;

import java.util.ArrayList;


public class MainActivity extends Activity implements WeatherView {

    String ACTIVITY = "MAIN ACTIVITY";
    String SHARED_PREFERENCES = "MyPrefs";
    CardView cardView;

    RecyclerView mRecyclerView;

    TextView temperature;
    TextView summary;
    TextView time;
    TextView precipitation;
    TextView wind;
    TextView feelsLike;

    ImageView icon;
    public WeatherPresenter weatherPresenter;

    int REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set sharedPreferences preferences to detect if user has a 'sweater weather'
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);


        weatherPresenter = new WeatherPresenterImpl(
                this,
                new WeatherInteractor(
                        new WeatherRepository(sharedPreferences)
                )
        );

        if(weatherPresenter.firstTime()) {
            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "sweater is 0", Toast.LENGTH_SHORT).show();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardView = (CardView) findViewById(R.id.currentData);

        temperature = (TextView) findViewById(R.id.temperature);
        summary = (TextView) findViewById(R.id.summary_text);
        time = (TextView) findViewById(R.id.time);
        precipitation = (TextView) findViewById(R.id.precipitation);
        feelsLike = (TextView) findViewById(R.id.feels);
        wind = (TextView) findViewById(R.id.wind);

        icon = (ImageView) findViewById(R.id.current_icon);

        if(isNetworkAvailible()) {
            getLocation();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        weatherPresenter.resume();
        getLocation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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

    public boolean isLocationEnabled(LocationManager lm){
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

    @Override
    public void displayDailyWeather(ArrayList<DailyWeather> dailyWeather, int sweaterTemp) {
        DailyAdapter adapter = new DailyAdapter(MainActivity.this, dailyWeather, sweaterTemp);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void displayCurrentWeather(CurrentWeather currentWeather) {
        temperature.setText(currentWeather.getTemp() + "°");
        time.setText("At " + currentWeather.getFormatedTime());
        icon.setImageResource(currentWeather.getIconId());
        summary.setText(currentWeather.getSummary());
        precipitation.setText(String.valueOf(currentWeather.getPrecip())+"%");
        feelsLike.setText(String.valueOf(currentWeather.getFeels())+ "°");
        wind.setText(String.valueOf(currentWeather.getWind()) + "mph");
    }

    public void buildDialog(Context context){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("GPS network is not enabled");
        dialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(myIntent, REQUEST_CODE);
            }
        });

        dialog.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {

            }
        });
        dialog.show();
    }

    public void getLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0, locationListener);
        if(isLocationEnabled(lm)) {
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.d("LOCATION",String.valueOf(location.getLatitude()));
            weatherPresenter.getCurrentWeather(location.getLatitude(), location.getLongitude());
            weatherPresenter.getDailyWeather(location.getLatitude(), location.getLongitude());

        } else {
            buildDialog(MainActivity.this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.set_sweather) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}