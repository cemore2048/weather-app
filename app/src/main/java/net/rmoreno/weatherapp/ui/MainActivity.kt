package net.rmoreno.weatherapp.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import net.rmoreno.weatherapp.LocationSensor
import net.rmoreno.weatherapp.R
import net.rmoreno.weatherapp.WeatherInteractor
import net.rmoreno.weatherapp.adapters.DailyAdapter
import net.rmoreno.weatherapp.models.Currently
import net.rmoreno.weatherapp.models.DailyDetail
import net.rmoreno.weatherapp.presenters.WeatherPresenter
import net.rmoreno.weatherapp.repositories.WeatherRepository

class MainActivity : Activity(), WeatherView {

    //internal var ACTIVITY = "MAIN ACTIVITY"
    private var SHARED_PREFERENCES = "MyPrefs"

    private lateinit var weatherPresenter: WeatherPresenter

    private var REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)
        weather_loading_bar.visibility = View.VISIBLE
        weather_loading_bar.bringToFront()

        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val locationSensor = LocationSensor(this@MainActivity)
        val weatherRepository = WeatherRepository(sharedPreferences)
        val weatherInteractor = WeatherInteractor(weatherRepository, locationSensor)
        weatherPresenter = WeatherPresenter(weatherInteractor)

    }

    public override fun onResume() {
        super.onResume()
        weatherPresenter.bindView(this@MainActivity)
        weatherPresenter.checkIfFirstTime()
        //TODO unbind presenter and connectivity manager
    }

    public override fun onPause() {
        super.onPause()
        weatherPresenter.unbindView()
    }

    //the way I'm setting this up doesn't make sense right now
    private fun setup() {

    }

    override fun goToIntroActivity() {
        val intent = Intent(this@MainActivity, IntroActivity::class.java)
        startActivity(intent)
    }

    override fun displayDailyWeather(dailyWeather: List<DailyDetail>, sweaterTemp: Int, timezone: String) {
        val adapter = DailyAdapter(this@MainActivity, dailyWeather, sweaterTemp, timezone)
        recycler_view.adapter = adapter
    }

    override fun displayCurrentWeather(currentWeather: Currently) {
        temperature.text = Math.round(currentWeather.temperature).toString() + "°"
        time.text = "At " + currentWeather.time

        //TODO: get icon
        //current_icon.setImageResource(currentWeather.icon)
        summary_text.text = currentWeather.summary
        precipitation.text = currentWeather.precipProbability.toString() + "%"
        feels.text = Math.round(currentWeather.apparentTemperature).toString() + "°"
        wind.text = Math.round(currentWeather.windSpeed).toString() + "mph"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun setLoading(isLoading: Boolean) {
        weather_loading_bar.visibility = if (isLoading) View.VISIBLE else View.GONE;
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