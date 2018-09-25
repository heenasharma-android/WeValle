package com.SigninSignup;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MainFragments.HomeFragmentNewActivity;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.albaniancircle.RoundedCornersTransform;
import com.android.camera.CropImageIntentBuilder;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.imageoptions.ObjectPreferences;
import com.models.CountryModel;
import com.models.SqlLiteDbHelper;
import com.models.UserModel;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by Sumit on 13/09/2015.
 */
public class SignUpFirstFragment extends Fragment implements View.OnClickListener
,AsyncTaskCompleted{


    /**********
     * Login Fragment UI Declaration
     */

    private View mView;
    private Button continuebtn;
    private EditText username, email,/* confirmemail,confirmpassword,
            howdidyou,*/ password;
    private EditText age;
//    private EditText  city;
    private RadioButton femaleradio;
    final int radius = 5;
    final int margin = 3;
//    private Spinner spCountry,spHeight,spReligion;
//    private Spinner spHear;
    private RadioGroup radiogroup_gender;
    private ImageView backImage;
    private ImageView ivImage,ivImage1;

    private ProgressDialog mImageDialog;
    /* variables declaration */


    private AlbanianPreferances pref;
    private String str_username, str_email, str_password;
    private String str_age;
//    private String str_height="",str_religion="",str_country,str_city;
    private UserModel usermodel;
    private SqlLiteDbHelper dbHelper;
    private ArrayList<CountryModel> countriesList;
    private ArrayList<String> arrayHights;
    private ArrayList<String> arrayReligions;
//    private ArrayList<String> hearAboutList;

    private Activity mActivity;
    private String imagename;
    private String selectedPath;
    private Uri selectedImageUri;
    private File filetosave;
    ObjectPreferences prefs;
    private static final int CAPTURE_PICTURE = 1;
    private static final int SELECT_PICTURE = 2;
    private static final int CROP_FROM_CAMERA = 3;
//    DisplayImageOptions options_image;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private HashMap<String,String> signupimages;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new AlbanianPreferances(getActivity());
        usermodel = new UserModel();
        signupimages=new HashMap<>();


        mImageDialog = new ProgressDialog(getActivity());
        mImageDialog.setTitle(getResources().getString(R.string.app_name));
        mImageDialog.setMessage("Loading Image...");
        mImageDialog.setCancelable(false);

//        options_image = new DisplayImageOptions.Builder()
//                .displayer(new RoundedBitmapDisplayer(300))
////                .showStubImage(R.drawable.profile_male_btn)
////                .showImageForEmptyUri(R.drawable.profile_male_btn)
////                .showImageOnFail(R.drawable.profile_male_btn)
//                .cacheInMemory()
////                .cacheOnDisc()
//
//                .build();

        File folder = new File(Environment.getExternalStorageDirectory().toString()+"/AlbanianCircle");
        folder.mkdirs();

        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                    } else {
                        // Oups permission denied
                    }
                });


    }

    @Override
    public void onStart() {
        super.onStart();
        AlbanianApplication.hideKeyBoard(getActivity(), getActivity());
        if (AlbanianApplication.isInternetOn(getActivity())) {
        } else {
            String mTitle = getResources().getString(R.string.app_name);
            AlbanianApplication.ShowAlert(getActivity(), mTitle,
                    getResources().getString(R.string.nointernet), false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("Signup Screen");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.signup1, container, false);
        init();
//        iniView(mView);
        return mView;
    }

    private void init() {
        iniView(mView);
        initListners();
    }

    private void initListners()
    {
//        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                str_country = countriesList.get(position).getCountry_name().toString();
//                int index = parent.getSelectedItemPosition();
//                if (position !=0) {
//                    RelativeLayout rl = ((RelativeLayout) spCountry.getSelectedView());
//                    ((TextView) rl.findViewById(R.id.sub_text_seen)).setTextColor(getResources().getColor(R.color.black));
//                }
//                Log.d("sumit","selected country is= "+str_country);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        spHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                str_height = arrayHights.get(position).toString();
//
//                if (position !=0) {
//
//
//                    RelativeLayout rl = ((RelativeLayout) spHeight.getSelectedView());
//                    ((TextView) rl.findViewById(R.id.sub_text_seen)).setTextColor(getResources().getColor(R.color.black));
//                }
//                Log.d("sumit","selected str_height is= "+str_height);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        spReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                str_religion = arrayReligions.get(position).toString();
//                if (position !=0) {
//                    RelativeLayout rl = ((RelativeLayout) spReligion.getSelectedView());
//                    ((TextView) rl.findViewById(R.id.sub_text_seen)).setTextColor(getResources().getColor(R.color.black));
//                }
//                Log.d("sumit","selected str_religion is= "+str_religion);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        spHear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                str_hearus = parent.getItemAtPosition(position).toString();
//                Log.d("sumit","selected str_hearus is= "+str_hearus);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }


    private void iniView(View mView) {

        AlbanianApplication.hideKeyBoard(getActivity(), getActivity());

        TextView tvTitle = (TextView) mView.findViewById(R.id.tv_title);
        tvTitle.setText("Register");
        TextView tvSave = (TextView) mView.findViewById(R.id.tv_save);
        RelativeLayout rel_Save = (RelativeLayout) mView.findViewById(R.id.rl_save_header);
        tvSave.setText("");
//        tvSave.setBackgroun(R.drawable.back_btn);
//        tvSave.setRotation(180);
//        rel_Save.setOnClickListener(this);
        TextView tvCancel = (TextView) mView.findViewById(R.id.tv_cancel);
        backImage = (ImageView) mView.findViewById(R.id.img_back);
        tvCancel.setVisibility(View.INVISIBLE);
        backImage.setVisibility(View.VISIBLE);
        backImage.setOnClickListener(this);

        radiogroup_gender = (RadioGroup)mView.findViewById(R.id.radio_group_gender);


        email = (EditText) mView.findViewById(R.id.edt_email);
//        confirmemail = (EditText) mView.findViewById(R.id.edt_reenter_email);

        username = (EditText) mView.findViewById(R.id.edt_username);
        email = (EditText) mView.findViewById(R.id.edt_email);
        femaleradio = (RadioButton) mView.findViewById(R.id.rb_female);

        password = (EditText) mView.findViewById(R.id.edt_password);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_NEXT) {

                    AlbanianApplication.hideKeyBoard(getActivity(),getActivity());

//                    username.clearFocus();
//                    email.clearFocus();
//                    password.clearFocus();
//
//                    username.setFocusable(false);
//                    email.setFocusable(false);
//                    password.setFocusable(false);
//
//                    femaleradio.setChecked(true);

                    return true;
                }

                return false;
            }
        });


