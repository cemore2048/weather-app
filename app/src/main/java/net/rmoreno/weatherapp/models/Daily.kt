package net.rmoreno.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Daily(

        @SerializedName("summary")
        @Expose
        val summary: String,

        @SerializedName("data")
        @Expose
        val data: List<DailyDetail>
)
