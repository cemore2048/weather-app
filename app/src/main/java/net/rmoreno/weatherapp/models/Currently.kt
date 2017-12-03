package net.rmoreno.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Currently(

        @SerializedName("time")
        @Expose
        val time: Long,

        @SerializedName("summary")
        @Expose
        val summary: String,

        @SerializedName("icon")
        @Expose
        val icon: String,

        @SerializedName("precipProbability")
        @Expose
        val precipProbability: Double,

        @SerializedName("precipType")
        @Expose
        val precipType: String,

        @SerializedName("temperature")
        @Expose
        val temperature: Double,

        @SerializedName("apparentTemperature")
        @Expose
        val apparentTemperature: Double,

        @SerializedName("windSpeed")
        @Expose
        val windSpeed: Double
)