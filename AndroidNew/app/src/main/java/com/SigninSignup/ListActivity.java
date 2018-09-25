package com.SigninSignup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.OldScreens.NewProfileActivity;
import com.adapter.CustomList;
import com.adapter.CustomList2;
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

public class ListActivity extends AppCompatActivity {

    ListView list;
    RecyclerView list2;
    Fdetail fdetail;
    List<Fdetail> Fdata;
    Detail detail;
    List<Detail> Vdetail;
    List<Detail> listitems;
    AlbanianPreferances pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        pref = new AlbanianPreferances(this);
        ImageView imageView = (ImageView) findViewById(R.id.img_back);
        TextView title=(TextView)findViewById(R.id.txt_headerlogo) ;
        title.setText("Activity");
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
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purp));
        Fdata = new ArrayList<>();
        list = (ListView) findViewById(R.id.list);
        list2 = (RecyclerView) findViewById(R.id.list2);
        Vdetail = new ArrayList<>();
        sendJsonRequest();


    }


    public void sendJsonRequest() {
        AlbanianApplication.showProgressDialog(ListActivity.this,"","Loading..");
        String webAddress = "http://culturalsinglesapps.com/WeValleAPICalls.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject object = new JSONObject();

        try {

//            SharedPreferences spppp2 = getSharedPreferences("tab", 0);
//            String send1 = spppp2.getString("SID","");

            object.put("user_id", pref.getUserData().getUserId());
            object.put("api_name", "profileviews");

        } catch (JSONException e) {
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, webAddress, object, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                AlbanianApplication.hideProgressDialog(ListActivity.this);
                Log.d("RESPONSE", response.toString());

                try {

                    JSONArray jsonArray=response.getJSONArray("UsersViewedMe");
                    JSONArray jsonArray1=response.getJSONArray("UserFavorites");
                    for(int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //arrayList.add(jsonObject.getString("UserName"));
                        //url.add(jsonObject.getString("UserImageUrl"));
                        //LocationActivity.add(jsonObject.getString("UserLocation"));
                        detail=new Detail();
                        detail.setVid(jsonObject.getString("UserId"));
                        detail.setVame(jsonObject.getString("UserName"));
                        detail.setVsubscription(jsonObject.getString("UserSubscriptionStatus"));
                        detail.setVlocation(jsonObject.getString("UserLocation"));
                        detail.setVage(jsonObject.getString("Age"));
                        detail.setVimage(jsonObject.getString("UserImageUrl"));
                        detail.setVfavquotq(jsonObject.getString("UserFavQuote"));
                        detail.setVpresence(jsonObject.getString("UserPresence"));
                        Vdetail.add(detail);
                        //   Toast.makeText(ListActivity.this, response.toString().trim(), Toast.LENGTH_LONG).show();
                    }



                    CustomList customList=new CustomList(ListActivity.this, Vdetail);
                    list.setAdapter(customList);
                    Utility.setListViewHeightBasedOnChildren(list);
                    customList.notifyDataSetChanged();
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Toast.makeText(ListActivity.this, "You Clicked at " + Vdetail.get(position).getVame(), Toast.LENGTH_SHORT).show();
                            String ID=getIntent().getStringExtra("Sid");
                            Intent intent=new Intent(ListActivity.this,NewProfileActivity.class);
                            intent.putExtra("Seid",pref.getUserData().getUserId());
                            intent.putExtra("userId",Vdetail.get(position).getVid());
                            startActivity(intent);

                        }
                    });
                    for (int i=0;i<jsonArray1.length();i++){
                        JSONObject object1=jsonArray1.getJSONObject(i);
                        fdetail=new Fdetail();
                        fdetail.setId(object1.getString("UserId"));
                        fdetail.setName(object1.getString("UserName"));
                        fdetail.setUsubscription(object1.getString("UserSubscriptionStatus"));
                        fdetail.setUlocation(object1.getString("UserLocation"));
                        fdetail.setAge(object1.getString("Age"));
                        fdetail.setUimage(object1.getString("UserImageUrl"));
                        fdetail.setUfavquotq(object1.getString("UserFavQuote"));
                        Fdata.add(fdetail);
                    }

                    list2.setHasFixedSize(true);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(ListActivity.this,LinearLayoutManager.HORIZONTAL,false);
                    list2.setLayoutManager(layoutManager);
                    CustomList2 customList1=new CustomList2(ListActivity.this,Fdata);
                    list2.setAdapter(customList1);
                    customList1.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlbanianApplication.hideProgressDialog(ListActivity.this);
                Toast.makeText(ListActivity.this,error.toString().trim(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request); }


}