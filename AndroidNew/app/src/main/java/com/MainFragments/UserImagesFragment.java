package com.MainFragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SigninSignup.AsyncTaskCompleted;
import com.SigninSignup.UploadImageAsync;
import com.adapter.UserImagesAdapter;
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
import com.imageoptions.ObjectPreferences;
import com.models.UserImagesModel;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumit on 22/10/2015.
 */
public class UserImagesFragment extends BaseFragment implements View.OnClickListener,
        AsyncTaskCompleted
{


    /*UI declaration........*/
    private View mView;

    private GridView gridUserimages;
    private TextView headertext;
    private RelativeLayout back;


    /* Variables...... */

    private ArrayList<UserImagesModel> images_arraylist;
    private String tag_whoiviewed_obj = "jsentmessages_req";
    private AlbanianPreferances pref;
    private String profileVisitedID;
    private String CURRENTTABTAG;


    private Bitmap bmp;
    ObjectPreferences prefs;
    private static final int CAPTURE_PICTURE = 1;
    private static final int SELECT_PICTURE = 2;
    private static final int CROP_FROM_CAMERA = 3;
    private Uri selectedImageUri;
    private String selectedPath;
    private File filetosave;
    private String imagename;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        images_arraylist=new ArrayList<>();

        pref = new AlbanianPreferances(getActivity());

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

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.userimagesgridview, container, false);


        initViews();

        if (getArguments() != null)
        {

            profileVisitedID = getArguments().getString(
                    AlbanianConstants.EXTRA_PROFILEVISITEDID);

            CURRENTTABTAG = getArguments().getString(
                    AlbanianConstants.EXTRA_CURRENTTAB_TAG);

        }

        if (profileVisitedID != null)
        {

            getUserImages(profileVisitedID);

        }


        return mView;
    }



    private void initViews() {

        gridUserimages = (GridView)mView.findViewById(R.id.gv_userimages);
        headertext = (TextView)mView.findViewById(R.id.txt_headerlogo);

        back = (RelativeLayout) mView.findViewById(R.id.rl_back_header);
        back.setOnClickListener(this);
        headertext.setText(mActivity.getString(R.string.Userimages));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back_header:

                getActivity().onBackPressed();

                break;

        }
    }



    private void getUserImages(final String profileVisitedID) {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");


        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "user images= "+response.toString());
                AlbanianApplication.hideProgressDialog(getActivity());

                parseResponse(response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "user images Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                    AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "userimages");
                params.put("user_id", profileVisitedID );
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        AlbanianApplication.getInstance().addToRequestQueue(sr);


    }

    private void parseResponse(String Result) {

        images_arraylist.clear();

        if (Result != null) {

            {
                try {

                    // add blank image at 0th position

                    if (profileVisitedID.equals(pref.getUserData().getUserId())) {

                        UserImagesModel msgModel_dummy = new UserImagesModel();

                        msgModel_dummy.setImageId("0");

                        msgModel_dummy.setUserImageUrl("0");

                        msgModel_dummy.setIsUserProfileImage("0");


                        ///////

                        images_arraylist.add(msgModel_dummy);
                    }

                    ///

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        String mTitle = getResources().getString(R.string.app_name);



                        JSONArray jArray_response=jObj.getJSONArray("UserImages");


                        for (int i = 0; i < jArray_response.length(); i++) {

                            UserImagesModel msgModel=new UserImagesModel();

                            JSONObject jObj_job=jArray_response.getJSONObject(i);

                            // Job object

//                            JSONObject jObj_job=jObj_Main.getJSONObject("Payment");

                            String ImageId=jObj_job.optString("ImageId");
                            msgModel.setImageId(ImageId);

                            String UserImageUrl=jObj_job.optString("UserImageUrl");
                            msgModel.setUserImageUrl(UserImageUrl);


                            String IsUserProfileImage=jObj_job.optString("IsUserProfileImage");
                            msgModel.setIsUserProfileImage(IsUserProfileImage);

                            ///////

                            images_arraylist.add(msgModel);

                        }



                        /////////////

                        setUserImagesGrid(images_arraylist);


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
                    Log.d("sumit","Whoiviewed list exception= "+e);
                }

            }

        }
    }

    private void setUserImagesGrid(final ArrayList<UserImagesModel> images_arraylist) {



        UserImagesAdapter adapter = new UserImagesAdapter(getActivity(), images_arraylist,
                profileVisitedID,pref.getUserData().getUserId());
        gridUserimages.setAdapter(adapter);

        gridUserimages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (profileVisitedID.equals(pref.getUserData().getUserId()))
                {

                    if (position == 0)
                    {

                        startDialog();
                    }
                    else
                    {
                        ArrayList<UserImagesModel> images_arraylistTemp=
                                (ArrayList<UserImagesModel>)images_arraylist.clone();
                        images_arraylistTemp.remove(0);
                        loadViewImageFragment(images_arraylistTemp,position-1);
                    }
                }
                else
                {
                    loadViewImageFragment(images_arraylist, position);
                }


            }
        });

        gridUserimages.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                if (pref.getUserData().getUserId().equals(profileVisitedID)) {

                    if (position > 0) {

                        android.app.AlertDialog.Builder builderSingle;

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                            builderSingle = new android.app.AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));
                        } else {
                            builderSingle = new android.app.AlertDialog.Builder(
                                    getActivity());
                        }


