package com.albaniancircle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

import com.MainFragments.HomeFragmentNewActivity;
import com.SigninSignup.SigninSignupFragmentActivity;
import com.models.SqlLiteDbHelper;

import java.io.IOException;

/**
 * Created by sumit on 21/2/15.
 */
public class Splash extends FragmentActivity
        {


    private AlbanianPreferances pref;
    private static String regId = "";
    private int REQUEST_CODE = 100;


    private Handler handle;

    SqlLiteDbHelper dbHelper;

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
//    protected LocationSettingsRequest mLocationSettingsRequest;
    /**
     * Constant used in the location settings dialog.
     */
//    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    /**
     * Provides the entry point to Google Play services.
     */
//    protected GoogleApiClient mGoogleApiClient;
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
//    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
//    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
//            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
//    protected LocationRequest mLocationRequest;
    private String TAG="sumit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity);




        dbHelper = new SqlLiteDbHelper(this);
        try {
            dbHelper.CopyDataBaseFromAsset();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.d("sumit","dbheper error= "+e);
        }
            try {
                dbHelper.openDataBase();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }

//        RxPermissions rxPermissions = new RxPermissions(this);
//        rxPermissions
//                .request(Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.ACCESS_FINE_LOCATION)
//                .subscribe(granted -> {
//                    if (granted) { // Always true pre-M
//                        // I can control the camera now
//                    } else {
//                        // Oups permission denied
//                    }
//                });


        pref = new AlbanianPreferances(Splash.this);

        // Register GCM

        if (AlbanianApplication.isInternetOn(Splash.this))
        {
            AlbanianApplication.registerDevice(this, this);
        }
        else
        {

        }

        startsplashthread();

//        buildGoogleApiClient();

//        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
//        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
//        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
//        builder.addLocationRequest(mLocationRequest);
//        builder.setAlwaysShow(true);
//        mLocationSettingsRequest = builder.build();
//
//        PendingResult<LocationSettingsResult> result =
//                LocationServices.SettingsApi.checkLocationSettings(
//                        mGoogleApiClient,
//                        mLocationSettingsRequest
//                );
//        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            @Override
//            public void onResult(LocationSettingsResult locationSettingsResult) {
//
//                final Status status = locationSettingsResult.getStatus();
//                switch (status.getStatusCode()) {
//                    case LocationSettingsStatusCodes.SUCCESS:
//                        Log.i(TAG, "All location settings are satisfied.");
//                        startsplashthread();
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" +
//                                "upgrade location settings ");
//
//                        try {
//                            // Show the dialog by calling startResolutionForResult(), and check the result
//                            // in onActivityResult().
//                            status.startResolutionForResult(Splash.this, REQUEST_CHECK_SETTINGS);
//                        } catch (IntentSender.SendIntentException e) {
//                            Log.i(TAG, "PendingIntent unable to execute request.");
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
//                                "not created.");
//                        break;
//                }
//            }
//        });

    }


    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the LocationServices API.
     */
//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//    }

    @Override
    protected void onStart() {
        super.onStart();
//        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mGoogleApiClient.disconnect();
    }


    protected void startsplashthread() {
        // TODO Auto-generated method stub
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {


                Message msg = new Message();
                msg.what = 1;
                handle.sendMessage(msg);


            }

        }, AlbanianConstants.SplashTimeout);
    }



            /**
             * Runs when a GoogleApiClient object successfully connects.
             */
//            @Override
//            public void onConnected(Bundle connectionHint) {
//                Log.i(TAG, "Connected to GoogleApiClient");
//
//
//
//
//            }






//            @Override
//            public void onConnectionFailed(ConnectionResult result) {
//                // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
//                // onConnectionFailed.
//                Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
//
//                mGoogleApiClient.connect();
//
////                startsplashthread();
//
//            }

//            @Override
//            public void onConnectionSuspended(int cause) {
//                // The connection to Google Play services was lost for some reason.
//                Log.i(TAG, "Connection suspended");
//
//                mGoogleApiClient.connect();
//
//                // onConnected() will be called again automatically when the service reconnects
//            }



//            @Override
//            protected void onActivityResult(int requestCode, int resultCode,
//                                            Intent intent) {
////                final LocationSettingsStates states = LocationSettingsStates.fromIntent(intent);
//                switch (requestCode) {
//                    case REQUEST_CHECK_SETTINGS:
//                        switch (resultCode) {
//                            case Activity.RESULT_OK:
//                                // All required changes were successfully made
//
//                                Log.d(TAG,"OOKKK");
//
//                                startsplashthread();
//
//                                break;
//                            case Activity.RESULT_CANCELED:
//                                // The user was asked to change settings, but chose not to
//
//                                Log.d(TAG,"CANCELLED");
//
//                                startsplashthread();
//
//                                break;
//                            default:
//                                break;
//                        }
//                        break;
//                }
//            }


            @Override
    protected void onResume() {
        // TODO Auto-generated method stub


        super.onResume();

                // Tracking the screen view
                AlbanianApplication.getInstance().trackScreenView("Splash");

        Log.d("sumit", "splash on resume===");

        handle = new Handler() {
//            private LocationManager locationManager;

            public void handleMessage(Message msg) {


                if (msg.what == 1) {


//                    if (pref.getUserData() != null && pref.getUserData().getUserId() != "")
//                    {
//
//                        Intent in = new Intent(Splash.this,
//                                HomeFragmentNewActivity.class);
//                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(in);
//                        finish();
//                    }

                    if (pref.getLogin())
                    {

                        Intent in = new Intent(Splash.this,
                                HomeFragmentNewActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in);
                        finish();
                    }



                    else

                    {

                        Intent mIntent = new Intent(Splash.this,
                                SigninSignupFragmentActivity.class);
                        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mIntent);
                        Splash.this.finish();

                    }


                }
            }
        };


    }

}
