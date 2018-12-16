package com.example.nishat.weatherforcaster.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.nishat.weatherforcaster.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Nishat on 3/25/2017.
 */

public class Day implements Parcelable {
    private long time;
    private String summary;
    private String timezone;
    private double temperature;
    private String icon;

    public int getIconId()
    {
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getTemperature() {
        return (int) Math.round((temperature - 32)/1.8);
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDayOfWeek(){
        SimpleDateFormat fomatter = new SimpleDateFormat("EEEE");
        fomatter.setTimeZone(TimeZone.getTimeZone(timezone));
        return fomatter.format(new Date(time * 1000));
    }

    public Day(){}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeLong(time);
        parcel.writeString(summary);
        parcel.writeString(timezone);
        parcel.writeDouble(temperature);
        parcel.writeString(icon);
    }

    private Day(Parcel in){
        time = in.readLong();
        summary = in.readString();
        timezone = in.readString();
        temperature = in.readDouble();
        icon = in.readString();
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel parcel) {
            return  new Day(parcel);
        }

        @Override
        public Day[] newArray(int i) {
            return new Day[i];
        }
    };
}
