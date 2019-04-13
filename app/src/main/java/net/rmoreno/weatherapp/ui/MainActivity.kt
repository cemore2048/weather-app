package net.rmoreno.weatherapp.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import net.rmoreno.weatherapp.*
import net.rmoreno.weatherapp.adapters.DailyAdapter
import net.rmoreno.weatherapp.models.Currently
import net.rmoreno.weatherapp.models.DailyDetail
import net.rmoreno.weatherapp.models.ForecastResponse
import net.rmoreno.weatherapp.presenters.BasePresenter
import net.rmoreno.weatherapp.presenters.WeatherPresenter
import net.rmoreno.weatherapp.repositories.WeatherRepository

class MainActivity : Activity(), WeatherView {

    private var SHARED_PREFERENCES = "MyPrefs"

    private lateinit var weatherPresenter: BasePresenter<ForecastResponse, WeatherView>

    private var REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission()
        }
        weather_loading_bar.visibility = View.VISIBLE
        weather_loading_bar.bringToFront()
    }

    public override fun onResume() {
        super.onResume()

        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val locationSensor = LocationSensor(this@MainActivity)
        val weatherRepository = WeatherRepository(sharedPreferences)
        val weatherInteractor = WeatherInteractor(weatherRepository, locationSensor)
        weatherPresenter = WeatherPresenter(weatherInteractor)
        weatherPresenter.bindView(this@MainActivity)
    }

    public override fun onPause() {
        super.onPause()
        weatherPresenter.unbindView()
    }

    override fun goToIntroActivity() {
        val intent = Intent(this@MainActivity, IntroActivity::class.java)
        startActivity(intent)
    }

    override fun displayDailyWeather(dailyWeather: List<DailyDetail>, sweaterTemp: Int, timezone: String) {
        val dailyAdapter = DailyAdapter(sweaterTemp, timezone)
        recycler_view.apply {
            adapter = dailyAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        dailyAdapter.addData(dailyWeather.toMutableList())
    }

    override fun displayCurrentWeather(currentWeather: Currently, timezone: String) {
//        val num = Math.round(currentWeather.precipProbability)
//        precipitation.text = getString(R.string.precip_probability, 1)
        temperature.text = getString(R.string.degrees, Math.round(currentWeather.temperature))
        //TODO: make this shake when we can't update the weather due to network errors or lack of location
        time.text = getString(R.string.time_at, TimeUtil.formatTime(currentWeather.time, timezone))
        current_icon.setImageResource(IconUtil.getIcon(currentWeather.icon))
        summary_text.text = currentWeather.summary
        feels.text = getString(R.string.degrees, Math.round(currentWeather.apparentTemperature))
        wind.text = getString(R.string.wind_speed, Math.round(currentWeather.windSpeed))
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
        dialog.setMessage(getString(R.string.network_not_available))
        dialog.setPositiveButton(context.resources.getString(R.string.open_location_settings)) { _, _ ->
            val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(myIntent, REQUEST_CODE)
        }

        dialog.setNegativeButton(context.getString(R.string.Cancel)) { _, _ -> }
        dialog.show()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(this,
                    listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).toTypedArray(),
                    123);
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            R.id.action_settings -> {
                //TODO retrieve location
                return true
            }

            R.id.set_sweather -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
            }

            R.id.refresh -> {
                //TODO implement refresh action
            }
        }
        return super.onOptionsItemSelected(item)
    }
}