package com.SigninSignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.adapter.ChatAdapter;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.models.ChatModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChattingActivity extends AppCompatActivity {

    private EditText metText;
    private ImageButton mbtSent;
    private List<ChatModel> mChats;
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private String mId;
    ProgressDialog progressDialog;
    AlbanianPreferances pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        pref = new AlbanianPreferances(this);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("name");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(userName);
        toolbar.setNavigationIcon(R.drawable.icons8_back_48);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });
        Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purp));

        progressDialog = new ProgressDialog(this);
        metText = (EditText) findViewById(R.id.etText);
        mbtSent = (ImageButton) findViewById(R.id.btSent);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvChat);
        mChats = new ArrayList<>();

        mId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setItemAnimator(new SlideInOutLeftItemAnimator(mRecyclerView));
        mAdapter = new ChatAdapter(mChats, mId);
        mRecyclerView.setAdapter(mAdapter);

        sendJsonRequest();

    }

    public void sendJsonRequest() {
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Chat...");
        String webAddress = "http://culturalsinglesapps.com/WeValleAPICalls.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject object = new JSONObject();

        try {
            String recieverId=getIntent().getStringExtra("id");
            SharedPreferences spppp = getSharedPreferences("tab", 0);
            String name = spppp.getString("SID","");
            object.put("api_name", "chatmessage");
            // Toast.makeText(ChattingActivity.this,name,Toast.LENGTH_LONG).show();
            object.put("msg_sender", pref.getUserData().getUserId());
            object.put("msg_receiver", recieverId);
            Log.d("chatt","chatt"+name+recieverId);


        } catch (JSONException e) {
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, webAddress, object, new com.android.volley.Response.Listener<JSONObject>() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE", response.toString());

                try {
                    progressDialog.dismiss();
                    JSONObject jsonObj = new JSONObject(String.valueOf(response));
                    JSONArray jsonArray = jsonObj.getJSONArray("Messages");
                    //Toast.makeText(ChatTab.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();

                    for(int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //arrayList.add(jsonObject.getString("UserName"));
                        //url.add(jsonObject.getString("UserImageUrl"));
                        //LocationActivity.add(jsonObject.getString("UserLocation"));
                        ChatModel chatModel =new ChatModel();
                        chatModel.setId(jsonObject.getString("MsgId"));
                        chatModel.setMessage(jsonObject.getString("MsgContent"));
                        chatModel.setMSender(jsonObject.getString("MsgSender"));
                        chatModel.setMReceiver(jsonObject.getString("MsgReceiver"));

                        mChats.add(chatModel);
                        //mRecyclerView.scrollToPosition(mChats.size() - 1);
                        mAdapter.notifyItemInserted(mChats.size() - 1);
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(mAdapter);
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChattingActivity.this,error.toString().trim(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        onBackPressed();
        return true;
    }


}
