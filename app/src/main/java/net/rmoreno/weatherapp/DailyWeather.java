package net.rmoreno.weatherapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Rafa on 12/12/15.
 */
public class DailyWeather {

    double mMinTemp;
    double mMaxTemp;
    long mTime;
    String mIcon;
    String mSummary;
    String mTimeZone;
    int mPrecipitation;


    public void setPrecip(int precipitation){
        mPrecipitation = precipitation;
    }

    public void setMinTemp(double minTemp){
        mMinTemp = minTemp;
    }

    public void setMaxTemp(double maxTemp) {mMaxTemp = maxTemp;}

    public void setTemp(double maxTemp){
        mMaxTemp = maxTemp;
    }

    public void setIcon(String icon){
        mIcon = icon;
    }

    public void setTimeZone(String timeZone){
        mTimeZone = timeZone;
    }

    public int getMaxTemp(){
        return (int) Math.round(mMaxTemp);
    }

    public int getMinTemp() { return (int) Math.round(mMinTemp);}

    public long  getTime(){
        return mTime;
    }

    public String getIcon(){return mIcon;}

    public int getPrecip(){
        return mPrecipitation;
    }

    //TODO: actually implement this

    public int getIconId(){
        int iconId = R.drawable.clear;

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
        return iconId;
    }

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
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);
        String timeString = formatter.format(dateTime);

        return timeString;
    }
}
