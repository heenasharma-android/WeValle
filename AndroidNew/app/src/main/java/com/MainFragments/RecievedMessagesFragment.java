package com.MainFragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

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
public class RecievedMessagesFragment extends BaseFragment implements View.OnClickListener {


    /*UI declaration........*/
    private View mView;

    private ListView lvProfileViewd;
    private RelativeLayout nomessages;


    /* Variables...... */

    private ArrayList<RecievedMessageModel> messages_arraylist;
    private String tag_whoiviewed_obj = "jsentmessages_req";
    private AlbanianPreferances pref;
    private Handler mHandler;
    private static int mPosition = -1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messages_arraylist=new ArrayList<>();

        pref = new AlbanianPreferances(getActivity());

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 5)
                {

                    getAllMessages(false);
                }



            }


        };
    }

    private BroadcastReceiver mChatMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent

//            receivedMessage = intent.getStringExtra("message");

//            Log.d("sumit", "MESSAGE= " + receivedMessage);


            Message msg5 = new Message();
            msg5.what = 5;
            mHandler.sendMessage(msg5);




        }
    };



    @Override
    public void onResume() {
        super.onResume();


        getActivity().registerReceiver(mChatMessageReceiver, new IntentFilter(
                AlbanianConstants.EXTRA_Chat_MessageTab_alert));

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("Received Messages");
    }

    @Override
    public void onPause() {
        super.onPause();

        mActivity.unregisterReceiver(mChatMessageReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.viewedmelist, container, false);

        initViews();

        getAllMessages(true);

        return mView;
    }



    private void initViews() {

        lvProfileViewd = (ListView)mView.findViewById(R.id.lv_peopleviewedme);
        nomessages = (RelativeLayout)mView.findViewById(R.id.rl_no_messages);

    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){


        }
    }



    private void getAllMessages(boolean showProgress) {

        if (showProgress ) {

            AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");
        }



        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "all messages= "+response.toString());

                if (showProgress ) {
                    AlbanianApplication.hideProgressDialog(getActivity());
                }


                parseResponse(response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                if (showProgress) {

                    AlbanianApplication.hideProgressDialog(getActivity());
                }


            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "conversations");
                params.put("user_id", pref.getUserData().getUserId());
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };



        AlbanianApplication.getInstance().addToRequestQueue(sr);

        }

    private void parseResponse(String Result) {

        Log.d("sumit","Rec mesage= "+Result);

        messages_arraylist.clear();
        if (Result != null) {

            {

                try {

                    Intent intent = new Intent("android.intent.action.MAIN").putExtra("some_msg", "I will be sent!");
                    mActivity.sendBroadcast(intent);

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {



                        JSONArray jArray_response=jObj.getJSONArray("Conversations");

                        int unreadMsgCount=0;

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

                            String UnreadMessagesCount=jObj_job.optString("UnreadMessagesCount");
                            msgModel.setUnreadMessagesCount(UnreadMessagesCount);

                            if (!UnreadMessagesCount.equalsIgnoreCase("0")) {

                                unreadMsgCount=unreadMsgCount+1;
                            }

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

                  //      mActivity.refreshTab(String.valueOf(unreadMsgCount));

                        setReceivedMessagesList(messages_arraylist);



                    }

                    else if(ErrorCode.equals("2") )
                    {

                        nomessages.setVisibility(View.VISIBLE);
                        lvProfileViewd.setVisibility(View.GONE);
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
                    Log.d("sumit","Whoiviewed list exception= "+e);
                }

            }

        }
    }

    private void setReceivedMessagesList(final ArrayList<RecievedMessageModel> messages_arraylist) {


        if (messages_arraylist != null & messages_arraylist.size()==0) {

            nomessages.setVisibility(View.VISIBLE);
            lvProfileViewd.setVisibility(View.GONE);
        }
        else {

            nomessages.setVisibility(View.GONE);
            lvProfileViewd.setVisibility(View.VISIBLE);

            MessagesAdapter adapter = new MessagesAdapter(getActivity(),getActivity(), messages_arraylist,
                    true,mActivity);
            lvProfileViewd.setAdapter(adapter);

            if (mPosition != -1) {

                lvProfileViewd.setSelection(mPosition);
            }

            lvProfileViewd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    mPosition = position;

                    loadChatFragment(messages_arraylist.get(position));
                }
            });

            lvProfileViewd.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    showUserOptions(messages_arraylist.get(i).getUserId());

                    return true;
                }
            });
        }


    }



    private void showUserOptions(String userId) {

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


        arrayAdapter.add("Delete");



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

                            deleteMessages(userId);


                        }


                    }
                });

        builderSingle.show();

    }

    private void deleteMessages(String userId) {

        AlbanianApplication.getInstance().cancelPendingRequests("conversationRequest");

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "deleteMessages messages= "+response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());
                getAllMessages(true);



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
                Log.d("sumit","userId= "+userId);

                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "deletemessage");
                params.put("msg_sender", userId);
                params.put("msg_receiver",pref.getUserData().getUserId());

                return params;
            }

        };



        AlbanianApplication.getInstance().addToRequestQueue(sr,"conversationRequest");
    }


    private void loadChatFragment(RecievedMessageModel userModel) {

        Log.d("sumit","rec image url= "+userModel.getUserImageUrl());

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

//        mFragmentTransaction.replace(R.id.container_body, mFragment);
//        mFragmentTransaction.addToBackStack(null);
//        mFragmentTransaction.commit();


        mActivity.pushFragments(AlbanianConstants.TAB_3_TAG,
                new ChatFragment(), false, true, bundle);



    }

}
