package com.koleber.android.weatherapp.database;

public class WeatherDbSchema {

    public static final class WeatherTable {

        public static final String NAME = "weather";

        public static final class Cols {
            public static final String CITY_NAME = "city_name";
            public static final String DATE = "date";
            public static final String DAY_OF_THE_WEEK = "day_of_the_week";
            public static final String TEMPERATURE = "temperature";
            public static final String HUMIDITY = "humidity";
            public static final String CLOUD_COVER = "cloud_cover";

        }
    }
}
