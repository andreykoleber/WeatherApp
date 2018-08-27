package com.koleber.android.weatherapp;

import android.support.annotation.StringRes;

public class DayManager {

    private static final int NUMBER_OF_DAYS_IN_THE_WEEK = Day.values().length;

    @StringRes
    public static int getDayName(int dayNumber) {

        int[] daysOfTheWeek = new int[NUMBER_OF_DAYS_IN_THE_WEEK];
        daysOfTheWeek[0] = Day.MONDAY.getDayNumber();
        daysOfTheWeek[1] = Day.TUESDAY.getDayNumber();
        daysOfTheWeek[2] = Day.Wednesday.getDayNumber();
        daysOfTheWeek[3] = Day.THURSDAY.getDayNumber();
        daysOfTheWeek[4] = Day.FRIDAY.getDayNumber();
        daysOfTheWeek[5] = Day.SATURDAY.getDayNumber();
        daysOfTheWeek[6] = Day.SUNDAY.getDayNumber();

        return daysOfTheWeek[dayNumber];
    }
}
