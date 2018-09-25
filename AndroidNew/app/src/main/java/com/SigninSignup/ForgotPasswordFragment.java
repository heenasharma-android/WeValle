package com.SigninSignup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.regex.Matcher;

/**
 * Created by Sumit on 20/09/2015.
 */
public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {

    /********** Login Fragment UI Declaration */

    private View mView;
    private RelativeLayout submit,back;
    private EditText email;
    private TextView emailusMail;



    /* variables declaration*/
    private String str_email;

    private AlbanianPreferances pref;


    private String tag_login_obj = "jforgot_req";
    private String TAG = "sumit";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new AlbanianPreferances(getActivity());




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

        mView = inflater.inflate(R.layout.forgotpassword, container, false);

        iniView(mView);

        return mView;
    }


    private void iniView(View mView) {


        submit=(RelativeLayout)mView.findViewById(R.id.rl_submit_btn);
        back=(RelativeLayout)mView.findViewById(R.id.rl_back_header);
        email=(EditText)mView.findViewById(R.id.edt_emailaddress);
        emailusMail=(TextView) mView.findViewById(R.id.txt_emailus);


        emailusMail.setOnClickListener(this);
        submit.setOnClickListener(this);
        back.setOnClickListener(this);


    }

    private void remoceAllFocus() {

        AlbanianApplication.hideKeyBoard(getActivity(), getActivity());


    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.rl_submit_btn:

                remoceAllFocus();
                validateDetails();

                break;
            case R.id.txt_emailus:

                sendEmail("");

                break;

            case R.id.rl_back_header:

                remoceAllFocus();

                 getFragmentManager().popBackStack();

                 break;


            default:
                break;
        }

    }



    private void sendEmail(String str) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"support"+AlbanianConstants.AppDomain});
        i.putExtra(Intent.EXTRA_SUBJECT, str);
        i.putExtra(Intent.EXTRA_TEXT, "");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients.", Toast.LENGTH_SHORT).show();
        }
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

        else
        {
            submitSignin();


        }
    }

    private void submitSignin() {

        Log.d(AlbanianConstants.TAG, "forgot pass api");

        AlbanianApplication.showProgressDialog(getActivity(),"","Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "forgot-- "+response.toString());

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

                params.put("api_name", "forgotpassword");
                params.put("user_email", str_email);
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        // Adding request to request queue
        AlbanianApplication.getInstance().addToRequestQueue(sr);


    }



    private void parseResponse(String Result) {

        Log.d("sumit","chat response-= "+Result);

//        images_arraylist.clear();

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");


                    Log.d(AlbanianConstants.TAG, "ErrorCode" + ErrorCode);
                    Log.d(AlbanianConstants.TAG, "ErrorMessage" + ErrorMessage);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        String mTitle = getResources().getString(R.string.app_name);



                        /////////////
                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);



                    }

                    else if(ErrorCode.equals("0") )
                    {

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
                    Log.d("sumit","chat message exception= "+e);
                }

            }

        }
    }

}
