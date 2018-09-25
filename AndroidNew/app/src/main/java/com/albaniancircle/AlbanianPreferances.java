package com.albaniancircle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.SigninSignup.SigninSignupFragmentActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.models.UserModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumit on 27/08/2015.
 */
public class AlbanianPreferances {

    private SharedPreferences pref;


    public AlbanianPreferances(Context context) {


        try {
            pref = context.getSharedPreferences("AlbanianPreferances",
                    Context.MODE_PRIVATE);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




    }

    public void setMatchedSubmitted(boolean flag) {
        // TODO Auto-generated method stub

        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean("matchingSubmited", flag);

        edit.commit();

    }

    public boolean getMatchedSubmitted() {

        boolean flag = pref.getBoolean("matchingSubmited", false);

        return flag;

    }

    public void setLogin(boolean login) {
        // TODO Auto-generated method stub

        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean("login", login);

        edit.commit();

    }

    public boolean getLogin() {

        boolean login = pref.getBoolean("login", false);

        return login;

    }

    public void setUserSubscription(String flag) {
        // TODO Auto-generated method stub

        SharedPreferences.Editor edit = pref.edit();
        edit.putString("UserSubscription", flag);

        edit.commit();

    }

    public String getUserSubscription() {

        String flag = pref.getString("UserSubscription", "0");

        return flag;

    }



    public void setCurrentScreen(String flag) {
        // TODO Auto-generated method stub

        SharedPreferences.Editor edit = pref.edit();
        edit.putString("CurrentScreen", flag);

        edit.commit();

    }

    public String getCurrentScreen() {

        String flag = pref.getString("CurrentScreen", "");

        return flag;

    }


    public void setChatUser(String flag) {
        // TODO Auto-generated method stub

        SharedPreferences.Editor edit = pref.edit();
        edit.putString("ChatUser", flag);

        edit.commit();

    }

    public String getChatUser() {

        String flag = pref.getString("ChatUser", "");

        return flag;

    }




    public void setUserData(UserModel result) {
        // TODO Auto-generated method stub

        SharedPreferences.Editor edit = pref.edit();

        Gson gson = new Gson();
        String json = gson.toJson(result);
        edit.putString("logindata", json);
        edit.commit();

    }



    public UserModel getUserData() {

        Gson gson = new Gson();
        String json = pref.getString("logindata", "");
        UserModel obj = gson.fromJson(json, UserModel.class);

        return obj;

    }













    public void clearCurrentBookingModel() {

        SharedPreferences.Editor edit = pref.edit();

        edit.remove("CurrentBookingModel").commit();

    }

    public void clearCurrentInvoiceModel() {

        SharedPreferences.Editor edit = pref.edit();

        edit.remove("CurrentInvoiceModel").commit();

    }





    public void deactivateUserAccount(final Activity mAct, final String userId,
                                      final String UserDeactivationCause, final String UserDeactivationText) {


        AlbanianApplication.showProgressDialog(mAct, "", "Loading...");



        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "deactivate= " + response.toString());
                AlbanianApplication.hideProgressDialog(mAct);

                parseResponse(response.toString(),mAct);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "deactivate= Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(mAct);

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "deactivate");
                params.put("user_id", userId );
                params.put("user_deactivation_cause", UserDeactivationCause );
                params.put("user_deactivation_text", UserDeactivationText );
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };


        AlbanianApplication.getInstance().addToRequestQueue(sr);




    }


    public void logoutUser(final Activity mAct, final String userId) {

        Log.d("sumit", "Logout Albanian's PREFS == ");

        AlbanianApplication.showProgressDialog(mAct, "", "Loading...");



        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "signout= " + response.toString());
                AlbanianApplication.hideProgressDialog(mAct);

                parseResponse(response.toString(),mAct);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "signout= Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(mAct);

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "signout");
                params.put("user_id", userId );
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };


        AlbanianApplication.getInstance().addToRequestQueue(sr);




    }

    private void parseResponse(String Result, Activity mAct) {

        if (Result != null) {

            {

                try {



                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


//                        String mTitle = mact.getResources().getString(R.string.app_name);

                        SharedPreferences.Editor edit = pref.edit();
                        edit.clear();
                        edit.commit();



                        Intent mIntent = new Intent(mAct,
                                SigninSignupFragmentActivity.class);
                        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mAct.startActivity(mIntent);
                        mAct.finish();

                        /////////////


                    }

                    else if(ErrorCode.equals("0") )
                    {

                        String mTitle = mAct.getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(mAct, mTitle,
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

    public void logoutManually() {

        SharedPreferences.Editor edit = pref.edit();
        edit.clear();
        edit.commit();


//        Intent mIntent = new Intent(mAct,
//                SigninSignupFragmentActivity.class);
//        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        mAct.startActivity(mIntent);
//        mAct.finish();
    }
}
