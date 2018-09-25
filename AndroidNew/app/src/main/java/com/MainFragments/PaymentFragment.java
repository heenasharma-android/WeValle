package com.MainFragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.stripe.ErrorDialogFragment;
import com.stripe.PaymentForm;
import com.stripe.ProgressDialogFragment;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by Sumit on 09/11/2015.
 */
public class PaymentFragment extends BaseFragment implements View.OnClickListener,PaymentForm {

    /* Views declarations*/

    private View mView;
    private TextView headertext;
    private RelativeLayout back;
    private double paymentPrice;
    private EditText email,name,cardnumber,cvc;
    private TextView date;
    private RelativeLayout submitpayment;

    /*variables declarations*/


    private String[] separated;
    private ProgressDialogFragment progressFragment;
    /*
         * Change this to your publishable key.
         *
         * You can get your key here: https://manage.stripe.com/account/apikeys
         */
    public static final String PaymentRequest = "PaymentRequest";


    public static final String PUBLISHABLE_KEY = "pk_live_Wceg4A5THtXAp1xBBEfrv87B";
//    public static final String PUBLISHABLE_KEY = "pk_test_rH81uzrShDX86NsafXXcFXp9";

    private AlbanianPreferances pref;
    private String monthDuration,planname,useremail;
    private LinearLayout oneyear,sixmonths,threemonts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        pref = new AlbanianPreferances(getActivity());
        progressFragment = ProgressDialogFragment.newInstance(R.string.progressMessage);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.paymentdetailpage, container, false);

        init();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("Subscriptions");

       // mActivity.hideCurrentTab();
    }

    @Override
    public void onStart() {
        super.onStart();



        if (getArguments() != null)
        {



            paymentPrice = getArguments().getDouble(
                    AlbanianConstants.EXTRA_PAYMENTPRICE);

            if (paymentPrice ==119.40) {

                monthDuration="12";
                planname="1yearplan";
                oneyear.setVisibility(View.VISIBLE);
                sixmonths.setVisibility(View.GONE);
                threemonts.setVisibility(View.GONE);
            }
            else if (paymentPrice ==71.70)
            {
                monthDuration="6";
                planname="6month";
                sixmonths.setVisibility(View.VISIBLE);
                oneyear.setVisibility(View.GONE);
                threemonts.setVisibility(View.GONE);
            }
            else
            {
                monthDuration="3";
                    planname="3month";
                threemonts.setVisibility(View.VISIBLE);
                oneyear.setVisibility(View.GONE);
                sixmonths.setVisibility(View.GONE);
            }


        }
    }

    private void init() {
        initViews();

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }

    private void initViews() {

        email= (EditText)mView.findViewById(R.id.edt_email);
        name= (EditText)mView.findViewById(R.id.edt_name);
        cardnumber= (EditText)mView.findViewById(R.id.edt_cardnumber);
        date= (TextView)mView.findViewById(R.id.edt_date);
        cvc= (EditText)mView.findViewById(R.id.edt_cvc);
        submitpayment= (RelativeLayout)mView.findViewById(R.id.rl_submitpayment_btn);
        oneyear= (LinearLayout)mView.findViewById(R.id.rl_oneyear);
        sixmonths= (LinearLayout)mView.findViewById(R.id.rl_sixmonth);
        threemonts= (LinearLayout)mView.findViewById(R.id.rl_threemonth);

        headertext = (TextView)mView.findViewById(R.id.txt_headerlogo);

        back = (RelativeLayout) mView.findViewById(R.id.rl_back_header);
        back.setOnClickListener(this);
        submitpayment.setOnClickListener(this);
        headertext.setText(mActivity.getString(R.string.paymentdetails));


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int y = c.get(Calendar.YEAR)+4;
                int m = c.get(Calendar.MONTH)-2;
                int d = c.get(Calendar.DAY_OF_MONTH);
                final String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};



                final DatePickerDialog dp = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String erg = "";
//                                erg = String.valueOf(dayOfMonth);
                            erg = String.valueOf(monthOfYear + 1);
                            erg += "/" + year;

//                              ((TextView) et1).setText(erg);

                            date.setText(erg);

                        }

                        }, y, m, d);

