package net.rmoreno.weatherapp

import android.util.Log
import io.reactivex.Observable
import net.rmoreno.weatherapp.models.ForecastResponse
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

open interface WeatherNetwork {

    @GET("forecast/")
    fun getForecast(@Query("latitude") lat: Double,
                    @Query("longitude") lng: Double): Observable<ForecastResponse>


    companion object Factory {
        private val interceptor: Interceptor = Interceptor { chain ->
            val request: Request = chain.request()
            val url: HttpUrl = request.url()

            val newUrl: HttpUrl = url.newBuilder()
                    .addQueryParameter("key", "5530508d3568e57848d53bf10cfade1f")
                    .build()

            val newRequest = request.newBuilder()
                    .url(newUrl)
                    .build()

            chain.proceed(newRequest)
        }

        fun create(): WeatherNetwork {
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            builder.interceptors().add(interceptor)

            val client: OkHttpClient = builder
                    .addNetworkInterceptor(LoggingInterceptor())
                    .build()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.darksky.net")
                    .client(client)
                    .build()

            return retrofit.create<WeatherNetwork>(WeatherNetwork::class.java)
        }
    }

    class LoggingInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            val t1 = System.nanoTime()
            Log.d("Sent Request", String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()))

            val response = chain.proceed(request)

            val t2 = System.nanoTime()
            Log.d("Received response", String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6, response.headers()))

            return response
        }
    }
}
