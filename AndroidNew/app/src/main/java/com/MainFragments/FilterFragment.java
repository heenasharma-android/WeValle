package com.MainFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.adapter.HeritageDataAdapter;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.models.FilterModel;
import com.models.Heritage;
import com.models.HeritageModel;
import com.models.UserModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sumit on 04-Feb-18.
 */

public class FilterFragment extends BaseFragment implements View.OnClickListener {

    /*UI declaration........*/
    private View mView;
    private Switch goinvisible;


    private RelativeLayout back;
    private TextView min_max_age;
    private TextView apply;
    private TextView headertext;
    private TextView txt_show_me;
    private RelativeLayout rl_showme;
//    private CheckBox check_meetsingle;
    private RecyclerView recycler_view_heritage;

    /* Variables...... */

    private AlbanianPreferances pref;
    private String str_minage,str_maxage,str_ShowMe;
    private String CURRENTTABTAG;


    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        pref = new AlbanianPreferances(getActivity());

        String[] mHeritageArray =   getResources().getStringArray(R.array.heritage_array);

//        heritageModelArrayList=new ArrayList<>();



    }

    @Override
    public void onStart() {
        super.onStart();


        if (getArguments() != null) {


            CURRENTTABTAG = getArguments().getString(
                    AlbanianConstants.EXTRA_CURRENTTAB_TAG);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.filter, container, false);

        initViews();

        getUserFilter();

        return mView;
    }

    private void getUserFilter() {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "response= "+response);

                parseUserFilterResponse(response.toString());

//                AlbanianApplication.hideProgressDialog(getActivity());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

