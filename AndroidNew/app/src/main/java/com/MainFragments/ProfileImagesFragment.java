package com.MainFragments;

import android.os.Bundle;
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

import java.util.ArrayList;


public class ProfileImagesFragment extends BaseFragment implements View.OnClickListener {

	
	/**
	 * Image Loader 
	 */
	
	
//	DisplayImageOptions options;
//	protected ImageLoader imageLoader = ImageLoader.getInstance();
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	
	private View mainView;
	
	private ImageView image;
//	private RelativeLayout makemain,delete;
//	private ImageView back;


	public UserImagesModel imageModel;
	private AlbanianPreferances pref;
	private String mProfileVisitedId;
	private ArrayList<UserImagesModel> userImagesModelsLst;
	private String position,CURRENTTABTAG;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		pref = new AlbanianPreferances(getActivity().getApplicationContext());

		imageModel = getArguments().getParcelable("imagestring");
		userImagesModelsLst = getArguments().getParcelableArrayList("imagestringlist");
		mProfileVisitedId= getArguments().getString(AlbanianConstants.EXTRA_USERID);

		position= getArguments().getString("position");

		CURRENTTABTAG= getArguments().getString(AlbanianConstants.EXTRA_CURRENTTAB_TAG);

		Log.d("sumit","image string= "+CURRENTTABTAG);
		Log.d("sumit","image string mProfileVisitedId= "+mProfileVisitedId);

		
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
		

		mainView = inflater.inflate(R.layout.viewimage_profile, container, false);
		

		image=(ImageView)mainView.findViewById(R.id.img_viewimage);


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


		image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (mProfileVisitedId .equals(pref.getUserData().getUserId())) {



					loadUserImagesFragment();
				}
				else
				{
//					Toast.makeText(getActivity(),"loading userimages from prfimages"
//							,Toast.LENGTH_SHORT).show();

					Log.d("sumit","loading userimages fom prfimgfes= "+CURRENTTABTAG);

					loadOtherUserImagesFragment();
				}
			}
		});


		return mainView;

	}


	private void loadOtherUserImagesFragment() {

		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(AlbanianConstants.EXTRA_IMAGESLIST, userImagesModelsLst);
		bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, mProfileVisitedId);
		bundle.putString("position", position);
		bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);

		mActivity.pushFragments(CURRENTTABTAG,
				new ViewImage_ProfileFragment(), false, true, bundle);
	}


	private void loadUserImagesFragment() {


		Bundle bundle = new Bundle();
		bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, mProfileVisitedId);
		bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);


		mActivity.pushFragments(CURRENTTABTAG,
				new UserImagesFragment(), false, true, bundle);

	}


	public static ProfileImagesFragment newInstance(int position) {
		ProfileImagesFragment fragment = new ProfileImagesFragment();

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
