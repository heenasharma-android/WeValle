package com.editprofile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import java.util.regex.Matcher;

/**
 * Created by Sumit on 9/24/2015.
 */
public class EmailFragment extends Fragment implements View.OnClickListener{

    private View mView;
    private TextView tvTitle, tvSave, tvCancel;
    private EditText edtOldEmail, edtNewEmail, edtVerifyEmail;
    private RelativeLayout save;
//    Variables  /////
    private String str_oldemail,str_newemail,str_verifyemail;
    private AlbanianPreferances pref;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new AlbanianPreferances(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.email_fragment, container, false);
        init();
        return mView;
    }

    private void init() {
        initViews();
    }

    private void initViews() {
        tvTitle = (TextView) mView.findViewById(R.id.tv_title);
        tvSave = (TextView) mView.findViewById(R.id.tv_save);
        tvCancel = (TextView) mView.findViewById(R.id.tv_cancel);
        edtOldEmail = (EditText) mView.findViewById(R.id.edt_old_email);
        edtNewEmail = (EditText) mView.findViewById(R.id.edt_new_email);
        edtVerifyEmail = (EditText) mView.findViewById(R.id.edt_verify_email);
        save = (RelativeLayout) mView.findViewById(R.id.rl_save_header);
        save.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_save_header:

                saveEmail();

                break;

            case R.id.tv_cancel:

                getActivity().onBackPressed();
                break;
        }
    }

    private void saveEmail() {

        String mTitle = getResources().getString(R.string.app_name);


        str_oldemail = edtOldEmail.getText().toString();
        str_newemail = edtNewEmail.getText().toString();
        str_verifyemail = edtVerifyEmail.getText().toString();



        Matcher mEmailMatcher = AlbanianConstants.EMAIL_ADDRESS_PATTERN
                .matcher(str_oldemail);
        Matcher mNewEmailMatcher = AlbanianConstants.EMAIL_ADDRESS_PATTERN
                .matcher(str_newemail);
        Matcher mVerifyEmailMatcher = AlbanianConstants.EMAIL_ADDRESS_PATTERN
                .matcher(str_verifyemail);


        if (str_oldemail.length() <= 0) {
            String mMessage = getString(R.string.Pleasefillemail);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }
        else if (mEmailMatcher.matches() == false) {
            // valid email
            String mMessage = getString(R.string.Signup_Pleasefillvalidemail);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }

        else if (str_newemail.length() <= 0) {

            String mMessage = getString(R.string.Signup_Pleasefillnewemail);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }
        else if (mNewEmailMatcher.matches() == false) {
            // valid email
            String mMessage = getString(R.string.Signup_Pleasefillvalidemail);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }
        else if (str_verifyemail.length() <= 0) {

            String mMessage = getString(R.string.Signup_Pleasefillverifyemail);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }
        else if (mVerifyEmailMatcher.matches() == false) {
            // valid email
            String mMessage = getString(R.string.Signup_Pleasefillvalidemail);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }



        else if (!str_newemail.equals(str_verifyemail)) {
            String mMessage = getString(R.string.Signup_newemailmatcherror);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);

        }



        else {

           updateEmail();


        }
    }

    private void updateEmail() {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "update email=" + response.toString());

                parseResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "update email Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "updateemail");
                params.put("user_email", str_newemail);
                params.put("user_id", pref.getUserData().getUserId());
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };



        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }

    private void parseResponse(String Result) {

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {



                        String mTitle = getResources().getString(R.string.app_name);

//                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
//                                ErrorMessage, false);

                        getActivity().onBackPressed();


                    } else if (ErrorCode.equals("0")) {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }
                    else
                    {
                        Log.d(AlbanianConstants.TAG, "signup error= " + ErrorCode);

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

}
