package com.MainFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.CircleTransform;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.models.MatchMaingModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumit on 14-Jul-16.
 */

public class MatchedCardDetailFragment extends BaseFragment implements View.OnClickListener{

    private View mView;
    //    private Button btnCancelMatches;
    private TextView question1left,question2left,question3left,question4left,
            question5left,question6left,question7left,question8left,question9left,
            question10left,question11left,question12left,question13left,question14left,
            question15left,question16left,question17left,question18left,question19left,
            question20left;

    private TextView question1right,question2right,question3right,question4right,
            question5right,question6right,question7right,question8right,question9right,
            question10right,question11right,question12right,question13right,question14right,
            question15right,question16right,question17right,question18right,question19right,
            question20right;
    //    private RadioGroup radioGroup;
    private TextView tvTitle;
    private ImageView userimageleft,userimageright;

    /*Variables*/

    //    private String[] listQuestion;
    private int count = 0;

    private Animation animLeftToRight, animRightToLeft;


    private ArrayList<JSONObject> jsonArraylist;
    private AlbanianPreferances pref;
    private String profileUserId;
    private String profileUserImage,CURRENTTABTAG;


    private ArrayList<HashMap<String,String>> matchlist;
    private ArrayList<String> matchmaking_string_arraylist;


//    DisplayImageOptions options;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();


    public MatchedCardDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        jsonArraylist = new ArrayList<>();
        pref = new AlbanianPreferances(getActivity());

        matchmaking_string_arraylist=AlbanianConstants.getMatchList();


        matchlist=new ArrayList<>();


//        options = new DisplayImageOptions.Builder()
//                .displayer(new RoundedBitmapDisplayer(200))
////                 .showStubImage(R.drawable.profile_male_btn)
////                 .showImageForEmptyUri(R.drawable.profile_male_btn)
////                 .showImageOnFail(R.drawable.profile_male_btn)
//                .cacheInMemory()
////               .cacheOnDisc()
//
//                .build();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.matchcarddetail, container, false);


        if (getArguments() != null) {

            profileUserId = getArguments()
                    .getString(AlbanianConstants.EXTRA_PROFILEVISITEDID);

            profileUserImage = getArguments()
                    .getString(AlbanianConstants.EXTRA_PROFILEVISITEDIMAGE);
            CURRENTTABTAG = getArguments()
                    .getString(AlbanianConstants.EXTRA_CURRENTTAB_TAG);

        }

        init();

        getMatchesQuestions();

        getMatchDetails();


