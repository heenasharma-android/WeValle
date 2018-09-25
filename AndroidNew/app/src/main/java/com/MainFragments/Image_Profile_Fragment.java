package com.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.models.UserImagesModel;
import com.squareup.picasso.Picasso;


public class Image_Profile_Fragment extends Fragment implements View.OnClickListener {

	
	/**
	 * Image Loader 
	 */
	
	
//	DisplayImageOptions options;
//	protected ImageLoader imageLoader = ImageLoader.getInstance();
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	
	private View mainView;
	
	private ImageView image;
//	private ImageView back;


	private UserImagesModel imageModel;
	private AlbanianPreferances pref;
	private String mProfileVisitedId,CURRENTTABTAG;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		pref = new AlbanianPreferances(getActivity().getApplicationContext());

		imageModel = getArguments().getParcelable("imagestring");
		mProfileVisitedId= getArguments().getString(AlbanianConstants.EXTRA_USERID);
		CURRENTTABTAG= getArguments().getString(AlbanianConstants.EXTRA_CURRENTTAB_TAG);

		Log.d("sumit","image string= "+imageModel.getUserImageUrl());

		
//		Log.d("sumit", "images received before === " + imagestring);
		
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
		
		
		mainView = inflater.inflate(R.layout.viewimage_profile_otheruser, container, false);
		

		image=(ImageView)mainView.findViewById(R.id.img_viewimage);





		try
		{


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

		image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				getActivity().onBackPressed();
			}
		});


		return mainView;

	}


	public static Image_Profile_Fragment newInstance(int position) {
		Image_Profile_Fragment fragment = new Image_Profile_Fragment();

		return fragment;
	}


	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.img_close_image:


				getActivity().onBackPressed();

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
