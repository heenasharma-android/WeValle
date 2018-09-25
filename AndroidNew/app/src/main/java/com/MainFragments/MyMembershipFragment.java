package com.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumit on 26/09/2015.
 */
public class MyMembershipFragment extends Fragment implements View.OnClickListener {

    /*UI declaration........*/
    private View mView;

    private RelativeLayout back;
    private Button cancelsubscription;

    /* Variables...... */


    private AlbanianPreferances pref;
    private TextView headertext;
    private TextView exp_date;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        pref = new AlbanianPreferances(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();



    }


    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("Blocked Users");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.mysubscription, container, false);



        initViews();

        getSubscription();


        return mView;
    }




    private void initViews() {

        headertext = (TextView)mView.findViewById(R.id.txt_headerlogo);
        exp_date = (TextView)mView.findViewById(R.id.txt_exp_date);
        headertext.setText(getActivity().getString(R.string.membership));

        cancelsubscription = (Button)mView.findViewById(R.id.btn_cancelsubscription);
        cancelsubscription.setOnClickListener(this);

        back = (RelativeLayout)mView.findViewById(R.id.rl_back_header);
        back.setOnClickListener(this);



    }






    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back_header:

               getActivity().onBackPressed();

                break;
//
            case R.id.btn_cancelsubscription:

                cancelSubscription();

                break;
        }
    }



    private void cancelSubscription() {


        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");



        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "mymembership cancel= "+response.toString());

                parseCancelResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "mymembership cancelError: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "cancelplan");
                params.put("user_id", pref.getUserData().getUserId());
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };


        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }

    private void parseCancelResponse(String Result) {


        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {

                        cancelsubscription.setVisibility(View.GONE);

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



                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","Bloackeduser list exception= "+e);
                }

            }

        }
    }




    private void getSubscription() {


        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");



        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "block user list= "+response.toString());

                parseSubscriptionResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

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

                params.put("api_name", "getsubscriptionduedate");
                params.put("user_id", pref.getUserData().getUserId());
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        // Adding request to request queue
//        AlbanianApplication.getInstance().addToRequestQueue(jsonObjReq,
//                tag_whoiviewed_obj);

        AlbanianApplication.getInstance().addToRequestQueue(sr);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_whoiviewed_obj);
    }

    private void parseSubscriptionResponse(String Result) {

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage- = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        String mTitle = getResources().getString(R.string.app_name);

                        String DueDate=jObj.optString("DueDate");
                        exp_date.setText(DueDate);
                        cancelsubscription.setVisibility(View.VISIBLE);



                    }

                    else
                    {

                        cancelsubscription.setVisibility(View.GONE);
                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }



                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","Bloackeduser list exception= "+e);
                }

            }

        }
    }
}
