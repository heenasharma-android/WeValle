package com.albaniancircle;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.MainFragments.HomeFragmentNewActivity;
import com.SigninSignup.SigninSignupFragmentActivity;
import com.google.android.gcm.GCMBaseIntentService;

import org.json.JSONObject;

import java.util.List;


public class GCMIntentService extends GCMBaseIntentService{

	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == 1) 
			{
				
//				Toast.makeText(getApplicationContext(), msg.obj + "",
//						Toast.LENGTH_LONG).show();
				
			    
			}

		}

	};
	private NotificationManager mNotificationManager;
	private AlbanianPreferances pref;
	private Context mContext;
//	private String tag;

	@Override
	protected void onError(Context arg0, String arg1) {

		try {
			Log.d("sumit", "gcm intent call error " + arg1);
			Message msg = Message.obtain();
			msg.what = 1;
			msg.obj = arg1;
			handler.sendMessage(msg);

		} catch (Exception exp) {
			
			Log.d("sumit", exp.getMessage(), exp);
			
			exp.printStackTrace();
		}

	}

	@Override
	protected void onMessage(final Context mContext, final Intent intent) {

	
		
		try
		{

			this.mContext=mContext;

			String action = intent.getAction();

			Log.d("sumit",action+" NOTIFICATION mesage received..");

			if ("com.google.android.c2dm.intent.RECEIVE".equals(action)) {
				final String messagejson = intent.getStringExtra("message");
                final String message1 = intent.getStringExtra("message1");
				
//			   	tag= intent.getStringExtra("tag");
				Log.d("sumit", "NOTIFICATION message= "+messagejson);

				JSONObject jObj = new JSONObject(messagejson);

				String notificationtype = jObj.getString("type");
				String message = jObj.getString("message");

				pref=	new AlbanianPreferances(getApplicationContext());


                ////parse notification type

                try
				{

					Log.d("sumit", "NOTIFICATION pref.getChatUser()= "+pref.getChatUser());
					Log.d("sumit", "NOTIFICATION message= "+message);
					Log.d("sumit", "NOTIFICATION pref.getChatUser().startsWith(message)= " + message.trim().startsWith(pref.getChatUser().trim()));

					if (message != null && pref.getCurrentScreen().equals("CHAT")
							&& message.trim().startsWith(pref.getChatUser().trim()))
					{

						sendBookingBroadcast(mContext,message.replace(pref.getChatUser()+":",""),message1,notificationtype);
					}
					else if (message != null && pref.getCurrentScreen().equals("MESSAGETAB"))
					{

						sendMessageTabBroadcast(mContext,message,message1,notificationtype);
					}

					else if (message != null && pref.getCurrentScreen().equals("PEOPLETAB")
							|| pref.getCurrentScreen().equals("ACTIVITYTAB"))
					{

						sendNonMessageTabBroadcast(mContext,message,message1,notificationtype);
					}

					else
					{

						if (notificationtype.equals("3")) {

							pref.logoutManually();
						}
						else {
							notification(message,message1,notificationtype);
						}

					}


//                    JSONObject jObj = new JSONObject(message1);
//                    String notificationtype = jObj.getString("s");
//                    Log.d("sumit","Noti type= "+notificationtype);
//
//					Log.d("sumit","Noti type= "+notificationtype);




//					else if (notificationtype.equalsIgnoreCase("5")) {
//
////                        NOTIFICATION= Noshow by driver.
////                        NOTIFICATION data= {"s":5,"a":"1","j_s":"3","y":"2015-04-21 13:02:25"}
//
////						sendNoShowBroadCast(mContext, alert, data);
//
//					}


                }
				catch (Exception e)
				{
                    // TODO Auto-generated catch block
                       e.printStackTrace();
                }


				
			}

		} 
		catch (Exception exp) 
		{
			Log.e("gcm", exp.getMessage(), exp);
			exp.printStackTrace();
		}

	}

	private void sendNonMessageTabBroadcast(Context mContext, String message, String data, String notificationtype) {

		if (isAppOpen()) {

			Handler mHandler = new Handler(getMainLooper());
			mHandler.post(new Runnable() {
				@Override
				public void run() {



					Intent intent1 = new Intent(AlbanianConstants.EXTRA_Chat_NonMessageTab_alert);
					//put whatever data you want to send, if any
					intent1.putExtra("message", message);
//					intent1.putExtra("data", data);
					//send broadcast
					mContext.sendBroadcast(intent1);



				}
			});
		}

		else  {

			Handler mHandler = new Handler(getMainLooper());
			mHandler.post(new Runnable() {
				@Override
				public void run() {



					Intent intent1 = new Intent(AlbanianConstants.EXTRA_Chat_NonMessageTab_alert);
					//put whatever data you want to send, if any
					intent1.putExtra("message", message);
//					intent1.putExtra("data", data);
					//send broadcast
					mContext.sendBroadcast(intent1);



				}
			});


		}

		notification(message,data, notificationtype);

	}


	private void sendMessageTabBroadcast(Context mContext, String message, String data, String notificationtype) {

		if (isAppOpen()) {

			Handler mHandler = new Handler(getMainLooper());
			mHandler.post(new Runnable() {
				@Override
				public void run() {



					Intent intent1 = new Intent(AlbanianConstants.EXTRA_Chat_MessageTab_alert);
					//put whatever data you want to send, if any
					intent1.putExtra("message", message);
//					intent1.putExtra("data", data);
					//send broadcast
					mContext.sendBroadcast(intent1);



				}
			});
		}
		else  {

			Handler mHandler = new Handler(getMainLooper());
			mHandler.post(new Runnable() {
				@Override
				public void run() {



					Intent intent1 = new Intent(AlbanianConstants.EXTRA_Chat_MessageTab_alert);
					//put whatever data you want to send, if any
					intent1.putExtra("message", message);
//					intent1.putExtra("data", data);
					//send broadcast
					mContext.sendBroadcast(intent1);



				}
			});


		}

		notification(message,data, notificationtype);

	}

	private void sendBookingBroadcast(final Context mContext, final String message, final String data, String notificationtype) {

		if (isAppOpen()) {

			Handler mHandler = new Handler(getMainLooper());
			mHandler.post(new Runnable() {
				@Override
				public void run() {



					Intent intent1 = new Intent(AlbanianConstants.EXTRA_ChatMessage_alert);
					//put whatever data you want to send, if any
					intent1.putExtra("message", message);
//					intent1.putExtra("data", data);
					//send broadcast
					mContext.sendBroadcast(intent1);



				}
			});
		}
		else
		{


			Handler mHandler = new Handler(getMainLooper());
			mHandler.post(new Runnable() {
				@Override
				public void run() {


					Intent intent1 = new Intent(AlbanianConstants.EXTRA_ChatMessage_alert);
					//put whatever data you want to send, if any
					intent1.putExtra("message", message);
//					intent1.putExtra("data", data);
					//send broadcast
					mContext.sendBroadcast(intent1);

				}
			});


			notification(message,data, notificationtype);


		}
	}


	@Override
	protected void onRegistered(Context arg0, String registrationId) {

		try {

			Log.d("sumit", "gcm intent call" + registrationId);
			AlbanianApplication.setReg_deviceID(registrationId);

		}
		catch (Exception exp) {
//			Log.e("gcm", exp.getMessage(), exp);
			exp.printStackTrace();
		}

	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	private void notification(String message, String data, String notificationtype) {
		try {


			Intent intent;

			if (pref.getUserData() != null
					&& pref.getUserData().getUserId() != "")
			{


				intent = new Intent(this, HomeFragmentNewActivity.class);
			}
			else
			{
				intent = new Intent(this, SigninSignupFragmentActivity.class);
			}



			Bundle mBundle = new Bundle();
			mBundle.putString(AlbanianConstants.EXTRA_NOTIFICATIONTYPE,
					notificationtype);
			intent.putExtras(mBundle);

			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);

// use System.currentTimeMillis() to have a unique ID for the pending intent
			PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

			Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


// build notification
// the addAction re-use the same intent to keep the example short
			Notification.Builder  n  = new Notification.Builder(this)
//					.setContentTitle("New mail from " + "test@gmail.com")
					.setContentText(message)
					.setSmallIcon(getNotificationIcon())
					.setContentIntent(pIntent)
					.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),
							R.mipmap.ic_launcher))
					.setAutoCancel(true)
					.setSound(alarmSound);
//					.addAction(R.drawable.icon, "Call", pIntent)
//					.addAction(R.drawable.icon, "More", pIntent)
//					.addAction(R.drawable.icon, "And more", pIntent).build();


			NotificationManager notificationManager =
					(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

			notificationManager.notify((int) System.currentTimeMillis(), n.build());


		} 
		catch (Exception exp) {
			
			exp.printStackTrace();

		}
	}


    private boolean isAppOpen() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(
                    getApplicationContext().getPackageName())) {
                return false;
            }
        }
        return true;
    }


	private int getNotificationIcon() {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//			int color = 0x008000;
//			notificationBuilder.setColor(color);
			return R.drawable.white_logo;

		} else {
			return R.mipmap.ic_launcher;
		}
	}


}
