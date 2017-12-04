package net.rmoreno.weatherapp.presenters

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.rmoreno.weatherapp.WeatherInteractor
import net.rmoreno.weatherapp.models.ForecastResponse
import net.rmoreno.weatherapp.ui.WeatherView

class WeatherPresenter(private var weatherInteractor: WeatherInteractor): BasePresenter<ForecastResponse, WeatherView>() {

    override fun updateView() {
        val sweather = weatherInteractor.sweaterWeather
        if (model != null) {
            view()!!.displayCurrentWeather(model!!.currently)
            view()!!.displayDailyWeather(model!!.daily.data, sweather, model!!.timezone)
        }
    }

    fun checkIfFirstTime() {
        if (isUsersFirstTime) view()!!.goToIntroActivity()
    }

    private val isUsersFirstTime: Boolean
        get() = weatherInteractor.sweaterWeather == 0

    fun getWeather() {
        val location = getCurrentLocation()!!
        weatherInteractor.startLocationService()
        weatherInteractor.getWeatherData(location.first, location.second)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            setM(response)
                        }
                )
    }

    override fun bindView(view: WeatherView) {
        super.bindView(view)

        if (model == null) {
            getWeather()
        }
    }

    private fun getCurrentLocation(): Pair<Double, Double>? {
        val location = weatherInteractor.getCurrentLocation()

        return if (location == null) {
            view()!!.displayNetworkError()
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
