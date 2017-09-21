package net.rmoreno.weatherapp.repositories

import android.content.SharedPreferences
import android.util.Log

import com.squareup.okhttp.Callback
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response

import net.rmoreno.weatherapp.WeatherNetwork

import java.io.IOException

class WeatherRepository(internal var preferences: SharedPreferences) {

    internal var network = WeatherNetwork()

    fun getWeather(lat: Double, lng: Double, callback: Callback) {
        network.getWeather(lat, lng, object : Callback {
            override fun onFailure(request: Request, e: IOException) {
                callback.onFailure(request, e)
            }

            @Throws(IOException::class)
            override fun onResponse(response: Response) {
                try {
                    if (response.isSuccessful) {
                        callback.onResponse(response)
                    }
                } catch (e: IOException) {
                    Log.d("DWPresent" + " IOEXCEPTION", e.message)
                }

            }
        })
    }

    val sweaterTemp: Int
        get() = preferences.getInt("sweater", 0)

    fun updateSweather(temperature: Int) {
        val edit = preferences.edit()

        edit.remove("sweater")
        edit.putInt("sweater", temperature)
        edit.commit()
        preferences.getInt("sweater", 0)
    }
}
