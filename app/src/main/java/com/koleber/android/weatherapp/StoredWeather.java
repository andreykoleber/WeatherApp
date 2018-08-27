package com.koleber.android.weatherapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StoredWeather extends Fragment {

    private RecyclerView mRecyclerView;
    private WeatherAdapter mAdapter;

    public static StoredWeather newInstance() {

        Bundle args = new Bundle();

        StoredWeather fragment = new StoredWeather();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stored_weather_list, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUi();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            updateUi();
        }
    }

    private void updateUi() {
        WeatherLab weatherLab = WeatherLab.get(getActivity());
        List<Weather> weatherList = weatherLab.getWeather();
        if (mAdapter == null) {
            mAdapter = new WeatherAdapter(weatherList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setWeatherList(weatherList);
            mAdapter.notifyDataSetChanged();
        }
    }



    private class WeatherAdapter extends RecyclerView.Adapter<WeatherHolder> {

        private List<Weather> mWeatherList;


        void setWeatherList(List<Weather> weatherList) {
            this.mWeatherList = weatherList;
        }

        WeatherAdapter(List<Weather> weatherList) {
            mWeatherList = weatherList;
        }

        @NonNull
        @Override
        public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new WeatherHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull WeatherHolder holder, int position) {
            Weather weather = mWeatherList.get(position);
            holder.bind(weather);
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return mWeatherList.size();
        }
    }

    private class WeatherHolder extends RecyclerView.ViewHolder {

        private TextView txvCityName;
        private TextView txvDate;
        private TextView txvDayOfTheWeek;
        private TextView txvTemperature;
        private TextView txvCloudCover;
        private TextView txvHumidity;

        WeatherHolder(LayoutInflater layoutInflater, ViewGroup parent) {
            super(layoutInflater.inflate(R.layout.list_item_weather, parent, false));
            txvCityName = itemView.findViewById(R.id.txvCityName);
            txvDate = itemView.findViewById(R.id.txvDate);
            txvDayOfTheWeek = itemView.findViewById(R.id.txvDayOfTheWeek);
            txvTemperature = itemView.findViewById(R.id.txvTemperature);
            txvCloudCover = itemView.findViewById(R.id.txvCloudCover);
            txvHumidity = itemView.findViewById(R.id.txvHumidity);
        }

        public void bind(Weather weather) {
            txvCityName.setText(weather.getCityName());
            txvDate.setText(weather.getDate());
            txvDayOfTheWeek.setText(weather.getDayOfTheWeek());
            txvTemperature.setText(String.valueOf((double) Math.round(weather.getTemperature() * 100) / 100));
            txvCloudCover.setText(String.valueOf(weather.getCloudCover()));
            txvHumidity.setText(String.valueOf(weather.getHumidity()));
        }
    }
}
