package net.rmoreno.weatherapp

object IconUtil {

    fun getIcon(icon: String): Int {
        var iconId: Int = R.drawable.clear_day

        when (icon) {
            "clear-day" -> iconId = R.drawable.clear_day
//        else if (icon.equals("clear-night")) {
//            iconId = R.drawable.clear_night
//        }
            "rain" -> iconId = R.drawable.rain
//        else if (icon.equals("snow")) {
//            iconId = R.drawable.snow
//        }
//        else if (icon.equals("sleet")) {
//            iconId = R.drawable.sleet
//        }
            "wind" -> iconId = R.drawable.windy
//        else if (icon.equals("fog")) {
//            iconId = R.drawable.fog
//        }
            "cloudy" -> iconId = R.drawable.cloudy
            "partly-cloudy-day" -> iconId = R.drawable.partly_cloudy_day
            "partly-cloudy-night" -> iconId = R.drawable.cloudy_night
        }
        return iconId
    }
}