        return mView;
    }

    private void getMatchDetails() {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response)

            {
                Log.d(AlbanianConstants.TAG, "profile= "+response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());
                parseMatchResponse(response.toString());



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "getmatchcards");
                params.put("user_id1", pref.getUserData().getUserId());
                params.put("user_id2", profileUserId);
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        // Adding request to request queue

        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }

    private void parseMatchResponse(String Result) {

        Log.d("sumit","Matchcards detail= "+Result);

        if (Result != null) {

            matchlist.clear();

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("MatchcardScore");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        JSONArray jsonArray=jObj.getJSONArray("Matchcards");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            HashMap<String,String> matchMap=new HashMap<>();

                            JSONObject jsonObjectInner=jsonArray.getJSONObject(i);

                            String Question1=jsonObjectInner.optString("Question1");
                            matchMap.put("Question1",Question1);

                            Log.d("sumit","Question1= "+i+" = "+Question1);


                            String Question2=jsonObjectInner.optString("Question2");
                            matchMap.put("Question2",Question2);

                            String Question3=jsonObjectInner.optString("Question3");
                            matchMap.put("Question3",Question3);

                            String Question4=jsonObjectInner.optString("Question4");
                            matchMap.put("Question4",Question4);

                            String Question5=jsonObjectInner.optString("Question5");
                            matchMap.put("Question5",Question5);

                            String Question6=jsonObjectInner.optString("Question6");
                            matchMap.put("Question6",Question6);

                            String Question7=jsonObjectInner.optString("Question7");
                            matchMap.put("Question7",Question7);

                            String Question8=jsonObjectInner.optString("Question8");
                            matchMap.put("Question8",Question8);

                            String Question9=jsonObjectInner.optString("Question9");
                            matchMap.put("Question9",Question9);

                            String Question10=jsonObjectInner.optString("Question10");
                            matchMap.put("Question10",Question10);

                            String Question11=jsonObjectInner.optString("Question11");
                            matchMap.put("Question11",Question11);

                            String Question12=jsonObjectInner.optString("Question12");
                            matchMap.put("Question12",Question12);

                            String Question13=jsonObjectInner.optString("Question13");
                            matchMap.put("Question13",Question13);

                            String Question14=jsonObjectInner.optString("Question14");
                            matchMap.put("Question14",Question14);

                            String Question15=jsonObjectInner.optString("Question15");
                            matchMap.put("Question15",Question15);

                            String Question16=jsonObjectInner.optString("Question16");
                            matchMap.put("Question16",Question16);

                            String Question17=jsonObjectInner.optString("Question17");
                            matchMap.put("Question17",Question17);

                            String Question18=jsonObjectInner.optString("Question18");
                            matchMap.put("Question18",Question18);

                            String Question19=jsonObjectInner.optString("Question19");
                            matchMap.put("Question19",Question19);

                            String Question20=jsonObjectInner.optString("Question20");
                            matchMap.put("Question20",Question20);

                            matchlist.add(matchMap);



                        }


                        setMatchedValues(matchlist);


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
                    Log.d("sumit","matchdetails exception= "+e);
                }

            }

        }
    }

    private void setMatchedValues(ArrayList<HashMap<String, String>> matchlist) {

            Log.d("sumit","left- "+matchlist.get(0).get("Question1"));
            Log.d("sumit","right- "+matchlist.get(1).get("Question1"));



            if (matchlist.get(0).get("Question1").equals(matchlist.get(1).get("Question1"))) {


                    setBlackColor(question1left,question1right,
                            matchlist.get(0).get("Question1"),(matchlist.get(1).get("Question1")),0);
            }
            else
            {
                setGrayColor(question1left,question1right,
                        matchlist.get(0).get("Question1"),(matchlist.get(1).get("Question1")),0);
            }

        if (matchlist.get(0).get("Question2").equals(matchlist.get(1).get("Question2"))) {

                    setBlackColor(question2left,question2right, matchlist.get(0).get("Question2"), (matchlist.get(1).get("Question2")), 1);
            }
        else
            {
                setGrayColor(question2left,question2right,
                        matchlist.get(0).get("Question2"),(matchlist.get(1).get("Question2")),1);
            }

        if (matchlist.get(0).get("Question3").equals(matchlist.get(1).get("Question3"))) {

                    setBlackColor(question3left,question3right, matchlist.get(0).get("Question3"), (matchlist.get(1).get("Question3")), 2);
            }
        else
            {
                setGrayColor(question3left,question3right,
                        matchlist.get(0).get("Question3"),(matchlist.get(1).get("Question3")),2);
            }

        if (matchlist.get(0).get("Question4").equals(matchlist.get(1).get("Question4"))) {

                    setBlackColor(question4left,question4right, matchlist.get(0).
                            get("Question4"), (matchlist.get(1).get("Question4")), 3);
            }
        else
            {
                setGrayColor(question4left,question4right,
                        matchlist.get(0).get("Question4"),(matchlist.get(1).get("Question4")),3);
            }

        if (matchlist.get(0).get("Question5").equals(matchlist.get(1).get("Question5"))) {

                    setBlackColor(question5left,question5right, matchlist.get(0).
                            get("Question5"), (matchlist.get(1).get("Question5")), 4);
            }
        else
            {
                setGrayColor(question5left,question5right,
                        matchlist.get(0).get("Question5"),(matchlist.get(1).get("Question5")),4);
            }

        if (matchlist.get(0).get("Question6").equals(matchlist.get(1).get("Question6"))) {

                    setBlackColor(question6left,question6right, matchlist.get(0).
                            get("Question6"), (matchlist.get(1).get("Question6")), 5);
            }
        else
            {
                setGrayColor(question6left,question6right,
                        matchlist.get(0).get("Question6"),(matchlist.get(1).
                                get("Question6")),5);
            }

        if (matchlist.get(0).get("Question7").equals(matchlist.get(1).get("Question7"))) {

                    setBlackColor(question7left,question7right,
                            matchlist.get(0).get("Question7"),
                            (matchlist.get(1).get("Question7")), 6);
            }
        else
            {
                setGrayColor(question7left,question7right,
                        matchlist.get(0).get("Question7"),(matchlist.get(1).
                                get("Question7")),6);
            }

        if (matchlist.get(0).get("Question8").equals(matchlist.get(1).get("Question8"))) {

                    setBlackColor(question8left,question8right, matchlist.get(0).
                            get("Question8"), (matchlist.get(1).get("Question8")), 7);
            }
        else
            {
                setGrayColor(question8left,question8right,
                        matchlist.get(0).get("Question8"),(matchlist.get(1).
                                get("Question8")),7);
            }

        if (matchlist.get(0).get("Question9").equals(matchlist.get(1).get("Question9"))) {

                    setBlackColor(question9left,question9right, matchlist.get(0).
                            get("Question9"), (matchlist.get(1).get("Question9")), 8);
            }
        else
            {
                setGrayColor(question9left,question9right,
                        matchlist.get(0).get("Question9"),(matchlist.get(1).
                                get("Question9")),8);
            }

        if (matchlist.get(0).get("Question10").equals(matchlist.get(1).get("Question10"))) {

                    setBlackColor(question10left,question10right, matchlist.get(0).
                            get("Question10"), (matchlist.get(1).get("Question10")), 9);
            }
        else
            {
                setGrayColor(question10left,question10right,
                        matchlist.get(0).get("Question10"),(matchlist.get(1).
                                get("Question10")),9);
            }

        if (matchlist.get(0).get("Question11").equals(matchlist.get(1).
                                get("Question11"))) {

                    setBlackColor(question11left,question11right,
                            matchlist.get(0).get("Question11"), (matchlist.get(1).
                                    get("Question11")), 10);
            }
        else
            {
                setGrayColor(question11left,question11right,
                        matchlist.get(0).get("Question11"),(matchlist.get(1).get("Question11")),10);
            }

        if (matchlist.get(0).get("Question12").equals(matchlist.get(1).get("Question12"))) {

                    setBlackColor(question12left,question12right, matchlist.get(0).
                            get("Question12"), (matchlist.get(1).get("Question12")), 11);
            }
        else
            {
                setGrayColor(question12left,question12right,
                        matchlist.get(0).get("Question12"),(matchlist.get(1).
                                get("Question12")),11);
            }

        if (matchlist.get(0).get("Question13").equals(matchlist.get(1).get("Question13"))) {

                    setBlackColor(question13left,question13right,
                            matchlist.get(0).get("Question13"), (matchlist.get(1).
                                    get("Question13")), 12);
            }
        else
            {
                setGrayColor(question13left,question13right,
                        matchlist.get(0).get("Question13"),(matchlist.get(1).
                                get("Question13")),12);
            }

        if (matchlist.get(0).get("Question14").equals(matchlist.get(1).get("Question14"))) {

                    setBlackColor(question14left,question14right,
                            matchlist.get(0).get("Question14"), (matchlist.get(1).
                                    get("Question14")), 13);
            }
        else
            {
                setGrayColor(question14left,question14right,
                        matchlist.get(0).get("Question14"),
                        (matchlist.get(1).get("Question14")),13);
            }

        if (matchlist.get(0).get("Question15").equals(matchlist.get(1).get("Question15"))) {

                    setBlackColor(question15left,question15right,
                            matchlist.get(0).get("Question15"), (matchlist.get(1).
                                    get("Question15")), 14);
            }
        else
            {
                setGrayColor(question15left,question15right,
                        matchlist.get(0).get("Question15"),(matchlist.get(1).get("Question15")),14);
            }

        if (matchlist.get(0).get("Question16").equals(matchlist.get(1).get("Question16"))) {

                    setBlackColor(question16left,question16right, matchlist.get(0)
                            .get("Question16"), (matchlist.get(1).get("Question16")), 15);
            }
        else
            {
                setGrayColor(question16left,question16right,
                        matchlist.get(0).get("Question16"),(matchlist.get(1).get("Question16")),15);
            }

        if (matchlist.get(0).get("Question17").equals(matchlist.get(1).get("Question17"))) {

                    setBlackColor(question17left,question17right, matchlist.get(0).
                            get("Question17"), (matchlist.get(1).get("Question17")), 16);
            }
        else
            {
                setGrayColor(question17left,question17right,
                        matchlist.get(0).get("Question17"),(matchlist.get(1).
                                get("Question17")),16);
            }

        if (matchlist.get(0).get("Question18").equals(matchlist.get(1).get("Question18"))) {

                    setBlackColor(question18left,question18right,
                            matchlist.get(0).get("Question18"), (matchlist.get(1).
                                    get("Question18")), 17);
            }
        else
            {
                setGrayColor(question18left,question18right,
                        matchlist.get(0).get("Question18"),(matchlist.get(1).get("Question18")),17);
            }

        if (matchlist.get(0).get("Question19").equals(matchlist.get(1).get("Question19"))) {

                    setBlackColor(question19left,question19right, matchlist.get(0).
                            get("Question19"), (matchlist.get(1).get("Question19")), 18);
            }
        else
            {
                setGrayColor(question19left,question19right,
                        matchlist.get(0).get("Question19"),(matchlist.get(1).get("Question19")),18);
            }

        if (matchlist.get(0).get("Question20").equals(matchlist.get(1).get("Question20"))) {

                    setBlackColor(question20left,question20right, matchlist.get(0).get("Question20")
                            , (matchlist.get(1).get("Question20")), 19);
            }
        else
            {
                setGrayColor(question20left,question20right,
                        matchlist.get(0).get("Question20"),(matchlist.get(1).get("Question20")),19);
            }



    }

    private void setGrayColor(TextView questionleft, TextView questionright,String leftuserquestoin,
                              String rightuserquestion, int i) {



        String[] separated = matchmaking_string_arraylist.get(i).split("\\-\\-");

        questionleft.setText(separated[Integer.parseInt(leftuserquestoin)-1]);
        questionright.setText(separated[Integer.parseInt(rightuserquestion)-1]);

        questionleft.setTextColor(getActivity().getResources().getColor(R.color.grey));
        questionright.setTextColor(getActivity().getResources().getColor(R.color.grey));
    }

    private void setBlackColor(TextView questionleft, TextView questionright, String leftuserquestoin,
                               String rightuserquestion, int i) {

        String[] separated = matchmaking_string_arraylist.get(i).split("\\-\\-");

        questionleft.setText(separated[Integer.parseInt(leftuserquestoin)-1]);
        questionright.setText(separated[Integer.parseInt(rightuserquestion)-1]);

        questionleft.setTextColor(getActivity().getResources().getColor(R.color.black));
        questionright.setTextColor(getActivity().getResources().getColor(R.color.black));
    }

    private void getMatchesQuestions() {

//        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response)

            {
                Log.d(AlbanianConstants.TAG, "profile= "+response.toString());

//                AlbanianApplication.hideProgressDialog(getActivity());
                parseResponse(response.toString());



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

//                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "getmatchcardscore");
                params.put("user_id1", pref.getUserData().getUserId());
                params.put("user_id2", profileUserId);
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        // Adding request to request queue

        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }

    private void parseResponse(String Result) {

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("MatchcardScore");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {

                        tvTitle.setText(ErrorMessage+"%"+" Match");


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
                    Log.d("sumit","block user exception= "+e);
                }

            }

        }
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


