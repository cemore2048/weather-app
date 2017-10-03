package net.rmoreno.weatherapp

import android.util.Log
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
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
                Log.d("is breaking here?", "this breaks here")
                err.printStackTrace()
                em.onError(err)
            }
        }
    }
}
