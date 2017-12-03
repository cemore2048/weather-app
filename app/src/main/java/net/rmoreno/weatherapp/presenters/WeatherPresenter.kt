package net.rmoreno.weatherapp.presenters

interface WeatherPresenter : Presenter {

    fun getWeather()

    fun checkIfFirstTime()
//    fun checkLocationEnabled(locationEnabled: Boolean)
}