//                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "get_user_filter");
                params.put("user_id", pref.getUserData().getUserId());


                return params;
            }

        };


        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }

    private void parseUserFilterResponse(String Result) {


            AlbanianApplication.hideProgressDialog(getActivity());

            if (Result != null) {

                {

                    Log.d("sumit", "UserFilterResponse= "+Result);



                    try {

                        JSONObject jObj = new JSONObject(Result);
                        String ErrorCode = jObj.optString("ErrorCode");
                        String ErrorMessage = jObj.optString("ErrorMessage");


                        if (ErrorCode.equalsIgnoreCase("1")) {

                        FilterModel filterModel=new FilterModel();

                        String IWantTo = jObj.optString("IWantTo");
                        filterModel.setIWantTo(IWantTo);

                        String UserInterestedIn = jObj.optString("UserInterestedIn");
                        filterModel.setUserInterestedIn(UserInterestedIn);
                        str_ShowMe = UserInterestedIn;

                        String UserMinAge = jObj.optString("UserMinAge");
                        filterModel.setUserMinAge(UserMinAge);
                            str_minage=UserMinAge;

                        String UserMaxAge = jObj.optString("UserMaxAge");
                        filterModel.setUserMaxAge(UserMaxAge);
                            str_maxage=UserMaxAge;

                        String UserInvisibleStatus = jObj.optString("UserInvisibleStatus");
                        filterModel.setUserInvisibleStatus(UserInvisibleStatus);

                        String UserHeritage = jObj.optString("UserHeritage");
                        filterModel.setUserHeritage(UserHeritage);

                        JSONArray jsonArrayHeritages=jObj.getJSONArray("heritages");

                        ArrayList<Heritage> heritageModelArrayList
                                =new ArrayList<>();

                        for (int i = 0; i < jsonArrayHeritages.length(); i++) {

                            Heritage heritageModel = new Heritage();

                            JSONObject jsonObjectHeritage=jsonArrayHeritages.getJSONObject(i);

                            String Heritage = jsonObjectHeritage.optString("Heritage");
                            heritageModel.setHeritageName(Heritage);

                            String Flag = jsonObjectHeritage.optString("Flag");
                            heritageModel.setFlag(Flag);

                            boolean Is_Interested = jsonObjectHeritage.optBoolean("Is_Interested");
                            heritageModel.setInterested(Is_Interested);

//                            String Is_Interested = jsonObjectHeritage.optString("Is_Interested");
//                            heritageModel.setSelected(false);

                            heritageModelArrayList.add(heritageModel);


                        }

                        filterModel.setHeritageModelArrayList(heritageModelArrayList);


                        Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                        setFilters(filterModel);

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
                        Log.d("sumit","getOnlineUsers exception= "+e);
                    }

                }

            }


    }

    private void setFilters(FilterModel filterModel) {


        txt_show_me.setText(getShowMe(filterModel));
        min_max_age.setText("Between "+filterModel.getUserMinAge()+
                        " - "+filterModel.getUserMaxAge());


        // create an Object for Adapter
        mAdapter = new HeritageDataAdapter(filterModel.getHeritageModelArrayList(),getContext());

        // set the adapter object to the Recyclerview
        recycler_view_heritage.setAdapter(mAdapter);
    }

    private String getShowMe(FilterModel filterModel) {

        String interestedIn = null;
        switch (filterModel.getUserInterestedIn())
        {
            case "f":

                interestedIn= "Women";

                break;
            case "m":

                interestedIn=  "Men";

                break;
            case "b":

                interestedIn=  "Women & Men";

                break;

            default:
                break;

        }

        return interestedIn;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    private void initViews() {

        headertext = (TextView)mView.findViewById(R.id.txt_headerlogo);
        headertext.setText(mActivity.getString(R.string.filters));

        back = (RelativeLayout)mView.findViewById(R.id.rl_back_header);
        apply = (TextView)mView.findViewById(R.id.txt_applyfilter);
        apply.setVisibility(View.VISIBLE);
        goinvisible = (Switch)mView.findViewById(R.id.toggle_onoff);

//        check_meetsingle = (CheckBox) mView.findViewById(R.id.check_meetsingle);

        txt_show_me = (TextView)mView.findViewById(R.id.txt_show_me);
        rl_showme = (RelativeLayout)mView.findViewById(R.id.rl_showme);

        min_max_age = (TextView)mView.findViewById(R.id.txt_min_max_age);

//        min_max_age.setText(pref.getUserData().getUserMinAge()+"-"+
//                pref.getUserData().getUserMaxAge());


        rl_showme.setOnClickListener(this);

        min_max_age.setOnClickListener(this);

        back.setOnClickListener(this);
        apply.setOnClickListener(this);

        goinvisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {



                if (pref.getUserData().getUserSubscriptionStatus().equals("0"))
                {

                    goinvisible.setOnCheckedChangeListener(null);
                    goinvisible.setChecked(false);
                    freeUserAlert();

                }
                else
                {
                    if (isChecked) {

                        goInvisible(1);
                    }
                    else
                    {

                        goInvisible(0);
                    }
                }


            }
        });

        recycler_view_heritage = (RecyclerView) mView.findViewById(R.id.recycler_view_heritage);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler_view_heritage.setHasFixedSize(true);

        // use a linear layout manager
        recycler_view_heritage.setLayoutManager(new LinearLayoutManager(mActivity));
        recycler_view_heritage.setNestedScrollingEnabled(false);



    }



    private void goInvisible(final int i) {


        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");



        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, response.toString());

                parseResponse(response.toString());

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

                params.put("api_name", "goinvisible");
                params.put("user_id", pref.getUserData().getUserId());
                params.put("user_invisible_status", String.valueOf(i));
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

    private void parseResponse(String s) {


    }






    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.txt_applyfilter:

                submitFilter();

                break;
            case R.id.txt_min_max_age:

                showAgesDalog();

                break;

            case R.id.tv_block_list:

                loadBlockedList();

                break;


            case R.id.tv_deactivate_account:

                showDeactivateAccount();
                break;

            case R.id.rl_showme:

                showMaleFemaleDialog();

                break;

            case R.id.rl_back_header:

                FragmentManager fm = getFragmentManager();

                for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++){
                    Log.d("sumit", "upgrade no of frag.= " + fm.getBackStackEntryCount());
                    Log.d("sumit", "upgrade frag.= " + fm.getBackStackEntryAt(entry).getId());
                }

                getActivity().onBackPressed();

                break;


            case R.id.rl_logout_btn:

                logoutAlert();

                break;


        }
    }

    private void showMaleFemaleDialog() {

        android.app.AlertDialog.Builder builderSingle;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
        {
            builderSingle = new android.app.AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));
        }
        else
        {
            builderSingle = new android.app.AlertDialog.Builder(
                    getActivity());
        }


