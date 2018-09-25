package com.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.models.UserImagesModel;

import java.util.ArrayList;

public class ViewImage_ProfileFragment extends BaseFragment implements
		OnClickListener {

	/**
	 * UI.
	 */
	private View mainView;



	private ImageView userimage;

	ViewPager mIntroViewPager;
	FragmentPagerAdapter adapter;

	
	/**
	 * Variables.
	 */

	private AlbanianApplication mapp;

	
	private AlbanianPreferances pref;

//	DisplayImageOptions options;
//	protected ImageLoader imageLoader = ImageLoader.getInstance();
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	

	private ArrayList<UserImagesModel> mImagesList;
//	private String mProfileVisitedId;
	private String position,CURRENTTABTAG;


	private TextView imagenumber;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mapp = (AlbanianApplication) getActivity().getApplication();
		pref = new AlbanianPreferances(getActivity().getApplicationContext());

//		options = new DisplayImageOptions.Builder()
//		// .showStubImage(R.drawable.ic_launcher)
//		// .showImageForEmptyUri(R.drawable.ic_launcher)
//		// .showImageOnFail(R.drawable.ic_launcher)
//				.cacheInMemory().cacheOnDisc().build();


		

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// super.onCreateView(inflater, container, savedInstanceState);

		mainView = inflater.inflate(R.layout.userimagespager_profile, container, false);

		init(mainView);

		return mainView;

	}

	private void init(View mainView) {
		// TODO Auto-generated method stub


		mIntroViewPager = (ViewPager) mainView.findViewById(R.id.pager);

		userimage = (ImageView) mainView.findViewById(R.id.img_viewimage);

		imagenumber = (TextView) mainView.findViewById(R.id.txt_image_number);
		

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		if(getArguments()!=null)
		{
			mImagesList= getArguments().getParcelableArrayList(AlbanianConstants.EXTRA_IMAGESLIST);
			CURRENTTABTAG= getArguments().getString(AlbanianConstants.EXTRA_CURRENTTAB_TAG);


			position= getArguments().getString("position");

			{
				adapter = new DataAdapter(getChildFragmentManager(), mImagesList);
			}



			mIntroViewPager.setAdapter(adapter);


			mIntroViewPager.setCurrentItem(Integer.parseInt(position));

			mIntroViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

					imagenumber.setText(position+1+"/"+mImagesList.size());
				}

				@Override
				public void onPageSelected(int position) {

				}

				@Override
				public void onPageScrollStateChanged(int state) {

				}
			});




		}
		

	}

	


	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.img_close_image:

//			loadMainFragment();

			getActivity().onBackPressed();

			break;



		

		default:
			break;
		}

	}



	// set adapter for sliding images

	class DataAdapter extends FragmentPagerAdapter {

		protected ArrayList<UserImagesModel> result;

		public DataAdapter(FragmentManager fm, ArrayList<UserImagesModel> result) {
			super(fm);

			this.result = result;

		}

		@Override
		public Fragment getItem(int position) {
			// return CalenderFragment.newInstance(position);

			Image_Profile_Fragment fragment = new Image_Profile_Fragment();

			if (result != null) {

				{

//                  Log.d("sumit", "image to send is= " + result.get(position));

					Bundle b = new Bundle();
					b.putParcelable("imagestring", result.get(position));
					b.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG, CURRENTTABTAG);
					fragment.setArguments(b);

				}

				return fragment;
			} else {
				return new Fragment();
			}

		}


		@Override
		public int getCount() {

			return result.size();

		}

	}

	// set adapter for sliding images

	class DataAdapter_User extends FragmentPagerAdapter {

		protected ArrayList<UserImagesModel> result;

		public DataAdapter_User(FragmentManager fm, ArrayList<UserImagesModel> result) {
			super(fm);

			this.result = result;

		}

		@Override
		public Fragment getItem(int position) {
			// return CalenderFragment.newInstance(position);


			ImageFragment fragment = new ImageFragment();

			if (result != null) {

				{

					Bundle b = new Bundle();
					b.putParcelable("imagestring", result.get(position));
					b.putParcelableArrayList(AlbanianConstants.EXTRA_IMAGESLIST, mImagesList);
//					b.putString(AlbanianConstants.EXTRA_USERID, mProfileVisitedId);
					fragment.setArguments(b);

				}

				return fragment;
			} else {
				return new Fragment();
			}

		}



		@Override
		public int getCount() {


			return result.size();

		}

	}


	@Override
	public void onResume() {
		super.onResume();

		// Tracking the screen view
		AlbanianApplication.getInstance().trackScreenView("ViewImage Screen");
	}
}
