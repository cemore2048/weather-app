package net.rmoreno.weatherapp.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.rmoreno.weatherapp.LocationSensor
import net.rmoreno.weatherapp.R
import net.rmoreno.weatherapp.WeatherInteractor
import net.rmoreno.weatherapp.adapters.DailyAdapter
import net.rmoreno.weatherapp.models.CurrentWeather
import net.rmoreno.weatherapp.models.DailyWeather
import net.rmoreno.weatherapp.presenters.WeatherPresenter
import net.rmoreno.weatherapp.presenters.WeatherPresenterImpl
import net.rmoreno.weatherapp.repositories.WeatherRepository
import java.util.*


class MainActivity : Activity(), WeatherView {

    //internal var ACTIVITY = "MAIN ACTIVITY"
    private var SHARED_PREFERENCES = "MyPrefs"

    lateinit var weatherPresenter: WeatherPresenter

    private var REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //set sharedPreferences preferences to detect if user has a 'sweater weather'

        recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)
        weather_loading_bar.setVisibility(View.VISIBLE)
        weather_loading_bar.bringToFront()

        weatherPresenter.checkIfFirstTime()

        setup()
    }

    public override fun onResume() {
        super.onResume()
        //TODO unbind presenter and connectivity manager
        setup()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun setup() {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val locationSensor = LocationSensor(this@MainActivity)
        val weatherRepository = WeatherRepository(sharedPreferences)
        val weatherInteractor = WeatherInteractor(weatherRepository, locationSensor)

        weatherPresenter = WeatherPresenterImpl(
                this,
                    weatherInteractor
        )

    }

    override fun goToIntroActivity() {
        val intent = Intent(this@MainActivity, IntroActivity::class.java)
        startActivity(intent)
        Toast.makeText(this@MainActivity, "sweater is 0", Toast.LENGTH_SHORT).show()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun setLoading(isLoading: Boolean) {
        weather_loading_bar.setVisibility(if (isLoading) View.VISIBLE else View.GONE);
    }

    override fun displayNetworkError() {
        val context: Context = this@MainActivity

        val dialog = AlertDialog.Builder(context)
        dialog.setMessage("GPS network is not enabled")
        dialog.setPositiveButton(context.resources.getString(R.string.open_location_settings)) { _, _ ->
            val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(myIntent, REQUEST_CODE)
        }

        dialog.setNegativeButton(context.getString(R.string.Cancel)) { _, _ -> }
        dialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            R.id.action_settings -> {
                return true
            }

            R.id.set_sweather -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
            }

            R.id.refresh -> {
                setup()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}