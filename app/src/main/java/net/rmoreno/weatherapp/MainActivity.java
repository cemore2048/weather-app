package net.rmoreno.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


    //take in
    String mURL = "https://api.forecast.io/forecast/5530508d3568e57848d53bf10cfade1f/30.627040,-96.341298";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient client = new OkHttpClient();

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
                        final String passingData = jsonData;

                        if(response.isSuccessful()) {

                            mCurrentWeather = getCurrentWeatherData(jsonData);
                            //change to daily weather
                            mDailyWeather = getDailyWeatherData(jsonData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    setUIValues(mCurrentWeather, mDailyWeather);
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

        for (int i = 0; i < data.length(); i++) {
            DailyWeather dailyWeather = new DailyWeather();

            dailyWeather.setTime(data.getJSONObject(i).getLong("time"));
            dailyWeather.setMinTemp(data.getJSONObject(i).getDouble("temperatureMin"));
            dailyWeather.setMaxTemp(data.getJSONObject(i).getDouble("temperatureMax"));
            dailyWeather.setTimeZone(jsonObject.getString("timezone"));
            dailyWeatherList.add(dailyWeather);
        }

        return dailyWeatherList;
    }

    public void setUIValues(CurrentWeather current, ArrayList<DailyWeather> daily) {
        mTemperature.setText(current.getTemp() + "°");
        mTime.setText("At " + current.getFormatedTime());
        mIcon.setImageResource(current.getIconId());
        mSummary.setText(current.getSummary());
        mPrecipitation.setText(String.valueOf(current.getPrecip())+"%");
        mFeels.setText(String.valueOf(current.getFeels())+ "°");
        mWind.setText(String.valueOf(current.getWind()) + "mph");

        DailyAdapter adapter = new DailyAdapter(MainActivity.this, daily);
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
