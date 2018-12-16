package com.example.nishat.weatherforcaster.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishat.weatherforcaster.R;
import com.example.nishat.weatherforcaster.model.Current;
import com.example.nishat.weatherforcaster.model.Day;
import com.example.nishat.weatherforcaster.model.Forecast;
import com.example.nishat.weatherforcaster.model.Hour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    private Forecast forecast;

    private TextView temperature;
    private TextView humidity;
    private TextView precipChance;
    private TextView summary;
    private TextView time;
    private ImageView icon;
    private ImageView refresh;
    private ProgressBar progressBar;
    private Button dailyButton;
    private Button hourlyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperature = (TextView) findViewById(R.id.temperature);
        humidity = (TextView) findViewById(R.id.humidityValue);
        precipChance = (TextView) findViewById(R.id.precipValue);
        time = (TextView) findViewById(R.id.time);
        summary = (TextView) findViewById(R.id.summary);
        icon = (ImageView) findViewById(R.id.icon);
        refresh = (ImageView) findViewById(R.id.refresh);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        hourlyButton = (Button) findViewById(R.id.hourlyButton);
        dailyButton = (Button) findViewById(R.id.dailyButton);

        progressBar.setVisibility(View.INVISIBLE);

        final double latitude = 19.0760;
        final double longitude = 72.8777;

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude, longitude);
            }
        });

        getForecast(latitude, longitude);

        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DailyForecastActivity.class);
                intent.putExtra(DAILY_FORECAST, forecast.getDays());
                startActivity(intent);
            }
        });
    }

    private void getForecast(double latitude, double longitude) {
        String apiKey = "21f4dd31f9a207960df9e720fb8820aa";

        // The url for forecasting.
        String forecastUrl = "https://api.darksky.net/forecast/" + apiKey + "/" + latitude + "," + longitude;


        if (isNetworkAvailable()) {
            toggleRefresh();

            // The okHttpClient is a third party api.
            OkHttpClient okHttpClient = new OkHttpClient();

            // Build the request with the forecastUrl.
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();

            // Call the request using the "Call" class in OkHttp in a asychronous way.
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUser();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            forecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUser();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, getString(R.string.exception_caught), e);
                    } catch (JSONException e) {
                        Log.e(TAG, getString(R.string.exception_caught), e);
                    }
                }
            });
        } else {
            Toast.makeText(this, R.string.network_unavailable, Toast.LENGTH_LONG).show();
        }
    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException
    {
        Forecast forecast = new Forecast();

        forecast.setCurrentWeather(getCurrentWeatherDetails(jsonData));
        forecast.setDays(getDailyDetails(jsonData));
        forecast.setHours(getHourlyDetails(jsonData));

        return forecast;
    }

    private Hour[] getHourlyDetails(String jsonData) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject hourly = jsonObject.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];

        for(int i = 0; i < data.length(); i++)
        {
            Hour hour = new Hour();
            JSONObject jsonHour = data.getJSONObject(i);

            hour.setTime(jsonHour.getLong("time"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setSummary(jsonHour.getString("summary"));
            hour.setTimezone(jsonObject.getString("timezone"));

            hours[i] = hour;
        }

        return hours;
    }

    private Day[] getDailyDetails(String jsonData) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject daily = jsonObject.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for(int i = 0; i < data.length(); i++)
        {
            Day day = new Day();
            JSONObject jsonDay = data.getJSONObject(i);

            day.setTime(jsonDay.getLong("time"));
            day.setTimezone(jsonObject.getString("timezone"));
            day.setSummary(jsonDay.getString("summary"));
            day.setTemperature(jsonDay.getDouble("temperatureMax"));
            day.setIcon(jsonDay.getString("icon"));

            days[i] = day;
        }

        return days;
    }

    private void toggleRefresh() {
        if(progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.INVISIBLE);
        } else{
            progressBar.setVisibility(View.INVISIBLE);
            refresh.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {

        Current current = forecast.getCurrentWeather();

        temperature.setText(current.getTemperature()+"");
        time.setText("At "+ current.formatTime()+" it will be");
        humidity.setText(current.getHumidity()+"");
        precipChance.setText(current.getPrecipChance()+"%");
        summary.setText('"'+ current.getSummary()+'"');

        Drawable drawable = getResources().getDrawable(current.getIconId());
        icon.setImageDrawable(drawable);
    }

    public Current getCurrentWeatherDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();
        current.setHumidity(currently.getDouble("humidity"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTime(currently.getLong("time"));
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setIcon(currently.getString("icon"));
        current.setSummary(currently.getString("summary"));
        current.setTimeZone(forecast.getString("timezone"));

        Log.d(TAG, current.formatTime());
        return current;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void alertUser() {
        AlertFragment dialog = new AlertFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }


}
