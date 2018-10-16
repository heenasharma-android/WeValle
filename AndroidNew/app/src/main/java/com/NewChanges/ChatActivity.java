package com.NewChanges;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adapter.ChatDataAdapter;
import com.adapter.FavAdapter;
import com.adapter.ViewedAdapter;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.models.ChatData;
import com.models.FavData;
import com.models.ViewData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {
    AlbanianPreferances albanianPreferances;
    FavData favData;
    ChatData chatData;
    List<FavData> favDataList=new ArrayList<>();
    List<ChatData> chatDataList=new ArrayList<>();
    ViewData viewData;
    List<ViewData> viewDataList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        albanianPreferances = new AlbanianPreferances(ChatActivity.this);
        ImageView imageView = (ImageView) findViewById(R.id.img_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purp));
        sendJsonRequest();
    }

    public void sendJsonRequest() {
        AlbanianApplication.showProgressDialog(ChatActivity.this,"","Loading..");
        String webAddress = "http://culturalsinglesapps.com/WeValleAPICalls.php";
        RequestQueue queue = Volley.newRequestQueue(ChatActivity.this);

        JSONObject object = new JSONObject();

        try {

            object.put("user_id", albanianPreferances.getUserData().getUserId());
            object.put("api_name", "activity_chat");

        } catch (JSONException e) {
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, webAddress, object, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                AlbanianApplication.hideProgressDialog(ChatActivity.this);
                Log.d("RESPONSE", response.toString());
                try {
                    JSONArray jsonArray=response.getJSONArray("UserFavorites");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        favData = new FavData();
                        favData.setuId(jsonObject.getString("UserId"));
                        favData.setuName(jsonObject.getString("UserName"));
                        favData.setuFullName(jsonObject.getString("UserFullName"));
                        favData.setuSubscriptionStatus(jsonObject.getString("UserSubscriptionStatus"));
                        favData.setuLocation(jsonObject.getString("UserLocation"));
                        favData.setuAge(jsonObject.getString("Age"));
                        favData.setuImageUrl(jsonObject.getString("UserImageUrl"));
                        favData.setuFavQuote(jsonObject.getString("UserFavQuote"));
                        favDataList.add(favData);
                    }
                    LinearLayoutManager llm = new LinearLayoutManager(ChatActivity.this);
                    llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rvFav.setLayoutManager(llm);
                    FavAdapter interAdapter = new FavAdapter(favDataList, ChatActivity.this);
                    rvFav.setAdapter(interAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray1=response.getJSONArray("UsersViewedMe");
                    for(int i=0;i<jsonArray1.length();i++) {
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                        viewData = new ViewData();
                        viewData.setuId(jsonObject1.getString("UserId"));
                        viewData.setuName(jsonObject1.getString("UserName"));
                        viewData.setuFullName(jsonObject1.getString("UserFullName"));
                        viewData.setuSubscriptionStatus(jsonObject1.getString("UserSubscriptionStatus"));
                        viewData.setuAge(jsonObject1.getString("Age"));
                        viewData.setuImageUrl(jsonObject1.getString("UserImageUrl"));
                        viewData.setuFavQuote(jsonObject1.getString("UserFavQuote"));
                        viewData.setuPresence(jsonObject1.getString("UserPresence"));
                        viewDataList.add(viewData);
                    }
                    LinearLayoutManager llm = new LinearLayoutManager(ChatActivity.this);
                    llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rvView.setLayoutManager(llm);
                    ViewedAdapter viewedAdapter = new ViewedAdapter(viewDataList, ChatActivity.this,albanianPreferances.getUserData().getUserId());
                    rvView.setAdapter(viewedAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray2=response.getJSONArray("Messages");
                    for(int i=0;i<jsonArray2.length();i++) {
                        JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
                        chatData = new ChatData();
                        chatData.setuId(jsonObject2.getString("UserId"));
                        chatData.setuName(jsonObject2.getString("UserName"));
                        chatData.setuFullName(jsonObject2.getString("UserFullName"));
                        chatData.setuDOB(jsonObject2.getString("UserDOB"));
                        chatData.setuPresence(jsonObject2.getString("UserPresence"));
                        chatData.setMssageSentTime(jsonObject2.getString("MessageSentTime"));
                        chatData.setMsgSentTime(jsonObject2.getString("MsgSentTime"));
                        chatData.setuSubscriptionStatus(jsonObject2.getString("UserSubscriptionStatus"));
                        chatData.setUnreadMessagesCount(jsonObject2.getString("UnreadMessagesCount"));
                        chatData.setuImageUrl(jsonObject2.getString("UserImageUrl"));
                        chatData.setuAge(jsonObject2.getInt("Age"));
                        chatDataList.add(chatData);
                    }
                    LinearLayoutManager llm = new LinearLayoutManager(ChatActivity.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    rvChat.setLayoutManager(llm);
                    ChatDataAdapter chatDataAdapter = new ChatDataAdapter(chatDataList, ChatActivity.this);
                    rvChat.setAdapter(chatDataAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlbanianApplication.hideProgressDialog(ChatActivity.this);
                Toast.makeText(ChatActivity.this,error.toString().trim(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request); }

    @BindView(R.id.rv_chat)
    RecyclerView rvChat;

    @BindView(R.id.rv_fav)
    RecyclerView rvFav;

    @BindView(R.id.rv_view)
    RecyclerView rvView;

}
