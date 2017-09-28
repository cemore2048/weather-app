package net.rmoreno.weatherapp

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import io.reactivex.Observable
import java.io.IOException

class WeatherNetwork {

    fun getWeather(lat: Double, lng: Double): Observable<Response> {
        val URL = "https://api.forecast.io/forecast/5530508d3568e57848d53bf10cfade1f/$lat,$lng"
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(URL)
                .build()

        return Observable.create { em ->
            try {
                val response = client.newCall(request).execute()
                em.onNext(response)
                em.onComplete()
            } catch (err: IOException) {
                err.printStackTrace()
                em.onError(err)
            }
        }
    }
}
