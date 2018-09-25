package com.SigninSignup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;

/**
 * Created by Sumit on 13/09/2015.
 */
public class SignInSignupFragment extends Fragment implements View.OnClickListener {


    /********** Login Fragment UI Declaration */

    private View mView;
    private RelativeLayout signin,signup;

    /* variables declaration*/

    private AlbanianPreferances pref;
    private java.lang.String s;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new AlbanianPreferances(getActivity());


    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        AlbanianApplication.hideKeyBoard(getActivity(), getActivity());

        if (AlbanianApplication.isInternetOn(getActivity()))
        {

        }
        else
        {
            String mTitle = getResources().getString(R.string.app_name);

            AlbanianApplication.ShowAlert(getActivity(), mTitle,
                    getResources().getString(R.string.nointernet), false);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.register, container, false);

        iniView(mView);

        return mView;
    }

    private void iniView(View mView) {

        signin=(RelativeLayout)mView.findViewById(R.id.rl_register);
        signup=(RelativeLayout)mView.findViewById(R.id.rl_login);

        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.rl_login:

                loadSigninFagment();

                break;

            case R.id.rl_register:


                loadSignupFagments();

//                    loadMainFragmentActivity();

                break;


            default:
                break;
        }

    }

    private void loadSignupFagments() {

        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction();
        SignUpFirstFragment mFragment = new SignUpFirstFragment();
        mFragmentTransaction.replace(R.id.frame_signinsignup, mFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    private void loadSigninFagment() {

        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction();
        SignInFragment mFragment = new SignInFragment();
        mFragmentTransaction.replace(R.id.frame_signinsignup, mFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

}
