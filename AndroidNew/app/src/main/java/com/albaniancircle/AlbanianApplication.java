package com.albaniancircle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Sumit on 27/08/2015.
 */
public class AlbanianApplication extends Application {


    // Albanian Application Class;
    // the member of this class can be used any where
    // in the application.
    public static final String TAG = AlbanianApplication.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
//    private ImageLoader mImageLoader;

    private static AlbanianApplication mInstance;


    public static String reg_deviceID;

    // flag for show dilog of logout or not
    public static boolean logoutdiologflag=false;

    private static TelephonyManager tm;


    public static String getAndroidUniqueId(Context mContext) {

        String android_id = Settings.Secure.getString(mContext.getContentResolver(),
                Settings.Secure.ANDROID_ID);


        if (android_id == null) {

            tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

            final String tmDevice, tmSerial, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(mContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

            UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
            String deviceId = deviceUuid.toString();

            return deviceId;

        }


        return android_id;
    }



    public static String getReg_deviceID() {
        return reg_deviceID;
    }

    public static void setReg_deviceID(String reg_deviceID) {
        AlbanianApplication.reg_deviceID = reg_deviceID;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



    public static synchronized AlbanianApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

//    public ImageLoader getImageLoader() {
//        getRequestQueue();
//        if (mImageLoader == null) {
//            mImageLoader = new ImageLoader(this.mRequestQueue,
//                    new LruBitmapCache());
//        }
//        return this.mImageLoader;
//    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    /************ Whistle Passenger Application Members Declaration */

    public static ProgressHUD mProgressDialog;

    @Override
    public void onCreate() {

        super.onCreate();
        Fabric.with(this, new Crashlytics());


        mInstance = this;

//        initImageLoader(getApplicationContext());



        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);

    }


    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    /***
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            t.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(
                            new StandardExceptionParser(this, null)
                                    .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }

    /***
     * Tracking event
     *
     * @param category event category
     * @param action   action of the event
     * @param label    label
     */
    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }


//    public static void initImageLoader(Context context) {
//        // This configuration tuning is custom. You can tune every option, you
//        // may tune some of them,
//        // or you can create default configuration by
//        // ImageLoaderConfiguration.createDefault(this);
//        // method.
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//                context).threadPriority(Thread.NORM_PRIORITY - 2)
//                .denyCacheImageMultipleSizesInMemory()
//                .discCacheFileNameGenerator(new Md5FileNameGenerator())
//                .tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging() // Not
//                        // necessary
//                        // in
//                        // common
//                .build();
//        // Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(config);
//    }







    /*
     * AlbanianCircle Global Alert Dialog
     */
    public static void ShowAlert(Context mContext, String mTitle,
                                 String mMessage, boolean isCancelBtn) {

        AlertDialog.Builder mAlert = new AlertDialog.Builder(mContext);
        mAlert.setCancelable(false);
        mAlert.setTitle(mTitle);
        mAlert.setMessage(mMessage);
        if (isCancelBtn) {
            mAlert.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }

        mAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        mAlert.show();
    }

    /*
     * Cally Global Progress Dialog
     */
    public static void showProgressDialog(Context mContext, String mTitle,
                                          String mMessage) {

        // if (android.os.Build.VERSION.SDK_INT >=
        // android.os.Build.VERSION_CODES.HONEYCOMB) {
        // mProgressDialog = new ProgressDialog(new ContextThemeWrapper(
        // mContext, android.R.style.Theme_Holo_Light_Dialog));
        // } else
        {
            // mProgressDialog = new ProgressHUD(mContext);
        }

        // mProgressDialog = new ProgressDialog(mContext);
        // mProgressDialog.setCancelable(false);
        // mProgressDialog.setTitle(mTitle);
        // mProgressDialog.setMessage(mMessage);

        ProgressHUD.show(mContext, mMessage, false);

        // mProgressDialog.show();

    }

    public static void hideProgressDialog(Context mContext) {
        // if (mProgressDialog != null && mProgressDialog.isShowing()) {
        // mProgressDialog.dismiss();
        // } else {
        // mProgressDialog = new ProgressHUD(mContext);
        // mProgressDialog.dismiss();
        // }

        ProgressHUD.hideProgressDialog(mContext);
    }

