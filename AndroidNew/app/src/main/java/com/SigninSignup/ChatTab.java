package com.SigninSignup;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.OldScreens.OldChatActivity;
import com.OldScreens.OldListActivity;
import com.adapter.CustomList;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.models.Detail;
import com.models.Fdetail;
import com.models.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatTab extends AppCompatActivity {

    ListView list;
    RecyclerView list2;
    Fdetail fdetail;
    List<Fdetail> Fdata;
    Detail detail;
    List<Detail> Vdetail;
    List<Detail> listitems;
    AlbanianPreferances pref;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_tab);
        pref = new AlbanianPreferances(this);
        ImageView imageView = (ImageView) findViewById(R.id.img_back);
        TextView title=(TextView)findViewById(R.id.txt_headerlogo) ;
        title.setText("Chat");
        imageView.setOnClickListener(new View.OnClickListener() {
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
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.purp));

        Fdata = new ArrayList<>();
        list = (ListView) findViewById(R.id.listChat);
        Vdetail = new ArrayList<>();
        sendJsonRequest();


    }
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;

        } else {
            return false;
        }

    }


    public void sendJsonRequest() {
        AlbanianApplication.showProgressDialog(ChatTab.this,"","Loading...");
        String webAddress = "http://culturalsinglesapps.com/WeValleAPICalls.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject object = new JSONObject();

        try {
            object.put("user_id", pref.getUserData().getUserId());
            object.put("api_name", "conversations");

        } catch (JSONException e) {
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, webAddress, object, new com.android.volley.Response.Listener<JSONObject>() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE", response.toString());
                AlbanianApplication.hideProgressDialog(ChatTab.this);
                try {

                    JSONObject jsonObj = new JSONObject(String.valueOf(response));
                    JSONArray jsonArray = jsonObj.getJSONArray("Conversations");
                    //Toast.makeText(ChatTab.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();

                    for(int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //arrayList.add(jsonObject.getString("UserName"));
                        //url.add(jsonObject.getString("UserImageUrl"));
                        //LocationActivity.add(jsonObject.getString("UserLocation"));
                        detail=new Detail();
                        detail.setVid(jsonObject.getString("UserId"));
                        detail.setVame(jsonObject.getString("UserName"));
                        detail.setVpresence(jsonObject.getString("UserPresence"));
                        detail.setVsubscription(jsonObject.getString("UserSubscriptionStatus"));
                        detail.setVage(jsonObject.getString("Age"));
                        detail.setVimage(jsonObject.getString("UserImageUrl"));
                        Vdetail.add(detail);
                        //   Toast.makeText(ListActivity.this, response.toString().trim(), Toast.LENGTH_LONG).show();
                    }



                    CustomList customList=new CustomList(ChatTab.this, Vdetail);
                    list.setAdapter(customList);
                    Utility.setListViewHeightBasedOnChildren(list);
                    customList.notifyDataSetChanged();
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Toast.makeText(ChatTab.this, "You Clicked at " + Vdetail.get(position).getVame(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(ChatTab.this,OldChatActivity.class);
                            intent.putExtra("id",Vdetail.get(position).getVid());
                            intent.putExtra("name",Vdetail.get(position).getVame());
                            intent.putExtra("image",Vdetail.get(position).getVimage());

                            startActivity(intent);

                        }
                    });




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlbanianApplication.hideProgressDialog(ChatTab.this);
                Toast.makeText(ChatTab.this,error.toString().trim(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request); }


}