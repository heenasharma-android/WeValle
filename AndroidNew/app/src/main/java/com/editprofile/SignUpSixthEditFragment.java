package com.editprofile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.adapter.UserHeightAdapter;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.models.SqlLiteDbHelper;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumit on 14/09/2015.
 */
public class SignUpSixthEditFragment extends Fragment implements View.OnClickListener {


    /**********
     * Login Fragment UI Declaration
     */

    private View mView;
    private ListView lvPets;

    /* variables declaration */

    private AlbanianPreferances pref;
    private String str_username,editProfile;
//    private UserModel usermodel;
    private SqlLiteDbHelper dbHelper;
    private String strPets;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new AlbanianPreferances(getActivity());
    }


    @Override
    public void onStart() {
        super.onStart();

        AlbanianApplication.hideKeyBoard(getActivity(), getActivity());

        if (AlbanianApplication.isInternetOn(getActivity())) {

        } else {
            String mTitle = getResources().getString(R.string.app_name);

            AlbanianApplication.ShowAlert(getActivity(), mTitle,
                    getResources().getString(R.string.nointernet), false);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.signup6, container, false);

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

            strPets = getArguments().getString(AlbanianConstants.EXTRA_PETS);

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

        if(TextUtils.isEmpty(editProfile)) {
            tvTitle.setText("");
            tvSave.setText(getActivity().getResources().getString(R.string.save));
            tvCancel.setText("Cancel");
        }else{

        }
        lvPets = (ListView) mView.findViewById(R.id.lv_pets);
//        String[] arrayPets = getResources().getStringArray(R.array.array_pets);

        ArrayList<String> arrayPets = new ArrayList<>();

        try {
            dbHelper = new SqlLiteDbHelper(getActivity());
            dbHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        arrayPets = dbHelper.getPetList();

        initAdapter(arrayPets);
    }

    private void initAdapter(final ArrayList<String> viewrsList) {

        final UserHeightAdapter adapter = new UserHeightAdapter(getActivity(), viewrsList);
        // adapter.setData(usermodel);
        lvPets.setAdapter(adapter);
        lvPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                strPets = viewrsList.get(position).toString();

                adapter.selectedPosition = position;
                adapter.notifyDataSetChanged();

            }
        });


        if (strPets != null) {

            int pos=-1;

            for (int i = 0; i <viewrsList.size() ; i++) {

                if (strPets.equals(viewrsList.get(i))) {

                    pos=i;
                    adapter.selectedPosition = pos;
                    adapter.notifyDataSetChanged();
                    break;
                }
            }


            lvPets.setSelection(pos);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                String mTitle = getResources().getString(R.string.app_name);
                if (strPets!=null && strPets.length() > 0) {

                    loadSignupSeventh(strPets);
                }
                else
                {
                    String mMessage = getString(R.string.Signup_Pleaseselectone);
                    AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
                }

                break;

            case R.id.tv_cancel:
//                getActivity().onBackPressed();
                getFragmentManager().popBackStack();

                break;
        }
    }

    private void loadSignupSeventh(final String strPets) {

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

                params.put("api_name", "singleupdate");
                params.put("column_name", "UserPets");
                params.put("column_value", strPets);
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

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }


                } catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit", "pets update exception= " + e);
                }

            }

        }
    }

}