//      builderSingle.setTitle("Upload image");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.select_dialog_item);


        arrayAdapter.add("Women");
        arrayAdapter.add("Men");
        arrayAdapter.add("Women & Men");


        builderSingle.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        long strName = arrayAdapter.getItemId(which);


                        if (strName == 0) {
                            // Women
                            str_ShowMe="f";
                            txt_show_me.setText("Women");


                        } else if (strName == 1) {
                            // Men
                            str_ShowMe="m";
                            txt_show_me.setText("Men");


                        }
                        else if (strName == 2) {
                            // Both
                            str_ShowMe ="b";
                            txt_show_me.setText("Women & Men");

                        }


                    }
                });

        builderSingle.show();

    }

    private void submitFilter() {

//        String data = "";
        List<Heritage> stList = ((HeritageDataAdapter) mAdapter)
                .getHeritageList();

        ArrayList<String> heritageSend=new ArrayList<>();

        for (int i = 0; i < stList.size(); i++) {
            Heritage heritageModel = stList.get(i);
            if (heritageModel.isInterested() == true) {

//                data = data + "," + heritageModel.getHeritageName().toString();

                heritageSend.add(heritageModel.getHeritageName().toString());
      /*
       * Toast.makeText( CardViewActivity.this, " " +
       * singleStudent.getName() + " " +
       * singleStudent.getEmailId() + " " +
       * singleStudent.isSelected(),
       * Toast.LENGTH_SHORT).show();
       */
            }

        }


        if (heritageSend.size() <=0) {

            String mTitle = getResources().getString(R.string.app_name);

            AlbanianApplication.ShowAlert(getActivity(), mTitle,
                    "Choose at least one heritage.", false);

        }
        else
        {

            AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");



            Log.d("sumit","pref.getUserData().getUserId() "+pref.getUserData().getUserId());
            Log.d("sumit","str_ShowMe "+str_ShowMe);
            Log.d("sumit","heritageSend "+TextUtils.join(",",heritageSend));
            Log.d("sumit","str_minage "+str_minage);
            Log.d("sumit","str_maxage "+str_maxage);
            Log.d("sumit","goinvisible.isChecked() "+goinvisible.isChecked());


            StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(AlbanianConstants.TAG, "age range= " + response.toString());
                    AlbanianApplication.hideProgressDialog(getActivity());

                    parseUpdateFilterResponse(response.toString());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(AlbanianConstants.TAG, "age range= Error: " + error.getMessage());
                    Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                    AlbanianApplication.hideProgressDialog(getActivity());

                }
            })

            {



                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("api_name", "post_user_filter");
                    params.put("user_id", pref.getUserData().getUserId());
                    params.put("user_interested_in",str_ShowMe);
                    params.put("user_min_age", str_minage );
                    params.put("user_max_age", str_maxage );
                    params.put("user_invisible_status", goinvisible.isChecked() ?"1":"0" );
                    params.put("heritage_interested_in", TextUtils.join(", ",heritageSend));

//                params.put("AppName", AlbanianConstants.AppName);

                    return params;
                }

            };


            AlbanianApplication.getInstance().addToRequestQueue(sr);

        }




    }

    private void parseUpdateFilterResponse(String Result) {

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");

                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {

                        getActivity().onBackPressed();


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


    private void logoutAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("LOG OUT");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to logout?");

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.delete);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                dialog.cancel();
                pref.logoutUser(getActivity(), pref.getUserData().getUserId());


            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void showAgesDalog() {

        final Dialog d = new Dialog(getActivity());
        d.setTitle("Select Age");
        d.setContentView(R.layout.agegroup_dialog);
        Button b1 = (Button) d.findViewById(R.id.btn_ok);
        Button b2 = (Button) d.findViewById(R.id.btn_cancel);
        final NumberPicker numberPicker_min = (NumberPicker) d.findViewById(R.id.numberPicker_min);
        numberPicker_min.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker_min.setMaxValue(55); // max value 100
        numberPicker_min.setMinValue(18);   // min value 0
        numberPicker_min.setWrapSelectorWheel(false);

        final NumberPicker numberPicker_max = (NumberPicker) d.findViewById(R.id.numberPicker_max);
        numberPicker_max.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker_max.setMaxValue(55); // max value 100
        numberPicker_max.setMinValue(18);   // min value 0
        numberPicker_max.setValue(55);
        numberPicker_max.setWrapSelectorWheel(false);
//        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(numberPicker_max.getValue()-numberPicker_min.getValue()<4)
                {
                    AlbanianApplication.ShowAlert(getActivity(),getActivity().getResources().getString(R.string.app_name),
                            "The maximum age must be (4) four greater than the minimum age.",false);

                    str_minage=String.valueOf(numberPicker_min.getValue());
                    str_maxage=String.valueOf(numberPicker_min.getValue()+4);


//                    setAgePreferances(str_minage,str_maxage);

                    min_max_age.setText("Between "+str_minage+
                            " - "+str_maxage);
                }
                else if(numberPicker_min.getValue()<18)
                {
                    AlbanianApplication.ShowAlert(getActivity(),getActivity().getResources().getString(R.string.app_name),
                            "The minimum age must be 18.",false);
                }
                else if(numberPicker_max.getValue()>55)
                {
                    AlbanianApplication.ShowAlert(getActivity(),getActivity().getResources().getString(R.string.app_name),
                            "The maximum age must be less than 55.",false);
                }
                else
                {
                    str_minage=String.valueOf(numberPicker_min.getValue());
                    str_maxage=String.valueOf(numberPicker_max.getValue());


//                    setAgePreferances(str_minage,str_maxage);

                    min_max_age.setText("Between "+str_minage+
                            " - "+str_maxage);
                    //set the value to textview
                }


                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d.dismiss();
            }
        });
        d.show();
    }




    private void showDeactivateAccount() {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to deactivate your account?");

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.delete);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                deactivateAccount();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();


    }


    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("Upgrade Screen");

        //mActivity.hideCurrentTab();
    }

    private void deactivateAccount() {

        pref.deactivateUserAccount(getActivity(),pref.getUserData().getUserId(),"","");
    }


    private void setAgePreferances(final String minAge, final String maxAge) {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");



        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "age range= " + response.toString());
                AlbanianApplication.hideProgressDialog(getActivity());

