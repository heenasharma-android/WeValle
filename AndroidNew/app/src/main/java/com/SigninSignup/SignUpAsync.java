package com.SigninSignup;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.models.UserModel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


/**
 * Created by sumit on 24/2/15.
 */
public class SignUpAsync extends AsyncTask<String, ProgressDialog, String> {


    public Context mContext;
    public String mSignupUrl;
    public String taskType;
    public AsyncTaskCompleted onTaskCompleted;


//    public String selectedImagePath;
    public UserModel usermodel;
    public HashMap<String,String> signupimages;


    public SignUpAsync(Context con,
                       AsyncTaskCompleted TaskcompletedListener, String url
            , UserModel usermodel, HashMap<String, String> signupimages,String taskType) {

        this.mContext = con;
        this.onTaskCompleted = TaskcompletedListener;
        this.mSignupUrl = url;
        this.taskType = taskType;

        this.usermodel=usermodel;
        this.signupimages=signupimages;


    }



    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();

//        AlbanianApplication.showProgressDialog(mContext, mContext.getString(R.string.app_name), "Loading...");
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            byte[] data = null;

            HttpClient mHttpClient = new DefaultHttpClient();
            HttpContext mHttpContext = new BasicHttpContext();

            Log.d("sumit", "signup url= " + mSignupUrl);






            HttpPost mHttpPost = new HttpPost(mSignupUrl);

            MultipartEntity mEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);
            mEntity.addPart("api_name",
                    new StringBody("register"));
            mEntity.addPart("AppName",
                    new StringBody(AlbanianConstants.AppName));

            mEntity.addPart("user_name",
                    new StringBody(usermodel.getUserName()));

            mEntity.addPart("user_password",
                    new StringBody(usermodel.getUserPassword()));

            mEntity.addPart("user_email",
                    new StringBody(usermodel.getUserEmail()));

            mEntity.addPart("user_latitude",
                    new StringBody(usermodel.getUserlatitude()));

            mEntity.addPart("user_longitude",
                    new StringBody(usermodel.getUserlogitude()));

            mEntity.addPart("UserFullName",
                    new StringBody(usermodel.getUserFullName()));

            mEntity.addPart("user_country",
                    new StringBody(usermodel.getUserCountry()));

            mEntity.addPart("UserState",
                    new StringBody(usermodel.getUserState()));

            mEntity.addPart("user_city",
                    new StringBody(usermodel.getUserCity()));

            mEntity.addPart("user_gender",
                    new StringBody(usermodel.getUserGender()));

            mEntity.addPart("UserHearAboutUs",
                    new StringBody(usermodel.getUserHearAboutUs()));

            mEntity.addPart("user_dob",
                    new StringBody(usermodel.getUserDOB()));
            mEntity.addPart("UserOtherLanguages",
                    new StringBody(usermodel.getUserOtherLanguages()));
            mEntity.addPart("UserDescOneWord",
                    new StringBody(usermodel.getUserDescOneWord()));

            mEntity.addPart("UserPets",
                    new StringBody(usermodel.getUserPets()));

            mEntity.addPart("UserFavMovies",
                    new StringBody(usermodel.getUserFavMovies()));
            mEntity.addPart("UserAlbanianSongs",
                    new StringBody(usermodel.getUserAlbanianSongs()));
            mEntity.addPart("UserDrinks",
                    new StringBody(usermodel.getUserDrinks()));
            mEntity.addPart("UserSmokes",
                    new StringBody(usermodel.getUserSmokes()));
            mEntity.addPart("user_height",
                    new StringBody(usermodel.getUserHeight()));
            mEntity.addPart("user_religion",
                    new StringBody(usermodel.getUserReligion()));
            mEntity.addPart("UserFavQuote",
                    new StringBody(usermodel.getUserFavQuote()));


            mEntity.addPart("UserDescription",
                    new StringBody(usermodel.getUserDescription()));
//
            mEntity.addPart("UserInterests",
                    new StringBody(usermodel.getUserInterestedIn()));