//                dp.setTitle("Calender");



                dp.getDatePicker().setCalendarViewShown(false);
                try {
                    dp.getDatePicker().findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                dp.show();


            }
        });

    }



    class DatePickerDialog extends android.app.DatePickerDialog {
        public DatePickerDialog(Context context, int theme,OnDateSetListener callBack,
                                int year, int monthOfYear, int dayOfMonth) {
            super(context, theme,callBack, year, monthOfYear, dayOfMonth);
            updateTitle(year, monthOfYear, dayOfMonth);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        public void onDateChanged(DatePicker view, int year,
                                  int month, int day) {
            updateTitle(year, month, day);
        }
        private void updateTitle(int year, int month, int day) {
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, day);
            setTitle(getFormat().format(mCalendar.getTime()));
        }
        /*
         * the format for dialog tile,and you can override this method
         */
        public SimpleDateFormat getFormat(){
            return new SimpleDateFormat("MMM, yyyy");
        };
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back_header:

                getActivity().onBackPressed();

                break;

            case R.id.rl_submitpayment_btn:

                submitPaymentToServer();

                break;

                default:
                    break;

        }
    }

    private void submitPaymentToServer() {

        useremail = email.getText().toString();
        String str_name = name.getText().toString();

        String str_cardnumber = cardnumber.getText().toString();
        String str_cvc = cvc.getText().toString();

        String str_date = date.getText().toString();

        Matcher mEmailMatcher = AlbanianConstants.EMAIL_ADDRESS_PATTERN
                .matcher(useremail);
        String mTitle = getResources().getString(R.string.app_name);

         if (useremail.length() <= 0) {
            String mMessage = getString(R.string.Pleasefillemail);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        } else if (mEmailMatcher.matches() == false) {
            // valid email
            String mMessage = getString(R.string.Signup_Pleasefillvalidemail);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        } else if (str_name.length() <= 0) {

            String mMessage = getString(R.string.Signup_Pleasefillcardname);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }

        else if (str_cardnumber.length() <= 0) {

            String mMessage = getString(R.string.Signup_Pleasefillcardnumber);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }
        else if (str_date.length() <= 0)
        {

            String mMessage = getString(R.string.Signup_Pleasefilldat);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }
         else if (str_cvc.length() <= 0) {

            String mMessage = getString(R.string.Signup_Pleasefillcvc);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }

        else {


             if(date.getText().length()>0
                     && date.getText().toString().contains("/"))
             {

                 submitpayment.setEnabled(false);

                 separated = date.getText().toString().split("/");

                 saveCreditCard(this);
             }


        }

    }


    public void saveCreditCard(PaymentForm form) {

        Log.d("sumit","UserId"+ pref.getUserData().getUserId());
        Log.d("sumit","Plan"+planname);
//        Log.d("sumit","CardToken"+ id);
        Log.d("sumit","SubscriptionDuration"+ monthDuration);



        Card card = new Card(
                form.getCardNumber(),
                form.getExpMonth(),
                form.getExpYear(),
                form.getCvc());
//        card.setCurrency("$");

        boolean validation = card.validateCard();
        if (validation) {
            startProgress();


            Stripe stripe = new Stripe(getActivity(), PUBLISHABLE_KEY);

            stripe.createToken(card, new TokenCallback() {
                @Override
                public void onError(Exception error) {

                    handleError(error.getLocalizedMessage());
                    finishProgress();

                    submitpayment.setEnabled(true);
                }

                @Override
                public void onSuccess(Token token) {
                    finishProgress();

                    if (token != null && token.getId()!=null) {

                        Log.d("sumit", "token is***= " + token.getId());
                        submitPayment(token.getId());
                    }
                    else
                    {
                        submitpayment.setEnabled(true);

                        handleError("Something went wrong!");
                    }

                }
            });

        } else if (!card.validateNumber()) {

            submitpayment.setEnabled(true);
            handleError("The card number that you entered is invalid");
        } else if (!card.validateExpiryDate()) {
            submitpayment.setEnabled(true);
            handleError("The expiration date that you entered is invalid");
        } else if (!card.validateCVC()) {
            submitpayment.setEnabled(true);
            handleError("The CVC code that you entered is invalid");
        } else {
            submitpayment.setEnabled(true);
            handleError("The card details that you entered are invalid");
        }
    }



    private void startProgress() {
        progressFragment.show(getFragmentManager(), "progress");
    }

    private void finishProgress() {
        progressFragment.dismiss();
    }

    private void handleError(String error) {
        DialogFragment fragment = ErrorDialogFragment.newInstance(R.string.validationErrors, error);
        fragment.show(getFragmentManager(), "error");
    }

    @Override
    public String getCardNumber() {
        return this.cardnumber.getText().toString();
    }

    @Override
    public String getCvc() {
        return this.cvc.getText().toString();
    }

    @Override
    public Integer getExpMonth() {
//        return getInteger(this.separated[0]);i

        Log.d("sumit","month expirat= "+this.separated[0]);
        return getInteger(this.separated[0]);
    }

    @Override
    public Integer getExpYear() {
//        return getInteger(this.separated[1]);
        Log.d("sumit","year expirat= "+this.separated[1]);
        return getInteger(this.separated[1]);
    }


    private Integer getInteger(String spinner) {
        try {
            return Integer.parseInt(spinner);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void submitPayment(final String id) {


        AlbanianApplication.getInstance().cancelPendingRequests(PaymentRequest);

        AlbanianApplication.showProgressDialog(getActivity(),"","Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d(AlbanianConstants.TAG, response.toString());

                parseResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                submitpayment.setEnabled(true);

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {


            @Override
            protected Map<String, String> getParams()
            {


                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "upgradeplan");
                params.put("user_id", pref.getUserData().getUserId());
                params.put("plan", planname);
                params.put("card_token", id);
                params.put("subscription_duration", monthDuration);
                params.put("AppName", AlbanianConstants.AppName);
//                params.put("Description", useremail);

                Log.d(AlbanianConstants.TAG, "params= payment ="+params);

                return params;
            }

        };

        // Adding request to request queue
        AlbanianApplication.getInstance().addToRequestQueue(sr, PaymentRequest);


    }

    private void parseResponse(String Result) {

        submitpayment.setEnabled(true);

        Log.d(AlbanianConstants.TAG, "stripe= " + Result);
        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        getActivity().onBackPressed();


                    }

                    else
                    {



                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }



                }
                catch (Exception e) {

                    submitpayment.setEnabled(true);
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","payment exception= "+e);
                }

            }

        }
    }



}
