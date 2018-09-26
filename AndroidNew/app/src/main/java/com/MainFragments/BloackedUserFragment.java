package com.MainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.OldScreens.NewProfileActivity;
import com.adapter.WhoIViewedAdapter;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.models.WhoIViewedModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumit on 26/09/2015.
 */
public class BloackedUserFragment extends Fragment implements View.OnClickListener {

    /*UI declaration........*/
    private View mView;

    private ListView lv_blockedusers;
    private RelativeLayout back;
    private TextView headertext;

    /* Variables...... */

    private ArrayList<WhoIViewedModel> whoiviewed_arraylist;
    private String tag_whoiviewed_obj = "jwhoiviewed_req";
    private AlbanianPreferances pref;

    private String CURRENTTABTAG;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        whoiviewed_arraylist=new ArrayList<>();

        pref = new AlbanianPreferances(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {

            CURRENTTABTAG = getArguments().getString(
                    AlbanianConstants.EXTRA_CURRENTTAB_TAG);
        }


    }


    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("Blocked Users");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.blockeduserlist, container, false);



        initViews();

        getWhoIViewed();

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {

//        initAdapter(viewrs);
    }

    private void initAdapter(String[] viewrsList) {

    }


    private void initViews() {

        headertext = (TextView)mView.findViewById(R.id.txt_headerlogo);
        headertext.setText(getActivity().getString(R.string.blocked));

        lv_blockedusers = (ListView)mView.findViewById(R.id.lv_blockedusers);
        back = (RelativeLayout)mView.findViewById(R.id.rl_back_header);
        back.setOnClickListener(this);



    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back_header:

                getActivity().onBackPressed();

                break;
        }
    }


    private void getWhoIViewed() {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "block user list= "+response.toString());

                parseResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        }, error ->
        {
            Log.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
            Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

            AlbanianApplication.hideProgressDialog(getActivity());
        })

        {


            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "getblockuser");
                params.put("user_id", pref.getUserData().getUserId());
                params.put("AppName", AlbanianConstants.AppName);


                return params;
            }

        };



        AlbanianApplication.getInstance().addToRequestQueue(sr);
 }

    private void parseResponse(String Result) {

        whoiviewed_arraylist.clear();

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        String mTitle = getResources().getString(R.string.app_name);



                        JSONArray jArray_response=jObj.getJSONArray("BlockUsers");


                        for (int i = 0; i < jArray_response.length(); i++) {

                            WhoIViewedModel whoiviewModel=new WhoIViewedModel();

                            JSONObject jObj_job=jArray_response.getJSONObject(i);

                            // Job object

//                            JSONObject jObj_job=jObj_Main.getJSONObject("Payment");

                            String UserId=jObj_job.optString("UserId");
                            whoiviewModel.setUserId(UserId);

                            String UserName=jObj_job.optString("UserName");
                            whoiviewModel.setUserName(UserName);

                            String ImageFileName=jObj_job.optString("ImageFileName");
                            whoiviewModel.setImageFileName(ImageFileName);

                            String UserJabberId=jObj_job.optString("UserJabberId");
                            whoiviewModel.setUserJabberId(UserJabberId);



                            String UserSubscriptionStatus=jObj_job.optString("UserSubscriptionStatus");
                            whoiviewModel.setUserSubscriptionStatus(UserSubscriptionStatus);

                            String Age=jObj_job.optString("Age");
                            whoiviewModel.setAge(Age);

                            String UserImageUrl=jObj_job.optString("UserImageUrl");
                            whoiviewModel.setUserImageUrl(UserImageUrl);

                            String UserFavQuote=jObj_job.optString("UserFavQuote");
                            whoiviewModel.setUserFavQuote(UserFavQuote);

                            String UserCity=jObj_job.optString("UserCity");
                            whoiviewModel.setUserCity(UserCity);

                            String UserCountry=jObj_job.optString("UserCountry");
                            whoiviewModel.setUserCountry(UserCountry);


                            String UserState=jObj_job.optString("UserState");
                            whoiviewModel.setUserState(UserState);

                            String BlockTo=jObj_job.optString("BlockTo");
                            whoiviewModel.setBlockTo(BlockTo);


                            ///////

                            whoiviewed_arraylist.add(whoiviewModel);

                        }



                        /////////////

                        setWalletHistoryList(whoiviewed_arraylist);





                    }

                    else if(ErrorCode.equals("0") )
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }



                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","Bloackeduser list exception= "+e);
                }

            }

        }
    }

    private void setWalletHistoryList(final ArrayList<WhoIViewedModel> whoiviewed_arraylist) {

        WhoIViewedAdapter adapter = new WhoIViewedAdapter(getActivity(), whoiviewed_arraylist);
        lv_blockedusers.setAdapter(adapter);

        lv_blockedusers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                loadProfile(whoiviewed_arraylist.get(position).getUserId());


            }
        });

    }


    private void loadProfile(String userId) {
        Intent intent=new Intent(getActivity(), NewProfileActivity.class);
        intent.putExtra("userId",userId);
        startActivity(intent);


//        Bundle bundle=new Bundle();
//        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);
//        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, userId);
//
//        mActivity.pushFragments(CURRENTTABTAG,
//                new ProfileFragment(), false, true, bundle);
    }
}
