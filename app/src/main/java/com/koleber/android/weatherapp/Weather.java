package com.koleber.android.weatherapp;

public class Weather {

    private String cityName;
    private String date;
    private String dayOfTheWeek;
    private double temperature;
    private double humidity;
    private double loudCover;

    public Weather() {}

    public Weather(String cityName, String date, String dayOfTheWeek, double temperature, double humidity, double loudCover) {
        this.cityName = cityName;
        this.date = date;
        this.dayOfTheWeek = dayOfTheWeek;
        this.temperature = temperature;
        this.humidity = humidity;
        this.loudCover = loudCover;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getCloudCover() {
        return loudCover;
    }

    public void setLoudCover(double loudCover) {
        this.loudCover = loudCover;
    }
}
