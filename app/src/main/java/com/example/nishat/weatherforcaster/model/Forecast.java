package com.example.nishat.weatherforcaster.model;

import com.example.nishat.weatherforcaster.R;

/**
 * Created by Nishat on 3/25/2017.
 */

public class Forecast {
    private Current currentWeather;
    private Hour[] hours;
    private Day[] days;

    public Current getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(Current currentWeather) {
        this.currentWeather = currentWeather;
    }

    public Hour[] getHours() {
        return hours;
    }

    public void setHours(Hour[] hours) {
        this.hours = hours;
    }

    public Day[] getDays() {
        return days;
    }

    public void setDays(Day[] days) {
        this.days = days;
    }

    public static int getIconId(String icon){
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
}
