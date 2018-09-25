package com.locationmanager;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.MainFragments.BaseFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;

public abstract class LocationFragment extends BaseFragment implements
        GoogleApiClient.ConnectionCallbacks {
    protected GoogleApiClient mGoogleApiClient;

    protected Location mLastLocation;;

    protected LocationRequest mLocationRequest;
    ArrayList<Location> list = new ArrayList<Location>();
    private static String msg;
    private static final int MILLISECONDS_PER_SECOND = 1000; // Milliseconds per
    // second
    public static final int UPDATE_INTERVAL_IN_SECONDS = 300;// Update frequency
    // in seconds
    protected static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND
            * UPDATE_INTERVAL_IN_SECONDS; // Update frequency in milliseconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 60; // The fastest
    // update
    // frequency, in
    // seconds
    // A fast frequency ceiling in milliseconds
    protected static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND
            * FASTEST_INTERVAL_IN_SECONDS;
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        if (getActivity() instanceof LocationManagerFragmentActivity) {

            mGoogleApiClient = ((LocationManagerFragmentActivity) getActivity())
                    .getLocationClient();

            ((LocationManagerFragmentActivity) getActivity())
                    .getLocationClient().registerConnectionCallbacks(this);

        } else {

            Toast.makeText(
                    getActivity(),
                    "Fragment parent activity must be extends with LocationManagerFragmentActivity ",
                    Toast.LENGTH_LONG).show();
        }
    }
}