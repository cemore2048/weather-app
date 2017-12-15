package net.rmoreno.weatherapp.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import net.rmoreno.weatherapp.R
import net.rmoreno.weatherapp.TimeUtil
import net.rmoreno.weatherapp.models.DailyDetail

class DailyAdapter(private var context: Context, private var dailyList: List<DailyDetail>, private var sweaterWeather: Int, private var timezone: String) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var maxTemperature: TextView
        var minTemperature: TextView
        var day: TextView
        var precipitation: TextView

        var card: CardView

        init {
            maxTemperature = v.findViewById(R.id.max_temperature) as TextView
            minTemperature = v.findViewById(R.id.min_temperature) as TextView
            day = v.findViewById(R.id.time) as TextView
            precipitation = v.findViewById(R.id.precipitation) as TextView
            card = v.findViewById(R.id.hourlyCard) as CardView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.daily_view, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.day.text = "Today"
        } else {
            holder.day.text = TimeUtil.formatTime(dailyList[position].time, timezone)
        }
        holder.minTemperature.text = Math.round(dailyList[position].tempLow).toString() + "° - "
        holder.maxTemperature.text = Math.round(dailyList[position].tempHigh).toString() + "°"

        holder.precipitation.text = Math.round(dailyList[position].precipProbability).toInt().toString() + "%"

        if (dailyList[position].tempLow < sweaterWeather) {
            holder.card.setCardBackgroundColor(context.resources.getColor(R.color.light_blue))
        } else {

            when {
                position % 4 == 0 -> holder.card.setCardBackgroundColor(context.resources.getColor(R.color.teal))
                position % 4 == 1 -> holder.card.setCardBackgroundColor(context.resources.getColor(R.color.yellow))
                position % 4 == 2 -> holder.card.setCardBackgroundColor(context.resources.getColor(R.color.orange))
                position % 4 == 3 -> holder.card.setCardBackgroundColor(context.resources.getColor(R.color.red))
            }
        }
    }

    override fun getItemCount(): Int {
        return dailyList.size
    }
}