//      builderSingle.setTitle("Upload image");
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                getActivity(),
                                android.R.layout.select_dialog_item);


                        arrayAdapter.add("Make main image");
                        arrayAdapter.add("Delete image");


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

                                            makeMainImage(images_arraylist.get(position).getImageId());

                                        } else if (strName == 1) {
                                            // comment report

                                            Log.d("sumit","image list sixe- "+images_arraylist.size());

                                            if (images_arraylist.size() > 2) {

                                                deleteImage(images_arraylist.get(position).getImageId());
                                            }
                                            else
                                            {

                                                String mTitle = getResources().getString(R.string.app_name);

                                                AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                                        mActivity.getResources().getString(R.string.imaagedeletealert), false);
                                            }



                                        }


                                    }
                                });

                        builderSingle.show();
                    }
                }








                return true;
            }
        });
    }

    private void makeMainImage(final String imageId) {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "make main image response= "+response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());
                parseDeleteImageResponse(response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "make main Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "singleupdate");
                params.put("column_name", "UserProfileImage");
                params.put("column_value", imageId);
                params.put("user_id", pref.getUserData().getUserId());
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };



        AlbanianApplication.getInstance().addToRequestQueue(sr);


    }

    private void deleteImage(final String imageId) {


        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "delete image response= "+response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());
                parseDeleteImageResponse(response.toString());



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

                params.put("api_name", "deleteimage");
                params.put("user_id", pref.getUserData().getUserId());
                params.put("image_id", imageId);
                params.put("AppName", AlbanianConstants.AppName);


                return params;
            }

        };



        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }

    private void parseDeleteImageResponse(String Result) {

        AlbanianApplication.hideProgressDialog(getActivity());

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

                        getUserImages(profileVisitedID);


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
                    Log.d("sumit","Whoiviewed list exception= "+e);
                }

            }

        }
    }

    private void loadViewImageFragment(ArrayList<UserImagesModel> userImages, int position) {

//        if (profileVisitedID.equals(pref.getUserData().getUserId())) {
//
//            userImages.remove(0);
//        }



        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(AlbanianConstants.EXTRA_IMAGESLIST, userImages);
        bundle.putString(AlbanianConstants.EXTRA_USERID, profileVisitedID);
        bundle.putInt("position", position);
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
                CURRENTTABTAG);


        mActivity.pushFragments(CURRENTTABTAG,
                new ViewImageFragment(), false, true, bundle);
    }


    private void startDialog() {
//        ivImage = ivImage1;
//        imagename = imagename1;
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(getActivity());
        myAlertDialog.setTitle(mActivity.getString(R.string.uploadphoto));
        myAlertDialog.setMessage(mActivity.getString(R.string.uploadfrom));
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        RxPermissions rxPermissions = new RxPermissions(getActivity());

                        Log.d("sumit","permission 44mra= "+rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        );


                        if (rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                            openGallery();
                        }
                        else
                        {
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
                        }
                        else
                        {
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


        Log.d("sumit","opening gallery in user images sumit");

        selectedPath = null;
        // profileimage.setImageResource(R.drawable.avatar1);
        Intent intent = new Intent();
        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_PICK);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        getActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                SELECT_PICTURE);



    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("UserImages Screen");
    }

    private void openCamera() {

        selectedPath=null;

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
                    getActivity().startActivityForResult(intent1, CAPTURE_PICTURE);

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


        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == SELECT_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {



                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                File croppedImageFile = new File("/sdcard/AlbanianCircle/", "QR_" + timeStamp+".jpg");

                filetosave=croppedImageFile;

                Uri croppedImage = Uri.fromFile(croppedImageFile);

                CropImageIntentBuilder cropImage = new CropImageIntentBuilder(400, 400, croppedImage);
                cropImage.setOutlineColor(0xFF03A9F4);
                cropImage.setSourceImage(intent.getData());

                getActivity().startActivityForResult(cropImage.getIntent(getActivity()), CROP_FROM_CAMERA);


            }



        }

        else if (requestCode == CAPTURE_PICTURE) {
            Log.d("sumit", "CAPTURE_PICTURE");
            if (resultCode == getActivity().RESULT_OK) {
                Log.d("sumit", " captureRESULT_OK");


                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                File croppedImageFile = new File("/sdcard/AlbanianCircle/", "QR_" + timeStamp+".jpg");

                filetosave=croppedImageFile;

                Uri croppedImage = Uri.fromFile(croppedImageFile);

                CropImageIntentBuilder cropImage = new CropImageIntentBuilder(500, 500, croppedImage);
                cropImage.setOutlineColor(0xFF03A9F4);
                cropImage.setSourceImage(selectedImageUri);

                getActivity().startActivityForResult(cropImage.getIntent(getActivity()), CROP_FROM_CAMERA);




            }
        }

        else if (requestCode == CROP_FROM_CAMERA) {

            if (resultCode == Activity.RESULT_OK) {

//                ivImage.setImageBitmap(BitmapFactory.decodeFile(filetosave.getAbsolutePath()));
//                signupimages.put(imagename,filetosave.getAbsolutePath());

                sendImageToServer(filetosave.getAbsolutePath());

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

    private void sendImageToServer(String absolutePath) {

        Log.d("sumit","absoute path= "+absolutePath);

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        String signupUrl = AlbanianConstants.base_url;
        AsyncTaskCompleted TaskCompleteListner = this;

        UploadImageAsync signupobj = new UploadImageAsync(getActivity(),
                TaskCompleteListner, signupUrl,absolutePath,pref.getUserData().getUserId(),"addimage");
        signupobj.execute();

    }


    @Override
    public void TaskCompleted(String Result,String taskType) {

        AlbanianApplication.hideProgressDialog(getActivity());

        if (taskType.equals("addimage")) {

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


                             getUserImages(profileVisitedID);


                        }

                        else
                        {

                            Log.d(AlbanianConstants.TAG, "signup error= " + ErrorCode);

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
        else
        {



        }




    }


}
