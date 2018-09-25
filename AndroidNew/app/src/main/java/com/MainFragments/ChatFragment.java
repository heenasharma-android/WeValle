package com.MainFragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adapter.ChattingAdapter;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.CircleTransform;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.models.ChatMessageModel;
import com.models.UserImagesModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by Sumit on 22/10/2015.
 */
public class ChatFragment extends BaseFragment implements View.OnClickListener

{


    /*UI declaration........*/
    private View mView;

    private ListView lvchatmessages;
    private RelativeLayout back;
    private RelativeLayout sendmessage;
    private ImageView userimage;
    private RelativeLayout rl_chatheader;
    private TextView chatInitaiteText/*,interests*/;
    private TextView headertext;
    private EditText chatMessage;

    /* Variables...... */

    private ArrayList<ChatMessageModel> chatmessages_arraylist;
    private String tag_whoiviewed_obj = "jsentmessages_req";
    private AlbanianPreferances pref;
    private String profileUserId;
    private String profileUserName;
    private String profileUserImage;
    private String profileUserAge;
    private String profileUserQuote;
    private String profileUserInterest;
    private String CURRENTTABTAG;


//    DisplayImageOptions options_image;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private ChattingAdapter adapter;
    private Handler mHandler;

    private String receivedMessage;
//    private RelativeLayout refreshChat;

    private HomeFragmentNewActivity mActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatmessages_arraylist=new ArrayList<>();

        mActivity = (HomeFragmentNewActivity) this.getActivity();

        pref = new AlbanianPreferances(getActivity());

//        options_image = new DisplayImageOptions.Builder()
//                .displayer(new RoundedBitmapDisplayer(300))
//
//                .cacheInMemory()
////                .cacheOnDisc()
//
//                .build();




        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                 if (msg.what == 5)
                {

                    reloadMessages();
                }



            }


        };
    }

    private void reloadMessages() {

        SimpleDateFormat dateFormat= new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.ENGLISH);
        String cDateTime=dateFormat.format(new Date());

        ChatMessageModel msgModel=new ChatMessageModel();

        msgModel.setMsgSender(profileUserId);

        msgModel.setMsgReceiver(pref.getUserData().getUserId());

        msgModel.setMsgContent(receivedMessage);

        msgModel.setMsgSentTime(cDateTime);

        msgModel.setMsgIsDeleted("0");

        msgModel.setMsgIsReaded("0");