//            Log.d("sumit", "usermodel.getUserPets()=" + usermodel.getUserPets());
//            Log.d("sumit","usermodel.getUserDescOneWord()="+usermodel.getUserDescOneWord());
//            Log.d("sumit","usermodel.getUserCountry()="+usermodel.getUserCountry());


            if (signupimages.get("image1") != null) {
                Bitmap bit = BitmapFactory.decodeFile(signupimages.get("image1"));

                // /////

                int orientation = 0;

                try {
                    ExifInterface ei = new ExifInterface(signupimages.get("image1"));

                    orientation = ei.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,

                            ExifInterface.ORIENTATION_NORMAL);

                    Log.d("sumit", "Image Orientation : " + orientation);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                switch (orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:

                        bit = rotateImage(90, bit);

                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:

                        bit = rotateImage(180, bit);

                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:

                        bit = rotateImage(270, bit);

                        break;

                }

                // /////

                String extention = signupimages.get("image1")
                        .substring(signupimages.get("image1").lastIndexOf('.') + 1);
                Log.d("sumit", "myaccount IMAGE TO UPLOAD == " + bit);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bit.compress(Bitmap.CompressFormat.PNG, 100, bos);

                data = bos.toByteArray();

//                String encodedImage = Base64.encodeToString(data, Base64.DEFAULT);

//                mEntity.addPart("img",
//                        new StringBody(encodedImage));

				mEntity.addPart("image1", new ByteArrayBody(data, "temp."
						+ extention));

            }

            ////////////

            if (signupimages.get("image2") != null) {
                Bitmap bit = BitmapFactory.decodeFile(signupimages.get("image2"));

//                double width_tmp = bit.getWidth(), height_tmp = bit.getHeight();


//				double width_tmp = bit.getWidth(), height_tmp = bit.getHeight();

//                Log.d("sumit", "THA IMAGE WIDTH ==== "+width_tmp);



                // /////

                int orientation = 0;

                try {
                    ExifInterface ei = new ExifInterface(signupimages.get("image2"));

                    orientation = ei.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,

                            ExifInterface.ORIENTATION_NORMAL);

                    Log.d("sumit", "Image Orientation : " + orientation);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                switch (orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:

                        bit = rotateImage(90, bit);

                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:

                        bit = rotateImage(180, bit);

                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:

                        bit = rotateImage(270, bit);

                        break;

                }

                // /////

                String extention = signupimages.get("image2")
                        .substring(signupimages.get("image2").lastIndexOf('.') + 1);
                Log.d("sumit", "myaccount IMAGE TO UPLOAD == " + bit);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bit.compress(Bitmap.CompressFormat.PNG, 100, bos);

                data = bos.toByteArray();


                mEntity.addPart("image2", new ByteArrayBody(data, "temp2."
                        + extention));

            }
            ///////////


            //////

            if (signupimages.get("image3") != null) {
                Bitmap bit = BitmapFactory.decodeFile(signupimages.get("image3"));

//                double width_tmp = bit.getWidth(), height_tmp = bit.getHeight();


//				double width_tmp = bit.getWidth(), height_tmp = bit.getHeight();

//                Log.d("sumit", "THA IMAGE WIDTH ==== "+width_tmp);



                // /////

                int orientation = 0;

                try {
                    ExifInterface ei = new ExifInterface(signupimages.get("image3"));

                    orientation = ei.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,

                            ExifInterface.ORIENTATION_NORMAL);

                    Log.d("sumit", "Image Orientation : " + orientation);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                switch (orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:

                        bit = rotateImage(90, bit);

                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:

                        bit = rotateImage(180, bit);

                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:

                        bit = rotateImage(270, bit);

                        break;

                }

                // /////

                String extention = signupimages.get("image3")
                        .substring(signupimages.get("image3").lastIndexOf('.') + 1);
                Log.d("sumit", "myaccount IMAGE TO UPLOAD == " + bit);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bit.compress(Bitmap.CompressFormat.PNG, 100, bos);

                data = bos.toByteArray();

                mEntity.addPart("image3", new ByteArrayBody(data, "temp3."
                        + extention));

            }

            //////

            /////

            if (signupimages.get("image4") != null) {
                Bitmap bit = BitmapFactory.decodeFile(signupimages.get("image4"));


                // /////

                int orientation = 0;

                try {
                    ExifInterface ei = new ExifInterface(signupimages.get("image4"));

                    orientation = ei.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,

                            ExifInterface.ORIENTATION_NORMAL);

                    Log.d("sumit", "Image Orientation : " + orientation);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                switch (orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:

                        bit = rotateImage(90, bit);

                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:

                        bit = rotateImage(180, bit);

                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:

                        bit = rotateImage(270, bit);

                        break;

                }

                // /////

                String extention = signupimages.get("image4")
                        .substring(signupimages.get("image4").lastIndexOf('.') + 1);
                Log.d("sumit", "myaccount IMAGE TO UPLOAD == " + bit);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bit.compress(Bitmap.CompressFormat.PNG, 100, bos);

                data = bos.toByteArray();

                mEntity.addPart("image4", new ByteArrayBody(data, "temp4."
                        + extention));

            }
            //////

            //////

            if (signupimages.get("image5") != null) {
                Bitmap bit = BitmapFactory.decodeFile(signupimages.get("image5"));

//                double width_tmp = bit.getWidth(), height_tmp = bit.getHeight();
//

                // /////

                int orientation = 0;

                try {
                    ExifInterface ei = new ExifInterface(signupimages.get("image5"));

                    orientation = ei.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,

                            ExifInterface.ORIENTATION_NORMAL);

                    Log.d("sumit", "Image Orientation : " + orientation);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                switch (orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:

                        bit = rotateImage(90, bit);

                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:

                        bit = rotateImage(180, bit);

                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:

                        bit = rotateImage(270, bit);

                        break;

                }

                // /////

                String extention = signupimages.get("image5")
                        .substring(signupimages.get("image5").lastIndexOf('.') + 1);
                Log.d("sumit", "myaccount IMAGE TO UPLOAD == " + bit);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bit.compress(Bitmap.CompressFormat.PNG, 100, bos);

                data = bos.toByteArray();


                mEntity.addPart("image5", new ByteArrayBody(data, "temp5."
                        + extention));

            }

            //////


            mHttpPost.setEntity(mEntity);
            HttpResponse mHttpResponse = mHttpClient.execute(mHttpPost,
                    mHttpContext);
            if (mHttpResponse != null) {
                HttpEntity mHttpEntity = mHttpResponse.getEntity();
                InputStream inStream = mHttpEntity.getContent();
                if (inStream != null) {
                    String result = AlbanianApplication
                            .convertStreamToString(inStream);

                    if (result != null && result.length() > 0) {
                        Log.d("sumit",
                                "Response Result of Signup list is : " + result);
                        return result;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

//        AlbanianApplication.hideProgressDialog(mContext);

        onTaskCompleted.TaskCompleted(result,taskType);
    }



    private Bitmap rotateImage(int angle, Bitmap bmp) {

        Matrix matrix = new Matrix();

        matrix.postRotate(angle);

        Bitmap bmp2 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),

                bmp.getHeight(), matrix, true);

        if (bmp != null && !bmp.isRecycled()) {
            bmp.recycle();
            bmp = null;
        }

        return bmp2;

    }


}
