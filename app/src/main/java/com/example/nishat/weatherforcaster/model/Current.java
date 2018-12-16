package com.example.nishat.weatherforcaster.model;

import com.example.nishat.weatherforcaster.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Nishat on 3/18/2017.
 */

public class Current {
    private String icon;
    private long time;
    private double temperature;
    private double humidity;
    private double precipChance;
    private String summary;
    private String timeZone;

    public String getTimeZone() {
        return timeZone;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIconId(){

        int iconId = R.drawable.clear_night;
        if (icon.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (icon.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (icon.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (icon.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (icon.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (icon.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (icon.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (icon.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (icon.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (icon.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }
        return iconId;
    }
    public void setIconId(String icon){

    }

    public long getTime() {
        return time;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String formatTime(){
        SimpleDateFormat format = new SimpleDateFormat("h:mm a");
        format.setTimeZone(TimeZone.getTimeZone(getTimeZone()));

        return format.format(new Date(getTime() * 1000));
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getTemperature() {

        return (int) Math.round((temperature - 32)/1.8);
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public int getPrecipChance() {
        return (int) Math.round(precipChance * 100);
    }

    public void setPrecipChance(double precipChance) {
        this.precipChance = precipChance;
    }
}
