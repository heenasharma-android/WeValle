package com.SigninSignup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.MainFragments.HomeFragmentNewActivity;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.models.UserModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by Sumit on 27/08/2015.
 */
public class SignInFragment extends Fragment implements View.OnClickListener {

    /********** Login Fragment UI Declaration */

    private View mView;
    private RelativeLayout login;
    private EditText email,password;
    private RelativeLayout back,registerbtn;
    private ImageView forgotpassword;



    /* variables declaration*/
    private String str_email;
    private String str_password;


    private AlbanianPreferances pref;

    public static String mLatitude;
    public static String mLongitude;


//    private AsyncTask<Void, Void, String> sociallogin_obj;
    private String tag_login_obj = "jlogin_req";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new AlbanianPreferances(getActivity());


    }


    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("Signin Screen");
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        AlbanianApplication.hideKeyBoard(getActivity(), getActivity());

        if (AlbanianApplication.isInternetOn(getActivity()))
        {

        }
        else
        {
            String mTitle = getResources().getString(R.string.app_name);

            AlbanianApplication.ShowAlert(getActivity(), mTitle,
                    getResources().getString(R.string.nointernet), false);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.login, container, false);

        iniView(mView);

        return mView;
    }


    private void iniView(View mView) {



        login=(RelativeLayout)mView.findViewById(R.id.rl_login_btn);
        email=(EditText)mView.findViewById(R.id.edt_emailaddress);
        back=(RelativeLayout)mView.findViewById(R.id.rl_back_header);
        password=(EditText)mView.findViewById(R.id.edt_password);


        forgotpassword=(ImageView) mView.findViewById(R.id.img_forgotpassword);
        registerbtn=(RelativeLayout) mView.findViewById(R.id.rl_registerbtn);
        registerbtn.setOnClickListener(this);
        login.setOnClickListener(this);
        forgotpassword.setOnClickListener(this);
        back.setOnClickListener(this);


    }

    private void remoceAllFocus() {

        AlbanianApplication.hideKeyBoard(getActivity(), getActivity());


    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.img_forgotpassword:

                loadForgotpasswordFagment();

                break;

            case R.id.rl_login_btn:

                validateDetails();


                break;

            case R.id.rl_back_header:

                remoceAllFocus();

                getFragmentManager().popBackStack();

                break;

            case R.id.rl_registerbtn:

               loadRegister();

                break;


            default:
                break;
        }

    }



    private void loadRegister() {

        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction();
        SignUpFirstFragment mFragment = new SignUpFirstFragment();

        Bundle bundle = new Bundle();
//        bundle.putString(AlbanianConstants.EXTRA_LOCALPAGENAME,  AlbanianConstants.EXTRA_FAQ);
        mFragment.setArguments(bundle);


        mFragmentTransaction.replace(R.id.frame_signinsignup, mFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    private void loadForgotpasswordFagment() {

        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction();
        ForgotPasswordFragment mFragment = new ForgotPasswordFragment();

        mFragmentTransaction.replace(R.id.frame_signinsignup, mFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    private void validateDetails() {

        try {

            AlbanianApplication.hideKeyBoard(getActivity(), getActivity());

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        String mTitle = getResources().getString(R.string.app_name);


        str_email=email.getText().toString();

        str_password=password.getText().toString();

        Matcher mEmailMatcher = AlbanianConstants.EMAIL_ADDRESS_PATTERN
                .matcher(str_email);



        if (str_email.length()<=0)
        {
            String mMessage = getString(R.string.Pleasefillemail);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }
        else if (mEmailMatcher.matches()==false)
        {
                // valid email
                String mMessage = getString(R.string.Signup_Pleasefillvalidemail);
                AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }
            else if (str_password.length()<=0){

                String mMessage = getString(R.string.Signup_Pleasefillpassword);
                AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
            }
        else
        {
            submitSignin();


        }
    }

    private void submitSignin() {

        String deviceToken=AlbanianApplication.getReg_deviceID();
        if (deviceToken == null || deviceToken.length()<=0) {

            deviceToken="";
        }
        if (mLatitude == null || mLatitude.length()<=0) {

            mLatitude="0.0";
        }
        if (mLongitude == null || mLongitude.length()<=0) {

            mLongitude="0.0";
        }

        Log.d(AlbanianConstants.TAG, "str_email: " + str_email);
        Log.d(AlbanianConstants.TAG, "str_password: " + str_password);
        Log.d(AlbanianConstants.TAG, "UserDeviceTocken: " + AlbanianApplication.getReg_deviceID());
        Log.d(AlbanianConstants.TAG, "UserLat: " + mLatitude);
        Log.d(AlbanianConstants.TAG, "UserLong: " +mLongitude);



//        {"UserId":"3122","UserName":"Sadman","UserFullName":"","UserStatus":"1","UserGender":"m",
//                "UserProfileImage":"http:\/\/54.85.75.103\/acappadmin\/upload\/1442898398image1.jpeg",
//                "UserMinAge":"22","UserMaxAge":"33","UserSubscriptionStatus":"0","UserInvisibleStatus":"0",
//                "ErrorCode":"4","ErrorMessage":" Already Login At Other Device "}


        AlbanianApplication.showProgressDialog(getActivity(),"","Loading...");

        final String finalDeviceToken = deviceToken;
        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());
                parseResponse(response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                    AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {


            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();



                params.put("api_name", "signin");
                params.put("user_email", str_email);
                params.put("user_password", str_password);
                params.put("user_device_token", finalDeviceToken);
                params.put("user_latitude", "");
                params.put("user_longitude", "");
                params.put("user_device_type", "2");
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        // Adding request to request queue
        sr.setRetryPolicy(new DefaultRetryPolicy(15000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AlbanianApplication.getInstance().addToRequestQueue(sr);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }



    private void parseResponse(String Result) {

        Log.d("sumit","login result--= "+Result);

        if (Result != null) {

            {

                try {

                    JSONObject jObj_job = new JSONObject(Result);
                    String ErrorCode = jObj_job.optString("ErrorCode");
                    String ErrorMessage = jObj_job.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")
                            || ErrorCode.equalsIgnoreCase("4")
                            ) {



                        String mTitle = getResources().getString(R.string.app_name);


                        UserModel usermodel=new UserModel();

                        String UserId=jObj_job.optString("UserId");
                        usermodel.setUserId(UserId);


                        String UserName=jObj_job.optString("UserName");
                        usermodel.setUserName(UserName);

                        String UserFullName=jObj_job.optString("UserFullName");
                        usermodel.setUserFullName(UserFullName);

                        String UserStatus=jObj_job.optString("UserStatus");
                        usermodel.setUserStatus(UserStatus);

                        String UserGender=jObj_job.optString("UserGender");
                        usermodel.setUserGender(UserGender);

                        String UserProfileImage=jObj_job.optString("UserProfileImage");
                        usermodel.setUserImage(UserProfileImage);

                        String UserMinAge=jObj_job.optString("UserMinAge");
                        usermodel.setUserMinAge(UserMinAge);

                        String UserMaxAge=jObj_job.optString("UserMaxAge");
                        usermodel.setUserMaxAge(UserMaxAge);

                        String UserSubscriptionStatus=jObj_job.optString("UserSubscriptionStatus");
                        usermodel.setUserSubscriptionStatus(UserSubscriptionStatus);

                        String UserInvisibleStatus=jObj_job.optString("UserInvisibleStatus");
                        usermodel.setUserInvisibleStatus(UserInvisibleStatus);

                        String UserCountry=jObj_job.optString("UserCountry");
                        usermodel.setUserCountry(UserCountry);


                        saveUser(usermodel);


                    }

                    else
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

    private void saveUser(UserModel usermodel) {

        AlbanianPreferances pref = new AlbanianPreferances(getActivity());

        pref.setUserData(usermodel);
        pref.setLogin(true);

        loadMainFragmentActivity();
    }

    private void loadMainFragmentActivity() {

//        Intent mIntent = new Intent(getActivity(),
//                HomeFragmentNewActivity.class);
//        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(mIntent);
//        getActivity().finish();
        Intent mIntent = new Intent(getActivity(),
                HomeFragmentNewActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mIntent);
        getActivity().finish();
    }


//    @Override
//    public void onConnected(Bundle bundle) {
//
//
//        try {
//
//            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//            if (mLastLocation != null)
//            {
//
//                mLatitude=String.valueOf(mLastLocation.getLatitude());
//                mLongitude=String.valueOf(mLastLocation.getLongitude());
//
//                Log.d("sumit","latitude== "+String.valueOf(mLastLocation.getLatitude()));
//
//            }
//            else
//            {
//
//                Log.d("sumit","NO location found...............");
//
//            }
//
//
//
//
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//
//
//            mLatitude="0.0";
//            mLongitude="0.0";
//
//        }
//
//    }

//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }


}
