package net.rmoreno.weatherapp.presenters

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.rmoreno.weatherapp.WeatherInteractor
import net.rmoreno.weatherapp.models.ForecastResponse
import net.rmoreno.weatherapp.ui.WeatherView

class WeatherPresenter(private var weatherInteractor: WeatherInteractor) : BasePresenter<ForecastResponse, WeatherView>() {

    override fun updateView() {
        val sweather = weatherInteractor.sweaterWeather
        if (model != null) {
            view()!!.displayCurrentWeather(model!!.currently, model!!.timezone)
            view()!!.displayDailyWeather(model!!.daily.data, sweather, model!!.timezone)
        }
    }

    private val isUsersFirstTime: Boolean
        get() = weatherInteractor.sweaterWeather == 0

    private fun getWeather() {
        weatherInteractor.getCurrentLocation().addOnSuccessListener { location ->
            if (location != null) {
                weatherInteractor.getWeatherData(location.latitude, location.longitude)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { response ->
                            setM(response)
                        }
            } else {
                view()!!.displayNetworkError()
            }
        }
    }

    override fun bindView(view: WeatherView) {
        super.bindView(view)
        if (isUsersFirstTime) {
            view()!!.goToIntroActivity()
        } else if (model == null) {
            getWeather()
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
