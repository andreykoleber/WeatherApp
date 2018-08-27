package com.koleber.android.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

public class SearchWeatherFragment extends Fragment {

    private LinearLayout llLabelContainer;
    private TextView txvCityName;
    private TextView txvDate;
    private TextView txvDayOfTheWeek;
    private TextView txvTemperature;
    private TextView txvCloudCover;
    private TextView txvHumidity;

    public static SearchWeatherFragment newInstance() {

        Bundle args = new Bundle();

        SearchWeatherFragment fragment = new SearchWeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_weather_fragment, container, false);

        llLabelContainer = view.findViewById(R.id.llLabelContainer);
        txvCityName = view.findViewById(R.id.txvCityName);
        txvDate = view.findViewById(R.id.txvDate);
        txvDayOfTheWeek = view.findViewById(R.id.txvDayOfTheWeek);
        txvTemperature = view.findViewById(R.id.txvTemperature);
        txvCloudCover = view.findViewById(R.id.txvCloudCover);
        txvHumidity = view.findViewById(R.id.txvHumidity);

        final EditText edtCityName = view.findViewById(R.id.edtCityName);

        Button btnSearchWeather = view.findViewById(R.id.btnSearchWeather);
        btnSearchWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                String cityName = edtCityName.getText().toString();
                if (cityName.equals("")) {
                    return;
                }
                SearchWeatherTask task = new SearchWeatherTask(SearchWeatherFragment.this);
                task.execute(getString(R.string.api_call, cityName));
            }
        });

        LinearLayout llContainer = view.findViewById(R.id.llContainer);
        llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });

        return view;
    }

    private void hideKeyboard() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                View currentFocus = getActivity().getCurrentFocus();
                if (currentFocus != null) {
                    inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                }
            }
        }
    }


    private static class SearchWeatherTask extends AsyncTask<String, Void, String> {

        private final WeakReference<SearchWeatherFragment> mActivityWeakReference;

        SearchWeatherTask(SearchWeatherFragment activity) {
            this.mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(String... urls) {

            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result.append(current);
                    data = reader.read();
                }

                return result.toString();

            } catch (IOException ignore) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if (result == null) {
                    return;
                }
                JSONObject jsonObject = new JSONObject(result);

                String cityName = jsonObject.getString("name");
                mActivityWeakReference.get().txvCityName.setText(cityName);

                long dateInMillis = jsonObject.getLong("dt");

                String date = getDateFromMillis(dateInMillis);
                mActivityWeakReference.get().txvDate.setText(date);

                String dayOfTheWeek = getDayOfTheWeekFromMillis(dateInMillis);
                mActivityWeakReference.get().txvDayOfTheWeek.setText(dayOfTheWeek);

                String main = jsonObject.getString("main");
                JSONObject jsonObjectMain = new JSONObject(main);
                double temperature = convertKelvinToCelsius(jsonObjectMain.getDouble("temp"));
                mActivityWeakReference.get().txvTemperature.setText(String.valueOf((double) Math.round(temperature * 100) / 100));

                double humidity = jsonObjectMain.getDouble("humidity");
                mActivityWeakReference.get().txvHumidity.setText(String.valueOf(humidity));

                String clouds = jsonObject.getString("clouds");
                JSONObject jsonObjectClouds = new JSONObject(clouds);
                double cloudCover = jsonObjectClouds.getDouble("all");
                mActivityWeakReference.get().txvCloudCover.setText(String.valueOf(cloudCover));

                mActivityWeakReference.get().llLabelContainer.setVisibility(View.VISIBLE);

                WeatherLab.get(mActivityWeakReference.get().getActivity())
                        .addWeather(new Weather(cityName, date, dayOfTheWeek, temperature, humidity, cloudCover));

            } catch (JSONException ignore) {
            }

        }

        private double convertKelvinToCelsius(double kelvin) {
            return kelvin - 273.15;
        }

        private String getDayOfTheWeekFromMillis(long millis) {
            Calendar cl = Calendar.getInstance();
            cl.setTimeInMillis(millis);
            int dayNumber = cl.get(Calendar.DAY_OF_WEEK);
            return mActivityWeakReference.get().getString(DayManager.getDayName(dayNumber));
        }

        private String getDateFromMillis(long millis) {
            return new java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new java.util.Date(millis * 1000));
        }
    }

}