//                    String Message=jsonSubmitObj.optString("Message");

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


    }

    @Override
    public void onStart() {
        super.onStart();



        try {
//            imageLoader
//                    .displayImage(pref.getUserData().getUserImage(),
//                            userimageright, options, animateFirstListener);
//            imageLoader
//                    .displayImage(profileUserImage,
//                            userimageleft, options, animateFirstListener);

            Picasso.with(getContext()).load(pref.getUserData().getUserImage()).fit().centerCrop()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
                    .transform(new CircleTransform())
                    .into(userimageright);

            Picasso.with(getContext()).load(profileUserImage).fit().centerCrop()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
                    .transform(new CircleTransform())
                    .into(userimageleft);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init() {
        initViews();

    }



    private void initViews() {


        tvTitle = (TextView) mView.findViewById(R.id.txt_headerlogo);

        tvTitle.setText("% Match");

        userimageleft = (ImageView) mView.findViewById(R.id.img_userimageleft);
        userimageright = (ImageView) mView.findViewById(R.id.img_userimageright);

        question1left = (TextView) mView.findViewById(R.id.txt_question1_left);
        question2left = (TextView) mView.findViewById(R.id.txt_question2_left);
        question3left = (TextView) mView.findViewById(R.id.txt_question3_left);
        question4left = (TextView) mView.findViewById(R.id.txt_question4_left);
        question5left = (TextView) mView.findViewById(R.id.txt_question5_left);
        question6left = (TextView) mView.findViewById(R.id.txt_question6_left);
        question7left = (TextView) mView.findViewById(R.id.txt_question7_left);
        question8left = (TextView) mView.findViewById(R.id.txt_question8_left);
        question9left = (TextView) mView.findViewById(R.id.txt_question9_left);
        question10left = (TextView) mView.findViewById(R.id.txt_question10_left);
        question11left = (TextView) mView.findViewById(R.id.txt_question11_left);
        question12left = (TextView) mView.findViewById(R.id.txt_question12_left);
        question13left = (TextView) mView.findViewById(R.id.txt_question13_left);
        question14left = (TextView) mView.findViewById(R.id.txt_question14_left);
        question15left = (TextView) mView.findViewById(R.id.txt_question15_left);
        question16left = (TextView) mView.findViewById(R.id.txt_question16_left);
        question17left = (TextView) mView.findViewById(R.id.txt_question17_left);
        question18left = (TextView) mView.findViewById(R.id.txt_question18_left);
        question19left = (TextView) mView.findViewById(R.id.txt_question19_left);
        question20left = (TextView) mView.findViewById(R.id.txt_question20_left);


        question1right = (TextView) mView.findViewById(R.id.txt_question1_right);
        question2right = (TextView) mView.findViewById(R.id.txt_question2_right);
        question3right = (TextView) mView.findViewById(R.id.txt_question3_right);
        question4right = (TextView) mView.findViewById(R.id.txt_question4_right);
        question5right = (TextView) mView.findViewById(R.id.txt_question5_right);
        question6right = (TextView) mView.findViewById(R.id.txt_question6_right);
        question7right = (TextView) mView.findViewById(R.id.txt_question7_right);
        question8right = (TextView) mView.findViewById(R.id.txt_question8_right);
        question9right = (TextView) mView.findViewById(R.id.txt_question9_right);
        question10right = (TextView) mView.findViewById(R.id.txt_question10_right);
        question11right = (TextView) mView.findViewById(R.id.txt_question11_right);
        question12right = (TextView) mView.findViewById(R.id.txt_question12_right);
        question13right = (TextView) mView.findViewById(R.id.txt_question13_right);
        question14right = (TextView) mView.findViewById(R.id.txt_question14_right);
        question15right = (TextView) mView.findViewById(R.id.txt_question15_right);
        question16right = (TextView) mView.findViewById(R.id.txt_question16_right);
        question17right = (TextView) mView.findViewById(R.id.txt_question17_right);
        question18right = (TextView) mView.findViewById(R.id.txt_question18_right);
        question19right = (TextView) mView.findViewById(R.id.txt_question19_right);
        question20right = (TextView) mView.findViewById(R.id.txt_question20_right);


        ImageView tvCancel = (ImageView) mView.findViewById(R.id.img_back);
        tvCancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_back:

                getActivity().onBackPressed();


                break;
        }
    }



}