//                parseResponse(response.toString());
                parseAgePreferanceResponse(response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "age range= Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "updateagerange");
                params.put("user_min_age", minAge );
                params.put("user_max_age", maxAge );
                params.put("user_id", pref.getUserData().getUserId() );
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };


        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }

    private void parseAgePreferanceResponse(String Result) {



        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        UserModel userModel=pref.getUserData();
                        userModel.setUserMinAge(str_minage);
                        userModel.setUserMaxAge(str_maxage);

                        pref.setUserData(userModel);

                        min_max_age.setText(str_minage+ "-" + str_maxage);



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


    private void loadBlockedList() {


        Bundle bundle=new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);

        mActivity.pushFragments(CURRENTTABTAG,
                new BloackedUserFragment(), false, true, bundle);
    }

    private void loadMembershipStatus() {

        Bundle bundle=new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);

        mActivity.pushFragments(CURRENTTABTAG,
                new MyMembershipFragment(), false, true, bundle);
    }


    private void loadTosPage(String extra) {


        String url = extra;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }


    private void freeUserAlert() {
        String mTitle = getResources().getString(R.string.app_name);

        AlertDialog.Builder mAlert = new AlertDialog.Builder(getActivity());
        mAlert.setCancelable(false);
        mAlert.setTitle(mTitle);
        mAlert.setMessage("Upgrade to premium to go invisible.");

        mAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                Bundle bundle=new Bundle();
                bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
                        CURRENTTABTAG);

                mActivity.pushFragments(CURRENTTABTAG,
                        new UpgradeFragment(), false, true, bundle);


            }
        });

        mAlert.show();
    }


}
