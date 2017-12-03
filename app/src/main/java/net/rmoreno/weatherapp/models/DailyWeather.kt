package net.rmoreno.weatherapp.models

import net.rmoreno.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*

class DailyWeather {

    internal var mMinTemp: Double = 0.toDouble()
    internal var mMaxTemp: Double = 0.toDouble()
    var time: Long = 0
    var icon: String? = null
    var summary: String? = null
    var timeZone: String? = null
    internal var mPrecipitation: Double = 0.toDouble()

    fun setMinTemp(minTemp: Double) {
        mMinTemp = minTemp
    }

    fun setMaxTemp(maxTemp: Double) {
        mMaxTemp = maxTemp
    }

    fun setTemp(maxTemp: Double) {
        mMaxTemp = maxTemp
    }

    val maxTemp: Int
        get() = Math.round(mMaxTemp).toInt()

    val minTemp: Int
        get() = Math.round(mMinTemp).toInt()

    var precip: Double
        get() {
            val precipPercentage = mPrecipitation * 100

            val value = Math.round(precipPercentage).toInt()
            println("ROUNDED PERCENTAGE " + value)
            return value.toDouble()
        }
        set(precipitation) {
            mPrecipitation = precipitation
        }

    //TODO: actually implement this

    //
    //        if (mIcon.equals("clear-day")) {
    //            iconId = R.drawable.clear_day;
    //        }
    //        else if (mIcon.equals("clear-night")) {
    //            iconId = R.drawable.clear_night;
    //        }
    //        else if (mIcon.equals("rain")) {
    //            iconId = R.drawable.rain;
    //        }
    //        else if (mIcon.equals("snow")) {
    //            iconId = R.drawable.snow;
    //        }
    //        else if (mIcon.equals("sleet")) {
    //            iconId = R.drawable.sleet;
    //        }
    //        else if (mIcon.equals("wind")) {
    //            iconId = R.drawable.wind;
    //        }
    //        else if (mIcon.equals("fog")) {
    //            iconId = R.drawable.fog;
    //        }
    //        else if (mIcon.equals("cloudy")) {
    //            iconId = R.drawable.cloudy;
    //        }
    //        else if (mIcon.equals("partly-cloudy-day")) {
    //            iconId = R.drawable.partly_cloudy;
    //        }
    //        else if (mIcon.equals("partly-cloudy-night")) {
    //            iconId = R.drawable.cloudy_night;
    //        }

    val iconId: Int
        get() {
            val iconId = R.drawable.clear_day
            return iconId
        }

    val formatedTime: String
        get() {
            val formatter = SimpleDateFormat("EEEE")
            formatter.timeZone = TimeZone.getTimeZone(timeZone)
            val dateTime = Date(time * 1000)
            val timeString = formatter.format(dateTime)

            return timeString
        }
}