//        username.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                username.setFocusable(true);
//                username.setFocusableInTouchMode(true);
//                username.requestFocus();
//
//                email.setFocusable(true);
//                email.setFocusableInTouchMode(true);
//
//                password.setFocusable(true);
//                password
//                        .setFocusableInTouchMode(true);
//
//                return false;
//            }
//        });

//        email.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                username.setFocusable(true);
//                username.setFocusableInTouchMode(true);
//
//
//                email.setFocusable(true);
//                email.setFocusableInTouchMode(true);
//
//                email.requestFocus();
//
//                password.setFocusable(true);
//                password
//                        .setFocusableInTouchMode(true);
//
//                return false;
//            }
//        });

//        password.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                username.setFocusable(true);
//                username.setFocusableInTouchMode(true);
//
//
//                email.setFocusable(true);
//                email.setFocusableInTouchMode(true);
//
//                password.setFocusable(true);
//                password
//                        .setFocusableInTouchMode(true);
//
//                password.requestFocus();
//                return false;
//            }
//        });


//        confirmpassword = (EditText) mView.findViewById(R.id.edt_confirmpassword);

//        city = (EditText) mView.findViewById(R.id.edt_city);
        String locale = getActivity().getResources().getConfiguration().locale.getCountry();
        String locale1 = getActivity().getResources().getConfiguration().locale.getDisplayCountry();
        Log.d("sumit","locale= "+getUserCountry(getActivity()));

