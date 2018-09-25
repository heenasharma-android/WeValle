package com.editprofile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

/**
 * Created by Sumit on 14/09/2015.
 */
public class SignUpThirteenthEditFragment extends Fragment implements View.OnClickListener {


    /**********
     * Login Fragment UI Declaration
     */
    private View mView;
    private EditText edtDescription;

    /* variables declaration */
    private AlbanianPreferances pref;
    private String  editProfile;

    private String strDesc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new AlbanianPreferances(getActivity());
    }


    @Override
    public void onStart() {
        super.onStart();

        AlbanianApplication.hideKeyBoard(getActivity(), getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.signup13, container, false);

        try {
            mView.setFocusableInTouchMode(true);
            mView.requestFocus();

            mView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            getFragmentManager().popBackStack();

                            return true;
                        }
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        init();
        return mView;
    }

    private void init() {
        if (getArguments() != null)
        {
            strDesc = getArguments().getString(AlbanianConstants.EXTRA_DESC);
        }
        iniView(mView);
        initListeners();

        edtDescription.setText(strDesc);
    }


    private void initListeners() {
    }

    private void iniView(View mView) {
        editProfile = getArguments().getString("EDIT_PROFILE");

        edtDescription = (EditText) mView.findViewById(R.id.edt_decription);
        TextView tvTitle = (TextView) mView.findViewById(R.id.tv_title);
        TextView tvSave = (TextView) mView.findViewById(R.id.tv_save);
        tvSave.setOnClickListener(this);
        TextView tvCancel = (TextView) mView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);

        if (TextUtils.isEmpty(editProfile)) {
            tvTitle.setText("");
            tvSave.setText(getActivity().getResources().getString(R.string.save));
            tvCancel.setText("Cancel");
        } else {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                if (TextUtils.isEmpty(editProfile))
                    goToNext();
                break;
            case R.id.tv_cancel:

                getFragmentManager().popBackStack();
                break;
        }
    }

    private void goToNext() {
        AlbanianApplication.hideKeyBoard(getActivity(), getActivity());
        String edt_description = edtDescription.getText().toString().trim();

        if (!TextUtils.isEmpty(edt_description) && edt_description.length() > 10) {

            uploadImages(edt_description);
        } else {
            Toast.makeText(getActivity(), getString(R.string.enter_ten_charactors), Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImages(final String strDesc) {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "update desc song=" + response.toString());

                parseResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "update desc Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "singleupdate");
                params.put("column_name", "UserDescription");
                params.put("column_value", strDesc);
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

                        getFragmentManager().popBackStack();


                    } else if (ErrorCode.equals("0")) {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }
                    else
                    {
                        Log.d(AlbanianConstants.TAG, "update desc error= " + ErrorCode);

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }


                } catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit", "update alb song exception= " + e);
                }

            }

        }
    }

}