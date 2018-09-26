package com.MainFragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.OldScreens.NewProfileActivity;
import com.SigninSignup.SigninSignupFragmentActivity;
import com.adapter.RecyclerItemClickListener;
import com.adapter.SearchedUsersAdapter;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.CircleTransform;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.locationmanager.LocationFragment;
import com.models.CountryModel;
import com.models.UserModel;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Sumit on 23/10/2015.
 */
public class SearchNewFragment extends LocationFragment implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,HomeFragmentNewActivity.FragmentRefreshListener {


    /*UI declaration........*/
    private View mView;

    private RecyclerView gridUserimages_Country;

    private TextView headertext;
    private TextView locationname;
    private ImageView localSearch, userSearch,searchCancel,userImage;
    private RelativeLayout nearMeLayout, searchLayout;
    private RelativeLayout usersearch_hint;
    private RelativeLayout locationLayout_main;
    private LinearLayout locationLayout_inner;
    private SearchView userSearchView;
    String placeName;

    /* Variables...... */

    private ArrayList<UserModel> onlineuser_arraylist;

    private AlbanianPreferances pref;

    private ArrayList<CountryModel> countriesList;
    private SearchedUsersAdapter adapter_countryusers;


    public static String mLatitude;
    public static String mLongitude;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;

    private String TAG="sumit";

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    protected LocationSettingsRequest mLocationSettingsRequest;
    /**
     * Constant used in the location settings dialog.
     */
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            4000;
    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    //    DisplayImageOptions options;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private boolean searchVisible;
    private RelativeLayout rl_locationname;

//    private MapView mMapView;
//    private GoogleMap googleMap;
//    private SwipeRefreshLayout swipeContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeFragmentNewActivity) getActivity()).setFragmentRefreshListener(this);

        onlineuser_arraylist = new ArrayList<>();

        countriesList = new ArrayList<>();

        pref = new AlbanianPreferances(getActivity());

//        options = new DisplayImageOptions.Builder()
//                .displayer(new RoundedBitmapDisplayer(200))
////                .showStubImage(R.drawable.profile_male_btn)
////                .showImageForEmptyUri(R.drawable.profile_male_btn)
////                .showImageOnFail(R.drawable.profile_male_btn)
//                .cacheInMemory()
//                .cacheOnDisc()
//
//                .build();


