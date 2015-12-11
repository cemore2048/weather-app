package net.rmoreno.weatherapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rafa on 12/10/15.
 */
public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTemperature;
        public TextView mTime;

        public ViewHolder(View v){
            super(v);

            mTemperature = (TextView) v.findViewById(R.id.temperature);
            mTime = (TextView) v.findViewById(R.id.time);
        }
    }

    ArrayList<HourlyWeather> mDataset;

    public HourlyAdapter(ArrayList<HourlyWeather> hourly){
        mDataset = hourly;
    }

    @Override
    public HourlyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_view, parent, false);

        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTemperature.setText(String.valueOf(mDataset.get(position).getTemp()));
        holder.mTime.setText(mDataset.get(position).getFormatedTime());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
