package com.koleber.android.weatherapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WeatherActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return AuthorizationFragment.newInstance();
    }
}
