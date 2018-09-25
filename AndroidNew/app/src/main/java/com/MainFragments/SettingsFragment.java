package com.MainFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.FragmentChangeListener;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.editprofile.EmailFragment;
import com.editprofile.PasswordFragment;
import com.editprofile.UsernameFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumit on 26/09/2015.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {




    /*UI declaration........*/
    private View mView;
//    private ToggleButton goinvisible;
    private TextView blockedlist,deactivateaccount,terms;
//    private TextView privacy,faq;

    private RelativeLayout back;
//    private TextView min_max_age;
    private RelativeLayout logout;
    private TextView headertext;
    private TextView submitqf;
    private TextView mymembership;

    /* Variables...... */

    private String tag_whoiviewed_obj = "jsentmessages_req";
    private AlbanianPreferances pref;
    private String str_minage,str_maxage;
    private String CURRENTTABTAG,emailOptIn;
    private Switch emailOptInSwitch;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        pref = new AlbanianPreferances(getActivity());

    }

    @Override
    public void onStart() {
        super.onStart();

//
//        if (getArguments() != null) {
//
//
//            CURRENTTABTAG = getArguments().getString(
//                    AlbanianConstants.EXTRA_CURRENTTAB_TAG);
//            emailOptIn = getArguments().getString(
//                    AlbanianConstants.EXTRA_EMAILOPTIN);
//
//            Log.d("sumit","emailOptIn rec= "+emailOptIn);
//
//        }

//        if (emailOptIn.equals("1")) {
//
//            emailOptInSwitch.setChecked(true);
//        }
//        else
//        {
//            emailOptInSwitch.setChecked(false);
//        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.settings, container, false);



        initViews();


        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }




    private void initViews() {

        emailOptInSwitch = (Switch)mView.findViewById(R.id.toggle_onoff_emailnoti);
        headertext = (TextView)mView.findViewById(R.id.txt_headerlogo);
       // headertext.setText(mActivity.getString(R.string.nav_item_settings));

        back = (RelativeLayout)mView.findViewById(R.id.rl_back_header);
        logout = (RelativeLayout)mView.findViewById(R.id.rl_logout_btn);
//        goinvisible = (ToggleButton)mView.findViewById(R.id.toggle_onoff);
        blockedlist = (TextView)mView.findViewById(R.id.tv_block_list);
        deactivateaccount = (TextView)mView.findViewById(R.id.tv_deactivate_account);
        terms = (TextView)mView.findViewById(R.id.tv_tos);
//        privacy = (TextView)mView.findViewById(R.id.tv_privacy_policy);
        mymembership = (TextView)mView.findViewById(R.id.tv_mymembership);

//        faq = (TextView)mView.findViewById(R.id.tv_faq);
        submitqf = (TextView)mView.findViewById(R.id.tv_contact);


        RelativeLayout tvUsername = (RelativeLayout) mView.findViewById(R.id.rl_username);
        RelativeLayout tvEmail = (RelativeLayout) mView.findViewById(R.id.rl_email);
        RelativeLayout tvPassword = (RelativeLayout) mView.findViewById(R.id.rl_password);

//        min_max_age = (TextView)mView.findViewById(R.id.txt_min_max_age);

//        min_max_age.setText(pref.getUserData().getUserMinAge()+"-"+
//                                pref.getUserData().getUserMaxAge());

//        faq.setOnClickListener(this);
        submitqf.setOnClickListener(this);

        mymembership.setOnClickListener(this);
//        min_max_age.setOnClickListener(this);
        blockedlist.setOnClickListener(this);
        deactivateaccount.setOnClickListener(this);
        terms.setOnClickListener(this);
//        privacy.setOnClickListener(this);
        logout.setOnClickListener(this);
        back.setOnClickListener(this);

        tvUsername.setOnClickListener(this);
        tvEmail.setOnClickListener(this);
        tvPassword.setOnClickListener(this);

//        goinvisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
//
//
//
//                if (pref.getUserData().getUserSubscriptionStatus().equals("0"))
//                {
//
//                    goinvisible.setOnCheckedChangeListener(null);
//                    goinvisible.setChecked(false);
//                    freeUserAlert();
//
//                }
//                else
//                {
//                    if (isChecked) {
//
//                        goInvisible(1);
//                    }
//                    else
//                    {
//
//                        goInvisible(0);
//                    }
//                }
//
//
//            }
//        });


        if (pref.getUserData().getUserSubscriptionStatus().equals("1")) {

            mymembership.setVisibility(View.VISIBLE);
        }
        else
        {
            mymembership.setVisibility(View.GONE);
        }


        emailOptInSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //is chkIos checked?
                if (((Switch) v).isChecked()) {
                    //Case 1
                    emailOptInSwitch.setChecked(true);
                    setEmailNotificationPreferance("1");
                }
                else
                {
                    emailOptInSwitch.setChecked(false);
                    setEmailNotificationPreferance("0");
                }
                //case 2
            }
        });



    }

    private void setEmailNotificationPreferance(String emailPref) {

        setEmailPrefOnServer(emailPref);
    }


    private void setEmailPrefOnServer(final String emailPref) {


            AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


            StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(AlbanianConstants.TAG, response.toString());

                    parseResponse(response.toString());

                    AlbanianApplication.hideProgressDialog(getActivity());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
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

                    params.put("api_name", "opt_in");
                    params.put("user_id", pref.getUserData().getUserId());
//                    params.put("user_invisible_status", String.valueOf(i));


                    return params;
                }

            };

            // Adding request to request queue
