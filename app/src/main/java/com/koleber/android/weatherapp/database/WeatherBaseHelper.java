package com.koleber.android.weatherapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.koleber.android.weatherapp.database.WeatherDbSchema.WeatherTable;


public class WeatherBaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "weather.db";

    public WeatherBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " +
                WeatherTable.NAME + "(" + " _id integer primary key autoincrement, " +
                WeatherTable.Cols.CITY_NAME + ", " +
                WeatherTable.Cols.DATE + ", " +
                WeatherTable.Cols.DAY_OF_THE_WEEK + ", " +
                WeatherTable.Cols.TEMPERATURE + "," +
                WeatherTable.Cols.HUMIDITY + "," +
                WeatherTable.Cols.CLOUD_COVER +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
