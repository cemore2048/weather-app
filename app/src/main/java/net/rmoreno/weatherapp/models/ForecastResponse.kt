package net.rmoreno.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastResponse(

        @SerializedName("latitude")
        @Expose
        val latitude: Double,

        @SerializedName("longitude")
        @Expose
        val longitude: Double,

        @SerializedName("timzone")
        @Expose
        val timezone: String,

        @SerializedName("currently")
        @Expose
        val currently: Currently,

        @SerializedName("daily")
        @Expose
        val daily: Daily
)
