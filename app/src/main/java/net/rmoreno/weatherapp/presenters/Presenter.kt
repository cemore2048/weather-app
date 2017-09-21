package net.rmoreno.weatherapp.presenters

import com.squareup.okhttp.Request
import com.squareup.okhttp.Response

import java.io.IOException

interface Presenter {

    fun resume()

    fun pause()

    fun destroy()

    interface WeatherCallback {
        fun onWeatherRetrieved(response: Response)

        fun onFailure(request: Request, e: IOException)
    }
}
