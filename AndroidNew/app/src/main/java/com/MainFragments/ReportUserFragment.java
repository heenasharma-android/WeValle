package com.MainFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.models.SignupModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ReportUserFragment extends Fragment implements
        View.OnClickListener{

    /*UI declaration*/

    private View mView;
    private Spinner spreason;
//    private Spinner spaction;
    private EditText details;
    private Button cancel,submit;
    private ImageView back;


     /* variables declaration*/

    private ArrayList<String> reasonsList;
    private ArrayList<String> actionsList;

    private AlbanianPreferances pref;
    private String profileVisitedID,CURRENTTABTAG;
String userId;
    public String str_reason/*,str_action*/;

    public static ReportUserFragment newInstance(String parameter) {
        Bundle args = new Bundle();
        args.putString("parameter", parameter);
        ReportUserFragment fragment = new ReportUserFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new AlbanianPreferances(getActivity());

        reasonsList=new ArrayList<>();
        reasonsList.add("Abusive User");
        reasonsList.add("Incomlplete/Nonsense Profile");
        reasonsList.add("Indecent Images");
        reasonsList.add("Copyright/Cartoon Images");
        reasonsList.add("Underage user");

        actionsList=new ArrayList<>();
        actionsList.add("Delete User's Account");
        actionsList.add("Delete Image/Modify profile");
        actionsList.add("Send User a Warning");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (getArguments() != null) {
            userId = getArguments().getString("parameter");
        }

        if(mView!=null)
        {
            ViewGroup parent =(ViewGroup)mView.getParent();
            if(parent!=null)
                parent.removeView(mView);
        }
        try
        {
            mView = inflater.inflate(R.layout.reportuser, container, false);

            init(mView);
        }
        catch (Exception e)
        {

        }


        return mView;

    }



    private void init(View mView) {

        cancel = (Button) mView.findViewById(R.id.btn_cancelreport);
        cancel.setOnClickListener(this);

        back = (ImageView) mView.findViewById(R.id.img_back);
        back.setOnClickListener(this);

        submit = (Button) mView.findViewById(R.id.btn_submitreport);
        submit.setOnClickListener(this);

        details = (EditText) mView.findViewById(R.id.edt_additionaldetails);
        spreason = (Spinner) mView.findViewById(R.id.sp_reportreason);
//        spaction = (Spinner) mView.findViewById(R.id.sp_action);


        spreason.setAdapter(new ReasonAdapter(getActivity(), R.layout.country_spinner_item,
                reasonsList));

        spreason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                str_reason = reasonsList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        spaction.setAdapter(new ActionAdapter(getActivity(), R.layout.country_spinner_item,
//                actionsList));

//        spaction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                str_action= actionsList.get(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


    }

    private void reportUser(final String str_reason, final String str_action) {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "reportuser= " + response.toString());

                parseResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "reportspam Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("api_name", "reportspam");
                params.put("self_user_id", pref.getUserData().getUserId());
                params.put("spam_reason", str_reason);
//                params.put("spam_action", str_action);
                params.put("spam_detail", details.getText().toString().trim());
                params.put("criminal_id", userId);
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        // Adding request to request queue

        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }





    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("Reportuser Screen");

    }

    @Override
    public void onStart() {
        super.onStart();

//        showWarningAlert();

        if (getArguments() != null)
        {

            profileVisitedID = getArguments().getString(
                    AlbanianConstants.EXTRA_PROFILEVISITEDID);

            CURRENTTABTAG = getArguments().getString(
                    AlbanianConstants.EXTRA_CURRENTTAB_TAG);

        }
    }

    private void showWarningAlert() {

        AlertDialog.Builder mAlert = new AlertDialog.Builder(getActivity());
        mAlert.setCancelable(false);
        mAlert.setTitle(getString(R.string.app_name));
        mAlert.setMessage(getString(R.string.reportwarning));
        {
            mAlert.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                            performBack();

                        }
                    });
        }

        mAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        mAlert.show();
    }

    private void performBack() {

        getActivity().onBackPressed();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_submitreport:

                String mTitle = getResources().getString(R.string.app_name);
                if (str_reason==null) {
                    String mMessage = getString(R.string.Signup_Pleasefillreason);
                    AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
                }
                else {
                    Log.d("sumit", "reason value= " + str_reason);
                    reportUser(str_reason,"");


                }

                break;

            case R.id.btn_cancelreport:

                 performBack();

                 break;

            case R.id.img_back:

                 performBack();

                 break;



        }
    }

    private void validateForm() {


//        try {
//            AlbanianApplication.hideKeyBoard(getActivity(), getActivity());
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //// validate date

        String mTitle = getResources().getString(R.string.app_name);
         if (str_reason.length() == 0) {
            String mMessage = getString(R.string.Signup_Pleasefillreason);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }
        else {
            Log.d("sumit", "reason value= " + str_reason);
            reportUser(str_reason,"");


        }
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

                            getActivity().onBackPressed();
                            getActivity().onBackPressed();



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
                    Log.d("sumit","profile exception= "+e);
                }

            }

        }

    }





    private void setProfileDetail(SignupModel profilemodel) {

        Log.d("sumit","profile user gallery= "+profilemodel.getUserImages());
        Log.d("sumit","profile user gallery size= "+profilemodel.getUserImages().size());



    }


    ///////spinner adapter for Reason for report

    public class ReasonAdapter extends ArrayAdapter<String> {
        public ReasonAdapter(Context ctx, int txtViewResourceId, ArrayList<String> objects) {
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
            main_text.setText(reasonsList.get(position));


            return mySpinner;
        }
    }

    public class ActionAdapter extends ArrayAdapter<String> {
        public ActionAdapter(Context ctx, int txtViewResourceId, ArrayList<String> objects) {
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
            main_text.setText(actionsList.get(position));


            return mySpinner;
        }
    }
}