//        AlbanianApplication.getInstance().addToRequestQueue(jsonObjReq,
//                tag_whoiviewed_obj);

            AlbanianApplication.getInstance().addToRequestQueue(sr);

            // Cancelling request
            // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_whoiviewed_obj);


    }

    private void parseResponse(String s) {


    }






    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.rl_username:

                if (pref.getUserData().getUserSubscriptionStatus().equals("0"))

                {
                    showPaymentAlert();
                }
                else
                {


//                    Bundle bundle=new Bundle();
//                    bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);
//
//                    mActivity.pushFragments(CURRENTTABTAG,
//                            new UsernameFragment(), false, true, bundle);
                }


                break;
            case R.id.rl_email:

//                Bundle bundle=new Bundle();
//                bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);
//
//                mActivity.pushFragments(CURRENTTABTAG,
//                        new EmailFragment(), false, true, bundle);

                break;

            case R.id.rl_password:

                Fragment fr = new PasswordFragment();
                FragmentChangeListener fc = (FragmentChangeListener) getActivity();
                fc.replaceFragment(fr, "Password");

//                Bundle bundlePassword=new Bundle();
//                bundlePassword.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);
//
//                mActivity.pushFragments(CURRENTTABTAG,
//                        new PasswordFragment(), false, true, bundlePassword);

                break;

            case R.id.txt_min_max_age:

//                showAgesDalog();

                break;

            case R.id.tv_block_list:

                loadBlockedList();

                break;

            case R.id.tv_mymembership:

                loadMembershipStatus();

                break;

            case R.id.tv_deactivate_account:

                showDeactivateAccount();
                break;

            case R.id.tv_tos:

//                loadTosPage("http://albaniancircle.com/terms.html");

//                Bundle bundleTos=new Bundle();
//                bundleTos.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);
//                bundleTos.putString(AlbanianConstants.EXTRA_LOCALPAGENAME, AlbanianConstants.EXTRA_TOS);
//
//                mActivity.pushFragments(CURRENTTABTAG,
//                        new FaqFragment(), false, true, bundleTos);

                Fragment fr1 = new FaqFragment();
                FragmentChangeListener fc1 = (FragmentChangeListener) getActivity();
                fc1.replaceFragment(fr1, "Password");

                break;

            case R.id.rl_back_header:

                FragmentManager fm = getFragmentManager();

                for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++){
                    Log.d("sumit", "upgrade no of frag.= " + fm.getBackStackEntryCount());
                    Log.d("sumit", "upgrade frag.= " + fm.getBackStackEntryAt(entry).getId());
                }

                getActivity().onBackPressed();

                break;

//            case R.id.tv_privacy_policy:
//
//                loadTosPage("http://albaniancircle.com/terms.html");
//                break;

            case R.id.rl_logout_btn:


                logoutAlert();



                break;