//        chatMessage.setText("");

        chatmessages_arraylist.add(msgModel);

        if(adapter != null) {

            adapter.notifyDataSetChanged();
        }
        else
        {
            setChatMessagesList(chatmessages_arraylist);
        }


    }

    @Override
    public void onStart() {
        super.onStart();

            pref.setCurrentScreen("CHAT");
            pref.setChatUser(profileUserName);


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

        pref.setCurrentScreen("");

        if (profileUserId != null) {

            markMessageRead(profileUserId);
        }

        //mActivity.showCurrentTab();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("Chatting");

        getActivity().registerReceiver(mChatMessageReceiver, new IntentFilter(
                AlbanianConstants.EXTRA_ChatMessage_alert));


        //mActivity.hideCurrentTab();

    }



    private BroadcastReceiver mChatMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent

            receivedMessage = intent.getStringExtra("message");

            Log.d("sumit", "MESSAGE= " + receivedMessage);


            Message msg5 = new Message();
            msg5.what = 5;
            mHandler.sendMessage(msg5);




        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("sumit","ON destroy driver in route called");

        try
        {
            getActivity().unregisterReceiver(mChatMessageReceiver);



        }
        catch (Exception e)
        {
            e.printStackTrace();

            Log.d("sumit"," on detroy driver in route axception called== "+e);
        }

        //mActivity.showCurrentTab();


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.chatlayout, container, false);

        initViews();



        if (getArguments() != null)
        {

            profileUserId =  getArguments()
                    .getString(AlbanianConstants.EXTRA_PROFILEVISITEDID);

            profileUserName =  getArguments()
                    .getString(AlbanianConstants.EXTRA_PROFILEVISITEDNAME);

            profileUserImage =  getArguments()
                    .getString(AlbanianConstants.EXTRA_PROFILEVISITEDIMAGE);

            profileUserAge =  getArguments()
                    .getString(AlbanianConstants.EXTRA_PROFILEVISITEDAGE);
            profileUserQuote =  getArguments()
                    .getString(AlbanianConstants.EXTRA_PROFILEVISITEDQUOTE);
            profileUserInterest =  getArguments()
                    .getString(AlbanianConstants.EXTRA_PROFILEVISITEDINTEREST);

            CURRENTTABTAG =  getArguments()
                    .getString(AlbanianConstants.EXTRA_CURRENTTAB_TAG);


//            username.setText(profileUserName+", "+profileUserAge);
            headertext.setText(profileUserName);
//            quote.setText(profileUserQuote);
//            quote.setText(profileUserInterest);
//            interests.setText(profileUserInterest);




            getLastChatMessages(profileUserId, true);

            markMessageRead(profileUserId);

        }



        return mView;
    }



    private void markMessageRead(final String profileUserId) {

        //        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("sumit","markMessageRead response== "+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("sumit","markMessageRead onErrorResponse== "+error);
            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "markmessageread");
                params.put("msg_sender",profileUserId  );
                params.put("msg_receiver", pref.getUserData().getUserId());
                params.put("AppName", AlbanianConstants.AppName);
                params.put("user_id", pref.getUserData().getUserId());

                return params;
            }

        };



        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }

    private void getLastChatMessages(final String profileUserId, final boolean showLoading) {

        if (showLoading) {

            AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");
        }



        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "chatmessage repsonse= "+response.toString());
                if (showLoading) {

                    AlbanianApplication.hideProgressDialog(getActivity());
                }



                parseChattingResponse(response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "chatmessage Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                if (showLoading ) {
                    AlbanianApplication.hideProgressDialog(getActivity());
                }

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "chatmessage");
                params.put("msg_sender", pref.getUserData().getUserId() );
                params.put("msg_receiver", profileUserId);
                params.put("AppName", AlbanianConstants.AppName);


                return params;
            }

        };



        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }

    private void parseChattingResponse(String Result) {


        chatmessages_arraylist.clear();

        if (Result != null) {

            {

                try {


                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
                    String SelfEmailVerified = jObj.optString("SelfEmailVerified");

//                    if (SelfEmailVerified.equals("0")) {
//
//                        showEmailVerificationAlert();
//                    }
//                    else
//                    {
//
//                    }

                    Log.d(AlbanianConstants.TAG, "parseChattingResponse= " + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        String mTitle = getResources().getString(R.string.app_name);


                        JSONArray jArray_response=jObj.getJSONArray("Messages");


                        for (int i = 0; i < jArray_response.length(); i++) {

                            ChatMessageModel msgModel=new ChatMessageModel();

                            JSONObject jObj_job=jArray_response.getJSONObject(i);

                            String MsgId=jObj_job.optString("MsgId");
                            msgModel.setMsgId(MsgId);

                            String MsgSender=jObj_job.optString("MsgSender");
                            msgModel.setMsgSender(MsgSender);

                            String MsgReceiver=jObj_job.optString("MsgReceiver");
                            msgModel.setMsgReceiver(MsgReceiver);

                            String MsgContent=jObj_job.optString("MsgContent");
                            msgModel.setMsgContent(MsgContent);

                            String MsgSentTime=jObj_job.optString("MsgSentTime");
                            msgModel.setMsgSentTime(MsgSentTime);

                            String MsgIsDeleted=jObj_job.optString("MsgIsDeleted");
                            msgModel.setMsgIsDeleted(MsgIsDeleted);

                            String MsgIsReaded=jObj_job.optString("MsgIsReaded");
                            msgModel.setMsgIsReaded(MsgIsReaded);

                            ///////

                            chatmessages_arraylist.add(msgModel);

                        }



                        setChatMessagesList(chatmessages_arraylist);


                    }

                    else if (ErrorCode.equalsIgnoreCase("2"))
                    {

                        showNoChatLayout();
                    }

                    else
                    {

                        String mTitle = getResources().getString(R.string.app_name);

//                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
//                                ErrorMessage, false);

                        lvchatmessages.setAdapter(null);
                    }



                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","chat message exception= "+e);
                }

            }

        }
    }

    private void showEmailVerificationAlert() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        AlertDialog b = dialogBuilder.create();
        b.show();

        final EditText emailaddress = (EditText) dialogView.findViewById(R.id.edit_email);
        final RelativeLayout okbutton = (RelativeLayout) dialogView.findViewById(R.id.rl_ok);
        final RelativeLayout cancel = (RelativeLayout) dialogView.findViewById(R.id.rl_cancel);

        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Matcher mEmailMatcher = AlbanianConstants.EMAIL_ADDRESS_PATTERN
                        .matcher(emailaddress.getText().toString());

                if (emailaddress.getText().toString().length() ==0) {

                    Toast.makeText(mActivity,"fill Email!",Toast.LENGTH_SHORT).show();
                }
                else if (mEmailMatcher.matches()==false)
                {
                    String mMessage = getString(R.string.Signup_Pleasefillvalidemail);
                    Toast.makeText(mActivity,mMessage,Toast.LENGTH_SHORT).show();

                }
                else
                {
                    sendVerificationEmail(emailaddress.getText().toString(),b);
                }

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b.dismiss();

                mActivity.onBackPressed();
            }
        });


    }

    private void sendVerificationEmail(String emailaddress, DialogInterface dialog) {


        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d(AlbanianConstants.TAG, "sendVerificationEmail repsonse= " + response.toString());
                AlbanianApplication.hideProgressDialog(getActivity());


                parseEmailVerifyResponse(response.toString(),dialog);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(AlbanianConstants.TAG, "pushnotification Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "pushnotification Error: " + error.getMessage() + "," + error.toString());

                    AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "verify_email");
                params.put("user_email", emailaddress );
                params.put("user_id", pref.getUserData().getUserId());

                return params;
            }

        };


        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }

    private void parseEmailVerifyResponse(String Result, DialogInterface dialog) {

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");


                    Log.d(AlbanianConstants.TAG, "ErrorCode" + ErrorCode);
                    Log.d(AlbanianConstants.TAG, "ErrorMessage" + ErrorMessage);

                    if(ErrorCode.equals("1") )
                    {

                        dialog.dismiss();
                        Toast.makeText(mActivity,"Email sent!",Toast.LENGTH_SHORT).show();
                        mActivity.onBackPressed();

                    }
                    else if(ErrorCode.equals("3") )
                    {

                        String mTitle = getResources().getString(R.string.app_name);

//                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
//                                ErrorMessage, false);

                        AlertDialog.Builder mAlert = new AlertDialog.Builder(getActivity());
                        mAlert.setCancelable(false);
                        mAlert.setTitle(mTitle);
//                        mAlert.setMessage("To send a message upgrade to a premium member.");
                        mAlert.setMessage(("This email is already registered to another account. "));


                        mAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        });

                        mAlert.show();

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
                    Log.d("sumit","chat message exception= "+e);
                }

            }

        }

    }

    private void showNoChatLayout() {

        rl_chatheader.setVisibility(View.VISIBLE);
        lvchatmessages.setVisibility(View.GONE);

        Log.d("sumit","user image to print= "+profileUserImage);


//        imageLoader.displayImage(profileUserImage,
//                userimage, options_image, animateFirstListener);

        Picasso.with(getContext()).load(profileUserImage).fit().centerCrop()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
                .transform(new CircleTransform())
                .into(userimage);


        chatInitaiteText.setText(chatInitaiteText.getText().toString().
                replace("Username",profileUserName));
    }

    private void showChatLayout() {

        rl_chatheader.setVisibility(View.GONE);
        lvchatmessages.setVisibility(View.VISIBLE);
    }

    private void setChatMessagesList(final ArrayList<ChatMessageModel> chatmessages_arraylist) {

        View.OnClickListener imageclick = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int pos = Integer.parseInt(v.getTag() + "");

                // result.get(0).getServiceslist().remove(pos);

                Log.d("sumit", "THE UIMAGE POS IS == " + pos);

//                final Section item = allshoutslist_main.get(pos);

                loadProfile(chatmessages_arraylist.get(pos).getMsgSender());



            }
        };

        adapter = new ChattingAdapter(getActivity(), chatmessages_arraylist,profileUserImage,imageclick);


        Log.d("sumit","chatmessages_arraylist= "+chatmessages_arraylist.size());

        if (chatmessages_arraylist.size()>0) {

//            userimage.setVisibility(View.GONE);
//            chatInitaiteText.setVisibility(View.GONE);
            showChatLayout();

            lvchatmessages.post(new Runnable() {
                @Override
                public void run() {
                    lvchatmessages.setSelection(lvchatmessages.getCount());
                }
            });

        }
        else
        {
//            userimage.setVisibility(View.VISIBLE);
//            chatInitaiteText.setVisibility(View.VISIBLE);
           showNoChatLayout();
        }

        lvchatmessages.setAdapter(adapter);


    }



    private void loadProfile(String userId) {


        Bundle bundle = new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, userId);
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG, CURRENTTABTAG);

        mActivity.pushFragments(CURRENTTABTAG,
                new ProfileFragment(), false, true, bundle);
    }



    private void initViews() {


        sendmessage= (RelativeLayout)mView.findViewById(R.id.rl_Send_message);
        sendmessage.setOnClickListener(this);

        rl_chatheader= (RelativeLayout)mView.findViewById(R.id.rl_chatheader);
//        refreshChat.setOnClickListener(this);


        chatMessage= (EditText)mView.findViewById(R.id.edt_chatText);

        lvchatmessages = (ListView)mView.findViewById(R.id.lv_chatmessages);

        LayoutInflater inflater = getActivity().getLayoutInflater();
//        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.chatheader, lvchatmessages, false);
//        lvchatmessages.addHeaderView(header, null, false);


        userimage = (ImageView)mView.findViewById(R.id.img_usertosendmessage_chat);
//        username = (TextView)header.findViewById(R.id.txt_username_chat);
        chatInitaiteText = (TextView)mView.findViewById(R.id.txt_username_chat);
//        interests = (TextView)header.findViewById(R.id.txt_interest_chat);
        headertext = (TextView) mView.findViewById(R.id.txt_headerlogo);


        back = (RelativeLayout) mView.findViewById(R.id.rl_back_header);
        back.setOnClickListener(this);


        

    }




    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.rl_back_header:

                getActivity().onBackPressed();

                break;

            case R.id.rl_refreshchat:

                getLastChatMessages(profileUserId,true);

                break;
