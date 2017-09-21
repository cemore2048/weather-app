package net.rmoreno.weatherapp.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

import net.rmoreno.weatherapp.MyLocationListener
import net.rmoreno.weatherapp.R
import net.rmoreno.weatherapp.WeatherInteractor
import net.rmoreno.weatherapp.adapters.DailyAdapter
import net.rmoreno.weatherapp.models.CurrentWeather
import net.rmoreno.weatherapp.models.DailyWeather
import net.rmoreno.weatherapp.presenters.WeatherPresenter
import net.rmoreno.weatherapp.presenters.WeatherPresenterImpl
import net.rmoreno.weatherapp.repositories.WeatherRepository

import java.util.ArrayList


class MainActivity : Activity(), WeatherView {

    internal var ACTIVITY = "MAIN ACTIVITY"
    internal var SHARED_PREFERENCES = "MyPrefs"

    lateinit var weatherPresenter: WeatherPresenter

    internal var REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set sharedPreferences preferences to detect if user has a 'sweater weather'
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

        val weatherPresenter = WeatherPresenterImpl(
                this,
                WeatherInteractor(
                        WeatherRepository(sharedPreferences)
                )
        )

        if (weatherPresenter.isUsersFirstTime()) {
            val intent = Intent(this@MainActivity, IntroActivity::class.java)
            startActivity(intent)
            Toast.makeText(this@MainActivity, "sweater is 0", Toast.LENGTH_SHORT).show()
        }

        recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)

        if (isNetworkAvailible()) {
            getLocation()
        }
    }

    public override fun onResume() {
        super.onResume()
        weatherPresenter.resume()
        getLocation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun isNetworkAvailible(): Boolean {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        var isAvailable = false
        if (networkInfo != null && networkInfo.isConnected) {
            isAvailable = true
        }
        return isAvailable
    }

    fun isLocationEnabled(lm: LocationManager): Boolean {
        var gps_enabled = false
        var network_enabled = false

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }

        return !gps_enabled && !network_enabled
    }

    override fun displayDailyWeather(dailyWeather: ArrayList<DailyWeather>, sweaterTemp: Int) {
        val adapter = DailyAdapter(this@MainActivity, dailyWeather, sweaterTemp)
        recycler_view.adapter = adapter
    }

    override fun displayCurrentWeather(currentWeather: CurrentWeather) {
        temperature.text = currentWeather.temp.toString() + "°"
        time.text = "At " + currentWeather.formatedTime
        current_icon.setImageResource(currentWeather.iconId)
        summary_text.text = currentWeather.summary
        precipitation.text = currentWeather.precip.toString() + "%"
        feels.text = currentWeather.feels.toString() + "°"
        wind.text = currentWeather.wind.toString() + "mph"
    }

    fun buildDialog(context: Context) {
        val dialog = AlertDialog.Builder(context)
        dialog.setMessage("GPS network is not enabled")
        dialog.setPositiveButton(context.resources.getString(R.string.open_location_settings)) { _, _ ->
            val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(myIntent, REQUEST_CODE)
        }

        dialog.setNegativeButton(context.getString(R.string.Cancel)) { paramDialogInterface, paramInt -> }
        dialog.show()
    }

    fun getLocation() {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = MyLocationListener()

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
        if (isLocationEnabled(lm)) {
            val location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            Log.d("LOCATION", location.latitude.toString())
            weatherPresenter.getCurrentWeather(location.latitude, location.longitude)
            weatherPresenter.getDailyWeather(location.latitude, location.longitude)

        } else {
            buildDialog(this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        } else if (id == R.id.set_sweather) {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}