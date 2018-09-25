package com.MainFragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adapter.FavoritesAdapter;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.models.RecievedMessageModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FavoritesFragment extends Fragment implements View.OnClickListener {



    /*UI declaration........*/
    private View mView;

    private ListView lvfavorites;
    private TextView headertext;
    private RelativeLayout back,nofavoritelayout;


    /* Variables...... */

    private ArrayList<RecievedMessageModel> messages_arraylist;
    private String tag_whoiviewed_obj = "jsentmessages_req";
    private AlbanianPreferances pref;
    private Activity mActivity;
    private FavoritesAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messages_arraylist=new ArrayList<>();

        pref = new AlbanianPreferances(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_favorites, container, false);



        initViews();


        getUserFavorites();

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("My Favorites");
    }

    private void initViews() {

        nofavoritelayout = (RelativeLayout) mView.findViewById(R.id.ll_nofavorite);
        lvfavorites = (ListView)mView.findViewById(R.id.lv_userfavorites);

        headertext = (TextView)mView.findViewById(R.id.txt_headerlogo);

        back = (RelativeLayout) mView.findViewById(R.id.rl_back_header);
        back.setOnClickListener(this);
        headertext.setText(mActivity.getString(R.string.Favorites));

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.mActivity=activity;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back_header:

                getFragmentManager().popBackStack();

                break;
//
//            case R.id.btn_who_i_viewd:
//
//                initAdapter(whoviewrs);
//                break;
        }
    }



    private void getUserFavorites() {


            AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "user fav= == "+response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

                parseResponse(response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "user fav= Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "user fav= Error: " + error.getMessage() + "," + error.toString());

                    AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "userfavourites");
                params.put("user_id", pref.getUserData().getUserId());
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

    private void parseResponse(String Result) {

        messages_arraylist.clear();

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        nofavoritelayout.setVisibility(View.GONE);

                        JSONArray jArray_response=jObj.getJSONArray("UserFav");


                        for (int i = 0; i < jArray_response.length(); i++) {

                            RecievedMessageModel msgModel=new RecievedMessageModel();

                            JSONObject jObj_job=jArray_response.getJSONObject(i);

                            // Job object

//                            JSONObject jObj_job=jObj_Main.getJSONObject("Payment");

                            String UserId=jObj_job.optString("UserId");
                            msgModel.setUserId(UserId);

                            String UserName=jObj_job.optString("UserName");
                            msgModel.setUserName(UserName);

                            String UserDOB=jObj_job.optString("UserDOB");
                            msgModel.setUserDOB(UserDOB);

                            String UserInterests=jObj_job.optString("UserInterests");
                            msgModel.setUserInterests(UserInterests);

                            String UserFavQuote=jObj_job.optString("UserFavQuote");
                            msgModel.setUserFavQuote(UserFavQuote);

                            String UserPresence=jObj_job.optString("UserPresence");
                            msgModel.setUserPresence(UserPresence);

                            String MessageSentTime=jObj_job.optString("MessageSentTime");
                            msgModel.setMessageSentTime(MessageSentTime);

                            String UserSubscriptionStatus=jObj_job.optString("UserSubscriptionStatus");
                            msgModel.setUserSubscriptionStatus(UserSubscriptionStatus);

                            String MsgIsReaded=jObj_job.optString("MsgIsReaded");
                            msgModel.setMsgIsReaded(MsgIsReaded);

                            String UserImageUrl=jObj_job.optString("UserImageUrl");
                            msgModel.setUserImageUrl(UserImageUrl);

                            String Age=jObj_job.optString("Age");
                            msgModel.setAge(Age);

                            String UserCity=jObj_job.optString("UserCity");
                            msgModel.setCity(UserCity);

                            String UserCountry=jObj_job.optString("UserCountry");
                            msgModel.setCountry(UserCountry);



                            String MsgContent=jObj_job.optString("MsgContent");
                            msgModel.setMsgContent(MsgContent);


                            ///////

                            messages_arraylist.add(msgModel);

                        }



                        /////////////

                        setFavoritesList(messages_arraylist);





                    }

                    else if(ErrorCode.equals("2") )
                    {
                        // no record found

                        nofavoritelayout.setVisibility(View.VISIBLE);

                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }


//                        String mTitle = getResources().getString(R.string.app_name);
//
//                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
//                                ErrorMessage, false);
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

    private void setFavoritesList(final ArrayList<RecievedMessageModel> messages_arraylist) {

        adapter = new FavoritesAdapter(getActivity(), messages_arraylist);
        lvfavorites.setAdapter(adapter);

        lvfavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                loadProfile(messages_arraylist.get(position).getUserId());


            }
        });


        lvfavorites.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                android.app.AlertDialog.Builder builderSingle;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                    builderSingle = new android.app.AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));
                } else {
                    builderSingle = new android.app.AlertDialog.Builder(
                            getActivity());
                }


//      builderSingle.setTitle("Upload image");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.select_dialog_item);


                arrayAdapter.add("Remove from favorites");


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

                                    removeFromFav(messages_arraylist.get(position));

                                }


                            }
                        });

                builderSingle.show();


                return true;
            }
        });

    }

    private void removeFromFav(final RecievedMessageModel recievedMessageModel) {


        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "remove from fav response= "+response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());
                parseRemoveFromFavResponse(response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "\"remove from fav Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "deleteuserfavourite");
                params.put("user_favorite_from", pref.getUserData().getUserId());
                params.put("user_favorite_to", recievedMessageModel.getUserId());
                params.put("AppName", AlbanianConstants.AppName);


                return params;
            }

        };



        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }

    private void parseRemoveFromFavResponse(String Result) {

        AlbanianApplication.hideProgressDialog(getActivity());

        if (Result != null) {

            {

                try {




                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {



                        getUserFavorites();


                    }

                    if (ErrorCode.equalsIgnoreCase("2")) {


                        String mTitle = getResources().getString(R.string.app_name);

                        getUserFavorites();


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


    private void loadProfile(String userId) {

        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction();
        ProfileFragment mFragment = new ProfileFragment();


        Bundle bundle = new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, userId);
        mFragment.setArguments(bundle);

        mFragmentTransaction.replace(R.id.container_body, mFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }



}