    public static void hideKeyBoard(Context mContext, FragmentActivity act) {

        InputMethodManager inputManager = (InputMethodManager) mContext
                .getApplicationContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        try {

            if (inputManager.isAcceptingText()) {
                inputManager.hideSoftInputFromWindow(act.getWindow()
                                .getDecorView().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }





    /*
     * Function to Convert Stream to String
     */
    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /*
     * Function to Check Value on Console Log
     */
	/*
	 * Log Type /1- Log.d/ 2- Log.v / 3- Log.i / 4- Log.e / 5-Log.w
	 */
    public static void ShowLog(String TAG, String Message, int LogType) {
        switch (LogType) {
            case 1:
                Log.d(TAG, Message);
                break;
            case 2:
                Log.v(TAG, Message);
                break;
            case 3:
                Log.i(TAG, Message);
                break;
            case 4:
                Log.e(TAG, Message);
                break;
            case 5:
                Log.w(TAG, Message);
                break;
            default:
                Log.d(TAG, Message);
                break;
        }
    }



    public static File getImage(String url1) {

        File filenew = null;

        try {

            URL url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            File SDCardRoot = Environment.getExternalStorageDirectory()
                    .getAbsoluteFile();
            String filename = "downloadedFile.png";
            Log.i("Local filename:", "" + filename);
            File file = new File(SDCardRoot, filename);
            if (file.createNewFile()) {
                file.createNewFile();
            }
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // Log.i("Progress:","downloadedSize:"+downloadedSize+"totalSize:"+
                // totalSize) ;
            }
            fileOutput.close();
            if (downloadedSize == totalSize)
                filenew = file;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            filenew = null;
            e.printStackTrace();
        }
        Log.i("filepath:", " " + filenew);
        return filenew;

    }

    @SuppressLint("NewApi")
    public static void setAlpha(View view, float alpha) {
        // TODO Auto-generated method stub

        if (Build.VERSION.SDK_INT < 11) {
            final AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
            animation.setDuration(0);
            animation.setFillAfter(true);
            view.startAnimation(animation);
        } else {
            view.setAlpha(alpha);
        }

    }

    public static void registerDevice(Context mContext, Activity act) {
        // TODO Auto-generated method stub

        try {

            GCMRegistrar.checkDevice(mContext);
            GCMRegistrar.checkManifest(mContext);

            String regId = GCMRegistrar.getRegistrationId(mContext);

            AlbanianApplication.setReg_deviceID(regId);


            Log.d("sumit", "GCM REG ID == " + regId.length());
            Log.d("sumit", "GCM REG ID == " + regId);


//            Toast.makeText(mContext, "sdgsfdgdfg", Toast.LENGTH_LONG);

            // Log.d("sumit",NextCutApplication.GCMregId+"");

            if (regId.length() == 0) {

                Log.d("sumit","regid inside register== ");

                try
                {

                    GCMRegistrar.register(mContext, AlbanianConstants.SENDER_ID);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }



            } else {
                Log.d("sumit", "Device Already registered");
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    /******************************
     * Check Network Connectivity
     */
    public static final boolean isInternetOn(Context ctx) {

        // ConnectivityManager connec = (ConnectivityManager) ctx
        // .getSystemService(Context.CONNECTIVITY_SERVICE);
        //
        //
        //
        // if (connec.getNetworkInfo(0).getState() ==
        // NetworkInfo.State.CONNECTED
        // || connec.getNetworkInfo(1).getState() ==
        // NetworkInfo.State.CONNECTED) {
        //
        // return true;
        // } else if (connec.getNetworkInfo(0).getState() ==
        // NetworkInfo.State.DISCONNECTED
        // || connec.getNetworkInfo(1).getState() ==
        // NetworkInfo.State.DISCONNECTED) {
        // return false;
        // }
        //
        // return false;

        try {
            ConnectivityManager connect = null;
            connect = (ConnectivityManager) ctx
                    .getSystemService(ctx.CONNECTIVITY_SERVICE);

            if (connect != null) {
                NetworkInfo result = connect
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (result != null && result.isConnectedOrConnecting()) {
                    return true;
                } else {

                    NetworkInfo result1 = connect
                            .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (result1 != null && result1.isConnectedOrConnecting()) {
                        return true;
                    } else {
                        return false;
                    }

                }
            } else
                return false;
        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }

    }



    public static Bitmap scaleBitmap(Bitmap bitmap, int RequiredWidth,
                                     int RequiredHeight) {
        Bitmap output = Bitmap.createBitmap(RequiredWidth, RequiredHeight,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        Matrix m = new Matrix();
        m.setScale((float) RequiredWidth / bitmap.getWidth(),
                (float) RequiredHeight / bitmap.getHeight());
        canvas.drawBitmap(bitmap, m, new Paint());

        // bitmap.recycle();
        return output;
    }


    public static String getCurrentDate() {
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());
            return formattedDate;
        } catch (Exception e) {

        }
        return null;
    }





    public static void updateUserLocation(final Activity mContext, final String lat,
                                      final String lon, final String userId) {


//        AlbanianApplication.showProgressDialog(mContext, "", "Loading...");



        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "update location= " + response.toString());
                AlbanianApplication.hideProgressDialog(mContext);

//                parseResponse(response.toString(),mContext);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "update location= Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(mContext);

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "updateuserlocation");
                params.put("user_id", userId );
                params.put("user_latitude", lat );
                params.put("user_longitude", lon );
                params.put("AppName", AlbanianConstants.AppName);


                return params;
            }

        };


        AlbanianApplication.getInstance().addToRequestQueue(sr);


    }




    public static void showKeyBoard(Context mContext, FragmentActivity act,EditText ed) {

        InputMethodManager inputManager = (InputMethodManager) mContext
                .getApplicationContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        try {

            inputManager.showSoftInput(ed, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }


}
