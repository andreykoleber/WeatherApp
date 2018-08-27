package com.koleber.android.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SignUpFragment extends Fragment {

    private SharedPreferences mSharedPreferences;

    public static SignUpFragment newInstance() {

        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);

        final EditText edtUserName = view.findViewById(R.id.edtUserName);

        final EditText edtPassWord = view.findViewById(R.id.edtPassword);

        final EditText edtConfirmPassword = view.findViewById(R.id.edtConfirmPassword);

        Activity activity = getActivity();
        if (activity != null) {
            mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        }

        LinearLayout llContainer = view.findViewById(R.id.llContainer);
        llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });


        Button btnSignUp = view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                String userName = edtUserName.getText().toString();
                if (userName.equals("")) {
                    Toast.makeText(getActivity(), getString(R.string.please_enter_your_usename), Toast.LENGTH_LONG).show();
                    return;
                }

                String password = edtPassWord.getText().toString();
                if (password.equals("")) {
                    Toast.makeText(getActivity(), getString(R.string.please_enter_your_password), Toast.LENGTH_LONG).show();
                    return;
                }

                String confirmPassword = edtConfirmPassword.getText().toString();
                if (confirmPassword.equals("")) {
                    Toast.makeText(getActivity(), getString(R.string.please_confirm_your_password), Toast.LENGTH_LONG).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getActivity(), getString(R.string.the_passwords_doesnt_match), Toast.LENGTH_LONG).show();
                    return;
                }

                saveCredentials(userName, password);
            }
        });


        return view;
    }

    private void hideKeyboard() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                View currentFocus = getActivity().getCurrentFocus();
                if (currentFocus != null) {
                    inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                }
            }
        }
    }

    private void saveCredentials(String userName, String password) {
        mSharedPreferences.edit().putString(userName, userName).apply();
        mSharedPreferences.edit().putString(password, password).apply();
        Toast.makeText(getActivity(), getString(R.string.credentials_saved), Toast.LENGTH_LONG).show();
        showMainFragment();
    }

    private void showMainFragment() {
        WeatherActivity weatherActivity = (WeatherActivity) getActivity();
        if (weatherActivity != null) {
            weatherActivity.replaceFragment(MainFragment.newInstance(), AnimationMode.Next);
        }
    }
}
