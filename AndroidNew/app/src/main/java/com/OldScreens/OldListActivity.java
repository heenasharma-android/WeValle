package com.OldScreens;

import android.content.BroadcastReceiver;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.MainFragments.MessagesFragment;
import com.MainFragments.RecievedMessagesFragment;
import com.MainFragments.SentMessagesFragment;
import com.MainFragments.UpgradeFragment;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;

import java.util.ArrayList;
import java.util.Random;

public class OldListActivity extends AppCompatActivity implements View.OnClickListener{

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_list);
        back = (RelativeLayout) findViewById(R.id.rl_back_header);
        back.setOnClickListener(this);
        back.setVisibility(View.INVISIBLE);

        headertext = (TextView)findViewById(R.id.txt_headerlogo);

        headertext.setText(getResources().getString(R.string.Chat));
        pref = new AlbanianPreferances(this);
//        pref.setCurrentScreen("MESSAGETAB");
//
//        String[] mOffersArray =   getResources().getStringArray(R.array.offers_array);
//
//        Random random = new Random();
//
//        int maxIndex = mOffersArray.length;
//        int generatedIndex = random.nextInt(maxIndex);
//
//        offertext.setText(mOffersArray[generatedIndex]);

        // mActivity.showCurrentTab();





        result.clear();

        result.add(getResources().getString(R.string.received_messages));
        mIntroViewPager = (ViewPager) findViewById(R.id.pager);

        upgrade=(RelativeLayout) findViewById(R.id.ll_upgrade_to_premium);

        offertext=(TextView) findViewById(R.id.txt_offertext);

        // mIntroViewPager.setOffscreenPageLimit(4);
//        indicator = (TitlePageIndicator) mView.findViewById(R.id.indicator);

        adapter = new DataAdapter(getSupportFragmentManager(), result);

        mIntroViewPager.setAdapter(adapter);

        upgrade.setOnClickListener(this);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purp));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.rl_back_header:

                if (getFragmentManager().getBackStackEntryCount()<=0) {
                    onBackPressed();
                }
                else
                {
                    getFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

                break;

            case R.id.ll_upgrade_to_premium:


//                Bundle bundle=new Bundle();
//                bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
//                        AlbanianConstants.TAB_3_TAG);
//
//                mActivity.pushFragments(AlbanianConstants.TAB_3_TAG,
//                        new UpgradeFragment(), false, true, bundle);


                break;

            default:
                break;

        }
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
//                RecievedMessagesFragment fragment = new RecievedMessagesFragment();
//                return fragment;

            }


            else if (position == 1) {

//                SentMessagesFragment fragment = new SentMessagesFragment();
//                return fragment;
            }

            else
            {

                RecievedMessagesFragment fragment = new RecievedMessagesFragment();
                return fragment;
            }

            return null;
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
