package com.MainFragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.editprofile.MatchQuestionsFragment;
import com.models.MatchMaingModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MatchesFragment extends Fragment implements View.OnClickListener {


    private View mView;
    private Button btnContinue;
    private RelativeLayout back;

    /* Variables*/


    private AlbanianPreferances pref;


//    DisplayImageOptions options_image;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private ArrayList<MatchMaingModel> matchmaking_arraylist;
    private String IsSubmitted;
    private Activity mActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new AlbanianPreferances(getActivity());
        matchmaking_arraylist=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)

    {
        mView = inflater.inflate(R.layout.fragment_matche_questions, container, false);

        init();

        getMatchesQuestions();

        return mView;
    }



    private void init() {
        initViews();
        initListners();
    }


    private void initViews() {

        TextView tvTitle = (TextView) mView.findViewById(R.id.txt_headerlogo);
        tvTitle.setText(mActivity.getString(R.string.Matches));



        back = (RelativeLayout) mView.findViewById(R.id.rl_back_header);
        back.setOnClickListener(this);

        btnContinue = (Button) mView.findViewById(R.id.btn_continue);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.mActivity=activity;
    }

    private void initListners() {

        btnContinue.setOnClickListener(this);
    }


    private void getMatchesQuestions() {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "matches questions= " + response.toString());

                parseMatchesQuestionsResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "matches questions Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "getmatchmakingquestions");
                params.put("user_id", pref.getUserData().getUserId());
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        // Adding request to request queue

        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }



    private void parseMatchesQuestionsResponse(String Result) {

        matchmaking_arraylist.clear();

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");


                    JSONObject jsonSubmitObj=jObj.getJSONObject("SubmitData");

                    IsSubmitted=jsonSubmitObj.optString("IsSubmitted");
                    String Message=jsonSubmitObj.optString("Message");

                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

//                    if (ErrorCode.equalsIgnoreCase("1"))
                    {


                        String mTitle = getResources().getString(R.string.app_name);

                        JSONArray jArray_QuestionData=jObj.getJSONArray("QuestData");


                        for (int i = 0; i < jArray_QuestionData.length(); i++) {

                            MatchMaingModel mtcModel=new MatchMaingModel();

                            JSONObject jObj_job=jArray_QuestionData.getJSONObject(i);

                            // Job object

//                          JSONObject jObj_job=jObj_Main.getJSONObject("Payment");

                            String MatchMakingId=jObj_job.optString("MatchMakingId");
                            mtcModel.setMatchMakingId(MatchMakingId);

                            String MatchMakingQuestions=jObj_job.optString("MatchMakingQuestions");
                            mtcModel.setMatchMakingQuestions(MatchMakingQuestions);



//                          Log.d("sumit","MatchMakingQuestions= "+MatchMakingQuestions);
                            ///////

                            matchmaking_arraylist.add(mtcModel);

                        }

                        Log.d("sumit","matchmaking_arraylist in parsing= "+matchmaking_arraylist);

                        /////////////

//                       setWalletHistoryList(messages_arraylist);

                    }





                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","Whoiviewed list exception= "+e);
                }

            }

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("My Matches");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back_header:
                    getFragmentManager().popBackStack();

                break;



            case R.id.btn_continue:


//                if (IsSubmitted.equals("2"))
                {

                    // questions data not submitted

                    Log.d("sumit","listtosned-= "+matchmaking_arraylist);

                    FragmentManager mFragmentManager = getFragmentManager();
                    FragmentTransaction mFragmentTransaction = mFragmentManager
                            .beginTransaction();
                    MatchQuestionsFragment mFragment = new MatchQuestionsFragment();



                    mFragmentTransaction.replace(R.id.container_body, mFragment);
                    mFragmentTransaction.addToBackStack(null);
                    mFragmentTransaction.commit();
                }




                        break;

        }
    }

}