        buildGoogleApiClient();

        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);




    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }








    @Override
    public void onStart() {
        super.onStart();

        pref.setCurrentScreen("PEOPLETAB");

        Log.d("sumit","searchnewfrag on start");

//        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d("sumit","searchnewfrag on stop called");

        mGoogleApiClient.disconnect();
    }


    @Override
    public void onResume() {
        super.onResume();

//        mMapView.onResume();

        Log.d("sumit","searchnewfrag on resume");

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("Search");

       // mActivity.showCurrentTab();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.search_country_new, container, false);

        initViews(savedInstanceState);

        Log.d("sumit","searchnewfrag on oncreateview "+ContextCompat.checkSelfPermission( getActivity() , android.Manifest.
                permission.ACCESS_COARSE_LOCATION ));

        checkForLocationPermision();


        mView.setFocusableInTouchMode(true);
        mView.requestFocus();
        mView.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
                {

                    if (searchVisible) {


                        nearMeLayout.setVisibility(View.VISIBLE);
                        searchLayout.setVisibility(View.GONE);
                        gridUserimages_Country.setVisibility(View.VISIBLE);
//                        swipeContainer.setVisibility(View.VISIBLE);
                        usersearch_hint.setVisibility(View.GONE);

                        searchVisible=false;

                    }
                    else
                    {
                        getActivity().onBackPressed();
                    }


                    return true;
                }
                return false;
            }
        } );


        return mView;
    }

    private void checkForLocationPermision() {

        if (ContextCompat.checkSelfPermission( getActivity() , android.Manifest.
                permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {

            locationPermissionAlert();

            gridUserimages_Country.setVisibility(View.GONE);
//            swipeContainer.setVisibility(View.GONE);
            locationLayout_main.setVisibility(View.VISIBLE);
            locationLayout_inner.setOnClickListener(view ->
            {

                locationPermissionAlert();

            });

        }
        else
        {

            Log.d("sumit","searchnewfrag on oncreateview else");

            locationLayout_main.setVisibility(View.GONE);
            gridUserimages_Country.setVisibility(View.VISIBLE);
//            swipeContainer.setVisibility(View.VISIBLE);

            checkForGps();
        }

    }

    private void locationPermissionAlert() {

        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        Log.d("sumit","permision given...");

                        checkForGps();
                    } else {
                        // Oups permission denied

                        Log.d("sumit","permision denied...");

                        gridUserimages_Country.setVisibility(View.GONE);
//                        swipeContainer.setVisibility(View.GONE);
                        locationLayout_main.setVisibility(View.VISIBLE);
                    }
                });

    }

    private void checkForGps() {

        Log.d("sumit","checking for gps now...");

        mGoogleApiClient.connect();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");

//                        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
                    {

                        checkForLatLon(LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
                    }
//                        else if (mGoogleApiClient != null && !mGoogleApiClient.isConnected())  {
//
//                            //  mGoogleApiClient.disconnect();
//                            mGoogleApiClient.connect();
//
//                        }
//                        else
//                        {
//                              mGoogleApiClient.disconnect();
//                            mGoogleApiClient.connect();
//                        }



                    break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("sumit", "Location settings are not satisfied. Show the user a dialog to" +
                                "upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(getActivity(),
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
                                "not created.");
                        break;
                }
            }
        });

    }


    private void initViews(Bundle savedInstanceState) {

//        mMapView = (MapView) mView.findViewById(R.id.mapView);
//        mMapView.onCreate(savedInstanceState);
//
//        mMapView.onResume(); // needed to get the map to display immediately

//        try {
//            MapsInitializer.initialize(getActivity().getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        mMapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap mMap) {
//                googleMap = mMap;
//
//                // For showing a move to my location button
//                try {
//                    googleMap.setMyLocationEnabled(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });

//        swipeContainer = (SwipeRefreshLayout) mView.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Your code to refresh the list here.
//                // Make sure you call swipeContainer.setRefreshing(false)
//                // once the network request has completed successfully.
//                try {
//
//                    checkForLocationPermision();
//
////                    mGoogleApiClient.disconnect();
////                    mGoogleApiClient.connect();
//
//                } catch (Exception e) {
//                    // TODO: Handle the error.
//                    e.printStackTrace();
//                }
//            }
//        });


        locationLayout_main = (RelativeLayout) mView.findViewById(R.id.rl_no_gps);
        locationLayout_inner = (LinearLayout) mView.findViewById(R.id.ll_no_gps);
        gridUserimages_Country = (RecyclerView) mView.findViewById(R.id.gv_userimages);
        gridUserimages_Country.setHasFixedSize(true);

        gridUserimages_Country
                .setLayoutManager(new GridLayoutManager(getActivity(), 3));


        userImage = (ImageView) mView.findViewById(R.id.img_profile_header);
        userImage.setOnClickListener(this);
        userSearchView = (SearchView) mView.findViewById(R.id.searchView_user);
        userSearchView.setQueryHint("Username");

        userSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(getActivity(), query, Toast.LENGTH_LONG).show();

                AlbanianApplication.hideKeyBoard(getActivity(),getActivity());

                locationLayout_main.setVisibility(View.GONE);
                gridUserimages_Country.setVisibility(View.VISIBLE);
//                swipeContainer.setVisibility(View.GONE);

                //searchUsersByName(query.toString());

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(getActivity(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });

        usersearch_hint = (RelativeLayout) mView.findViewById(R.id.rl_usersearch_hint);
        nearMeLayout = (RelativeLayout) mView.findViewById(R.id.rl_nearMe);
        searchLayout = (RelativeLayout) mView.findViewById(R.id.rl_searchUser);
        localSearch = (ImageView) mView.findViewById(R.id.img_local_search);
        locationname = (TextView) mView.findViewById(R.id.txt_locationname);
        rl_locationname = (RelativeLayout) mView.findViewById(R.id.rl_locationname);
        userSearch = (ImageView) mView.findViewById(R.id.img_user_search);
        searchCancel = (ImageView) mView.findViewById(R.id.img_search_cancel);

        locationname.setOnClickListener(this);
        userSearch.setOnClickListener(this);
        localSearch.setOnClickListener(this);
        searchCancel.setOnClickListener(this);


    }


    @Override
    public void onPause() {
        super.onPause();
//        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mMapView.onLowMemory();
    }

