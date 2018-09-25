package com.MainFragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;

import java.util.ArrayList;
import java.util.Random;


public class MessagesFragment extends BaseFragment implements View.OnClickListener{

    /*View */

    private View mView;
    private RelativeLayout back;
    ViewPager mIntroViewPager;
//    TitlePageIndicator indicator;
    private TextView headertext;

    private RelativeLayout upgrade;
    private TextView offertext;

    /*VAriables.*/

    AlbanianPreferances pref;

    private BroadcastReceiver mReceiver;



    FragmentPagerAdapter adapter;
    private ArrayList<String> result = new ArrayList<>();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new AlbanianPreferances(mActivity);


        result.clear();

        result.add(getResources().getString(R.string.received_messages));
//        result.add(getResources().getString(R.string.sent_messages));



    }

    @Override
    public void onResume() {
        super.onResume();


        IntentFilter intentFilter = new IntentFilter(
                "android.intent.action.MAIN");

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                //extract our message from intent
                String msg_for_me = intent.getStringExtra("some_msg");
                //log our message value
                Log.i("sumit", msg_for_me);

                setCounters(pref.getUserSubscription());

            }
        };
        //registering our receiver
        mActivity.registerReceiver(mReceiver, intentFilter);


        AlbanianApplication.getInstance().trackScreenView("Messages");

    }




    @Override
    public void onPause() {
        super.onPause();

        mActivity.unregisterReceiver(this.mReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_viewd_me, container, false);

        mIntroViewPager = (ViewPager) mView.findViewById(R.id.pager);

        upgrade=(RelativeLayout)mView.findViewById(R.id.ll_upgrade_to_premium);

        offertext=(TextView)mView.findViewById(R.id.txt_offertext);

        // mIntroViewPager.setOffscreenPageLimit(4);
//        indicator = (TitlePageIndicator) mView.findViewById(R.id.indicator);



        upgrade.setOnClickListener(this);

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();

        pref.setCurrentScreen("MESSAGETAB");

        String[] mOffersArray =   getResources().getStringArray(R.array.offers_array);

        Random random = new Random();

        int maxIndex = mOffersArray.length;
        int generatedIndex = random.nextInt(maxIndex);

        offertext.setText(mOffersArray[generatedIndex]);

       // mActivity.showCurrentTab();


        adapter = new DataAdapter(getChildFragmentManager(), result);

        mIntroViewPager.setAdapter(adapter);
//        indicator.setViewPager(mIntroViewPager);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        initViews();

    }



    private void initViews() {

        back = (RelativeLayout) mView.findViewById(R.id.rl_back_header);
        back.setOnClickListener(this);
        back.setVisibility(View.INVISIBLE);

        headertext = (TextView)mView.findViewById(R.id.txt_headerlogo);

        headertext.setText(mActivity.getString(R.string.Chat));


    }



    class DataAdapter extends FragmentPagerAdapter {

        protected ArrayList<String> result;

        public DataAdapter(FragmentManager fm, ArrayList<String> result) {
            super(fm);

            this.result = result;

        }

        @Override
        public Fragment getItem(int position) {
            // return CalenderFragment.newInstance(position);

            Log.d("sumit", "FRAMENT POS== " + position);


            if (position == 0)

            {
                RecievedMessagesFragment fragment = new RecievedMessagesFragment();
                return fragment;

            }


            else if (position == 1) {

                SentMessagesFragment fragment = new SentMessagesFragment();
                return fragment;
            }

            else
                {

                RecievedMessagesFragment fragment = new RecievedMessagesFragment();
                return fragment;
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            // return CONTENT[position % CONTENT.length].toUpperCase();

            // String[] separated = result.get(position);

            if (result.size() > 0) {
                return result.get(position);
            } else {
                return "";
            }

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return result.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            // TODO Auto-generated method stub
            super.destroyItem(container, position, object);
        }
        //

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.rl_back_header:

                if (getFragmentManager().getBackStackEntryCount()<=0) {
                    getActivity().onBackPressed();
                }
                else
                {
                    getFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

                break;

            case R.id.ll_upgrade_to_premium:


                Bundle bundle=new Bundle();
                bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
                        AlbanianConstants.TAB_3_TAG);

                mActivity.pushFragments(AlbanianConstants.TAB_3_TAG,
                        new UpgradeFragment(), false, true, bundle);


                break;

            default:
                break;

        }
    }


    private void setCounters(String userSubscriptionStatus) {



        Log.d("sumit","userSubscriptionStatus= "+userSubscriptionStatus);

        if (userSubscriptionStatus.equals("0")) {

//            upgrade_sidebar_main.setVisibility(View.VISIBLE);
            upgrade.setVisibility(View.VISIBLE);

        }
        else
        {
//            upgrade_sidebar_main.setVisibility(View.GONE);
            upgrade.setVisibility(View.GONE);
        }

//        mActivity.setMesageCount(newMsg);


    }

}
