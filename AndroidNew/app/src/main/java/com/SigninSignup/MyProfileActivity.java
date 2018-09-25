package com.SigninSignup;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.MainFragments.HomeFragmentNewActivity;
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

public class MyProfileActivity extends AppCompatActivity {

    ListView list;
    RecyclerView list2;
    Mprofile fdetail;
    List<Mprofile> Mdata;
    List<Detail> Vdetail;
    FloatingActionButton button;
    TextView name,age,loc,heritage,prof,lang,about;
    String Name;
    AlbanianPreferances pref;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        pref = new AlbanianPreferances(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title=(TextView)toolbar.findViewById(R.id.toolbar_title) ;
        title.setText("Profile");
        setTitle(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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
        prof=(TextView)findViewById(R.id.Pprofession);
        lang=(TextView)findViewById(R.id.Plang);
        about=(TextView)findViewById(R.id.Pabout);
        heritage=(TextView) findViewById(R.id.Pher);
        Vdetail = new ArrayList<>();
        sendJsonRequest();


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile) {
            startActivity(new Intent(MyProfileActivity.this, SettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void sendJsonRequest() {
        String webAddress = "http://culturalsinglesapps.com/WeValleAPICalls.php";
        AlbanianApplication.showProgressDialog(MyProfileActivity.this,"","Loading");
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject object = new JSONObject();

        try {
            object.put("viewing_profile_id",pref.getUserData().getUserId());
            object.put("api_name", "vieweingUserProfile");
            object.put("user_id", pref.getUserData().getUserId());
            Log.d("ido","ido"+pref.getUserData().getUserId());

        } catch (JSONException e) {
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, webAddress, object, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                AlbanianApplication.hideProgressDialog(MyProfileActivity.this);
                Log.d("RESPONSE", response.toString());
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

                    Toast.makeText(MyProfileActivity.this,response.getString("UserName").toString(),Toast.LENGTH_LONG).show();
                    list2.setHasFixedSize(true);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(MyProfileActivity.this,LinearLayoutManager.HORIZONTAL,false);
                    list2.setLayoutManager(layoutManager);
                    CustomList3 customList1=new CustomList3(MyProfileActivity.this,Mdata);
                    list2.setAdapter(customList1);
                    customList1.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlbanianApplication.hideProgressDialog(MyProfileActivity.this);
                Toast.makeText(MyProfileActivity.this,error.toString().trim(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request); }

}