package com.MainFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sumit on 04-Feb-18.
 */

public class UpdateFilterFragment extends BaseFragment implements View.OnClickListener {

    /*UI declaration........*/
    private View mView;

//    private RelativeLayout back;
    private RelativeLayout apply;
    private RecyclerView recycler_view_heritage;

    /* Variables...... */

    private AlbanianPreferances pref;
    private String CURRENTTABTAG;


    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        pref = new AlbanianPreferances(getActivity());

        String[] mHeritageArray =   getResources().getStringArray(R.array.heritage_array);


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

        mView = inflater.inflate(R.layout.updatefilter, container, false);

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

                params.put("api_name", "list_heritage");
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



                        JSONArray jsonArrayHeritages=jObj.getJSONArray("Heritages");

                        ArrayList<Heritage> heritageModelArrayList
                                =new ArrayList<>();

                        for (int i = 0; i < jsonArrayHeritages.length(); i++) {

                            Heritage heritageModel = new Heritage();

                            JSONObject jsonObjectHeritage=jsonArrayHeritages.getJSONObject(i);

                            String Heritage = jsonObjectHeritage.optString("Heritage");
                            heritageModel.setHeritageName(Heritage);

                            String Flag = jsonObjectHeritage.optString("Flag");
                            heritageModel.setFlag(Flag);

                            String Id = jsonObjectHeritage.optString("Id");
                            heritageModel.setHeritageId(Id);



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


        apply.setVisibility(View.VISIBLE);

        // create an Object for Adapter
        mAdapter = new HeritageDataAdapter(filterModel.getHeritageModelArrayList(),getContext());

        // set the adapter object to the Recyclerview
        recycler_view_heritage.setAdapter(mAdapter);
    }


    private void initViews() {


        apply = (RelativeLayout)mView.findViewById(R.id.rl_apply_btn);
//        apply.setVisibility(View.VISIBLE);



        apply.setOnClickListener(this);



        recycler_view_heritage = (RecyclerView) mView.findViewById(R.id.recycler_view_heritage);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler_view_heritage.setHasFixedSize(true);

        // use a linear layout manager
        recycler_view_heritage.setLayoutManager(new LinearLayoutManager(mActivity));
        recycler_view_heritage.setNestedScrollingEnabled(false);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.rl_apply_btn:

                submitFilter();

                break;



            case R.id.rl_back_header:

                FragmentManager fm = getFragmentManager();

                for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++){
                    Log.d("sumit", "upgrade no of frag.= " + fm.getBackStackEntryCount());
                    Log.d("sumit", "upgrade frag.= " + fm.getBackStackEntryAt(entry).getId());
                }

                getActivity().onBackPressed();

                break;





        }
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

            Log.d("sumit","heritageSend "+TextUtils.join(",",heritageSend));



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

                    params.put("api_name", "update_heritage");
                    params.put("user_id", pref.getUserData().getUserId());

                    params.put("user_heritage", TextUtils.join(", ",heritageSend));

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




    private void showSubmitOptions() {

        AlertDialog.Builder builderSingle;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
        {
            builderSingle = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));
        }
        else
        {
            builderSingle = new AlertDialog.Builder(
                    getActivity());
        }


//      builderSingle.setTitle("Upload image");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.select_dialog_item);


        arrayAdapter.add("Feedback");
        arrayAdapter.add("Question");


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
                            // comment report

                            sendEmail("I have feedback");

                        } else if (strName == 1) {
                            // comment report

                            sendEmail("I have a question");

                        }


                    }
                });

        builderSingle.show();

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



    private void loadMembershipStatus() {

        Bundle bundle=new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);

        mActivity.pushFragments(CURRENTTABTAG,
                new MyMembershipFragment(), false, true, bundle);
    }




    private void freeUserAlert() {
        String mTitle = getResources().getString(R.string.app_name);

        AlertDialog.Builder mAlert = new AlertDialog.Builder(getActivity());
        mAlert.setCancelable(false);
        mAlert.setTitle(mTitle);
        mAlert.setMessage("To send a message upgrade to a premium member.");

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
