package com.example.nishat.weatherforcaster.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nishat.weatherforcaster.R;
import com.example.nishat.weatherforcaster.model.Day;

/**
 * Created by Nishat on 3/26/2017.
 */

public class DayAdapter extends BaseAdapter {

    private Context context;
    private Day[] days;

    public DayAdapter(Context context, Day[] days)
    {
        this.context = context;
        this.days = days;
    }

    @Override
    public int getCount() {
        return days.length;
    }

    @Override
    public Object getItem(int i) {
        return days[i];
    }

    @Override
    public long getItemId(int i) {
        return 0; // Ignore this method
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();

            holder.iconImageView = (ImageView) view.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) view.findViewById(R.id.temperatureLabel);
            holder.dayName = (TextView) view.findViewById(R.id.dayName);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        Day day = days[i];

        holder.iconImageView.setImageResource(day.getIconId());
        Log.d("Icon Id", day.getIconId()+"");
        holder.temperatureLabel.setText(day.getTemperature() + "");
        holder.dayName.setText(day.getDayOfWeek());

        return view;
    }

    private static class ViewHolder{
        ImageView iconImageView;
        TextView temperatureLabel;
        TextView dayName;
    }
}
