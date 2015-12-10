package net.rmoreno.weatherapp;

/**
 * Created by Rafa on 12/9/15.
 */
public class CurrentWeather {
    String mTemp;
    String mTime;
    String mIcon;
    String mSummary;




    public void setTemp(String temp){
        mTemp = temp;
    }

    public String getTemp(){
        return mTemp;
    }

    public void setTime(String time){
        mTime = time;
    }

    public String  getTime(){
        return mTime;
    }

    //TODO: actually implement this
    public void setIcon(String icon){
        mIcon = icon;
    }

    public String getIcon(){

        return mIcon;
    }

    public void setSummary(String summary){
        mSummary = summary;
    }

    public String getSummary(){
        return mSummary;
    }
}
