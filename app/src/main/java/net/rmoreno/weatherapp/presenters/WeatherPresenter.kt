package net.rmoreno.weatherapp.presenters

interface WeatherPresenter : Presenter {

    fun getDailyWeather()

    fun getCurrentWeather()

    fun checkIfFirstTime()
//    fun checkLocationEnabled(locationEnabled: Boolean)
}