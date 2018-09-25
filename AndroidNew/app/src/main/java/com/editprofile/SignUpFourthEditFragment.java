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
public class SignUpFourthEditFragment extends Fragment implements View.OnClickListener {

    /**********
     * Fragment UI Declaration
     */
    private View mView;
//    private ListView lvLanguages;
    private EditText languages;

    /* variables declaration */

    private AlbanianPreferances pref;
    private String str_username, editProfile;

//    private SqlLiteDbHelper dbHelper;
//    private static List<String> languageList;
//    private String languagestosend;
    private String languagesSelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new AlbanianPreferances(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();

        AlbanianApplication.hideKeyBoard(getActivity(), getActivity());

        editProfile = getArguments().getString("EDIT_PROFILE");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.signup_languages, container, false);

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
            String strLang = getArguments().getString(AlbanianConstants.EXTRA_OTHERLANGUAGES);

//            languageList = new ArrayList<String>(Arrays.asList(strLang.split("\\s*,\\s*")));

            languagesSelected=strLang;


        }

        iniView(mView);
        initListeners();
    }

    private void initListeners() {
    }


    private void iniView(View mView) {
        editProfile = getArguments().getString("EDIT_PROFILE");

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
//        lvLanguages = (ListView) mView.findViewById(R.id.lv_languages);
        languages = (EditText) mView.findViewById(R.id.edt_languages);
//        String[] arrayLanguages = getResources().getStringArray(R.array.array_languages);

//        ArrayList<String> arrayLanguages = new ArrayList<>();
//
//        try {
//            dbHelper = new SqlLiteDbHelper(getActivity());
//            dbHelper.openDataBase();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        arrayLanguages = dbHelper.getLanguagesList();

//        initAdapter(arrayLanguages);

        if (languagesSelected != null) {

            languages.setText(languagesSelected);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_save:
                String mTitle = getResources().getString(R.string.app_name);

                String languagesString=languages.getText().toString();

//                if (languageList!=null && languageList.size()>0)
                if (languagesString!=null && languagesString.length()>0)
                {
                    loadUserLanguages(languagesString);
                }
                else
                {
                    String mMessage = getString(R.string.Signup_Pleasefilllanguages);
                    AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
                }

                break;

            case R.id.tv_cancel:
//                getActivity().onBackPressed();

                getFragmentManager().popBackStack();
                break;


        }
    }

    private void loadUserLanguages(final String languagestosend) {


        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response)

            {


                Log.d(AlbanianConstants.TAG, "languagestosend response= "+response.toString());

                parseResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(AlbanianConstants.TAG, "languagestosend Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "singleupdate");
                params.put("column_name", "UserOtherLanguages");
                params.put("column_value", languagestosend);
                params.put("user_id", pref.getUserData().getUserId());
                params.put("AppName", AlbanianConstants.AppName);


                return params;
            }

        };

        // Adding request to request queue

        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }



    private void parseResponse(String Result) {


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


                        AlbanianApplication.hideProgressDialog(getActivity());

                        getFragmentManager().popBackStack();



//                        JSONArray jArray_response=jObj.getJSONArray("UserImages");


//                        for (int i = 0; i < jArray_response.length(); i++) {
//
//                            UserImagesModel msgModel=new UserImagesModel();
//
//                            JSONObject jObj_job=jArray_response.getJSONObject(i);
//
//                            // Job object
//
////                            JSONObject jObj_job=jObj_Main.getJSONObject("Payment");
//
//                            String ImageId=jObj_job.optString("ImageId");
//                            msgModel.setImageId(ImageId);
//
//                            String UserImageUrl=jObj_job.optString("UserImageUrl");
//                            msgModel.setUserImageUrl(UserImageUrl);
//
//
//                            String IsUserProfileImage=jObj_job.optString("IsUserProfileImage");
//                            msgModel.setIsUserProfileImage(IsUserProfileImage);
//
//                            ///////
//
//
//                        }



                        /////////////




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
}