//            case R.id.tv_faq:
//
//
//                Bundle bundle=new Bundle();
//                bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);
//                bundle.putString(AlbanianConstants.EXTRA_LOCALPAGENAME, AlbanianConstants.EXTRA_FAQ);
//
//                mActivity.pushFragments(CURRENTTABTAG,
//                        new FaqFragment(), true, true, bundle);
//
//                break;

            case R.id.tv_contact:


               showSubmitOptions();;



                break;



        }
    }


    private void showPaymentAlert() {

        AlertDialog.Builder mAlert = new AlertDialog.Builder(getActivity());
        mAlert.setCancelable(false);
        mAlert.setTitle(getResources().getString(R.string.app_name));
        mAlert.setMessage("To change your username you must be a premium member.");
//        if (isCancelBtn) {
//            mAlert.setNegativeButton("Cancel",
//                    new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//        }

        mAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                loadUpgradeFragment();
            }
        });

        mAlert.show();
    }

    private void loadUpgradeFragment() {

//        Fragment upgradeFragment = new UpgradeFragment();
//        addFragment(upgradeFragment,"","");

//        Bundle bundle=new Bundle();
//        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
//                CURRENTTABTAG);
//
//        mActivity.pushFragments(CURRENTTABTAG,
//                new UpgradeFragment(), false, true, bundle);

        Fragment fr = new UpgradeFragment();
        FragmentChangeListener fc = (FragmentChangeListener) getActivity();
        fc.replaceFragment(fr, "Password");


    }



    private void logoutAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("LOG OUT");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to logout?");

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.delete);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                dialog.cancel();
                pref.logoutUser(getActivity(), pref.getUserData().getUserId());


            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

//    private void showAgesDalog() {
//
//        final Dialog d = new Dialog(getActivity());
//        d.setTitle("Select Age");
//        d.setContentView(R.layout.agegroup_dialog);
//        Button b1 = (Button) d.findViewById(R.id.btn_ok);
//        Button b2 = (Button) d.findViewById(R.id.btn_cancel);
//        final NumberPicker numberPicker_min = (NumberPicker) d.findViewById(R.id.numberPicker_min);
//        numberPicker_min.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
//        numberPicker_min.setMaxValue(55); // max value 100
//        numberPicker_min.setMinValue(18);   // min value 0
//        numberPicker_min.setWrapSelectorWheel(false);
//
//        final NumberPicker numberPicker_max = (NumberPicker) d.findViewById(R.id.numberPicker_max);
//        numberPicker_max.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
//        numberPicker_max.setMaxValue(55); // max value 100
//        numberPicker_max.setMinValue(18);   // min value 0
//        numberPicker_max.setValue(55);
//        numberPicker_max.setWrapSelectorWheel(false);
////        np.setOnValueChangedListener(this);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if(numberPicker_max.getValue()-numberPicker_min.getValue()<6)
//                {
//                    AlbanianApplication.ShowAlert(getActivity(),getActivity().getResources().getString(R.string.app_name),
//                            "The maximum age must be (6) six greater than the minimum age.",false);
//                }
//                else if(numberPicker_min.getValue()<18)
//                {
//                    AlbanianApplication.ShowAlert(getActivity(),getActivity().getResources().getString(R.string.app_name),
//                            "The minimum age must be 18.",false);
//                }
//                else if(numberPicker_max.getValue()>55)
//                {
//                    AlbanianApplication.ShowAlert(getActivity(),getActivity().getResources().getString(R.string.app_name),
//                            "The maximum age must be less than 55.",false);
//                }
//                else
//                {
//                    str_minage=String.valueOf(numberPicker_min.getValue());
//                    str_maxage=String.valueOf(numberPicker_max.getValue());
//
//                    setAgePreferances(str_minage,str_maxage);
//
//                     //set the value to textview
//                }
//
//
//                d.dismiss();
//            }
//        });
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                d.dismiss();
//            }
//        });
//        d.show();
//    }



    private void showSubmitOptions() {

        android.app.AlertDialog.Builder builderSingle;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
        {
            builderSingle = new android.app.AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));
        }
        else
        {
            builderSingle = new android.app.AlertDialog.Builder(
                    getActivity());
        }


