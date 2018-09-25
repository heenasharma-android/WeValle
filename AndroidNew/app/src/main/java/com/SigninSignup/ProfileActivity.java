package com.SigninSignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adapter.CustomList3;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.models.Detail;
import com.models.Mprofile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    ListView list;
    RecyclerView list2;
    Mprofile fdetail;
    List<Mprofile> Mdata;
    Detail detail;
    List<Detail> Vdetail;
    List<Detail> listitems;
    FloatingActionButton button;
    AlbanianPreferances pref;
    TextView name,age,loc,heritage,prof,lang,about;
    String Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        pref = new AlbanianPreferances(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title=(TextView)toolbar.findViewById(R.id.toolbar_title) ;
        title.setText("Profile");
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

        Mdata = new ArrayList<>();
        list = (ListView) findViewById(R.id.list);
        list2 = (RecyclerView) findViewById(R.id.list2);
        name=(TextView)findViewById(R.id.Puser);
        age=(TextView)findViewById(R.id.Page);
        loc=(TextView)findViewById(R.id.Ploc);
        button=(FloatingActionButton)findViewById(R.id.fab);
        prof=(TextView)findViewById(R.id.Pprofession);
        lang=(TextView)findViewById(R.id.Plang);
        about=(TextView)findViewById(R.id.Pabout);
        heritage=(TextView) findViewById(R.id.Pher);
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
        String webAddress = "http://culturalsinglesapps.com/WeValleAPICalls.php";
        AlbanianApplication.showProgressDialog(ProfileActivity.this,"","Loading..");
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject object = new JSONObject();
        try {
            String id=getIntent().getStringExtra("id");

            SharedPreferences spppp1 = getSharedPreferences("tab2", 0);
            SharedPreferences.Editor editors = spppp1.edit();
            editors.putString("pid", id);
            editors.commit();

            SharedPreferences spppp2 = getSharedPreferences("tab2", 0);
            String name2 = spppp2.getString("pid","");


            //String Sid=getIntent().getStringExtra("self");
            if(name2.equals(pref.getUserData().getUserId()))
            {
                button.hide();
            }
            object.put("viewing_profile_id",id);
            //object.put("viewing_profile_id",Preferences.getInstance().getUserId());
            object.put("api_name", "vieweingUserProfile");
            object.put("user_id", pref.getUserData().getUserId());

        } catch (JSONException e) {
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, webAddress, object, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE", response.toString());
                AlbanianApplication.hideProgressDialog(ProfileActivity.this);
                try {


                    JSONArray jsonArray1=response.getJSONArray("UserImages");
                    for (int i=0;i<jsonArray1.length();i++){
                        JSONObject object1=jsonArray1.getJSONObject(i);
                        fdetail= new Mprofile();
                        fdetail.setUserImages(object1.getString("UserImageUrl"));
                        Mdata.add(fdetail);
                    }

                    name.setText(response.getString("UserName").toString());
                    age.setText(response.getString("Age").toString());
                    loc.setText(response.getString("UserLocation").toString());
                    prof.setText(response.getString("UserProfession").toString());
                    lang.setText(response.getString("UserOtherLanguages").toString());
                    about.setText(response.getString("UserDescription").toString());
                    Name=response.getString("UserName").toString();
                    JSONArray json=response.getJSONArray("UserHeritage");
                    for(int i=0;i<json.length();i++)
                    {

                        JSONObject jsonObject=json.getJSONObject(i);
                        heritage.setText(jsonObject.getString("Heritage"));

                    }



                    Toast.makeText(ProfileActivity.this,response.getString("UserName").toString(),Toast.LENGTH_LONG).show();
                    list2.setHasFixedSize(true);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(ProfileActivity.this,LinearLayoutManager.HORIZONTAL,false);
                    list2.setLayoutManager(layoutManager);
                    CustomList3 customList1=new CustomList3(ProfileActivity.this,Mdata);
                    list2.setAdapter(customList1);
                    customList1.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlbanianApplication.hideProgressDialog(ProfileActivity.this);
                Toast.makeText(ProfileActivity.this,error.toString().trim(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request); }

    public void connect(View view)
    {
        SharedPreferences spppp = getSharedPreferences("tab1", 0);
        String name = spppp.getString("self","");
        String id=getIntent().getStringExtra("id");
        String SendId=getIntent().getStringExtra("Seid");
        Intent intent=new Intent(ProfileActivity.this,ChattingActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("name",Name);
        startActivity(intent);

    }


}
