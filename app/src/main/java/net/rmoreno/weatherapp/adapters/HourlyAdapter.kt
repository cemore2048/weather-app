package net.rmoreno.weatherapp.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.rmoreno.weatherapp.R
import net.rmoreno.weatherapp.models.HourlyWeather
import java.util.*

class HourlyAdapter(internal var mContext: Context, internal var mDataset: ArrayList<HourlyWeather>) : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var mTemperature: TextView
        var mTime: TextView

        var mCard: CardView

        init {

            mTemperature = v.findViewById(R.id.temperature) as TextView
            mTime = v.findViewById(R.id.time) as TextView
            mCard = v.findViewById(R.id.hourlyCard) as CardView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.hourly_view, parent, false)

        val holder = ViewHolder(v)

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.mTemperature.text = mDataset[position].temp.toString()
        holder.mTime.text = mDataset[position].formatedTime

        if (position % 4 == 0) {
            holder.mCard.setCardBackgroundColor(mContext.resources.getColor(R.color.teal))
        } else if (position % 4 == 1) {
            holder.mCard.setCardBackgroundColor(mContext.resources.getColor(R.color.yellow))

        } else if (position % 4 == 2) {
            holder.mCard.setCardBackgroundColor(mContext.resources.getColor(R.color.orange))
        } else if (position % 4 == 3) {
            holder.mCard.setCardBackgroundColor(mContext.resources.getColor(R.color.red))
        }
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }
}