//    private void addMarker(double lat, double lon) {
//
//        // For dropping a marker at a point on the Map
//        LatLng sydney = new LatLng(lat, lon);
////        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
//
//        // For zooming automatically to the location of the marker
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
////        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.i("sumit", "Place: " + place.getName());

                locationname.setText(place.getName().toString());
                rl_locationname.setVisibility(View.VISIBLE);

                mLatitude=String.valueOf(place.getLatLng().latitude);
                mLongitude=String.valueOf(place.getLatLng().longitude);

                if (AlbanianApplication.isInternetOn(getActivity())) {

                    {

                        getUserNearby("0",place.getName().toString());
                         // 0 for google address
                    }

                }
                else {
                    String mTitle = getResources().getString(R.string.app_name);

                    AlbanianApplication.ShowAlert(getActivity(), mTitle,
                            getResources().getString(R.string.nointernet), false);
                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i("sumit", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        else
        {

            switch (requestCode) {
                case REQUEST_CHECK_SETTINGS:
                    switch (resultCode) {
                        case Activity.RESULT_OK:
                            // All required changes were successfully made

                            Log.d(TAG,"OOKKK=  "+mGoogleApiClient.isConnected());

//                            mGoogleApiClient.disconnect();
//                            mGoogleApiClient.connect();

                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Log.d("sumit","OOKKK googlepai "+mGoogleApiClient);
                                    Log.d("sumit","OOKKK googlepai "+LocationServices.FusedLocationApi);
                                    Log.d("sumit","OOKKK googlepai "+LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));

                                    checkForLatLon(LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
                                }
                            },3000);



                            break;
                        case Activity.RESULT_CANCELED:
                            // The user was asked to change settings, but chose not to

                            Log.d(TAG,"CANCELLED");

//                            startsplashthread();

                            break;
                        default:
                            break;
                    }
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.txt_locationname:

                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(getActivity());
                    getActivity().startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }

                break;

            case R.id.img_local_search:

                try {

                    checkForLocationPermision();

//                    mGoogleApiClient.disconnect();
//                    mGoogleApiClient.connect();

                } catch (Exception e) {
                    // TODO: Handle the error.
                    e.printStackTrace();
                }

                break;

            case R.id.img_user_search:

                try {
                    InputMethodManager imm = (InputMethodManager) getActivity().
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    userSearchView.setFocusable(true);
                    userSearchView.requestFocus();
                    userSearchView.setIconified(false);
                    userSearchView.requestFocusFromTouch();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                searchVisible=true;

                nearMeLayout.setVisibility(View.GONE);
                gridUserimages_Country.setVisibility(View.GONE);
//                swipeContainer.setVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);
                usersearch_hint.setVisibility(View.VISIBLE);

                break;

            case R.id.img_search_cancel:

                searchVisible=false;

                nearMeLayout.setVisibility(View.VISIBLE);
                searchLayout.setVisibility(View.GONE);
                gridUserimages_Country.setVisibility(View.VISIBLE);
//                swipeContainer.setVisibility(View.VISIBLE);
                usersearch_hint.setVisibility(View.GONE);

                break;

            case R.id.img_profile_header:

                AlbanianApplication.getInstance().trackEvent("ProfilePage", "ProfilePage", "ProfilePage");

                loadProfile(pref.getUserData().getUserId());

                break;

            default:
                break;
        }
    }

    private void loadProfile(String userId) {

        final FragmentManager fragmentManager = getFragmentManager();
        final Fragment content = fragmentManager.findFragmentById(R.id.container_body);
        if (content == null || !(content instanceof ProfileFragment))
        {

            Log.d("sumit","profile fragment loading now............");


            Bundle bundle = new Bundle();
            bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, userId);
            bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
                    AlbanianConstants.TAB_1_TAG);
//                myFragment.setArguments(bundle);