//      builderSingle.setTitle("Upload image");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.select_dialog_item);


        arrayAdapter.add("Feedback");
        arrayAdapter.add("Question");


        builderSingle.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        long strName = arrayAdapter.getItemId(which);


                        if (strName == 0) {
                            // comment report

                            sendEmail("I have feedback");

                        } else if (strName == 1) {
                            // comment report

                            sendEmail("I have a question");

                        }


                    }
                });

        builderSingle.show();

    }

    private void sendEmail(String str) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"hello"+AlbanianConstants.AppDomain});
        i.putExtra(Intent.EXTRA_SUBJECT, str);
        i.putExtra(Intent.EXTRA_TEXT, "");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients.", Toast.LENGTH_SHORT).show();
        }
    }



    private void showDeactivateAccount() {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Deactivate account");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to deactivate your account?");

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.delete);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                deactivateAccount();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();


    }


    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("Upgrade Screen");

        //mActivity.hideCurrentTab();
    }

    private void deactivateAccount() {

        pref.deactivateUserAccount(getActivity(),pref.getUserData().getUserId(),"","");
    }


//    private void setAgePreferances(final String minAge, final String maxAge) {
//
//        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");
//
//
//
//        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d(AlbanianConstants.TAG, "age range= " + response.toString());
//                AlbanianApplication.hideProgressDialog(getActivity());
//
////                parseResponse(response.toString());
//                parseAgePreferanceResponse(response.toString());
//
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(AlbanianConstants.TAG, "age range= Error: " + error.getMessage());
//                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());
//
//                AlbanianApplication.hideProgressDialog(getActivity());
//
//            }
//        })
//
//        {
//
//
//
//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("api_name", "updateagerange");
//                params.put("user_min_age", minAge );
//                params.put("user_max_age", maxAge );
//                params.put("user_id", pref.getUserData().getUserId() );
//                params.put("AppName", AlbanianConstants.AppName);
//
//                return params;
//            }
//
//        };
//
//
//        AlbanianApplication.getInstance().addToRequestQueue(sr);
//    }

//    private void parseAgePreferanceResponse(String Result) {
//
//
//
//        if (Result != null) {
//
//            {
//
//                try {
//
//                    JSONObject jObj = new JSONObject(Result);
//                    String ErrorCode = jObj.optString("ErrorCode");
//                    String ErrorMessage = jObj.optString("ErrorMessage");
////                    String mMessage = jObj.getString("msg");
//
//
//                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);
//
//                    if (ErrorCode.equalsIgnoreCase("1")) {
//
//
//                                UserModel userModel=pref.getUserData();
//                                userModel.setUserMinAge(str_minage);
//                                userModel.setUserMaxAge(str_maxage);
//
//                                pref.setUserData(userModel);
//
//                                min_max_age.setText(str_minage+ "-" + str_maxage);
//
//
//
//                    }
//
//                    else
//                    {
//
//                        String mTitle = getResources().getString(R.string.app_name);
//
//                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
//                                ErrorMessage, false);
//                    }
//
//
//
//                }
//                catch (Exception e) {
//                    // TODO Auto-generated catch block
////                        e.printStackTrace();
//                    Log.d("sumit","profile exception= "+e);
//                }
//
//            }
//
//        }
//
//
//
//
//    }


    private void loadBlockedList() {

//
//        Bundle bundle=new Bundle();
//        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);
//
//        mActivity.pushFragments(CURRENTTABTAG,
//                new BloackedUserFragment(), false, true, bundle);
        Fragment fr = new BloackedUserFragment();
        FragmentChangeListener fc = (FragmentChangeListener) getActivity();
        fc.replaceFragment(fr, "Password");
    }

    private void loadMembershipStatus() {

//        Bundle bundle=new Bundle();
//        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);
//
//        mActivity.pushFragments(CURRENTTABTAG,
//                new MyMembershipFragment(), false, true, bundle);
        Fragment fr = new MyMembershipFragment();
        FragmentChangeListener fc = (FragmentChangeListener) getActivity();
        fc.replaceFragment(fr, "Password");
    }


    private void loadTosPage(String extra) {


        String url = extra;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }


    private void freeUserAlert() {
        String mTitle = getResources().getString(R.string.app_name);

        AlertDialog.Builder mAlert = new AlertDialog.Builder(getActivity());
        mAlert.setCancelable(false);
        mAlert.setTitle(mTitle);
        mAlert.setMessage("To send a message upgrade to a premium member.");

        mAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

//                Bundle bundle=new Bundle();
//                bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
//                        CURRENTTABTAG);
//
//                mActivity.pushFragments(CURRENTTABTAG,
//                        new UpgradeFragment(), false, true, bundle);

                Fragment fr = new UpgradeFragment();
                FragmentChangeListener fc = (FragmentChangeListener) getActivity();
                fc.replaceFragment(fr, "Password");


            }
        });

        mAlert.show();
    }

}
