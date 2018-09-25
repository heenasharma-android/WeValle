package com.SigninSignup;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MainFragments.FaqFragment;
import com.MainFragments.HomeFragmentNewActivity;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.camera.CropImageIntentBuilder;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.LocationServices;
import com.imageoptions.ObjectPreferences;
import com.locationmanager.LocationFragment;
import com.models.UserModel;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumit on 9/21/2015.
 */
public class UploadImagesFragment extends LocationFragment implements View.OnClickListener, AsyncTaskCompleted {

    private View mView;
    private Button btnRegister;
    private ImageView ivImage, ivImage1, ivImage2, ivImage3, ivImage4, ivImage5;

    private ProgressDialog mImageDialog;
    private RelativeLayout back;
    private TextView headertext;

    /* Variables*/

    private ArrayList<String> imagesPath;
    private AlbanianPreferances pref;
    private HashMap<String, String> signupimages;

//    DisplayImageOptions options_image;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private UserModel usermodel;

    private Bitmap bmp;
    ObjectPreferences prefs;
    private static final int CAPTURE_PICTURE = 1;
    private static final int SELECT_PICTURE = 2;
    private static final int CROP_FROM_CAMERA = 3;
    private Uri selectedImageUri;
    private String selectedPath;
    private File filetosave;
    private String imagename;
    private Activity mActivity;

    public static String mLatitude;
    public static String mLongitude;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.upload_images, container, false);
        init();
        return mView;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imagesPath = new ArrayList<>();
        signupimages = new HashMap<>();

        pref = new AlbanianPreferances(getActivity());

        mImageDialog = new ProgressDialog(getActivity());
        mImageDialog.setTitle(getResources().getString(R.string.app_name));
        mImageDialog.setMessage("Loading Image...");
        mImageDialog.setCancelable(false);

