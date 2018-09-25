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

/**
 * Created by Sumit on 9/24/2015.
 */
public class PasswordFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private TextView tvTitle, tvSave, tvCancel;
    private EditText edtOldPwd, edtNewPwd, edtVerifyPwd;
    private RelativeLayout save;

    /*Variables */
    private AlbanianPreferances pref;
    private String str_oldpassword,str_newpassword,str_verifypassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new AlbanianPreferances(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.password_fragment, container, false);
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
        edtOldPwd = (EditText) mView.findViewById(R.id.edt_old_password);
        edtNewPwd = (EditText) mView.findViewById(R.id.edt_new_password);
        edtVerifyPwd = (EditText) mView.findViewById(R.id.edt_verify_password);

        save = (RelativeLayout) mView.findViewById(R.id.rl_save_header);
        save.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_save_header:

                savePassword();

                break;

            case R.id.tv_cancel:
//                Intent intent=new Intent(getContext(), SettingActivity.class);
//                startActivity(intent);
                getActivity().onBackPressed();

                break;
        }
    }

    private void savePassword() {

        String mTitle = getResources().getString(R.string.app_name);


        str_oldpassword = edtOldPwd.getText().toString();
        str_newpassword = edtNewPwd.getText().toString();
        str_verifypassword = edtVerifyPwd.getText().toString();






        if (str_oldpassword.length() <= 0) {
            String mMessage = getString(R.string.Signup_Pleasefillpassword);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }


        else if (str_newpassword.length() <= 0) {

            String mMessage = getString(R.string.Signup_Pleasefillnewpassword);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }

        else if (str_verifypassword.length() <= 0) {

            String mMessage = getString(R.string.Signup_Pleasefillverifypassword);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }




        else if (!str_newpassword.equals(str_verifypassword)) {
            String mMessage = getString(R.string.Signup_passwordmatcherror);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);

        }



        else {

            updatePassword();


        }

    }

    private void updatePassword() {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "update password=" + response.toString());

                parseResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "update password Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "updateemail");
                params.put("user_email", pref.getUserData().getUserEmail());
                params.put("user_password", str_newpassword);
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

//                        onBackPressed();
//

                    } else if (ErrorCode.equals("0")) {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }
                    else
                    {
                        Log.d(AlbanianConstants.TAG, "password error= " + ErrorCode);

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