//
            case R.id.rl_Send_message:

                AlbanianApplication.hideKeyBoard(getActivity(),getActivity());

                String str_chatMessage = chatMessage.getText().toString();

                String mTitle = getResources().getString(R.string.app_name);

                if (str_chatMessage.length() <= 0) {
                    String mMessage = getString(R.string.Pleasefillmessage);
//                    AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
                }
                else {

//                    if (pref.getUserData().getUserSubscriptionStatus().equals("0"))
//                    {
//
//                        if (chatmessages_arraylist != null && chatmessages_arraylist.size() > 0)
//                        {
//                            sendMessage(profileUserId);
//
//                        }
//                        else
//                        {
//                            freeUserAlert();
//                        }
//
//
//
//                    }
//                    else
                    {
                        sendMessage(profileUserId);
                    }

                }


                break;
        }
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


    private void sendMessage(final String profileUserId) {

        final ChatMessageModel msgModel=new ChatMessageModel();

        SimpleDateFormat dateFormat= new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.ENGLISH);
        String cDateTime=dateFormat.format(new Date());

        msgModel.setMsgSender(pref.getUserData().getUserId());

        msgModel.setMsgReceiver(profileUserId);

        msgModel.setMsgContent(chatMessage.getText().toString().trim());

        msgModel.setMsgSentTime(cDateTime);

        msgModel.setMsgIsDeleted("0");

        msgModel.setMsgIsReaded("0");

        chatMessage.setText("");
        chatmessages_arraylist.add(msgModel);
        if (adapter != null) {

            showChatLayout();

            adapter.notifyDataSetChanged();
        }
        else
        {
            setChatMessagesList(chatmessages_arraylist);
        }



        Log.d(AlbanianConstants.TAG, "pushnotification sending= "+pref.getUserData().getUserId() );
        Log.d(AlbanianConstants.TAG, "pushnotification sending= "+profileUserId );
        Log.d(AlbanianConstants.TAG, "pushnotification sending= "+msgModel.getMsgContent().toString() );
        Log.d(AlbanianConstants.TAG, "pushnotification sending= " );

