package net.rmoreno.weatherapp.presenters

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.rmoreno.weatherapp.WeatherInteractor
import net.rmoreno.weatherapp.models.CurrentWeather
import net.rmoreno.weatherapp.models.DailyWeather
import net.rmoreno.weatherapp.ui.MainActivity
import net.rmoreno.weatherapp.ui.WeatherView
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

class WeatherPresenterImpl(view: MainActivity, private var weatherInteractor: WeatherInteractor) : WeatherPresenter {

    var view: WeatherView? = null

    init {
        this.view = view
    }

    override fun resume() {
        this.view = view
    }

    override fun destroy() {
        view = null
    }

    override fun checkIfFirstTime() {
        if (isUsersFirstTime) view!!.goToIntroActivity()
    }

    private val isUsersFirstTime: Boolean
        get() = weatherInteractor.sweaterWeather == 0

    override fun getWeather() {
        val location = getCurrentLocation()!!

        weatherInteractor.getWeatherData(location.first, location.second)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            response ->
                                val currentWeather = response.currently
                                val dailyWeather = response.daily
                                val sweather = weatherInteractor.sweaterWeather
                                view!!.displayCurrentWeather(currentWeather)
                                view!!.displayDailyWeather(dailyWeather.data, sweather, response.timezone)
                        }
                )
    }

    private fun getCurrentLocation(): Pair<Double, Double>? {
        val location = weatherInteractor.getCurrentLocation()

        return if (location == null) {
            view!!.displayNetworkError()
            null
        } else {
            location
        }
    }

//    fun checkLocationEnabled(locationEnabled: Boolean) {
//        if (locationEnabled) {
//            view
//        }
//    }

//    private fun isNetworkAvailable(): Boolean {
//        var isAvailable = false
//        if (networkInfo != null && networkInfo.isConnected) {
//            isAvailable = true
//        }
//        return isAvailable
//    }

}
