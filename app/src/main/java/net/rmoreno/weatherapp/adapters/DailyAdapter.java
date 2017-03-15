package net.rmoreno.weatherapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.rmoreno.weatherapp.R;
import net.rmoreno.weatherapp.models.DailyWeather;

import java.util.ArrayList;

/**
 * Created by Rafa on 12/12/15.
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mMaxTemperature;
        public TextView mMinTemperature;
        public TextView mDay;
        public TextView mPrecip;

        public CardView mCard;

        public ViewHolder(View v){
            super(v);

            mMaxTemperature = (TextView) v.findViewById(R.id.max_temperature);
            mMinTemperature = (TextView) v.findViewById(R.id.min_temperature);
            mDay = (TextView) v.findViewById(R.id.time);
            mPrecip = (TextView) v.findViewById(R.id.precipitation);
            mCard = (CardView) v.findViewById(R.id.hourlyCard);
        }
    }

    ArrayList<DailyWeather> mDataset;
    Context mContext;
    int mSweaterTemp;

    public DailyAdapter(Context context, ArrayList<DailyWeather> hourly, int sweaterTemp){
        mContext = context;
        mDataset = hourly;
        mSweaterTemp = sweaterTemp;
    }

    @Override
    public DailyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_view, parent, false);

        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position == 0){
            holder.mDay.setText("Today");
        } else{
            holder.mDay.setText(mDataset.get(position).getFormatedTime());
        }
        holder.mMinTemperature.setText(String.valueOf(mDataset.get(position).getMinTemp())+ "° - ");
        holder.mMaxTemperature.setText(String.valueOf(mDataset.get(position).getMaxTemp()) + "°");

        //don't know why the rounding in the 'DailyWeather' class didn't work
        holder.mPrecip.setText(String.valueOf(((int) Math.round(mDataset.get(position).getPrecip()))) + "%");
        System.out.println("PRECIPITATION CHANCE AT " + position + " index " + mDataset.get(position).getPrecip());

        if(mDataset.get(position).getMinTemp() < mSweaterTemp){
            holder.mCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.light_blue));
        } else{

            if(position % 4 == 0){
                holder.mCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.teal));
            }
            else if(position % 4 == 1){
                holder.mCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.yellow));

            }
            else if(position % 4  == 2){
                holder.mCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.orange));
            }
            else if(position % 4 == 3){
                holder.mCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.red));
            }
        }


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
