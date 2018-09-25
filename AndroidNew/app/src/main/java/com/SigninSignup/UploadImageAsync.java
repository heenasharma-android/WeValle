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

/**
 * Created by Sumit on 23/10/2015.
 */
public class UploadImageAsync extends AsyncTask<String, ProgressDialog, String> {




    public Context mContext;
    public String mSignupUrl;
    public AsyncTaskCompleted onTaskCompleted;


    //    public String selectedImagePath;
    public String absolutePath;
    public String userId;
    public String taskType;


    public UploadImageAsync(Context con,
                       AsyncTaskCompleted TaskcompletedListener, String url
            ,  String absolutePath,String userId,String taskType) {

        this.mContext = con;
        this.onTaskCompleted = TaskcompletedListener;
        this.mSignupUrl = url;

        this.absolutePath=absolutePath;
        this.userId=userId;
        this.taskType=taskType;


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

            Log.d("sumit", "uploadimages url= " + mSignupUrl);






            HttpPost mHttpPost = new HttpPost(mSignupUrl);

            MultipartEntity mEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);

            mEntity.addPart("api_name",
                    new StringBody("insertimage"));
            mEntity.addPart("user_id",
                    new StringBody(userId));
            mEntity.addPart("AppName",
                    new StringBody(AlbanianConstants.AppName));






            if (absolutePath != null) {
                Bitmap bit = BitmapFactory.decodeFile(absolutePath);

                double width_tmp = bit.getWidth(), height_tmp = bit.getHeight();


//				double width_tmp = bit.getWidth(), height_tmp = bit.getHeight();

                Log.d("sumit", "THA IMAGE WIDTH ==== "+width_tmp);

                if(width_tmp>400)
                {

                    double ratio = 1.0;

                    double newHeight = 0, newWidth = 0;

                    ratio = height_tmp / width_tmp;

                    newWidth = 400;

                    newHeight = newWidth * ratio;

                    bit = Bitmap.createScaledBitmap(bit,

                            (int)(newWidth + 0.5), (int)(newHeight + 0.5), true);

                    Log.d("sumit", "THA IMAGE WIDTH after scale==== "+width_tmp);

                }



                Log.d("sumit", "THA IMAGE WIDTH ==== " + width_tmp);

                // bitmap = BitmapFactory.decodeFile(selectedImagePath);
                // Bitmap bmpCompressed = Bitmap.createScaledBitmap(bit,
                // bit.getWidth(),
                // bit.getHeight(), true);

                // /////

                int orientation = 0;

                try {
                    ExifInterface ei = new ExifInterface(absolutePath);

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

                String extention = absolutePath
                        .substring(absolutePath.lastIndexOf('.') + 1);
                Log.d("sumit", "uploadimage IMAGE TO UPLOAD == " + bit);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bit.compress(Bitmap.CompressFormat.PNG, 80, bos);

                data = bos.toByteArray();

//                String encodedImage = Base64.encodeToString(data, Base64.DEFAULT);

//                mEntity.addPart("img",
//                        new StringBody(encodedImage));

                mEntity.addPart("image_to_be_uploaded", new ByteArrayBody(data, "temp."
                        + extention));

            }





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

        AlbanianApplication.hideProgressDialog(mContext);


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
