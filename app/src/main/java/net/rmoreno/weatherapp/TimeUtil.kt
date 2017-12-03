package net.rmoreno.weatherapp

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    fun formatTime(time: Long, timeZone: String) :String {
        val formatter = SimpleDateFormat("h:mm a")
        formatter.timeZone = TimeZone.getTimeZone(timeZone)
        val dateTime = Date(time * 1000)

        return formatter.format(dateTime)
    }
}
