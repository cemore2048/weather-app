package net.rmoreno.weatherapp

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    fun formatTime(time: Long, timeZone: String): String {
        val formatter = SimpleDateFormat("h:mm a")
        formatter.timeZone = TimeZone.getTimeZone(timeZone)
        val dateTime = Date(time * 1000)

        return formatter.format(dateTime)
    }

    fun formatDay(time: Long, timezone: String): String {
        val formatter = SimpleDateFormat("EEE")
        formatter.timeZone = TimeZone.getTimeZone(timezone)
        return formatter.format(Date(time * 1000))
    }

}
