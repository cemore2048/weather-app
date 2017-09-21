package net.rmoreno.weatherapp.ui

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_daily.*
import net.rmoreno.weatherapp.R
import net.rmoreno.weatherapp.adapters.HourlyAdapter
import net.rmoreno.weatherapp.models.HourlyWeather
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class HourlyActivity : Activity() {
    internal var ACTIVITY = "HOURLY ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily)

        recycler_view.layoutManager = LinearLayoutManager(this)

        val extras = intent.extras

        //converted to json
        val hourlyString = extras.getString("hello")
        try {

            val hourlyWeatherArrayList = getHourlyWeatherData(hourlyString)
            setUIValues(hourlyWeatherArrayList)
        } catch (e: JSONException) {
            Log.d(ACTIVITY, "JSON EXCEPTION")
        }
    }

    @Throws(JSONException::class)
    fun getHourlyWeatherData(jsonData: String): ArrayList<HourlyWeather> {
        val hourlyWeatherList = ArrayList<HourlyWeather>()

        val jsonObject = JSONObject(jsonData)
        val hourly = jsonObject.getJSONObject("hourly")
        val data = hourly.getJSONArray("data")

        for (i in 0..data.length() - 1) {
            val hourlyWeather = HourlyWeather()

            hourlyWeather.time = data.getJSONObject(i).getLong("time")
            hourlyWeather.setTemp(data.getJSONObject(i).getDouble("temperature"))
            hourlyWeather.timeZone = jsonObject.getString("timezone")
            hourlyWeatherList.add(hourlyWeather)
        }

        return hourlyWeatherList
    }

    fun setUIValues(hourly: ArrayList<HourlyWeather>) {
        //the hourly data is get inside the adapter class
        val adapter = HourlyAdapter(this@HourlyActivity, hourly)
        recycler_view.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_daily, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