//                fragmentTransaction.replace(R.id.container_body, myFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();


            mActivity.pushFragments(AlbanianConstants.TAB_1_TAG,
                    new ProfileFragment(), false, true,bundle);



        }
    }


    private void parseResponse(String Result,String apiName,String selectedlocation,String updateaddress) {
//        if (apiname.equals("usersearch")) {
//
//            onlinesearcheduser_arraylist.clear();
//        } else
        {
            onlineuser_arraylist.clear();
        }

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
                    String ProfileImage = jObj.optString("ProfileImage");
                    String UserDisabled = jObj.optString("UserDisabled");
                    String UserLocation = jObj.optString("UserLocation");

                    if (updateaddress.equals("0")) {

                        UserLocation = selectedlocation;
                    }


                    String UserHeritage = jObj.optString("UserHeritage");
                    String UserSubscriptionStatusUser = jObj.optString("UserSubscriptionStatus");

                    pref.setUserSubscription(UserSubscriptionStatusUser);

                    Log.d("sumit","UserLocation= "+UserLocation);
                    Log.d("sumit","UserHeritage= "+UserHeritage);

                    if (UserHeritage.length() == 0 && apiName.equals("getlocalsaroundme"))
                    {

                        loadUpdateFilterFragment();
                    }


                    try {
//                        imageLoader
//                                .displayImage(ProfileImage,
//                                        userImage, options, animateFirstListener);

                        Picasso.with(getContext()).load(ProfileImage).fit().centerCrop()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
                                .transform(new CircleTransform())
                                .into(userImage);

                        userImage.setBackground(mActivity.getResources().
                                getDrawable(R.drawable.graycircleprofile));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (UserDisabled.equalsIgnoreCase("true")) {

                        showAlert();
                    }

                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        String mTitle = getResources().getString(R.string.app_name);


                        JSONArray jArray_response;

                        if (apiName.equals("getlocalsaroundme")) {

                            jArray_response = jObj.getJSONArray("Locals");
                        }
                        else
                        {
                            jArray_response = jObj.getJSONArray("UserList");
                        }



                        for (int i = 0; i < jArray_response.length(); i++) {

                            UserModel usermodel = new UserModel();

                            JSONObject jObj_job = jArray_response.getJSONObject(i);


//                          JSONObject jObj_job=jObj_Main.getJSONObject("Payment");

                            String UserId = jObj_job.optString("UserId");
                            usermodel.setUserId(UserId);

                            String UserName = jObj_job.optString("UserName");
                            usermodel.setUserName(UserName);

                            String ImageFileName = jObj_job.optString("ImageFileName");
                            usermodel.setImageFileName(ImageFileName);

                            String Age = jObj_job.optString("Age");
                            usermodel.setAge(Age);

                            String UserImageUrl = jObj_job.optString("UserImageUrl");
                            usermodel.setUserImageUrl(UserImageUrl);

                            String UserSubscriptionStatus = jObj_job.optString("UserSubscriptionStatus");
                            usermodel.setUserSubscriptionStatus(UserSubscriptionStatus);


                            String UserFavQuote = jObj_job.optString("UserFavQuote");
                            usermodel.setUserFavQuote(UserFavQuote);

                            String UserCity = jObj_job.optString("UserCity");
                            usermodel.setUserCity(UserCity);

                            String UserState = jObj_job.optString("UserState");
                            usermodel.setUserState(UserState);

                            String UserPresence = jObj_job.optString("UserPresence");
                            usermodel.setUserPresence(UserPresence);

                            String MilesDistance = jObj_job.optString("MilesDistance");
                            usermodel.setMilesDistance(MilesDistance);


                            ///////
//                            if (apiname.equals("usersearch")) {
//
//                                onlinesearcheduser_arraylist.add(usermodel);
//                            } else
                            {
                                onlineuser_arraylist.add(usermodel);
                            }


                        }


                        /////////////


//                        if (apiname.equals("usersearch")) {
//
//                            gridUserimages_Username.setVisibility(View.VISIBLE);
//                            gridUserimages_Country.setVisibility(View.GONE);
//
//                            if (onlinesearcheduser_arraylist .size()>0) {
//
//                                setSearchedUserImagesGrid(onlinesearcheduser_arraylist);
//                            }
//                            else
//                            {
//                                adapter_searchedusers.notifyDataSetChanged();
//                            }
//
//                        }
//                        else
                        {
//                            gridUserimages_Username.setVisibility(View.GONE);

                            userSearchView.clearFocus();
                            AlbanianApplication.hideKeyBoard(getActivity(),getActivity());


                            gridUserimages_Country.setVisibility(View.VISIBLE);

                            if (apiName.equals("usersearch")) {

//                                swipeContainer.setVisibility(View.VISIBLE);
//                                mMapView.setVisibility(View.GONE);
                            }
                            else
                            {
//                                swipeContainer.setVisibility(View.VISIBLE);
//                                mMapView.setVisibility(View.VISIBLE);
                            }



                            usersearch_hint.setVisibility(View.GONE);


                            if (onlineuser_arraylist.size() > 0) {

                                setUserImagesGrid(onlineuser_arraylist,UserLocation);
                            } else
                            {
                                adapter_countryusers.notifyDataSetChanged();
                            }

                        }


                    } else if (ErrorCode.equals("0")) {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }
                    else
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }


                } catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit", "Whoiviewed list exception= " + e);
                }

            }

        }
    }

    private void loadUpdateFilterFragment() {

        Bundle bundle = new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG, AlbanianConstants.TAB_1_TAG);


        mActivity.pushFragments(AlbanianConstants.TAB_1_TAG,
                new UpdateFilterFragment(), true, true, bundle);
    }


    private void setUserImagesGrid(final ArrayList<UserModel> onlineuser_arraylist, String userLocation) {


        try {

            locationname.setText(userLocation);
            rl_locationname.setVisibility(View.VISIBLE);

            adapter_countryusers = new SearchedUsersAdapter(getActivity(), onlineuser_arraylist);
            gridUserimages_Country.setAdapter(adapter_countryusers);


            final RecyclerItemClickListener.OnItemClickListener listener = new RecyclerItemClickListener.
                    OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    //loadProfileFragment(onlineuser_arraylist.get(position).getUserId());
                    Intent intent=new Intent(getContext(),NewProfileActivity.class);
                    intent.putExtra("Seid",pref.getUserData().getUserId());
                    intent.putExtra("userId",onlineuser_arraylist.get(position).getUserId());
                    startActivity(intent);


                }
            };


            gridUserimages_Country.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity(), listener));

            adapter_countryusers.notifyDataSetChanged();

        } catch (Exception e) {

        }


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRefresh(String lat, String lng,Place place) {
        mLatitude=lat;
        mLongitude=lng;
        placeName=place.getName().toString();
        getUserNearby("0",place.getName().toString());
        }

    ///////spinner adapter for country

    public class MyAdapter extends ArrayAdapter<CountryModel> {
        public MyAdapter(Context ctx, int txtViewResourceId, ArrayList<CountryModel> objects) {
            super(ctx, txtViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View mySpinner = inflater.inflate(R.layout.country_spinner_item, parent, false);
            TextView main_text = (TextView) mySpinner.findViewById(R.id.sub_text_seen);
            main_text.setText(countriesList.get(position).getCountry_name());


            return mySpinner;
        }
    }


    private void loadProfileFragment(String userId) {

//        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction mFragmentTransaction = mFragmentManager
//                .beginTransaction();
//        ProfileFragment mFragment = new ProfileFragment();


        Bundle bundle = new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, userId);
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG, AlbanianConstants.TAB_1_TAG);
//        mFragment.setArguments(bundle);