//        options_image = new DisplayImageOptions.Builder()
////                .displayer(new RoundedBitmapDisplayer(200))
//
//                .cacheInMemory()
////                .cacheOnDisc()
//
//                .build();

        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/AlbanianCircle");
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

        if (getArguments() != null) {
            usermodel = getArguments().getParcelable(
                    AlbanianConstants.EXTRA_USERMODEL);
            if (usermodel != null) {
//                tripdate.setText(changeDate(tripcancelmodel.getJob_end_trip_time()));
            }
        }

    }


    private void init() {
        initViews();
        initListners();
    }

    private void initViews() {

        back = (RelativeLayout) mView.findViewById(R.id.rl_back_header);
        back.setOnClickListener(this);

        headertext = (TextView) mView.findViewById(R.id.txt_headerlogo);

        headertext.setText(mActivity.getString(R.string.uploadimages));

//        TextView tvSave = (TextView) mView.findViewById(R.id.tv_save);
//        tvSave.setVisibility(View.INVISIBLE);

//        TextView tvCancel = (TextView) mView.findViewById(R.id.tv_cancel);
//        tvCancel.setBackgroundResource(R.drawable.back_btn);
//        tvCancel.setText("");

//        tvCancel.setOnClickListener(this);
        btnRegister = (Button) mView.findViewById(R.id.btn_register);
        ivImage1 = (ImageView) mView.findViewById(R.id.iv_image1);
        ivImage2 = (ImageView) mView.findViewById(R.id.iv_image2);
        ivImage3 = (ImageView) mView.findViewById(R.id.iv_image3);
        ivImage4 = (ImageView) mView.findViewById(R.id.iv_image4);
        ivImage5 = (ImageView) mView.findViewById(R.id.iv_image5);

        TextView terms = (TextView) mView.findViewById(R.id.txt_termstext);

//        String temp = "By clicking Sign up! you agree to the Terms of Service and Privacy Policy";
        String temp = "By clicking register you agree to our Terms of Use and Privacy policy.";
        // #ff8661

        SpannableString spannable = new SpannableString(temp);

        spannable.setSpan(new MyClickableSpan("Terms of Use"), 38, 50,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 46 to 58 stack is clickable

        spannable.setSpan(new BackgroundColorSpan
                        (android.R.color.transparent), 38, 50,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannable.setSpan(new MyClickableSpan("Privacy policy"), 55, 69,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannable.setSpan(new BackgroundColorSpan
                        (android.R.color.transparent), 55, 69,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        terms.setText(spannable);
        terms.setMovementMethod(LinkMovementMethod.getInstance());


    }


    public class MyClickableSpan extends ClickableSpan {
        String keyword;

        public MyClickableSpan(String keyword) {
            super();
            this.keyword = keyword;

            Log.d("sumit", "span== " + this.keyword);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            // TODO Auto-generated method stub
            super.updateDrawState(ds);
            ds.setColor(Color.parseColor("#95150c"));

            ds.setUnderlineText(true);
        }

        @Override
        public void onClick(View widget) {
            // TODO Auto-generated method stub

            widget.setBackgroundColor(getResources().getColor(
                    android.R.color.transparent));

            if (keyword.contains("Terms of Use")) {


                loadLocalPage(AlbanianConstants.EXTRA_TOS);


            } else {

                loadLocalPage(AlbanianConstants.EXTRA_PRIVACY);

            }
            widget.invalidate();

        }


    }

    private void loadLocalPage(String extraFaq) {

        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction();
        FaqFragment mFragment = new FaqFragment();

        Bundle bundle = new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_LOCALPAGENAME, extraFaq);
        mFragment.setArguments(bundle);


        mFragmentTransaction.replace(R.id.frame_signinsignup, mFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.mActivity = activity;
    }

    private void initListners() {
        ivImage1.setOnClickListener(this);
        ivImage2.setOnClickListener(this);
        ivImage3.setOnClickListener(this);
        ivImage4.setOnClickListener(this);
        ivImage5.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private Intent cameraIntent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_image1:
                startDialog(ivImage1, "image1");
                break;

            case R.id.iv_image2:
                startDialog(ivImage2, "image2");

                break;
            case R.id.iv_image3:
                startDialog(ivImage3, "image3");

                break;
            case R.id.iv_image4:
                startDialog(ivImage4, "image4");

                break;
            case R.id.iv_image5:
                startDialog(ivImage5, "image5");
                break;
            case R.id.tv_cancel:
                getActivity().onBackPressed();
                break;

            case R.id.rl_back_header:
                getActivity().onBackPressed();
                break;

            case R.id.btn_register:
//                String mMessage = "You have successfully registered. Continue by entering your email" +
//                        "and password at the login screen to sign in.";
//                AlbanianApplication.ShowAlert(getActivity(), "Alert", mMessage, false);
                String mTitle = getResources().getString(R.string.app_name);

                if (signupimages.get("image1") == null || signupimages.get("image1").length() <= 0) {

                    String mMessage = getString(R.string.Signup_Pleasefillmainimage);
                    AlbanianApplication.ShowAlert(getActivity(), mTitle, mMessage, false);
                } else {
                    submitSignup();
                }


                break;
        }
    }

    private void submitSignup() {


        String signupUrl = AlbanianConstants.base_url;
        AsyncTaskCompleted TaskCompleteListner = this;

        SignUpAsync signupobj = new SignUpAsync(getActivity(),
                TaskCompleteListner, signupUrl, usermodel, signupimages, "signup");
        signupobj.execute();


    }

    @Override
    public void TaskCompleted(String Result, String taskType) {

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
                        UserModel usermodel = new UserModel();

                        String UserId = jObj_job.optString("UserId");
                        usermodel.setUserId(UserId);


                        String UserName = jObj_job.optString("UserName");
                        usermodel.setUserName(UserName);

                        String UserFullName = jObj_job.optString("UserFullName");
                        usermodel.setUserFullName(UserFullName);

                        String UserStatus = jObj_job.optString("UserStatus");
                        usermodel.setUserStatus(UserStatus);


                        loadSigninFRagment();


                    } else {

                        Log.d(AlbanianConstants.TAG, "signup error= " + ErrorCode);


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

    private void loadSigninFRagment() {

        Log.d(AlbanianConstants.TAG, "str_email: " + usermodel.getUserEmail());
        Log.d(AlbanianConstants.TAG, "str_password: " + usermodel.getUserPassword());
        Log.d(AlbanianConstants.TAG, "UserDeviceTocken: " + AlbanianApplication.getReg_deviceID());
        Log.d(AlbanianConstants.TAG, "UserLat: " + mLatitude);
        Log.d(AlbanianConstants.TAG, "UserLong: " + mLongitude);


        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());
                parseResponse(response.toString());


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


                params.put("api_name", "signin");
                params.put("user_email", usermodel.getUserEmail());
                params.put("user_password", usermodel.getUserPassword());
                params.put("user_device_token", AlbanianApplication.getReg_deviceID());
                params.put("user_latitude", mLatitude);
                params.put("user_longitude", mLongitude);
                params.put("user_device_type", "2");
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        // Adding request to request queue
        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }

    private void openCamera() {

        selectedPath = null;

        try {
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
            } else {
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

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {


        Log.d("sumit", "onActivity");

//        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == SELECT_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {


                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                File croppedImageFile = new File("/sdcard/AlbanianCircle/", "QR_" + timeStamp + ".jpg");

                filetosave = croppedImageFile;

                Uri croppedImage = Uri.fromFile(croppedImageFile);

                CropImageIntentBuilder cropImage = new CropImageIntentBuilder(500, 500, croppedImage);
                cropImage.setOutlineColor(0xFF03A9F4);
                cropImage.setSourceImage(intent.getData());

                startActivityForResult(cropImage.getIntent(getActivity()), CROP_FROM_CAMERA);


            }


        } else if (requestCode == CAPTURE_PICTURE) {
            Log.d("sumit", "CAPTURE_PICTURE");
            if (resultCode == getActivity().RESULT_OK) {
                Log.d("sumit", " captureRESULT_OK");


                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                File croppedImageFile = new File("/sdcard/AlbanianCircle/", "QR_" + timeStamp + ".jpg");

                filetosave = croppedImageFile;

                Uri croppedImage = Uri.fromFile(croppedImageFile);

                CropImageIntentBuilder cropImage = new CropImageIntentBuilder(400, 400, croppedImage);
                cropImage.setOutlineColor(0xFF03A9F4);
                cropImage.setSourceImage(selectedImageUri);

                startActivityForResult(cropImage.getIntent(getActivity()), CROP_FROM_CAMERA);


            }
        } else if (requestCode == CROP_FROM_CAMERA) {

            if (resultCode == Activity.RESULT_OK) {
//                    Log.d("sumit", "onActivity Result for Crop image");


                ivImage.setImageBitmap(BitmapFactory.decodeFile(filetosave.getAbsolutePath()));
                signupimages.put(imagename, filetosave.getAbsolutePath());


            }

        }

    }


    public void openGallery() {

        Log.d("sumit", "opening gallery");

        selectedPath = null;
        // profileimage.setImageResource(R.drawable.avatar1);
        Intent intent = new Intent();
        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_PICK);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                SELECT_PICTURE);


    }


    private void startDialog(ImageView ivImage1, String imagename1) {
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


    @Override
    public void onConnected(Bundle bundle) {


        try {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {

                mLatitude = String.valueOf(mLastLocation.getLatitude());
                mLongitude = String.valueOf(mLastLocation.getLongitude());

                Log.d("sumit", "latitude== " + String.valueOf(mLastLocation.getLatitude()));

            } else {

                Log.d("sumit", "NO location found in uploadimages...............");

            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();


            mLatitude = "0.0";
            mLongitude = "0.0";

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    private void parseResponse(String Result) {

        if (Result != null) {

            {

                try {

                    JSONObject jObj_job = new JSONObject(Result);
                    String ErrorCode = jObj_job.optString("ErrorCode");
                    String ErrorMessage = jObj_job.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        String mTitle = getResources().getString(R.string.app_name);


//                            JSONObject jObj_job=jObj_Main.getJSONObject("Payment");
                        UserModel usermodel = new UserModel();

                        String UserId = jObj_job.optString("UserId");
                        usermodel.setUserId(UserId);


                        String UserName = jObj_job.optString("UserName");
                        usermodel.setUserName(UserName);

                        String UserFullName = jObj_job.optString("UserFullName");
                        usermodel.setUserFullName(UserFullName);

                        String UserStatus = jObj_job.optString("UserStatus");
                        usermodel.setUserStatus(UserStatus);

                        String UserGender = jObj_job.optString("UserGender");
                        usermodel.setUserGender(UserGender);

                        String UserProfileImage = jObj_job.optString("UserProfileImage");
                        usermodel.setUserImage(UserProfileImage);

                        String UserMinAge = jObj_job.optString("UserMinAge");
                        usermodel.setUserMinAge(UserMinAge);

                        String UserMaxAge = jObj_job.optString("UserMaxAge");
                        usermodel.setUserMaxAge(UserMaxAge);

                        String UserSubscriptionStatus = jObj_job.optString("UserSubscriptionStatus");
                        usermodel.setUserSubscriptionStatus(UserSubscriptionStatus);

                        String UserInvisibleStatus = jObj_job.optString("UserInvisibleStatus");
                        usermodel.setUserInvisibleStatus(UserInvisibleStatus);


                        saveUser(usermodel);


                    } else {

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

    private void saveUser(UserModel usermodel) {

        AlbanianPreferances pref = new AlbanianPreferances(getActivity());

        pref.setUserData(usermodel);

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
