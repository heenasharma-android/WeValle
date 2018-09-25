package com.MainFragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adapter.LocalsUsersAdapter;
import com.adapter.RecyclerItemClickListener;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.LocationServices;
import com.locationmanager.LocationFragment;
import com.models.UserModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocalsFragment extends LocationFragment implements View.OnClickListener {


    /*UI declaration........*/
    private View mView;

    private RecyclerView gridUserimages;



    /* Variables...... */

    private ArrayList<UserModel> users_arraylist;
    private String tag_whoiviewed_obj = "jsentmessages_req";
    private AlbanianPreferances pref;

    public static String mLatitude;
    public static String mLongitude;
    private boolean localFlag;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        users_arraylist=new ArrayList<>();

        pref = new AlbanianPreferances(getActivity());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_view_images, container, false);


        localFlag=true;
        initViews();



        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("LocalsAroundMe");
    }

    private void initViews() {

        gridUserimages = (RecyclerView) mView.findViewById(R.id.gv_userimages);
        gridUserimages.setHasFixedSize(true);

        gridUserimages
                .setLayoutManager(new GridLayoutManager(getActivity(), 3));

    }


//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        this.mActivity=activity;
//    }









    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back_header:
                getFragmentManager().popBackStack();

                break;
        }
    }



    private void getUserNearby() {

//        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");



        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "user locals= " + response.toString());
                AlbanianApplication.hideProgressDialog(getActivity());

                localFlag=false;
                parseResponse(response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "user locals Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "getlocalsaroundme");
                params.put("user_id", pref.getUserData().getUserId() );
                params.put("update_address", "1" );

                params.put("user_latitude", mLatitude);
                params.put("user_longitude", mLongitude );
                params.put("AppName", AlbanianConstants.AppName);


                return params;
            }

        };



        AlbanianApplication.getInstance().addToRequestQueue(sr);

       }

    private void parseResponse(String Result) {

        users_arraylist.clear();

        if (Result != null) {

            {

                try {



                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        String mTitle = getResources().getString(R.string.app_name);



                        JSONArray jArray_response=jObj.getJSONArray("Locals");


                        for (int i = 0; i < jArray_response.length(); i++) {

                            UserModel usermodel=new UserModel();

                            JSONObject jObj_job=jArray_response.getJSONObject(i);

                            // Job object

//                            JSONObject jObj_job=jObj_Main.getJSONObject("Payment");

                            String UserId=jObj_job.optString("UserId");
                            usermodel.setUserId(UserId);

                            String UserName=jObj_job.optString("UserName");
                            usermodel.setUserName(UserName);

                            String Age=jObj_job.optString("Age");
                            usermodel.setAge(Age);

                            String ImageFileName=jObj_job.optString("ImageFileName");
                            String UserImageUrl=jObj_job.optString("UserImageUrl");
                            usermodel.setUserImageUrl(UserImageUrl+ImageFileName);

                            String UserSubscriptionStatus=jObj_job.optString("UserSubscriptionStatus");
                            usermodel.setUserSubscriptionStatus(UserSubscriptionStatus);

                            String MilesDistance=jObj_job.optString("MilesDistance");
                            usermodel.setMilesDistance(MilesDistance);

                            String Unit=jObj_job.optString("Unit");
                            usermodel.setUnit(Unit);

                            String RowDistance=jObj_job.optString("RowDistance");
                            usermodel.setRowDistance(RowDistance);

                            String UserPresence=jObj_job.optString("UserPresence");
                            usermodel.setUserPresence(UserPresence);

                            ///////

                            users_arraylist.add(usermodel);

                        }



                        /////////////

                        setUserImagesGrid(users_arraylist);


                    }

                    else if(ErrorCode.equals("0") )
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }



                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","Whoiviewed list exception= "+e);
                }

            }

        }
    }

    private void setUserImagesGrid(final ArrayList<UserModel> users_arraylist) {





        LocalsUsersAdapter adapter_countryusers = new LocalsUsersAdapter
                (getActivity(), users_arraylist);

        gridUserimages.setAdapter(adapter_countryusers);


        final RecyclerItemClickListener.OnItemClickListener listener=new RecyclerItemClickListener.
                OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                loadProfileFragment(users_arraylist.get(position).getUserId());


            }
        };


        gridUserimages.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), listener) );


    }





    private void loadProfileFragment(String userId) {

        Log.d("sumit","uid= "+userId);

//        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction mFragmentTransaction = mFragmentManager
//                .beginTransaction();
//        ProfileFragment mFragment = new ProfileFragment();

        Bundle bundle = new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, userId);

//      mFragment.setArguments(bundle);
//      mFragmentTransaction.replace(R.id.container_body, mFragment);
//      mFragmentTransaction.addToBackStack(null);
//      mFragmentTransaction.commit();

        mActivity.pushFragments(AlbanianConstants.TAB_2_TAG,
                new ProfileFragment(), false, true, bundle);

    }


    @Override
    public void onConnected(Bundle bundle) {

        try {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null)
            {

                mLatitude=String.valueOf(mLastLocation.getLatitude());
                mLongitude=String.valueOf(mLastLocation.getLongitude());

                Log.d("sumit", "latitude== " + String.valueOf(mLastLocation.getLatitude()));


                if (AlbanianApplication.isInternetOn(getActivity())) {

                    if (localFlag) {


                        getUserNearby();
                    }

                }
                else {
                    String mTitle = getResources().getString(R.string.app_name);

                    AlbanianApplication.ShowAlert(getActivity(), mTitle,
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


            mLatitude="0.0";
            mLongitude="0.0";

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
