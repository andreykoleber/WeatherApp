package com.koleber.android.weatherapp;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class GoogleMapsFragment extends SupportMapFragment {

    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static GoogleMapsFragment newInstance() {

        Bundle args = new Bundle();

        GoogleMapsFragment fragment = new GoogleMapsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Activity activity = getActivity();
        if (activity != null) {
            mGoogleApiClient = new GoogleApiClient.Builder(activity).addApi(LocationServices.API).addConnectionCallbacks(
                    new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {

                        }
                    }
            ).build();
        }

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                showCurrentUserLocation();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                if (hasLocationPermission()) {
                    updateUi();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    private void showCurrentUserLocation() {
        if (mGoogleMap == null) {
            return;
        }

        if (hasLocationPermission()) {
            updateUi();
        } else {
            requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
        }
    }

    private boolean hasLocationPermission() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            int result = ContextCompat.checkSelfPermission(activity, LOCATION_PERMISSIONS[0]);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    private void updateUi() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

                                Bitmap donaldDuck = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.donald_duck);
                                BitmapDescriptor donaldDuckIcon = BitmapDescriptorFactory.fromBitmap(donaldDuck);
                                MarkerOptions markerOptions = new MarkerOptions()
                                        .position(currentPosition)
                                        .title(getString(R.string.your_are_here))
                                        .icon(donaldDuckIcon);

                                mGoogleMap.addMarker(markerOptions);
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentPosition, 10);
                                mGoogleMap.moveCamera(cameraUpdate);
                            }
                        }
                    });
        }
    }


}
