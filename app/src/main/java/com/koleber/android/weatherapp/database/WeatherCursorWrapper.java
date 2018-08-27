package com.koleber.android.weatherapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.koleber.android.weatherapp.Weather;

import static com.koleber.android.weatherapp.database.WeatherDbSchema.*;

public class WeatherCursorWrapper extends CursorWrapper {

    public WeatherCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Weather getWeather() {
        String cityName = getString(getColumnIndex(WeatherTable.Cols.CITY_NAME));
        String date = getString(getColumnIndex(WeatherTable.Cols.DATE));
        String dayOfTheWeek = getString(getColumnIndex(WeatherTable.Cols.DAY_OF_THE_WEEK));
        double temperature = getDouble(getColumnIndex(WeatherTable.Cols.TEMPERATURE));
        double humidity = getDouble(getColumnIndex(WeatherTable.Cols.HUMIDITY));
        double cloudCover = getDouble(getColumnIndex(WeatherTable.Cols.CLOUD_COVER));

        return new Weather(cityName, date, dayOfTheWeek, temperature, humidity, cloudCover);

    }
}
