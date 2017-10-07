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

class WeatherPresenterImpl(view: MainActivity, var weatherInteractor: WeatherInteractor) : WeatherPresenter {

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

    override fun pause() {

    }

    override val isUsersFirstTime: Boolean
        get() = if (weatherInteractor.sweaterWeather == 0) true else false

    override fun getCurrentWeather(lat: Double, lng: Double) {
        weatherInteractor.getWeatherData(lat, lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {response ->

                            try {
                                val jsonData = response.body()!!.string()
                                val currentWeather = getCurrentWeatherData(jsonData)

                                view!!.displayCurrentWeather(currentWeather)
                            } catch (e: JSONException) {
                                Log.d("Present JSON Exception", e.message)
                            } catch (e: IOException) {
                                Log.d("Present IO Exception", e.message)
                            }
                        },
                        {
                            error ->
                            error.printStackTrace()
                        }
                )
    }

    override fun getDailyWeather(lat: Double, lng: Double) {
        view!!.setLoading(true)
        weatherInteractor.getWeatherData(lat, lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {response ->
                            try {
                                val jsonData = response.body()!!.string()
                                val dailyWeather = getDailyWeatherData(jsonData)
                                val sweaterWeather = weatherInteractor.sweaterWeather
                                view!!.displayDailyWeather(dailyWeather, sweaterWeather)
                            } catch (e: JSONException) {
                                Log.d("Present JSON Exception", e.message)
                            } catch (e: IOException) {
                                Log.d("Present IO Exception", e.message)
                            }
                        },
                        {error ->
                            view!!.setLoading(false)
                            error.printStackTrace()
                        },
                        {
                            view!!.setLoading(false)
                        })
    }

    @Throws(JSONException::class)
    fun getDailyWeatherData(jsonData: String): ArrayList<DailyWeather> {
        val dailyWeatherList = ArrayList<DailyWeather>()

        val jsonObject = JSONObject(jsonData)
        val hourly = jsonObject.getJSONObject("daily")
        val data = hourly.getJSONArray("data")

        Log.d("DWPresent", data.getJSONObject(1).getDouble("precipProbability").toString())
        for (i in 0..data.length() - 1) {
            val dailyWeather = DailyWeather()

            dailyWeather.time = data.getJSONObject(i).getLong("time")
            dailyWeather.setMinTemp(data.getJSONObject(i).getDouble("temperatureMin"))
            dailyWeather.setMaxTemp(data.getJSONObject(i).getDouble("temperatureMax"))
            dailyWeather.timeZone = jsonObject.getString("timezone")
            dailyWeather.precip = data.getJSONObject(i).getDouble("precipProbability")
            dailyWeatherList.add(dailyWeather)
        }

        return dailyWeatherList
    }

    @Throws(JSONException::class)
    private fun getCurrentWeatherData(jsonData: String): CurrentWeather {

        val currentWeather = CurrentWeather()
        val jsonObject = JSONObject(jsonData)

        val currently = jsonObject.getJSONObject("currently")

        currentWeather.setTemp(currently.getDouble("temperature"))
        currentWeather.summary = currently.getString("summary")
        currentWeather.time = currently.getLong("time")
        currentWeather.timeZone = jsonObject.getString("timezone")
        currentWeather.icon = currently.getString("icon")
        currentWeather.setPrecip(currently.getInt("precipProbability").toDouble())
        currentWeather.setFeels(currently.getDouble("apparentTemperature"))
        currentWeather.setWind(currently.getDouble("windSpeed"))

        return currentWeather
    }

}
