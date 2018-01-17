package net.rmoreno.weatherapp.models

import java.text.SimpleDateFormat
import java.util.*

class HourlyWeather {
    var time: Long = 0
    private var mTemp: Double = 0.toDouble()
    var timeZone: String? = null

    fun setTemp(temp: Double) {
        mTemp = temp
    }

    val temp: Int
        get() = Math.round(mTemp).toInt()

    val formattedTime: String
        get() {
            val formatter = SimpleDateFormat("h:mm a")
            formatter.timeZone = TimeZone.getTimeZone(timeZone)
            val dateTime = Date(time * 1000)

            return formatter.format(dateTime)
        }
}
