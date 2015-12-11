package net.rmoreno.weatherapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Rafa on 12/9/15.
 */
public class CurrentWeather {
    double mTemp;
    long mTime;
    int mIcon;
    String mSummary;
    String mTimeZone;




    public void setTemp(double temp){
        mTemp = temp;
    }

    public int getTemp(){
        return (int) Math.round(mTemp);
    }

    public long  getTime(){
        return mTime;
    }

    //TODO: actually implement this
    public void setIcon(int icon){
        mIcon = icon;
    }

    public void setTimeZone(String timeZone){
        mTimeZone = timeZone;
    }

//    public int getIcon(){
//        int iconId = R.drawable.clear_day;
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
//        return iconId;
//    }

    public void setSummary(String summary){
        mSummary = summary;
    }

    public String getSummary(){
        return mSummary;
    }

    public String getTimeZone(){
        return mTimeZone;
    }
    public void setTime(long time){
        mTime = time;
    }
    public String getFormatedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);
        String timeString = formatter.format(dateTime);

        return timeString;
    }

}