//        mFragmentTransaction.replace(R.id.container_body, mFragment);
//        mFragmentTransaction.addToBackStack(null);
//        mFragmentTransaction.commit();


        mActivity.pushFragments(AlbanianConstants.TAB_1_TAG,
                new ProfileFragment(), false, true, bundle);
    }


    @Override
    public void onConnected(Bundle bundle) {

        Log.d("sumit","onconnected googlepai "+mGoogleApiClient);
        Log.d("sumit","onconnected googlepai "+LocationServices.FusedLocationApi);
        Log.d("sumit","onconnected googlepai "+LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));

//        checkForLatLon(LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
    }

    private void checkForLatLon(Location mLastLocation) {

        try {

//            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null)
            {

                mLatitude=String.valueOf(mLastLocation.getLatitude());
                mLongitude=String.valueOf(mLastLocation.getLongitude());

                Log.d("sumit", "latitude== " + String.valueOf(mLastLocation.getLatitude()));


                if (AlbanianApplication.isInternetOn(mActivity)) {
//                    if (localFlag)
                    {

                        getUserNearby("1",""); // 1 for users local address
                    }

                }
                else {
                    String mTitle = getResources().getString(R.string.app_name);

                    AlbanianApplication.ShowAlert(mActivity, mTitle,
                            getResources().getString(R.string.nointernet), false);
                }


            }
            else
            {

                Log.d("sumit","NO location found...............");

            }




        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

            Log.d("sumit","onconnected Exception "+e);

            mLatitude="0.0";
            mLongitude="0.0";

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.d("sumit","onconnected onConnectionSuspended "+i);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getUserNearby(String updateaddress, String selectedlocation) {
        AlbanianApplication.hideProgressDialog(getActivity());
        gridUserimages_Country.setVisibility(View.VISIBLE);
//        swipeContainer.setVisibility(View.VISIBLE);
        locationLayout_main.setVisibility(View.GONE);
        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "user locals= " + response.toString());
//                swipeContainer.setRefreshing(false);
//                localFlag=false;
               AlbanianApplication.hideProgressDialog(getActivity());

                parseResponse(response.toString(),"getlocalsaroundme",selectedlocation,updateaddress);

//                addMarker(Double.parseDouble(mLatitude),Double.parseDouble(mLongitude));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    AlbanianApplication.hideProgressDialog(getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                VolleyLog.d(AlbanianConstants.TAG, "user locals Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());
            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "getlocalsaroundme");
                params.put("user_id", pref.getUserData().getUserId() );
                params.put("update_address", updateaddress);

                params.put("user_latitude", mLatitude);
                params.put("user_longitude", mLongitude );
                params.put("AppName", AlbanianConstants.AppName);


                Log.d("AppName","AppName"+updateaddress+mLatitude+mLongitude);


                return params;
            }

        };



        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }



    private void searchUsersByName(final String userName ){

        AlbanianApplication.getInstance().cancelPendingRequests("SearchUserName");
        //AlbanianApplication.hideProgressDialog(getActivity());


        Log.d("sumit", "userName name= " + userName);
        Log.d("sumit", "user_id name= " + pref.getUserData().getUserId());

        //AlbanianApplication.showProgressDialog(getActivity(), "","Loading...");



        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "getuserbycountry= " + response.toString());
              //  AlbanianApplication.hideProgressDialog(getActivity());

                gridUserimages_Country.setVisibility(View.VISIBLE);
