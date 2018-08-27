package com.koleber.android.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.koleber.android.weatherapp.database.WeatherBaseHelper;
import com.koleber.android.weatherapp.database.WeatherCursorWrapper;

import java.util.ArrayList;
import java.util.List;

import static com.koleber.android.weatherapp.database.WeatherDbSchema.*;

public class WeatherLab {

    private static WeatherLab sWeatherLab;
    private SQLiteDatabase mSQLiteDatabase;

    static WeatherLab get(Context context) {
        if (sWeatherLab == null) {
            sWeatherLab = new WeatherLab(context);
        }
        return sWeatherLab;
    }

    private WeatherLab(Context context) {
        Context applicationContext = context.getApplicationContext();
        mSQLiteDatabase = new WeatherBaseHelper(applicationContext).getWritableDatabase();
    }

    private static ContentValues getContentValues(Weather weather) {
        ContentValues values = new ContentValues();
        values.put(WeatherTable.Cols.CITY_NAME, weather.getCityName());
        values.put(WeatherTable.Cols.DATE, weather.getDate());
        values.put(WeatherTable.Cols.DAY_OF_THE_WEEK, weather.getDayOfTheWeek());
        values.put(WeatherTable.Cols.TEMPERATURE, weather.getTemperature());
        values.put(WeatherTable.Cols.HUMIDITY, weather.getHumidity());
        values.put(WeatherTable.Cols.CLOUD_COVER, weather.getCloudCover());

        return values;
    }

    void addWeather(Weather weather) {
        ContentValues values = getContentValues(weather);
        mSQLiteDatabase.insert(WeatherTable.NAME, null, values);
    }

    List<Weather> getWeather() {
        List<Weather> weathers = new ArrayList<>();
        try (WeatherCursorWrapper cursorWrapper = queryWeathers(null, null)) {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                weathers.add(cursorWrapper.getWeather());
                cursorWrapper.moveToNext();
            }
        }
        return weathers;
    }

    private WeatherCursorWrapper queryWeathers(String whereClause, String[] whereArgs) {
        Cursor cursor = mSQLiteDatabase.query(WeatherTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new WeatherCursorWrapper(cursor);
    }

}
