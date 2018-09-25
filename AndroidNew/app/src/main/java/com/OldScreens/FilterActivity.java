package com.OldScreens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.MainFragments.HomeFragmentNewActivity;
import com.adapter.HeritageAdapter;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.models.FilterModel;
import com.models.Heritage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity {

    @BindView(R.id.txt_show_me)
    TextView txtShowMe;

    @BindView(R.id.recycler_view_heritage)
    RecyclerView rvHeritage;

    @BindView(R.id.goInvis)
    Switch goInvisible;

    @BindView(R.id.txt_min_max_age)
    TextView txtAge;

    List<Heritage> heritageList=new ArrayList<>();

    @OnClick(R.id.ll_show_me)
    void showMe(){
        showMaleFemaleDialog();
    }

    @OnClick(R.id.ll_show_age)
    void showAge(){
        showAgesDalog();
    }

    String showMe,maxAge,minAge;
    String[] mHeritageArray;
    HeritageAdapter mAdapter;
    AlbanianPreferances pref;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        setStatusBar();
        //setHeritage();
        pref = new AlbanianPreferances(this);
        getUserFilter();
        TextView textView=(TextView)findViewById(R.id.txt_applyfilter);
        ImageView back=(ImageView)findViewById(R.id.img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitFilter();
            }
        });
        mHeritageArray = getResources().getStringArray(R.array.array_country);
        goInvisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {

                if (pref.getUserSubscription().equalsIgnoreCase("0"))
                {
                    goInvisible.setOnCheckedChangeListener(null);
                    goInvisible.setChecked(false);
                    freeUserAlert();
                }
                else
                {
                    if (isChecked) {
                        goToInvisible(1);
                    }
                    else
                    {
                        goToInvisible(0);
                    }
                }


            }
        });

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purp));
    }

    public void getUserFilter() {
        AlbanianApplication.showProgressDialog(FilterActivity.this,"","Loading..");
        String webAddress = "http://culturalsinglesapps.com/WeValleAPICalls.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject object = new JSONObject();

        try {
            object.put("api_name", "get_user_filter");
            object.put("user_id", pref.getUserData().getUserId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, webAddress, object, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                AlbanianApplication.hideProgressDialog(FilterActivity.this);
                Log.d("RESPONSE", response.toString());
                parseUserFilterResponse(response.toString());

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlbanianApplication.hideProgressDialog(FilterActivity.this);
                Toast.makeText(FilterActivity.this,"error:  "+error.toString().trim(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request); }


    private void parseUserFilterResponse(String Result) {

        if (Result != null) {
            {
                Log.d("sumit", "UserFilterResponse= "+Result);

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");

                    if (ErrorCode.equalsIgnoreCase("1")) {

                        FilterModel filterModel=new FilterModel();

                        String IWantTo = jObj.optString("IWantTo");
                        filterModel.setIWantTo(IWantTo);

                        String UserInterestedIn = jObj.optString("UserInterestedIn");
                        filterModel.setUserInterestedIn(UserInterestedIn);
                        showMe = UserInterestedIn;

                        String UserMinAge = jObj.optString("UserMinAge");
                        filterModel.setUserMinAge(UserMinAge);
                        minAge=UserMinAge;

                        String UserMaxAge = jObj.optString("UserMaxAge");
                        filterModel.setUserMaxAge(UserMaxAge);
                        maxAge=UserMaxAge;

                        String UserInvisibleStatus = jObj.optString("UserInvisibleStatus");
                        filterModel.setUserInvisibleStatus(UserInvisibleStatus);

                        String UserHeritage = jObj.optString("UserHeritage");
                        filterModel.setUserHeritage(UserHeritage);

                        JSONArray jsonArrayHeritages=jObj.getJSONArray("heritages");

                        ArrayList<Heritage> heritageModelArrayList
                                =new ArrayList<>();

                        for (int i = 0; i < jsonArrayHeritages.length(); i++) {

                            Heritage heritageModel = new Heritage();

                            JSONObject jsonObjectHeritage=jsonArrayHeritages.getJSONObject(i);

                            String Heritage = jsonObjectHeritage.optString("Heritage");
                            heritageModel.setHeritageName(Heritage);

                            String Flag = jsonObjectHeritage.optString("Flag");
                            heritageModel.setFlag(Flag);

                            boolean Is_Interested = jsonObjectHeritage.optBoolean("Is_Interested");
                            heritageModel.setInterested(Is_Interested);

                            heritageModelArrayList.add(heritageModel);


                        }

                        filterModel.setHeritageModelArrayList(heritageModelArrayList);

                        Log.d("TAB", "" + ErrorCode);

                        setFilters(filterModel);

                    }
                    else if(ErrorCode.equals("0") )
                    {
                        String mTitle = getResources().getString(R.string.app_name);
                        showAlert(this, mTitle,ErrorMessage, false);
                    }
                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
                    Log.d("sumit","getOnlineUsers exception= "+e);
                }

            }

        }


    }

    private void setFilters(FilterModel filterModel) {
        txtShowMe.setText(getShowMe(filterModel));
        txtAge.setText("Between "+filterModel.getUserMinAge()+ " - "+filterModel.getUserMaxAge());
        // create an Object for Adapter
        mAdapter = new HeritageAdapter(filterModel.getHeritageModelArrayList(),this,"filter");
        rvHeritage.setLayoutManager(new LinearLayoutManager(this));
        rvHeritage.setNestedScrollingEnabled(false);
        rvHeritage.setAdapter(mAdapter);
    }

    private String getShowMe(FilterModel filterModel) {

        String interestedIn = null;
        switch (filterModel.getUserInterestedIn())
        {
            case "f":

                interestedIn= "Women";

                break;
            case "m":

                interestedIn=  "Men";

                break;
            case "b":

                interestedIn=  "Women & Men";

                break;

            default:
                break;

        }

        return interestedIn;
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBar() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#816fff"));
    }


    public void goToInvisible(final int i) {
        String webAddress = "http://culturalsinglesapps.com/WeValleAPICalls.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject object = new JSONObject();

        try {
            object.put("api_name", "goinvisible");
            object.put("user_id", pref.getUserData().getUserId());
            object.put("user_invisible_status", String.valueOf(i));
            object.put("AppName", "WeValle");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, webAddress, object, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.d("RESPONSE", response.toString());

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FilterActivity.this,"error:  "+error.toString().trim(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }

    public void showMaleFemaleDialog() {
        android.app.AlertDialog.Builder builderSingle;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
        {
            builderSingle = new android.app.AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light_Dialog));
        }
        else
        {
            builderSingle = new android.app.AlertDialog.Builder(this);
        }
        // builderSingle.setTitle("Select Gender");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        arrayAdapter.add("Women");
        arrayAdapter.add("Men");
        arrayAdapter.add("Women & Men");
        builderSingle.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                long strName = arrayAdapter.getItemId(which);
                if (strName == 0) {
                    // Women
                    showMe="f";
                    txtShowMe.setText("Women");
                } else if (strName == 1) {
                    // Men
                    showMe="m";
                    txtShowMe.setText("Men");
                }
                else if (strName == 2) {
                    // Both
                    showMe ="b";
                    txtShowMe.setText("Women & Men");
                }
            }
        });

        builderSingle.show();
    }

    private void showAgesDalog() {

        final Dialog d = new Dialog(this);
        d.setTitle("Select Age");
        d.setContentView(R.layout.agegroup_dialog);
        Button b1 = (Button) d.findViewById(R.id.btn_ok);
        Button b2 = (Button) d.findViewById(R.id.btn_cancel);
        final NumberPicker numberPicker_min = (NumberPicker) d.findViewById(R.id.numberPicker_min);
        numberPicker_min.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker_min.setMaxValue(55); // max value 55
        numberPicker_min.setMinValue(18);   // min value 18
        numberPicker_min.setWrapSelectorWheel(false);

        final NumberPicker numberPicker_max = (NumberPicker) d.findViewById(R.id.numberPicker_max);
        numberPicker_max.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker_max.setMaxValue(55); // max value 55
        numberPicker_max.setMinValue(18);   // min value 18
        numberPicker_max.setValue(55);
        numberPicker_max.setWrapSelectorWheel(false);
//        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(numberPicker_max.getValue()-numberPicker_min.getValue()<4)
                {
                    showAlert(FilterActivity.this,"WeValle",
                            "The maximum age must be (4) four greater than the minimum age.",false);

                    minAge=String.valueOf(numberPicker_min.getValue());
                    maxAge=String.valueOf(numberPicker_min.getValue()+4);
                    txtAge.setText("Between "+minAge+ " - "+maxAge);
                }
                else if(numberPicker_min.getValue()<18)
                {
                    showAlert(FilterActivity.this,"WeValle", "The minimum age must be 18.",false);
                }
                else if(numberPicker_max.getValue()>55)
                {
                    showAlert(FilterActivity.this,"WeValle", "The maximum age must be less than 55.",false);
                }
                else
                {
                    minAge=String.valueOf(numberPicker_min.getValue());
                    maxAge=String.valueOf(numberPicker_max.getValue());
                    txtAge.setText("Between "+minAge+ " - "+maxAge);
                }


                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d.dismiss();
            }
        });
        d.show();
    }


    public static void showAlert(Context mContext, String mTitle, String mMessage, boolean isCancelBtn) {

        AlertDialog.Builder mAlert = new AlertDialog.Builder(mContext);
        mAlert.setCancelable(false);
        mAlert.setTitle(mTitle);
        mAlert.setMessage(mMessage);
        if (isCancelBtn) {
            mAlert.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }

        mAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        mAlert.show();
    }

    private void freeUserAlert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);

        builder1.setMessage("Upgrade to premium to go invisible.");
        builder1.setCancelable(true);

        builder1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    private void submitFilter() {
        List<Heritage> stList = ((HeritageAdapter) mAdapter).getHeritageList();

        ArrayList<String> heritageSend=new ArrayList<>();

        for (int i = 0; i < stList.size(); i++) {
            Heritage heritageModel = stList.get(i);
            if (heritageModel.isInterested() == true) {

//                data = data + "," + heritageModel.getHeritageName().toString();

                heritageSend.add(heritageModel.getHeritageName().toString());
                /*
                 * Toast.makeText( CardViewActivity.this, " " +
                 * singleStudent.getName() + " " +
                 * singleStudent.getEmailId() + " " +
                 * singleStudent.isSelected(),
                 * Toast.LENGTH_SHORT).show();
                 */
            }

        }


        if (heritageSend.size() <=0) {

            String mTitle = getResources().getString(R.string.app_name);

            showAlert(this, mTitle,
                    "Choose at least one heritage.", false);

        }
        else
        {
            AlbanianApplication.showProgressDialog(FilterActivity.this,"WeValle","Loading...");
            Log.d("sumit","pref.getUserData().getUserId() "+pref.getUserData().getUserId());
            Log.d("sumit","str_ShowMe "+showMe);
            Log.d("sumit","heritageSend "+ TextUtils.join(",",heritageSend));
            Log.d("sumit","str_minage "+minAge);
            Log.d("sumit","str_maxage "+maxAge);
            Log.d("sumit","goinvisible.isChecked() "+goInvisible.isChecked());
            String webAddress = "http://culturalsinglesapps.com/WeValleAPICalls.php";
            RequestQueue queue = Volley.newRequestQueue(this);
            JSONObject object = new JSONObject();

            try {
                object.put("api_name", "post_user_filter");
                object.put("user_id", pref.getUserData().getUserId());
                object.put("user_interested_in",showMe);
                object.put("user_min_age", minAge );
                object.put("user_max_age", maxAge );
                object.put("user_invisible_status", goInvisible.isChecked() ?"1":"0" );
                object.put("heritage_interested_in", TextUtils.join(", ",heritageSend));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, webAddress, object, new com.android.volley.Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    AlbanianApplication.hideProgressDialog(FilterActivity.this);

                    Log.d("RESPONSE", response.toString());
                    parseUpdateFilterResponse(response.toString());

                }

            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AlbanianApplication.hideProgressDialog(FilterActivity.this);
                    Toast.makeText(FilterActivity.this,"error:  "+error.toString().trim(),Toast.LENGTH_LONG).show();
                }
            });
            queue.add(request);

        }




    }

    private void parseUpdateFilterResponse(String Result) {
        if (Result != null) {
            {
                try {
                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
                    if (ErrorCode.equalsIgnoreCase("1")) {
                        Intent intent=new Intent(FilterActivity.this,HomeFragmentNewActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        String mTitle = getResources().getString(R.string.app_name);
                        showAlert(this, mTitle, ErrorMessage, false);
                    }
                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","profile exception= "+e);
                }

            }

        }

    }


}

