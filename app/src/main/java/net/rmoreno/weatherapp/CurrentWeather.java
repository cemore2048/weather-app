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
    String mIcon;
    String mSummary;
    String mTimeZone;
    int mPrecipitation;
    double mWindSpeed;
    double mFeelsLike;

    public void setPrecip(int precipitation){
        mPrecipitation = precipitation;
    }

    public void setFeels(double feels){
        mFeelsLike = feels;
    }

    public void setWind(double wind){
        mWindSpeed = wind;
    }

    public void setTemp(double temp){
        mTemp = temp;
    }

    public void setIcon(String icon){
        mIcon = icon;
    }

    public void setTimeZone(String timeZone){
        mTimeZone = timeZone;
    }

    public int getWind(){
        return (int) Math.round(mWindSpeed);
    }

    public int getFeels(){
        return (int) Math.round(mFeelsLike);
    }

    public int getTemp(){
        return (int) Math.round(mTemp);
    }

    public long  getTime(){
        return mTime;
    }

    public String getIcon(){return mIcon;}

    public int getPrecip(){
        double precipPercentage = mPrecipitation * 100;
        return (int) Math.round(precipPercentage);
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
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);
        String timeString = formatter.format(dateTime);

        return timeString;
    }

}
