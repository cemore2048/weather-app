package net.rmoreno.weatherapp.presenters

import okhttp3.Request
import okhttp3.Response
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