//                swipeContainer.setVisibility(View.VISIBLE);

                parseResponse(response.toString(),"usersearch","","");



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "getuserbycountry Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

              //
                //  AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "usersearch");
                params.put("user_id", pref.getUserData().getUserId());
                params.put("search_string", userName );
//                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        AlbanianApplication.getInstance().addToRequestQueue(sr,"SearchUserName");


    }



    private void showAlert() {

        AlertDialog.Builder mAlert = new AlertDialog.Builder(getActivity());
        mAlert.setCancelable(false);
        mAlert.setTitle("Deleted");
        mAlert.setMessage("Your account has been deleted for being incomplete or fake photos.");


        mAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                logoutUser();


            }
        });

        mAlert.show();

    }


    private void logoutUser() {


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "signout= " + response.toString());
//                AlbanianApplication.hideProgressDialog(mAct);

                parseLogoutResponse(response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "signout= Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

//                AlbanianApplication.hideProgressDialog(mAct);

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "signout");
                params.put("user_id", pref.getUserData().getUserId() );
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };


        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }

    private void parseLogoutResponse(String Result) {

        if (Result != null) {

            {

                try {



                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        deleteUserFromServer();


                    }

                    else if(ErrorCode.equals("0") )
                    {

                        String mTitle = getActivity().getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }



                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","signout parse exception= "+e);
                }

            }

        }

    }


    private void deleteUserFromServer() {

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "deleteuser= " + response.toString());
//                AlbanianApplication.hideProgressDialog(mAct);

                try {



                    JSONObject jObj = new JSONObject(response.toString());
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


//                        String mTitle = mact.getResources().getString(R.string.app_name);


                        SharedPreferences preferences = getActivity().getSharedPreferences("AlbanianPreferances", 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();




                        Intent mIntent = new Intent(getActivity(),
                                SigninSignupFragmentActivity.class);
                        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().startActivity(mIntent);
                        getActivity().finish();

                        /////////////


                    }

                    else if(ErrorCode.equals("0") )
                    {

                        String mTitle = getActivity().getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }



                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","signout parse exception= "+e);
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "signout= Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

//                AlbanianApplication.hideProgressDialog(mAct);

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {

                Log.d(AlbanianConstants.TAG, "deleteuser UserId= " + pref.getUserData().getUserId());

                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "deleteuser");
                params.put("user_id", pref.getUserData().getUserId() );
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };


        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }




}
