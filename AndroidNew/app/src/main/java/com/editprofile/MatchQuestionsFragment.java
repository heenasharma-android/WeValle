package com.editprofile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.MainFragments.BaseFragment;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.models.MatchMaingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MatchQuestionsFragment extends BaseFragment implements View.OnClickListener {

    private View mView;
//    private Button btnCancelMatches;
    private TextView tvQuestionone,tvQuestiontwo;
//    private RadioGroup radioGroup;
    private TextView tvTitle;

    /*Variables*/

//    private String[] listQuestion;
    private int count = 0;

    private Animation animLeftToRight, animRightToLeft;
//    private ArrayList<MatchMaingModel> matchmaking_arraylist;
    private ArrayList<String> matchmaking_string_arraylist;

    private ArrayList<JSONObject> jsonArraylist;
    private AlbanianPreferances pref;
    private String CURRENTTABTAG;

    public MatchQuestionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        matchmaking_string_arraylist=AlbanianConstants.getMatchList();

        jsonArraylist = new ArrayList<>();
        pref = new AlbanianPreferances(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.question_new_layout, container, false);

        init();
        getMatchesQuestions();


        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {

            CURRENTTABTAG = getArguments().getString(
                    AlbanianConstants.EXTRA_CURRENTTAB_TAG);
        }
    }

    private void getMatchesQuestions() {


        /////////////
        String[] separated = matchmaking_string_arraylist.get(count).split("\\-\\-");


        tvTitle.setText("" + (count+1) + "/" + matchmaking_string_arraylist.size());
        tvQuestionone.setText(separated[0].toString());
        tvQuestiontwo.setText(separated[1].toString());

        initAnimation();



    }



    private void parseMatchesQuestionsResponse(String Result) {

//        matchmaking_arraylist.clear();

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


//                    JSONObject jsonSubmitObj=jObj.getJSONObject("SubmitData");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

//                    if (ErrorCode.equalsIgnoreCase("1"))


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


//                            Log.d("sumit","MatchMakingQuestions in quesion frag= "+MatchMakingQuestions);
                            ///////

//                            matchmaking_arraylist.add(mtcModel);

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



    private void init() {
        initViews();

    }

    private void initAnimation() {
        animLeftToRight = AnimationUtils.loadAnimation(getActivity(),
                R.anim.anim_left_to_right);
        animRightToLeft = AnimationUtils.loadAnimation(getActivity(),
                R.anim.anim_right_to_left);



        tvQuestionone.startAnimation(animLeftToRight);
        tvQuestiontwo.startAnimation(animLeftToRight);

    }

    private void initViews() {

//        if (getArguments() != null)
//        {
//
//            matchmaking_arraylist = getArguments().getParcelableArrayList(
//                    AlbanianConstants.EXTRA_MATCHMAKINGLIST);
//
//            Log.d("sumit","matchmaking_arraylist= "+matchmaking_arraylist);
//
//
//
//            if (matchmaking_arraylist != null)
//            {
////                getUserDetail(profileVisitedID);
//
//            }
//
//
//        }

        tvTitle = (TextView) mView.findViewById(R.id.txt_headerlogo);

//        TextView tvSave = (TextView) mView.findViewById(R.id.tv_save);
//        tvSave.setOnClickListener(this);
        ImageView tvCancel = (ImageView) mView.findViewById(R.id.img_back);
        tvCancel.setOnClickListener(this);


//        tvSave.setText("Next");
//        tvCancel.setText("Previous");

//        listQuestion = getResources().getStringArray(R.array.array_country);
        tvQuestionone = (TextView) mView.findViewById(R.id.txt_question_one);
        tvQuestiontwo = (TextView) mView.findViewById(R.id.txt_question_second);

        tvQuestionone.setOnClickListener(this);
        tvQuestiontwo.setOnClickListener(this);
//        tvTitle.setText(""+count+1);

//        tvQuestion.setOnClickListener(this);

//        btnCancelMatches = (Button) mView.findViewById(R.id.btn_cancel_matches);
//        btnCancelMatches.setOnClickListener(this);
//        radioGroup = (RadioGroup) mView.findViewById(R.id.radio_group);
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                int pos = radioGroup.indexOfChild(mView.findViewById(checkedId));
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txt_question_one:


                if (tvTitle.getText().toString().equals(matchmaking_string_arraylist.size()
                        +"/"+matchmaking_string_arraylist.size())) {

                    Log.d("sumit","counter is equal to list size = " +
                            ""+matchmaking_string_arraylist.size());
                    Log.d("sumit","counter is equal to list size = "+count);

//                    int pos=0;
//                    pos = radioGroup.indexOfChild(mView.findViewById(radioGroup.getCheckedRadioButtonId()));

//                    makeJson(matchmaking_arraylist.size(), pos, tvQuestion.getText().toString());

                    makeNewJson(matchmaking_string_arraylist.size(),"1",true);





                }

                else
                {


//                    if (radioGroup.getCheckedRadioButtonId() != -1)
//                    {
                        count++;
                        tvQuestionone.startAnimation(animLeftToRight);
                        tvQuestiontwo.startAnimation(animLeftToRight);
                        Log.d("sumit", "counter right= " + count);

                        makeNewJson(count,"1",false);

                        nextQuestion();
//                    }

                }

                break;
            case R.id.txt_question_second:


                if (tvTitle.getText().toString().equals(matchmaking_string_arraylist.size()
                        +"/"+matchmaking_string_arraylist.size())) {

                    Log.d("sumit","counter is equal to list size = " +
                            ""+matchmaking_string_arraylist.size());
                    Log.d("sumit","counter is equal to list size = "+count);

                    makeNewJson(matchmaking_string_arraylist.size(),"2",true);





                }

                else
                {


//                    if (radioGroup.getCheckedRadioButtonId() != -1)
//                    {
                        count++;
                        tvQuestionone.startAnimation(animLeftToRight);
                        tvQuestiontwo.startAnimation(animLeftToRight);
                        Log.d("sumit", "counter right= " + count);

                        makeNewJson(count,"2",false);

                        nextQuestion();
//                    }

                }

                break;
            case R.id.img_back:


                if (count <=0)
                {
                    getActivity().onBackPressed();
                }
                else
                {
                    count--;
                    tvQuestionone.startAnimation(animRightToLeft);
                    tvQuestiontwo.startAnimation(animRightToLeft);
                    Log.d("sumit", "counter left= " + count);
                    preQuestion();
                }


                break;
        }
    }

    private void makeNewJson(int count, String s,Boolean submittoserver) {

        JSONObject student1 = new JSONObject();
        try {
            student1.put("Question"+count, s);
//            student1.put("AnswerSelected", AnswerSelected+1);
//            student1.put("QuestionText", QuestionText);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        jsonArraylist.add(student1);

        if (submittoserver ) {

            Log.d("sumit","json list= "+jsonArraylist.toString());

            JSONObject jsonObject=new JSONObject();

            for (int i = 0; i < jsonArraylist.size(); i++) {

                JSONObject jsonObject1=jsonArraylist.get(i);

                try {
                    jsonObject.put(jsonObject1.names().getString(0),jsonObject1.get(jsonObject1.names().getString(0)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


//            String jsonStr = jsonArraylist.toString();

            Log.d("sumit","json list= "+jsonObject);

            sendMatckMakingToServer(jsonObject.toString());
        }

        String jsonStr = jsonArraylist.toString();
        Log.d("sumit","jsonarray= "+jsonStr);
    }

    private void showAlertForCncel() {

        AlertDialog.Builder mAlert = new AlertDialog.Builder(getActivity());
        mAlert.setCancelable(false);
        mAlert.setTitle(getActivity().getString(R.string.app_name));
        mAlert.setMessage(getActivity().getString(R.string.macthcancel));
        {
            mAlert.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            dialog.dismiss();

                        }
                    });
        }

        mAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        mAlert.show();
    }



    private void sendMatckMakingToServer(final String stringtosend) {

        Log.d("sumit","MMAnswerUserID"+ pref.getUserData().getUserId());
        Log.d("sumit","MMAnswer"+  jsonArraylist.toString());

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "Match rsponse= == " + response.toString());

                parseMatchesResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "Match= Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "Match= Error: " + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "insertmatchcard");
                params.put("user_id", pref.getUserData().getUserId());
                params.put("mc_answer", stringtosend);
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        // Adding request to request queue
//        AlbanianApplication.getInstance().addToRequestQueue(jsonObjReq,
//                tag_whoiviewed_obj);

        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }

    private void parseMatchesResponse(String Result) {


        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equals("2")|| ErrorCode.equals("1"))
                    {

//                        pref.setMatchCardNaswered("1");

                        loadMatchesListFragment();

                    }

                    else
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }



                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","Match submit exception= "+e);
                }

            }

        }
    }


    @Override
    public void onResume() {
        super.onResume();

       // mActivity.hideCurrentTab();
    }

    private void nextQuestion()
    {

        tvTitle.setText("" + (count+1) + "/" + matchmaking_string_arraylist.size());

        Log.d("sumit","counter is equal to list size = " +
                ""+matchmaking_string_arraylist.size());
        Log.d("sumit","counter is equal to list size = "+count);

        int pos=0;
//                    pos = radioGroup.indexOfChild(mView.findViewById(radioGroup.getCheckedRadioButtonId()));

//                    makeJson(matchmaking_arraylist.size(), pos, tvQuestion.getText().toString());

//        makeNewJson(count,"1");

        if (0 < count && matchmaking_string_arraylist.size() > count)
        {
//            tvQuestion.setText(matchmaking_arraylist.get(count).getMatchMakingQuestions().toString());

            String[] separated = matchmaking_string_arraylist.get(count).split("\\-\\-");

            tvQuestionone.setText(separated[0].toString());
            tvQuestiontwo.setText(separated[1].toString());

//            Toast.makeText(getActivity(), "Question: " + pos, Toast.LENGTH_SHORT).show();
        }
        else
        {
            count = 0;
            Toast.makeText(getActivity(), "Question: finish ", Toast.LENGTH_SHORT).show();
        }

//        radioGroup.clearCheck();



   }

    private void preQuestion()
    {
        tvTitle.setText("" + (count+1) + "/" + matchmaking_string_arraylist.size());

//        if (0 < count && matchmaking_arraylist.size() > count)
        {
            int pos;
//            pos = radioGroup.indexOfChild(mView.findViewById(radioGroup.getCheckedRadioButtonId()));
            animRightToLeft.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

//                    tvQuestionone.setText(matchmaking_arraylist.get(count).getMatchMakingQuestions().toString());

                    String[] separated = matchmaking_string_arraylist.get(count).split("\\-\\-");

                    tvQuestionone.setText(separated[0].toString());
                    tvQuestiontwo.setText(separated[1].toString());
                     
//                    Toast.makeText(getActivity(), "Question: " + pos, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAnimationRepeat(Animation animation)
                {

                }
            });

