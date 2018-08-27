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

public class AuthorizationFragment extends Fragment {

    private SharedPreferences mSharedPreferences;

    public static AuthorizationFragment newInstance() {

        Bundle args = new Bundle();

        AuthorizationFragment fragment = new AuthorizationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.authorization_fragment, container, false);

        final EditText edtUserName = view.findViewById(R.id.edtUserName);

        final EditText edtPassword = view.findViewById(R.id.edtPassword);

        LinearLayout llContainer = view.findViewById(R.id.llContainer);
        llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });

        Button btnSignIn = view.findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                String userName = edtUserName.getText().toString();
                if (userName.equals("")) {
                    Toast.makeText(getActivity(), getString(R.string.please_enter_your_usename), Toast.LENGTH_LONG).show();
                    return;
                }

                String password = edtPassword.getText().toString();
                if (password.equals("")) {
                    Toast.makeText(getActivity(), getString(R.string.please_enter_your_password), Toast.LENGTH_LONG).show();
                    return;
                }

                verifyWithStoredCredentials(userName, password);

            }
        });

        Button btnOrSignUp = view.findViewById(R.id.btnOrSignUp);
        btnOrSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                replaceFragment(SignUpFragment.newInstance(), AnimationMode.Next);
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

    public void replaceFragment(Fragment fragment, AnimationMode animationMode) {
        WeatherActivity weatherActivity = (WeatherActivity) getActivity();
        if (weatherActivity != null) {
            weatherActivity.replaceFragment(fragment, animationMode);
        }
    }

    private void verifyWithStoredCredentials(String userName, String password) {

        initSharedPreferences();

        String storedUserName = mSharedPreferences.getString(userName, "");
        if (!storedUserName.equals(userName)) {
            Toast.makeText(getActivity(), getString(R.string.user_name_not_found, userName), Toast.LENGTH_LONG).show();
            return;
        }

        String storedPassword = mSharedPreferences.getString(password, "");
        if (!storedPassword.equals(password)) {
            Toast.makeText(getActivity(), getString(R.string.not_correct_password), Toast.LENGTH_LONG).show();
            return;
        }

       replaceFragment(MainFragment.newInstance(), AnimationMode.Next);

    }

    private void initSharedPreferences() {
        Activity activity = getActivity();
        if (activity != null) {
            mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        }
    }
}
