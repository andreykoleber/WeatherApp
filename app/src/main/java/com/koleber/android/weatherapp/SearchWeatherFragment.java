package com.koleber.android.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchWeatherFragment extends Fragment {

    private static final String API_CALL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String API_KEY = "&appid=b5cd0fd79808f0da4fde8b0ce9eeae7d";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.search_weather_fragment, container, false);


        final EditText edtCityName = view.findViewById(R.id.edtCityName);

        Button btnSearchWeather = view.findViewById(R.id.btnSearchWeather);
        btnSearchWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName = edtCityName.getText().toString();
                DownloadTask task = new DownloadTask();

                task.execute(getString(R.string.api_call, cityName) + getString(R.string.api_key));
            }
        });

        return view;
    }


    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;
            } catch (MalformedURLException ignore) {
            } catch (IOException ignore) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("hello", s);
        }
    }

}