//        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d(AlbanianConstants.TAG, "pushnotification repsonse= " + response.toString());
//                AlbanianApplication.hideProgressDialog(getActivity());


                parseResponse(response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(AlbanianConstants.TAG, "pushnotification Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "pushnotification Error: " + error.getMessage() + "," + error.toString());

//                    AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "pushnotification");
                params.put("msg_sender", pref.getUserData().getUserId() );
                params.put("msg_receiver", profileUserId);
                params.put("msg_content", msgModel.getMsgContent().toString());
                params.put("user_id", pref.getUserData().getUserId());
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };


        AlbanianApplication.getInstance().addToRequestQueue(sr);

        }

    private void parseResponse(String Result) {

        Log.d("sumit","chat response-= "+Result);

//        images_arraylist.clear();

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");


                    Log.d(AlbanianConstants.TAG, "ErrorCode" + ErrorCode);
                    Log.d(AlbanianConstants.TAG, "ErrorMessage" + ErrorMessage);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        String mTitle = getResources().getString(R.string.app_name);



                        JSONArray jArray_response=jObj.getJSONArray("UserImages");


                        for (int i = 0; i < jArray_response.length(); i++) {

                            UserImagesModel msgModel=new UserImagesModel();

                            JSONObject jObj_job=jArray_response.getJSONObject(i);

                            // Job object

                            String ImageId=jObj_job.optString("ImageId");
                            msgModel.setImageId(ImageId);

                            String UserImageUrl=jObj_job.optString("UserImageUrl");
                            msgModel.setUserImageUrl(UserImageUrl);


                            String IsUserProfileImage=jObj_job.optString("IsUserProfileImage");
                            msgModel.setIsUserProfileImage(IsUserProfileImage);

                            ///////

//                            images_arraylist.add(msgModel);

                        }


                    }

                    else if(ErrorCode.equals("0") )
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }
                    else if(ErrorCode.equals("5") )
                    {

                        String mTitle = getResources().getString(R.string.app_name);

//                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
//                                ErrorMessage, false);

                        AlertDialog.Builder mAlert = new AlertDialog.Builder(getActivity());
                        mAlert.setCancelable(false);
                        mAlert.setTitle(mTitle);
//                        mAlert.setMessage("To send a message upgrade to a premium member.");
                        mAlert.setMessage(("To send a message, you or Username need to be a " +
                                "premium member. Upgrade to message Username.").replace("Username",profileUserName));





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
                    Log.d("sumit","chat message exception= "+e);
                }

            }

        }
    }






}
