package com.koleber.android.weatherapp;

public enum Day {

    MONDAY(R.string.monday),
    TUESDAY(R.string.tuesday),
    Wednesday(R.string.wednesday),
    THURSDAY(R.string.thursday),
    FRIDAY(R.string.friday),
    SATURDAY(R.string.saturday),
    SUNDAY(R.string.sunday);

    private int mDayNumber;

    Day(int dayNumber) {
        this.mDayNumber = dayNumber;
    }

    public int getDayNumber() {
        return mDayNumber;
    }
}
