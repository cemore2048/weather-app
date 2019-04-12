package net.rmoreno.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import net.rmoreno.weatherapp.R
import net.rmoreno.weatherapp.models.HourlyWeather
import java.util.*

class HourlyAdapter(private var dataset: ArrayList<HourlyWeather>) : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var temperature: TextView = v.findViewById(R.id.temperature) as TextView
        var time: TextView = v.findViewById(R.id.time) as TextView

        var card: CardView = v.findViewById(R.id.hourlyCard) as CardView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.hourly_view, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.temperature.text = dataset[position].temp.toString()
        holder.time.text = dataset[position].formattedTime

        when {
            position % 4 == 0 -> holder.card.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.teal))
            position % 4 == 1 -> holder.card.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.yellow))
            position % 4 == 2 -> holder.card.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.orange))
            position % 4 == 3 -> holder.card.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.red))
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
