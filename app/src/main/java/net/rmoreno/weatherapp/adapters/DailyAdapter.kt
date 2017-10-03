package net.rmoreno.weatherapp.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import net.rmoreno.weatherapp.R
import net.rmoreno.weatherapp.models.DailyWeather

import java.util.ArrayList

class DailyAdapter(internal var mContext: Context, internal var mDataset: ArrayList<DailyWeather>, internal var mSweaterTemp: Int) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var mMaxTemperature: TextView
        var mMinTemperature: TextView
        var mDay: TextView
        var mPrecip: TextView

        var mCard: CardView

        init {
            mMaxTemperature = v.findViewById(R.id.max_temperature) as TextView
            mMinTemperature = v.findViewById(R.id.min_temperature) as TextView
            mDay = v.findViewById(R.id.time) as TextView
            mPrecip = v.findViewById(R.id.precipitation) as TextView
            mCard = v.findViewById(R.id.hourlyCard) as CardView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.daily_view, parent, false)

        val holder = ViewHolder(v)

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position == 0) {
            holder.mDay.text = "Today"
        } else {
            holder.mDay.text = mDataset[position].formatedTime
        }
        holder.mMinTemperature.text = mDataset[position].minTemp.toString() + "° - "
        holder.mMaxTemperature.text = mDataset[position].maxTemp.toString() + "°"

        //don't know why the rounding in the 'DailyWeather' class didn't work
        holder.mPrecip.text = Math.round(mDataset[position].precip).toInt().toString() + "%"
        println("PRECIPITATION CHANCE AT " + position + " index " + mDataset[position].precip)

        if (mDataset[position].minTemp < mSweaterTemp) {
            holder.mCard.setCardBackgroundColor(mContext.resources.getColor(R.color.light_blue))
        } else {

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


    }

    override fun getItemCount(): Int {
        return mDataset.size
    }


}
