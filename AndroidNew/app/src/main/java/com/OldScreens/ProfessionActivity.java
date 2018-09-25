package com.OldScreens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.adapter.ProfessionAdapter;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.models.Profession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfessionActivity extends AppCompatActivity implements ProfessionAdapter.OnItemSelectedListener{
Profession profession;
    List<Profession> selectedItems;
List<Profession> professionList;
    List<Profession> currentSelectedItems ;
    ProfessionAdapter professionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession);
        ButterKnife.bind(this);
        professionList=new ArrayList<>();
        currentSelectedItems = new ArrayList<>();
        setRecycle();
        getProfession();

    }

    public void getProfession() {
        AlbanianApplication.showProgressDialog(ProfessionActivity.this,"","Loading..");
        String webAddress = "http://culturalsinglesapps.com/WeValleAPICalls.php";
        RequestQueue queue = Volley.newRequestQueue(ProfessionActivity.this);

        JSONObject object = new JSONObject();

        try {

            object.put("api_name", "list_profession");

        } catch (JSONException e) {
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, webAddress, object, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                AlbanianApplication.hideProgressDialog(getApplicationContext());
                Log.d("RESPONSE", response.toString());

                try {

                    JSONArray jsonArray=response.getJSONArray("professions");
                    for(int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        profession = new Profession(jsonObject.getString("id"),jsonObject.getString("profession"),false);
                        professionList.add(profession);
                    }

                     professionAdapter=new ProfessionAdapter(professionList, ProfessionActivity.this,ProfessionActivity.this );
                    rvProfession.setAdapter(professionAdapter);

                    } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlbanianApplication.hideProgressDialog(ProfessionActivity.this);
                Toast.makeText(ProfessionActivity.this,error.toString().trim(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request); }



    private void setRecycle() {
        LinearLayoutManager manager = new LinearLayoutManager(ProfessionActivity.this);
        rvProfession.setHasFixedSize(true);
        rvProfession.setLayoutManager(manager);
    }

    @BindView(R.id.rv_profession)
    RecyclerView rvProfession;

    @OnClick(R.id.arrow)
    void arrow(){
        finish();
    }

    @OnClick(R.id.title)
    void title()
    {
        finish();
    }

    @OnClick(R.id.bt_apply)
    void btApply(){
        if (selectedItems==null){
            Toast.makeText(ProfessionActivity.this, "Please select atleast one profession", Toast.LENGTH_SHORT).show();
        }
        else {
            if (selectedItems.size() > 3) {
                Toast.makeText(ProfessionActivity.this, "Please select only three professions", Toast.LENGTH_SHORT).show();
            } else if (selectedItems.size() == 0) {
                Toast.makeText(ProfessionActivity.this, "Please select atleast one profession", Toast.LENGTH_SHORT).show();
            } else {
                EditProfile.fa.finish();
                Intent intent = new Intent(ProfessionActivity.this, EditProfile.class);
                intent.putParcelableArrayListExtra("profession", (ArrayList<? extends Parcelable>) selectedItems);
                startActivity(intent);
                finish();
            }
        }
    }


    @Override
    public void onItemSelected(Profession item) {
      selectedItems = professionAdapter.getSelectedItems();
      Log.d("select","select"+selectedItems.get(0).getProfession());

    }
}
