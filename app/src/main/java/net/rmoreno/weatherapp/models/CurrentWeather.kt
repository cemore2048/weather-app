package net.rmoreno.weatherapp.models

import net.rmoreno.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*

class CurrentWeather {
    private var mTemp: Double = 0.toDouble()
    var time: Long = 0
    var icon: String? = null
    var summary: String? = null
    var timeZone: String? = null
    internal var mPrecipitation: Double = 0.toDouble()
    internal var mWindSpeed: Double = 0.toDouble()
    internal var mFeelsLike: Double = 0.toDouble()

    fun setPrecip(precipitation: Double) {
        mPrecipitation = precipitation
    }

    fun setFeels(feels: Double) {
        mFeelsLike = feels
    }

    fun setWind(wind: Double) {
        mWindSpeed = wind
    }

    fun setTemp(temp: Double) {
        mTemp = temp
    }

    val wind: Int
        get() = Math.round(mWindSpeed).toInt()

    val feels: Int
        get() = Math.round(mFeelsLike).toInt()

    val temp: Int
        get() = Math.round(mTemp).toInt()

    val precip: Int
        get() {
            val precipPercentage = mPrecipitation * 100
            val value = Math.round(precipPercentage).toInt()
            return value
        }


    //        else if (mIcon.equals("clear-night")) {
    //            iconId = R.drawable.clear_night;
    //        }
    //        else if (mIcon.equals("snow")) {
    //            iconId = R.drawable.snow;
    //        }
    //        else if (mIcon.equals("sleet")) {
    //            iconId = R.drawable.sleet;
    //        }
    //        else if (mIcon.equals("fog")) {
    //            iconId = R.drawable.fog;
    //        }

    val iconId: Int
        get() {
            var iconId = R.drawable.clear_day

            when (icon) {
                "clear-day" -> iconId = R.drawable.clear_day
                "rain" -> iconId = R.drawable.rain
                "wind" -> iconId = R.drawable.windy
                "cloudy" -> iconId = R.drawable.cloudy
                "partly-cloudy-day" -> iconId = R.drawable.partly_cloudy_day
                "partly-cloudy-night" -> iconId = R.drawable.cloudy_night
            }
            return iconId
        }

    val formatedTime: String
        get() {
            val formatter = SimpleDateFormat("h:mm a")
            formatter.timeZone = TimeZone.getTimeZone(timeZone)
            val dateTime = Date(time * 1000)
            val timeString = formatter.format(dateTime)

            return timeString
        }

}
