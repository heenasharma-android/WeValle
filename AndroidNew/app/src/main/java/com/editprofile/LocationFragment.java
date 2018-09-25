package com.editprofile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.models.CountryModel;
import com.models.SqlLiteDbHelper;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumit on 9/24/2015.
 */
public class LocationFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private Spinner spCountry/*, spStates*/;
    private EditText city;

    private RelativeLayout save_header;


    /*Variables*/
    private SqlLiteDbHelper dbHelper;
    private ArrayList<CountryModel> countriesList;
//    private ArrayList<CountryModel> statesList;
    private AlbanianPreferances pref;
//    private String str_statename;
    private String str_countryname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new AlbanianPreferances(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.location_fragment, container, false);
        init();
        return mView;
    }

    private void init() {
        initViews();

    }

    private void initViews() {


        spCountry = (Spinner) mView.findViewById(R.id.sp_country);
//        spStates = (Spinner) mView.findViewById(R.id.sp_state);
        save_header = (RelativeLayout) mView.findViewById(R.id.rl_save_header);
        save_header.setOnClickListener(this);

        TextView tvTitle = (TextView) mView.findViewById(R.id.tv_title);
        TextView tvSave = (TextView) mView.findViewById(R.id.tv_save);
        TextView tvCancel = (TextView) mView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);
        city = (EditText) mView.findViewById(R.id.edt_new_city);


        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                str_countryname=countriesList.get(position).getCountry_name();

//                statesList = dbHelper.getStateList(countriesList.get(position).getCountry_id());

//                Log.d("sumit", "states are= " + statesList.get(0).getState_name());

//                spStates.setAdapter(new StatesAdapter(getActivity(), R.layout.country_spinner_item,
//                        statesList));

//                spStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                        str_statename = statesList.get(position).getState_name();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addSpinnerCountry();

    }


    public void addSpinnerCountry() {
        countriesList = new ArrayList<>();
//        statesList = new ArrayList<>();

        try {
            dbHelper = new SqlLiteDbHelper(getActivity());
            dbHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        countriesList = dbHelper.getCountryList();


        spCountry.setAdapter(new MyAdapter(getActivity(), R.layout.country_spinner_item,
                countriesList));


    }

    ///////spinner adapter for country

    public class MyAdapter extends ArrayAdapter<CountryModel> {
        public MyAdapter(Context ctx, int txtViewResourceId, ArrayList<CountryModel> objects) {
            super(ctx, txtViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View mySpinner = inflater.inflate(R.layout.country_spinner_item, parent, false);
            TextView main_text = (TextView) mySpinner.findViewById(R.id.sub_text_seen);
            main_text.setText(countriesList.get(position).getCountry_name());


            return mySpinner;
        }
    }

//    public class StatesAdapter extends ArrayAdapter<CountryModel> {
//        public StatesAdapter(Context ctx, int txtViewResourceId, ArrayList<CountryModel> objects) {
//            super(ctx, txtViewResourceId, objects);
//        }
//
//        @Override
//        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
//            return getCustomView(position, cnvtView, prnt);
//        }
//
//        @Override
//        public View getView(int pos, View cnvtView, ViewGroup prnt) {
//            return getCustomView(pos, cnvtView, prnt);
//        }
//
//        public View getCustomView(int position, View convertView, ViewGroup parent) {
//            LayoutInflater inflater = LayoutInflater.from(getActivity());
//            View mySpinner = inflater.inflate(R.layout.country_spinner_item, parent, false);
//            TextView main_text = (TextView) mySpinner.findViewById(R.id.sub_text_seen);
//            main_text.setText(statesList.get(position).getState_name());
//
//
//            return mySpinner;
//        }
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                getFragmentManager().popBackStack();

                break;
            case R.id.rl_save_header:

                saveCity();


                break;


        }
    }

    private void saveCity() {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "update desc song=" + response.toString());

                parseResponse(response.toString());

//                AlbanianApplication.hideProgressDialog(getActivity());

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
                params.put("column_name", "UserCity");
                params.put("column_value", city.getText().toString().trim());
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


//                        saveState(str_statename);
                        saveCountry(str_countryname);


                    } else if (ErrorCode.equals("0")) {

//                        String mTitle = getResources().getString(R.string.app_name);
//
//                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
//                                ErrorMessage, false);
                    } else {
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

//    private void saveState(final String str_statename) {
//
////        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");
//
//
//        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d(AlbanianConstants.TAG, "update desc song=" + response.toString());
//                AlbanianApplication.hideProgressDialog(getActivity());
//                parseStateResponse(response.toString());
//
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(AlbanianConstants.TAG, "update desc Error: " + error.getMessage());
//                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());
//
//                AlbanianApplication.hideProgressDialog(getActivity());
//
//            }
//        })
//
//        {
//
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("api_name", "singleupdate");
//                params.put("column_name", "UserState");
//                params.put("column_value", str_statename);
//                params.put("UserId", pref.getUserData().getUserId());
//
//
//                return params;
//            }
//
//        };
//
//
//        AlbanianApplication.getInstance().addToRequestQueue(sr);
//    }

//    private void parseStateResponse(String Result) {
//
//        if (Result != null) {
//
//            {
//
//                try {
//
//                    JSONObject jObj = new JSONObject(Result);
//                    String ErrorCode = jObj.optString("ErrorCode");
//                    String ErrorMessage = jObj.optString("ErrorMessage");
//
//
//                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);
//
//                    if (ErrorCode.equalsIgnoreCase("1")) {
//
//                        String mTitle = getResources().getString(R.string.app_name);
//
////                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
////                                ErrorMessage, false);
//
//                        saveCountry(str_countryname);
//
//
//
//
//
//                    } else if (ErrorCode.equals("0")) {
//
//
//                    } else {
//                        Log.d(AlbanianConstants.TAG, "update desc error= " + ErrorCode);
//
//                        String mTitle = getResources().getString(R.string.app_name);
//
//                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
//                                ErrorMessage, false);
//                    }
//
//
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
////                        e.printStackTrace();
//                    Log.d("sumit", "update alb song exception= " + e);
//                }
//
//            }
//        }
//    }

    private void saveCountry(final String str_countryname) {

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "update desc song=" + response.toString());
                AlbanianApplication.hideProgressDialog(getActivity());
                parseCountryResponse(response.toString());



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
                params.put("column_name", "UserCountry");
                params.put("column_value", str_countryname);
                params.put("user_id", pref.getUserData().getUserId());
                params.put("AppName", AlbanianConstants.AppName);


                return params;
            }

        };


        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }

    private void parseCountryResponse(String Result) {

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

                        performBack();





                    } else if (ErrorCode.equals("0")) {


                    } else {
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

    private void performBack() {

        getFragmentManager().popBackStack();
    }
}
