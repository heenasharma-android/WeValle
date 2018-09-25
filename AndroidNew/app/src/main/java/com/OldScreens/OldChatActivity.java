package com.OldScreens;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MainFragments.HomeFragmentNewActivity;
import com.MainFragments.UpgradeFragment;
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

public class OldChatActivity extends AppCompatActivity implements View.OnClickListener{

    AlbanianPreferances pref;
    private View mView;

    private ListView lvchatmessages;
    private RelativeLayout back;
    private ChattingAdapter adapter;
    private RelativeLayout sendmessage;
    private ImageView userimage;
    private RelativeLayout rl_chatheader;
    private TextView chatInitaiteText/*,interests*/;
    private TextView headertext;
    private EditText chatMessage;

    /* Variables...... */

    private ArrayList<ChatMessageModel> chatmessages_arraylist;
    private String tag_whoiviewed_obj = "jsentmessages_req";
    private String profileUserId;
    private String profileUserName;
    private String profileUserImage;
    String userId,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_chat);
        initViews();
        chatmessages_arraylist=new ArrayList<>();
        pref = new AlbanianPreferances(this);
         userId=getIntent().getStringExtra("id");
         name=getIntent().getStringExtra("name");
         profileUserImage=getIntent().getStringExtra("image");
        headertext.setText(name);
        markMessageRead(userId);
        getLastChatMessages(userId,true);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purp));
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
                params.put("msg_sender",pref.getUserData().getUserId()  );
                params.put("msg_receiver", profileUserId);
                params.put("AppName", AlbanianConstants.AppName);
                params.put("user_id", pref.getUserData().getUserId());

                return params;
            }

        };



        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }

    private void getLastChatMessages(final String profileUserId, final boolean showLoading) {

        if (showLoading) {

            AlbanianApplication.showProgressDialog(this, "", "Loading...");
        }



        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "chatmessage repsonse= "+response.toString());
                if (showLoading) {

                    AlbanianApplication.hideProgressDialog(OldChatActivity.this);
                }



                parseChattingResponse(response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "chatmessage Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                if (showLoading ) {
                    AlbanianApplication.hideProgressDialog(OldChatActivity.this);
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

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
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

                    Toast.makeText(OldChatActivity.this,"fill Email!",Toast.LENGTH_SHORT).show();
                }
                else if (mEmailMatcher.matches()==false)
                {
                    String mMessage = getString(R.string.Signup_Pleasefillvalidemail);
                    Toast.makeText(OldChatActivity.this,mMessage,Toast.LENGTH_SHORT).show();

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

                onBackPressed();
            }
        });


    }

    private void sendVerificationEmail(String emailaddress, DialogInterface dialog) {


        AlbanianApplication.showProgressDialog(this, "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d(AlbanianConstants.TAG, "sendVerificationEmail repsonse= " + response.toString());
                AlbanianApplication.hideProgressDialog(OldChatActivity.this);


                parseEmailVerifyResponse(response.toString(),dialog);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(AlbanianConstants.TAG, "pushnotification Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "pushnotification Error: " + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(OldChatActivity.this);

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
                        Toast.makeText(this,"Email sent!",Toast.LENGTH_SHORT).show();
                        onBackPressed();

                    }
                    else if(ErrorCode.equals("3") )
                    {

                        String mTitle = getResources().getString(R.string.app_name);

//                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
//                                ErrorMessage, false);

                        AlertDialog.Builder mAlert = new AlertDialog.Builder(this);
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

                        AlbanianApplication.ShowAlert(this, mTitle,
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

        Picasso.with(this).load(profileUserImage).fit().centerCrop()
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

        ChattingAdapter adapter = new ChattingAdapter(this, chatmessages_arraylist,profileUserImage,imageclick);


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
        Intent intent= new Intent(OldChatActivity.this, NewProfileActivity.class);
        intent.putExtra("userId",userId);
        startActivity(intent);


//        Bundle bundle = new Bundle();
//        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, userId);
//        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG, CURRENTTABTAG);
//
//        mActivity.pushFragments(CURRENTTABTAG,
//                new ProfileFragment(), false, true, bundle);

    }



    private void initViews() {


        sendmessage= (RelativeLayout)findViewById(R.id.rl_Send_message);
        sendmessage.setOnClickListener(this);
        rl_chatheader= (RelativeLayout)findViewById(R.id.rl_chatheader);
        chatMessage= (EditText) findViewById(R.id.edt_chatText);
        lvchatmessages = (ListView) findViewById(R.id.lv_chatmessages);
        LayoutInflater inflater = getLayoutInflater();
        userimage = (ImageView) findViewById(R.id.img_usertosendmessage_chat);
        chatInitaiteText = (TextView) findViewById(R.id.txt_username_chat);
        headertext = (TextView) findViewById(R.id.txt_headerlogo);
        back = (RelativeLayout) findViewById(R.id.rl_back_header);
        back.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl_back_header:

                onBackPressed();

                break;

            case R.id.rl_refreshchat:

                getLastChatMessages(profileUserId,true);

                break;
//
            case R.id.rl_Send_message:

                AlbanianApplication.hideKeyBoard(this,this);

                String str_chatMessage = chatMessage.getText().toString();

                String mTitle = getResources().getString(R.string.app_name);

                if (str_chatMessage.length() <= 0) {
                    String mMessage = getString(R.string.Pleasefillmessage);
                }
                else {

                    {
                        sendMessage(profileUserId);
                    }

                }


                break;
        }
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
                params.put("msg_receiver", userId);
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

                        AlbanianApplication.ShowAlert(this, mTitle,
                                ErrorMessage, false);
                    }
                    else if(ErrorCode.equals("5") )
                    {

                        String mTitle = getResources().getString(R.string.app_name);

//                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
//                                ErrorMessage, false);

                        android.app.AlertDialog.Builder mAlert = new android.app.AlertDialog.Builder(this);
                        mAlert.setCancelable(false);
                        mAlert.setTitle(mTitle);
//                        mAlert.setMessage("To send a message upgrade to a premium member.");
                        mAlert.setMessage(("To send a message, you or Username need to be a " +
                                "premium member. Upgrade to message Username.").replace("Username",profileUserName));





                        mAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

//                                Bundle bundle=new Bundle();
//                                bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
//                                        CURRENTTABTAG);
//
//                                mActivity.pushFragments(CURRENTTABTAG,
//                                        new UpgradeFragment(), false, true, bundle);


                            }
                        });

                        mAlert.show();

                    }
                    else
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(this, mTitle,
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
