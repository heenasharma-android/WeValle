package com.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.models.UserImagesModel;

import java.util.ArrayList;

public class ViewImageFragment extends BaseFragment implements
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
	private String mProfileVisitedId;
	private int position;

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

		mainView = inflater.inflate(R.layout.userimagespager, container, false);

		init(mainView);

		return mainView;

	}

	private void init(View mainView) {
		// TODO Auto-generated method stub


		mIntroViewPager = (ViewPager) mainView.findViewById(R.id.pager);

		userimage = (ImageView) mainView.findViewById(R.id.img_viewimage);
		

		

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		if(getArguments()!=null)
		{
			mImagesList= getArguments().getParcelableArrayList(AlbanianConstants.EXTRA_IMAGESLIST);



			mProfileVisitedId= getArguments().getString(AlbanianConstants.EXTRA_USERID);
			position= getArguments().getInt("position");


			if (pref.getUserData().getUserId().equals(mProfileVisitedId)) {

				adapter = new DataAdapter_User(getChildFragmentManager(), mImagesList);
			}
			else
			{
				adapter = new DataAdapter(getChildFragmentManager(), mImagesList);
			}



			mIntroViewPager.setAdapter(adapter);


			mIntroViewPager.setCurrentItem(position);


		}
		

	}

	


	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.img_close_image:

			getFragmentManager().popBackStack();

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

			ImageFragment fragment = new ImageFragment();

			if (result != null) {

				{

//                  Log.d("sumit", "image to send is= " + result.get(position));

					Bundle b = new Bundle();
					b.putParcelable("imagestring", result.get(position));
					b.putString(AlbanianConstants.EXTRA_USERID, mProfileVisitedId);

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

                  Log.d("sumit", "result.size() is= " + result.size());

					Bundle b = new Bundle();
					b.putParcelable("imagestring", result.get(position));
					b.putString(AlbanianConstants.EXTRA_USERID, mProfileVisitedId);

					if (result.size() > 1) {

						b.putBoolean(AlbanianConstants.EXTRA_LASTIMAGE, true);
					}
					else
					{
						b.putBoolean(AlbanianConstants.EXTRA_LASTIMAGE, false);
					}


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
