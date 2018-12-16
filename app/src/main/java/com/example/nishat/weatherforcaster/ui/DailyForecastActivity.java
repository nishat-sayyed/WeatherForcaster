package com.example.nishat.weatherforcaster.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.nishat.weatherforcaster.R;
import com.example.nishat.weatherforcaster.adapters.DayAdapter;
import com.example.nishat.weatherforcaster.model.Day;

import java.util.Arrays;

public class DailyForecastActivity extends ListActivity {

    private Day[] days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        days = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        DayAdapter adapter = new DayAdapter(this, days);

        setListAdapter(adapter);
    }
}