//       howdidyou = (EditText) mView.findViewById(R.id.edt_hear_us);
        continuebtn = (Button) mView.findViewById(R.id.btn_continue_signup1);

        age = (EditText) mView.findViewById(R.id.edt_age);
        age.setOnClickListener(this);

        continuebtn.setOnClickListener(this);

        addSpinnerCountry();


        ivImage1 = (ImageView) mView.findViewById(R.id.iv_image1);
        ivImage1.setOnClickListener(this);





        if (usermodel != null) {

            username.setText(usermodel.getUserName());
            email.setText(usermodel.getUserEmail());
//            confirmemail.setText(usermodel.getUserEmail());
            password.setText(usermodel.getUserPassword());
//            confirmpassword.setText(usermodel.getUserPassword());
            age.setText(usermodel.getAge());
//            city.setText(usermodel.getUserCity());

        }

    }


    /**
     * Get ISO 3166-1 alpha-2 country code for this device (or null if not available)
     * @param context Context reference to get the TelephonyManager instance from
     * @return country code or null
     */
    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        }
        catch (Exception e) { }
        return null;
    }


    public void addSpinnerCountry() {
        countriesList = new ArrayList<>();
//        hearAboutList = new ArrayList<>();

        try {
            dbHelper = new SqlLiteDbHelper(getActivity());
            dbHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        countriesList = dbHelper.getCountryList();






//        hearAboutList = dbHelper.getHearAboutList();
//        hearAboutList.add(0,AlbanianConstants.EXTRA_HEARSTRING);


//        spCountry = (Spinner) mView.findViewById(R.id.sp_country);
//        spHear = (Spinner) mView.findViewById(R.id.sp_hear_us);
        ArrayAdapter<CountryModel> dataAdapter = new ArrayAdapter<CountryModel>(getActivity(),
                android.R.layout.simple_spinner_item, countriesList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spCountry.setAdapter(new MyAdapter(getActivity(), R.layout.country_spinner_item,
//                countriesList));

//        spHear.setAdapter(new HearAdapter(getActivity(), R.layout.country_spinner_item,
//                hearAboutList));

//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_spinner_item, hearAboutList);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spHear.setAdapter(dataAdapter);






        arrayHights = new ArrayList<>();
        arrayReligions = new ArrayList<>();

        try {
            dbHelper = new SqlLiteDbHelper(getActivity());
            dbHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        arrayHights = dbHelper.getTallList();
        arrayHights.add(0,mActivity.getString(R.string.selectheight));

//        spHeight = (Spinner) mView.findViewById(R.id.sp_height);
//        spHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                str_height=arrayHights.get(position);
//                Log.d("sumit","str_height selc= "+str_height);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        spHeight.setAdapter(new HearAdapter(getActivity(), R.layout.country_spinner_item,
//                arrayHights));

        ////////////////


        try {
            dbHelper = new SqlLiteDbHelper(getActivity());
            dbHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        arrayReligions = dbHelper.getRelgionList();
        arrayReligions.add(0,mActivity.getString(R.string.selectreligion));
//        spReligion = (Spinner) mView.findViewById(R.id.sp_religion);

        final ArrayList<String> finalArrayReligions = arrayReligions;

//        spReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                str_religion= finalArrayReligions.get(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        spReligion.setAdapter(new HearAdapter(getActivity(), R.layout.country_spinner_item,
//                arrayReligions));

    }

    private void removeAllFocus() {
        AlbanianApplication.hideKeyBoard(getActivity(), getActivity());
    }

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "MMM-dd-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        age.setText(sdf.format(myCalendar.getTime()));

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_image1:
                startDialog(ivImage1,"image1");
                break;

            case R.id.edt_age:



                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog,date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setCalendarViewShown(false);
//                datePickerDialog.getDatePicker().findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

                break;
            case R.id.btn_continue_signup1:

                validateDetails();

                break;

            case R.id.img_back:

                getFragmentManager().popBackStack();

                break;
            default:
                break;
        }
    }

    private void validateDetails() {
        int diff = 0;

        try {
            AlbanianApplication.hideKeyBoard(getActivity(), getActivity());

            SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy");
            str_age = age.getText().toString();
            //convert the string date to a java date

            java.util.Date pickerdate=null;

            try
            {
                pickerdate = formatter.parse(str_age);

                Log.d("sumit","picker date= "+pickerdate);

            } catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();

                Log.d("sumit","picker date exception = "+e);
            }

            //printing the system current date and picker date

            java.util.Date systemdate=new Date(System.currentTimeMillis());

            Log.d("sumit","current date= "+systemdate);


            Calendar a = getCalendar(pickerdate);
            Calendar b = getCalendar(systemdate);
            diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
            if (a.get(Calendar.DAY_OF_YEAR) > b.get(Calendar.DAY_OF_YEAR)) {
                diff--;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //// validate date

        String mTitle = getResources().getString(R.string.app_name);




        str_username = username.getText().toString();
        str_email = email.getText().toString();
//        str_confirmemail = confirmemail.getText().toString();
        str_password = password.getText().toString();
//        str_confirmpassword = confirmpassword.getText().toString();

//           str_country = country.getText().toString();
//        str_city = city.getText().toString();

//            str_password=password.getText().toString();

        Matcher mEmailMatcher = AlbanianConstants.EMAIL_ADDRESS_PATTERN
                .matcher(str_email);

//            String radiovalue = ((RadioButton)mView.findViewById(radiogroup_gender.getCheckedRadioButtonId())).getText().toString();

        int id = radiogroup_gender.getCheckedRadioButtonId();
        if (id == -1){
            //no item selected
        }
        else{
            if (id == R.id.rb_female){
                //Do something with the button
                usermodel.setUserGender("f");
            }
            else
            {
                usermodel.setUserGender("m");
            }

            Log.d("sumit", "radio value= " + usermodel.getUserGender());
//            Log.d("sumit", "str_height= " + str_height);
        }


        if (str_username.length() <= 0) {
            String mMessage = getString(R.string.Pleasefillusername);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }
        else if (str_email.length() <= 0) {
            String mMessage = getString(R.string.Pleasefillemail);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        } else if (mEmailMatcher.matches() == false) {
            // valid email
            String mMessage = getString(R.string.Signup_Pleasefillvalidemail);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }
        else if (str_password.length() <= 0) {

            String mMessage = getString(R.string.Signup_Pleasefillpassword);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }

        else if (id == -1){
            //no item selected
            String mMessage = getString(R.string.Signup_Pleasefillgender);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }

        else if (str_age.length() <= 0) {

            String mMessage = getString(R.string.Signup_Pleasefillage);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }
        else if(diff<18)
        {
            AlbanianApplication.ShowAlert(getActivity(),mTitle, getString(R.string.agerestriction),false);
        }

//        else if (str_country.length() <= 0||
//                str_country.equalsIgnoreCase("Country")) {
//
//            String mMessage = getString(R.string.Signup_Pleasefillcountry);
//            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
//        }


//        else if (str_height.length()<=0||
//                str_height.equalsIgnoreCase(getString(R.string.selectheight))) {
//
//            String mMessage = getString(R.string.Signup_Pleasefillheight);
//            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
//        }
//        else if (str_religion.length()<=0||
//                str_religion.equalsIgnoreCase(getString(R.string.selectreligion))) {
//
//            String mMessage = getString(R.string.Signup_Pleasefillreligion);
//            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
//        }
//        else if (str_hearus.equals(AlbanianConstants.EXTRA_HEARSTRING)) {
//
//            String mMessage = getString(R.string.Signup_Pleasestr_hearus);
//            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
//
//        }

        else if (signupimages.get("image1")==null ||signupimages.get("image1").length()<=0 ) {

            String mMessage = getString(R.string.Signup_Pleasefillmainimage);
            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
        }

//        else if (str_city.length() <= 0) {
//
//            String mMessage = getString(R.string.Signup_Pleasefillcity);
//            AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
//        }

        else {



            usermodel.setUserName(str_username);
            usermodel.setUserEmail(str_email);
            usermodel.setUserPassword(str_password);
            usermodel.setUserDOB(str_age);
//            usermodel.setUserCountry((str_country==null||str_country.length()<=0)?"":str_country);
//            usermodel.setUserCity((str_city==null||str_city.length()<=0)?"":str_city);
//            usermodel.setUserHeight(str_height.replace("\"","&quot;"));
//            usermodel.setUserReligion(str_religion);
            usermodel.setUserCountry("");
            usermodel.setUserCity("");
            usermodel.setUserHeight("");
            usermodel.setUserReligion("");
            usermodel.setUserOtherLanguages("");
            usermodel.setUserDescOneWord("");
            usermodel.setUserPets("");
            usermodel.setUserFavMovies("");
            usermodel.setUserDrinks("");
            usermodel.setUserSmokes("");
            usermodel.setUserInterestedIn("");
            usermodel.setUserFavQuote("");
            usermodel.setUserAlbanianSongs("");
            usermodel.setUserDescription("");
            usermodel.setUserFullName("");
            usermodel.setUserState("");
            usermodel.setUserHearAboutUs("");
            usermodel.setUserlatitude("");
            usermodel.setUserlogitude("");



            checkDuplicacy(usermodel);


        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.mActivity=activity;
    }


    private void startDialog(ImageView ivImage1,String imagename1) {
        ivImage = ivImage1;
        imagename = imagename1;
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(getActivity());

        myAlertDialog.setTitle(mActivity.getString(R.string.uploadphoto));
        myAlertDialog.setMessage(mActivity.getString(R.string.uploadfrom));
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        RxPermissions rxPermissions = new RxPermissions(getActivity());

                        Log.d("sumit", "permission mra= " + rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        );

                        if (rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                            openGallery();
                        } else {
                            rxPermissions
                                    .request(Manifest.permission.CAMERA,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    .subscribe(granted -> {
                                        if (granted) { // Always true pre-M
                                            // I can control the camera now
                                        } else {
                                            // Oups permission denied
                                        }
                                    });
                        }

                    }
                });
        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        RxPermissions rxPermissions = new RxPermissions(getActivity());


                        if (rxPermissions.isGranted(Manifest.permission.CAMERA)) {

                            openCamera();
                        } else {
                            rxPermissions
                                    .request(Manifest.permission.CAMERA,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    .subscribe(granted -> {
                                        if (granted) { // Always true pre-M
                                            // I can control the camera now
                                        } else {
                                            // Oups permission denied
                                        }
                                    });
                        }
                    }
                });
        myAlertDialog.show();
    }



    public void openGallery() {

        selectedPath = null;
        // profileimage.setImageResource(R.drawable.avatar1);
        Intent intent = new Intent();
        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_PICK);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                SELECT_PICTURE);



    }

    private void openCamera() {

        selectedPath=null;

        try
        {
            PackageManager pm = getActivity().getApplicationContext()
                    .getPackageManager();
            if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                try {
                    String fileName = "new-photo1.jpg";
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    selectedImageUri = getActivity().getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);

                    if (prefs == null)
                        prefs = new ObjectPreferences(getActivity()
                                .getApplicationContext());
                    prefs.saveImageUri(selectedImageUri);
                    try {
                        File file = new File(selectedImageUri.getPath());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent1.putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUri);
                    intent1.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                    startActivityForResult(intent1, CAPTURE_PICTURE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(),
                        "This device does not support the camera feature",
                        Toast.LENGTH_LONG).show();

                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(
                        getActivity().getApplicationContext());
                alertDialog.setTitle("");
                alertDialog
                        .setMessage("This device does not support the camera feature.");
                alertDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        });
                alertDialog.show();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    public static Calendar getCalendar(java.util.Date date) {

        Calendar cal = Calendar.getInstance(Locale.US);


        cal.setTime(date);
        return cal;

    }

    private void checkDuplicacy(final UserModel usermodel) {


        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "signup duplicay=" + response.toString());

                parseDuplicacyResponse(response.toString());

