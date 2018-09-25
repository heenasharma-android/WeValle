package com.MainFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.adapter.MessagesAdapter;
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

/**
 * Created by Sumit on 20/09/2015.
 */
public class SentMessagesFragment extends BaseFragment implements View.OnClickListener {



    /*UI declaration........*/
    private View mView;

    private ListView lvProfileViewd;



    /* Variables...... */

    private ArrayList<RecievedMessageModel> messages_arraylist;
    private String tag_whoiviewed_obj = "jsentmessages_req";
    private AlbanianPreferances pref;


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

        mView = inflater.inflate(R.layout.viewedmelist, container, false);



        initViews();


        getWhoIViewed();

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



    private void initViews() {

        lvProfileViewd = (ListView)mView.findViewById(R.id.lv_peopleviewedme);



    }

    @Override
    public void onResume() {
        super.onResume();


        AlbanianApplication.getInstance().trackScreenView("Sent Messages");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }



    private void getWhoIViewed() {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());
                parseResponse(response.toString());



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

                params.put("api_name", "sendmessage");
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


                        String mTitle = getResources().getString(R.string.app_name);



                        JSONArray jArray_response=jObj.getJSONArray("Messages");


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

                            String MsgContent=jObj_job.optString("MsgContent");
                            msgModel.setMsgContent(MsgContent);


                            ///////

                            messages_arraylist.add(msgModel);

                        }



                        /////////////

                        setWalletHistoryList(messages_arraylist);





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

    private void setWalletHistoryList(final ArrayList<RecievedMessageModel> messages_arraylist) {

        MessagesAdapter adapter = new MessagesAdapter(getActivity(), getActivity(),messages_arraylist, false,mActivity);
        lvProfileViewd.setAdapter(adapter);

        lvProfileViewd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                loadChatFragment(messages_arraylist.get(position));
            }
        });
    }


    private void loadChatFragment(RecievedMessageModel userModel) {


        Log.d("sumit","sent image url= "+userModel.getUserImageUrl());

//        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction mFragmentTransaction = mFragmentManager
//                .beginTransaction();
//        ChatFragment mFragment = new ChatFragment();


        Bundle bundle = new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, userModel.getUserId());
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDNAME, userModel.getUserName());
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDIMAGE, userModel.getUserImageUrl());
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDAGE, userModel.getAge());
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDQUOTE, userModel.getUserFavQuote());
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDINTEREST, userModel.getUserInterests());
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG, AlbanianConstants.TAB_3_TAG);
//        mFragment.setArguments(bundle);
//
//        mFragmentTransaction.replace(R.id.container_body, mFragment);
//        mFragmentTransaction.addToBackStack(null);
//        mFragmentTransaction.commit();


        mActivity.pushFragments(AlbanianConstants.TAB_3_TAG,
                new ChatFragment(), false, true, bundle);


    }




}
