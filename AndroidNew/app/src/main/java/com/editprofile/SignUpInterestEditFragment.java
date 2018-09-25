package com.editprofile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.adapter.InterestsAdapter;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sumit on 14/09/2015.
 */
public class SignUpInterestEditFragment extends Fragment implements View.OnClickListener {

    /**********
     * Fragment UI Declaration
     */
    private View mView;
    private ListView lvLanguages;

    /* variables declaration */

    private AlbanianPreferances pref;
    private String  editProfile;

    private SqlLiteDbHelper dbHelper;
    private static List<String> interestArrayList;
    private String intereststosend;

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
        mView = inflater.inflate(R.layout.signupinterest, container, false);
        init();
        return mView;
    }

    private void init() {

        if (getArguments() != null)
        {
            String strLang = getArguments().getString(AlbanianConstants.EXTRA_INTEREST);

            interestArrayList = new ArrayList<String>(Arrays.asList(strLang.split("\\s*,\\s*")));



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
        lvLanguages = (ListView) mView.findViewById(R.id.lv_languages);
//        String[] arrayLanguages = getResources().getStringArray(R.array.array_languages);

        ArrayList<String> arrayLanguages = new ArrayList<>();

        try {
            dbHelper = new SqlLiteDbHelper(getActivity());
            dbHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        arrayLanguages = dbHelper.getInterestList();

        initAdapter(arrayLanguages);
    }

    private void initAdapter(final ArrayList<String> viewrsList) {

        final InterestsAdapter adapter = new InterestsAdapter(getActivity(), viewrsList);
        // adapter.setData(usermodel);
        lvLanguages.setAdapter(adapter);
        lvLanguages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                adapter.toggleChecked(position);
                adapter.notifyDataSetChanged();

//                if (usermodel != null) {

                    interestArrayList = adapter.getCheckedServicesName();

                    intereststosend = interestArrayList.toString().replace("[", "").replace("]", "");

                    Log.d("sumit", "intereststosend NameS = " + intereststosend);


//                    usermodel.setUserOtherLanguages(languages);
//                }


            }
        });


        if (interestArrayList != null && interestArrayList.size()>0) {

            int pos=-1;

            for (int i = 0; i <interestArrayList.size() ; i++) {

                for (int j = 0; j < viewrsList.size(); j++) {

                    if (interestArrayList.get(i).equals(viewrsList.get(j))) {

                        pos=j;
//                      adapter.selectedPosition = pos;
                        adapter.toggleChecked(pos);
                        adapter.notifyDataSetChanged();

                    }
                }
            }



        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_save:
                String mTitle = getResources().getString(R.string.app_name);

                if (interestArrayList!=null && interestArrayList.size()>0)
                {
                    loadUserLanguages(intereststosend);
                }
                else
                {
                    String mMessage = getString(R.string.Signup_Pleaseselectone);
                    AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
                }

                break;

            case R.id.tv_cancel:
                getActivity().onBackPressed();
                break;


        }
    }

    private void loadUserLanguages(final String intereststosend) {


        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response)

            {


                Log.d(AlbanianConstants.TAG, "intereststosend response= "+response.toString());

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
                params.put("column_name", "UserInterests");
                params.put("column_value", intereststosend);
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

                        getFragmentManager().popBackStack();



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