//                AlbanianApplication.hideProgressDialog(getActivity());

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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "checkduplicacy");
                params.put("user_email", usermodel.getUserEmail());
                params.put("user_name", usermodel.getUserName());
                params.put("AppName", AlbanianConstants.AppName);


                return params;
            }

        };

        // Adding request to request queue
//        AlbanianApplication.getInstance().addToRequestQueue(jsonObjReq,
//                tag_whoiviewed_obj);

        AlbanianApplication.getInstance().addToRequestQueue(sr);


    }

    private void parseDuplicacyResponse(String Result) {


        Log.d("sumit","checkduplicay responce= "+Result);

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {



                        loadSignupSecond(usermodel);


                    } else if (ErrorCode.equals("0")) {

                        AlbanianApplication.hideProgressDialog(getActivity());

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }
                    else
                    {

                        AlbanianApplication.hideProgressDialog(getActivity());

                        Log.d(AlbanianConstants.TAG, "signup error= " + ErrorCode);

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }


                } catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit", "Whoiviewed list exception= " + e);
                }

            }

        }

    }

    private void loadSignupSecond(UserModel usermodel) {



        String signupUrl = AlbanianConstants.base_url;
        AsyncTaskCompleted TaskCompleteListner = this;

        SignUpAsync signupobj = new SignUpAsync(getActivity(),
                TaskCompleteListner, signupUrl,usermodel,signupimages,"signup");
        signupobj.execute();

    }




    ///////spinner adapter for country

    public class MyAdapter extends ArrayAdapter<CountryModel> {
        public MyAdapter(Context ctx, int txtViewResourceId, ArrayList<CountryModel> objects) {
            super(ctx, txtViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View mySpinner = inflater.inflate(R.layout.country_spinner_item, parent, false);
            TextView main_text = (TextView) mySpinner.findViewById(R.id.sub_text_seen);
            main_text.setText(countriesList.get(position).getCountry_name());


            return mySpinner;
        }
    }

    ///////spinner adapter for country

    public class HearAdapter extends ArrayAdapter<String> {

        private ArrayList<String> list;
        public HearAdapter(Context ctx, int txtViewResourceId, ArrayList<String> objects) {
            super(ctx, txtViewResourceId, objects);

            list=objects;
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View mySpinner = inflater.inflate(R.layout.country_spinner_item, parent, false);
            TextView main_text = (TextView) mySpinner.findViewById(R.id.sub_text_seen);
            main_text.setText(list.get(position));


            return mySpinner;
        }
    }



    public void onActivityResult(int requestCode, int resultCode, Intent intent) {


        Log.d("sumit", "onActivity");

        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == SELECT_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {



                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());

                File croppedImageFile = new File("/sdcard/AlbanianCircle/", "QR_" + timeStamp+".jpg");

                filetosave=croppedImageFile;

                Uri croppedImage = Uri.fromFile(croppedImageFile);

                CropImageIntentBuilder cropImage = new CropImageIntentBuilder(500, 500, croppedImage);
                cropImage.setOutlineColor(0xFF03A9F4);
                cropImage.setSourceImage(intent.getData());

                startActivityForResult(cropImage.getIntent(getActivity()), CROP_FROM_CAMERA);


            }



        }




        else if (requestCode == CAPTURE_PICTURE) {
            Log.d("sumit", "CAPTURE_PICTURE");
            if (resultCode == getActivity().RESULT_OK) {
                Log.d("sumit", " captureRESULT_OK");


                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());

                File croppedImageFile = new File("/sdcard/AlbanianCircle/", "QR_" + timeStamp+".jpg");

                filetosave=croppedImageFile;

                Uri croppedImage = Uri.fromFile(croppedImageFile);

                CropImageIntentBuilder cropImage = new CropImageIntentBuilder(400, 400, croppedImage);
                cropImage.setOutlineColor(0xFF03A9F4);
                cropImage.setSourceImage(selectedImageUri);

                startActivityForResult(cropImage.getIntent(getActivity()), CROP_FROM_CAMERA);


//                try {
//                    // Uri uri = data.getData();
//                    // imageUri = uri;
//                    bmp = MediaStore.Images.Media.getBitmap(
//                            getActivity().getContentResolver(), selectedImageUri);
//
//                    getRealPathFromURI(selectedImageUri);
//
//
//
//                } catch (Exception e) {
//
//                    e.printStackTrace();
//
//                }


            }
        }


        else if (requestCode == CROP_FROM_CAMERA) {

            if (resultCode == Activity.RESULT_OK) {
                    Log.d("sumit", "onActivity Result for Crop image= "+filetosave.getAbsolutePath());


//                imageLoader
//                        .displayImage("file://"+filetosave.getAbsolutePath(),
//                                ivImage, options_image, animateFirstListener);

                Picasso.with(getContext()).load("file://"+filetosave.getAbsolutePath()).fit().centerCrop()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
                        .transform(new RoundedCornersTransform(radius,margin))
                        .into(ivImage);

//                ivImage.setImageBitmap(BitmapFactory.decodeFile(filetosave.getAbsolutePath()));
                signupimages.put(imagename,filetosave.getAbsolutePath());


//                    File dir = new File("/sdcard/AlbanianCircle/");
//                    if (dir.isDirectory())
//                    {
//                        String[] children = dir.list();
//                        for (int i = 0; i < children.length; i++)
//                        {
//                            new File(dir, children[i]).delete();
//                        }
//                    }


            }

        }

    }



    @Override
    public void TaskCompleted(String Result,String taskType) {

        Log.d("sumit","signup responce= "+Result);

        if (Result != null) {

            {

                try {

                    String mTitle = getResources().getString(R.string.app_name);

                    JSONObject jObj_job = new JSONObject(Result);
                    String ErrorCode = jObj_job.optString("ErrorCode");
                    String ErrorMessage = jObj_job.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {




//                            JSONObject jObj_job=jObj_Main.getJSONObject("Payment");
//                        UserModel usermodel=new UserModel();
//
//                        String UserId=jObj_job.optString("UserId");
//                        usermodel.setUserId(UserId);
//
//
//                        String UserName=jObj_job.optString("UserName");
//                        usermodel.setUserName(UserName);
//
//                        String UserFullName=jObj_job.optString("UserFullName");
//                        usermodel.setUserFullName(UserFullName);
//
//                        String UserStatus=jObj_job.optString("UserStatus");
//                        usermodel.setUserStatus(UserStatus);

//                        String UserGender=jObj_job.optString("UserGender");
//                        usermodel.setUserGender(UserGender);

//                        String UserProfileImage=jObj_job.optString("UserProfileImage");
//                        usermodel.setUserImage(UserProfileImage);

//                        String UserMinAge=jObj_job.optString("UserMinAge");
//                        usermodel.setUserMinAge(UserMinAge);
//
//                        String UserMaxAge=jObj_job.optString("UserMaxAge");
//                        usermodel.setUserMaxAge(UserMaxAge);

//                        String UserSubscriptionStatus=jObj_job.optString("UserSubscriptionStatus");
//                        usermodel.setUserSubscriptionStatus(UserSubscriptionStatus);

//                        String UserInvisibleStatus=jObj_job.optString("UserInvisibleStatus");
//                        usermodel.setUserInvisibleStatus(UserInvisibleStatus);


//                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
//                                ErrorMessage, false);

                        loadSigninFRagment();


                    }

                    else
                    {

                        Log.d(AlbanianConstants.TAG, "signup error= " + ErrorCode);



                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);


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



    private void loadSigninFRagment() {

//        FragmentManager mFragmentManager = getFragmentManager();
//        FragmentTransaction mFragmentTransaction = mFragmentManager
//                .beginTransaction();
//        SignInFragment mFragment = new SignInFragment();
////        Bundle bundle = new Bundle();
////        bundle.putParcelable(AlbanianConstants.EXTRA_USERMODEL, usermodel);
////        mFragment.setArguments(bundle);
//
//        mFragmentTransaction.replace(R.id.frame_signinsignup, mFragment);
//        mFragmentTransaction.addToBackStack(null);
//        mFragmentTransaction.commit();

        String deviceToken=AlbanianApplication.getReg_deviceID();

        if (deviceToken == null|| deviceToken.length()<=0) {

            AlbanianApplication.registerDevice(getActivity(),getActivity());
        }


        if (deviceToken == null || deviceToken.length()<=0) {

            deviceToken="";
        }
//        if (mLatitude == null || mLatitude.length()<=0) {
//
//            mLatitude="0.0";
//        }
//        if (mLongitude == null || mLongitude.length()<=0) {
//
//            mLongitude="0.0";
//        }

        Log.d(AlbanianConstants.TAG, "str_email: " + usermodel.getUserEmail());
        Log.d(AlbanianConstants.TAG, "str_password: " + usermodel.getUserPassword());
        Log.d(AlbanianConstants.TAG, "UserDeviceTocken: " + deviceToken);
//        Log.d(AlbanianConstants.TAG, "UserLat: " + mLatitude);
//        Log.d(AlbanianConstants.TAG, "UserLong: " +mLongitude);


        AlbanianApplication.showProgressDialog(getActivity(),"","Loading...");

        final String finalDeviceToken = deviceToken;
        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());
                parseResponse(response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlbanianApplication.hideProgressDialog(getActivity());
                VolleyLog.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());



            }
        })

        {


            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();



                params.put("api_name", "signin");
                params.put("user_email", usermodel.getUserEmail());
                params.put("user_password", usermodel.getUserPassword());
//                params.put("UserDeviceTocken", (finalDeviceToken==null||finalDeviceToken.length()<=0)?"":finalDeviceToken);
//                params.put("user_latitude", (mLatitude==null||mLatitude.length()<=0)?"0.0":mLatitude);
//                params.put("user_longitude", (mLongitude==null||mLongitude.length()<=0)?"0.0":mLongitude);
                params.put("user_latitude", "");
                params.put("user_longitude", "");
                params.put("user_device_type", "2");
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        // Adding request to request queue
        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }



//    @Override
//    public void onConnected(Bundle bundle) {
//
//
//        try {
//
//            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//            if (mLastLocation != null)
//            {
//
//                mLatitude=String.valueOf(mLastLocation.getLatitude());
//                mLongitude=String.valueOf(mLastLocation.getLongitude());
//
//                Log.d("sumit","latitude== "+String.valueOf(mLastLocation.getLatitude()));
//
//            }
//            else
//            {
//
//                Log.d("sumit","NO location found in uploadimages...............");
//                mLatitude="0.0";
//                mLongitude="0.0";
//            }
//
//
//
//            getCountrynameGeo(mLatitude,mLongitude);
//
//
//
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//
//
//            mLatitude="0.0";
//            mLongitude="0.0";
//
//        }
//
//    }

    private void getCountrynameGeo(String mLatitude, String mLongitude) {

        try {
            Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
            final List<Address> addresses = gcd.getFromLocation(Double.parseDouble(mLatitude),
                    Double.parseDouble(mLongitude), 1);
            if (addresses.size() > 0)
            {

//                System.out.println(addresses.get(0).getLocality());
//                city.setText(addresses.get(0).getLocality());

            }





        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }



    private void parseResponse(String Result) {


        Log.d("sumit","signin responce= "+Result);

        if (Result != null) {

            {

                try {

                    JSONObject jObj_job = new JSONObject(Result);
                    String ErrorCode = jObj_job.optString("ErrorCode");
                    String ErrorMessage = jObj_job.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")
                            || ErrorCode.equalsIgnoreCase("4")) {


                        String mTitle = getResources().getString(R.string.app_name);



//                            JSONObject jObj_job=jObj_Main.getJSONObject("Payment");
                        UserModel usermodel=new UserModel();

                        String UserId=jObj_job.optString("UserId");
                        usermodel.setUserId(UserId);


                        String UserName=jObj_job.optString("UserName");
                        usermodel.setUserName(UserName);

                        String UserFullName=jObj_job.optString("UserFullName");
                        usermodel.setUserFullName(UserFullName);

                        String UserStatus=jObj_job.optString("UserStatus");
                        usermodel.setUserStatus(UserStatus);

                        String UserGender=jObj_job.optString("UserGender");
                        usermodel.setUserGender(UserGender);

                        String UserProfileImage=jObj_job.optString("UserProfileImage");
                        usermodel.setUserImage(UserProfileImage);

                        String UserMinAge=jObj_job.optString("UserMinAge");
                        usermodel.setUserMinAge(UserMinAge);

                        String UserMaxAge=jObj_job.optString("UserMaxAge");
                        usermodel.setUserMaxAge(UserMaxAge);

                        String UserSubscriptionStatus=jObj_job.optString("UserSubscriptionStatus");
                        usermodel.setUserSubscriptionStatus(UserSubscriptionStatus);

                        String UserInvisibleStatus=jObj_job.optString("UserInvisibleStatus");
                        usermodel.setUserInvisibleStatus(UserInvisibleStatus);



                        String UserCountry=jObj_job.optString("UserCountry");
                        usermodel.setUserCountry(UserCountry);



                        saveUser(usermodel);


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
                    Log.d("sumit","Whoiviewed list exception= "+e);
                }

            }

        }
    }


    private void saveUser(UserModel usermodel) {

        AlbanianPreferances pref = new AlbanianPreferances(getActivity());

        pref.setUserData(usermodel);
        pref.setLogin(true);

        loadMainFragmentActivity();
    }

    private void loadMainFragmentActivity() {

        Intent mIntent = new Intent(getActivity(),
                HomeFragmentNewActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mIntent);
        getActivity().finish();
    }


}