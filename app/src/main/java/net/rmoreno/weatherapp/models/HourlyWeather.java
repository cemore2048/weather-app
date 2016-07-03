package net.rmoreno.weatherapp.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Rafa on 12/10/15.
 */
public class HourlyWeather {
    private long mTime;
    private double mTemp;
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

    public void setTimeZone(String timeZone){
        mTimeZone = timeZone;
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
