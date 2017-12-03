package net.rmoreno.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DailyDetail(

        @SerializedName("time")
        @Expose
        val time: Long,

        @SerializedName("summary")
        @Expose
        val summary: String,

        @SerializedName("precipProbability")
        @Expose
        val precipProbability: Double,

        @SerializedName("temperatureHigh")
        @Expose
        val tempHigh: Double,

        @SerializedName("temperatureLow")
        @Expose
        val tempLow: Double
)