//            radioGroup.clearCheck();
            removeJson(count);

        }
//        else
//        {
//            count = 0;
//            Toast.makeText(getActivity(), "Question: finish ", Toast.LENGTH_SHORT).show();
//        }
        
    }

    private void makeJson(int QuestionId, int AnswerSelected, String QuestionText) {




        JSONObject student1 = new JSONObject();
        try {
            student1.put("QuestionId", QuestionId);
            student1.put("AnswerSelected", AnswerSelected+1);
            student1.put("QuestionText", QuestionText);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        jsonArraylist.add(student1);

        String jsonStr = jsonArraylist.toString();
        Log.d("sumit","jsonarray= "+jsonStr);
    }

    private void removeJson(int count) {

        jsonArraylist.remove(count);

        String jsonStr = jsonArraylist.toString();
        Log.d("sumit","jsonarray= "+jsonStr);
    }


    private void loadMatchesListFragment() {

//        pref.setMatchCardNaswered("1");


        getActivity().onBackPressed();

//        FragmentManager mFragmentManager = getFragmentManager();
//        FragmentTransaction mFragmentTransaction = mFragmentManager
//                .beginTransaction();
//        ProfileFragment mFragment = new ProfileFragment();
//
//
////      Bundle bundle = new Bundle();
////      bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, userId);
////      mFragment.setArguments(bundle);
//
//        mFragmentTransaction.replace(R.id.container_body, mFragment);
////      mFragmentTransaction.addToBackStack(null);
//        mFragmentTransaction.commit();
    }
}
