package com.MainFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.models.UserImagesModel;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ImageFragment extends BaseFragment implements View.OnClickListener {

	

//	DisplayImageOptions options;
//	protected ImageLoader imageLoader = ImageLoader.getInstance();
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	
	private View mainView;
	
	private ImageView image;
	private RelativeLayout makemain,delete;
	private ImageView back;


	private UserImagesModel imageModel;
	private AlbanianPreferances pref;
	private String mProfileVisitedId;
	private boolean lastImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		pref = new AlbanianPreferances(getActivity().getApplicationContext());

		imageModel = getArguments().getParcelable("imagestring");
		mProfileVisitedId= getArguments().getString(AlbanianConstants.EXTRA_USERID);
		lastImage= getArguments().getBoolean(AlbanianConstants.EXTRA_LASTIMAGE);

		Log.d("sumit","image string= "+imageModel.getUserImageUrl());
		Log.d("sumit","image string lastImage= "+lastImage);


//		options = new DisplayImageOptions.Builder()
////		.showStubImage(R.drawable.logo)
////		.showImageForEmptyUri(R.drawable.logo)
////		.showImageOnFail(R.drawable.logo)
//		.cacheInMemory()
//		.cacheOnDisc()
//		.build();
		
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// super.onCreateView(inflater, container, savedInstanceState);
		
		
		mainView = inflater.inflate(R.layout.viewimage, container, false);
		

		image=(ImageView)mainView.findViewById(R.id.img_viewimage);

		makemain= (RelativeLayout) mainView.findViewById(R.id.rl_makemain_btn);
		delete= (RelativeLayout) mainView.findViewById(R.id.rl_delete_btn);

		makemain.setOnClickListener(this);
		delete.setOnClickListener(this);

		back = (ImageView) mainView.findViewById(R.id.img_close_image);
		back.setOnClickListener(this);


			if (mProfileVisitedId .equals(pref.getUserData().getUserId())) {

				makemain.setVisibility(View.VISIBLE);
				delete.setVisibility(View.VISIBLE);
			}
			else
			{
				makemain.setVisibility(View.GONE);
				delete.setVisibility(View.GONE);
			}


		try
		{
			// get input stream
//			InputStream ims = getActivity().getAssets().open("image/" + imagestring);
//			// load image as Drawable
//			Drawable d = Drawable.createFromStream(ims, null);
//			// set image to ImageView
//			image.setImageDrawable(d);


			if (imageModel != null) {

//				imageLoader
//						.displayImage(imageModel.getUserImageUrl(),
//								image, options, animateFirstListener);

				Picasso.with(getContext()).load(imageModel.getUserImageUrl()).fit().centerCrop()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
//						.transform(new RoundedCornersTransform())
						.into(image);
			}


		} catch (Exception ex)
		{

		}



		return mainView;

	}



	private void makeMainImage() {

		AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

		StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d(AlbanianConstants.TAG, "make main image response= " + response.toString());

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
				params.put("column_value", imageModel.getImageId());
				params.put("user_id", pref.getUserData().getUserId());
				params.put("AppName", AlbanianConstants.AppName);


				return params;
			}

		};



		AlbanianApplication.getInstance().addToRequestQueue(sr);
	}

	private void deleteImage() {

		AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

		StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d(AlbanianConstants.TAG, "delete image response= " + response.toString());

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
				params.put("image_id", imageModel.getImageId());
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

						mActivity.onBackPressed();


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


	public static ImageFragment newInstance(int position) {
		ImageFragment fragment = new ImageFragment();

		return fragment;
	}


	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.img_close_image:

				Log.d("sumit","back from user images slide");
				getActivity().onBackPressed();

				break;

			case R.id.rl_makemain_btn:

				makeMainImage();

				break;

			case R.id.rl_delete_btn:

				if (lastImage)
				{

					deleteImage();
				}
				else
				{

					String mTitle = getResources().getString(R.string.app_name);

					AlbanianApplication.ShowAlert(getActivity(), mTitle,
							mActivity.getResources().getString(R.string.imaagedeletealert), false);
				}

				break;
		}

	}

	@Override
	public void onResume() {
		super.onResume();

		// Tracking the screen view
		AlbanianApplication.getInstance().trackScreenView("View Image Screen");
	}
}
