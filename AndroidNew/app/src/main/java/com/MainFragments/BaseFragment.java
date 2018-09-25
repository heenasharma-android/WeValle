package com.MainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.SigninSignup.TabbedActivity;

public class BaseFragment extends Fragment {
	public HomeFragmentNewActivity mActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mActivity = (HomeFragmentNewActivity) this.getActivity();
	}

	public boolean onBackPressed() {
		return false;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	}
}
