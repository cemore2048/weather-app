package net.rmoreno.weatherapp.presenters

interface WeatherPresenter : Presenter {

    fun getDailyWeather(lat: Double, lng: Double)

    fun getCurrentWeather(lat: Double, lng: Double)

    val isUsersFirstTime: Boolean
}