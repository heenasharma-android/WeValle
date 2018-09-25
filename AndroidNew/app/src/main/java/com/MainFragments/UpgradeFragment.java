package com.MainFragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.R;
import com.models.UpgradeModel;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by Sumit on 20/09/2015.
 */
public class UpgradeFragment extends Fragment implements View.OnClickListener {

    /* Views declarations*/

    private View mView;
    private TextView headertext;
    private RelativeLayout back;
    private TextView headertextright;
    private ViewPager plan_pager;
    private CirclePageIndicator titlePageIndicator;
    private LinearLayout oneyearSubs,sixmonthSubs,threemonthSubs;


    /*variables declarations*/
    private String CURRENTTABTAG;
    private ArrayList<UpgradeModel> upgradeModelArrayList;
    private DataAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        upgradeModelArrayList=new ArrayList<>();

        UpgradeModel upgradeModel1=new UpgradeModel();

        upgradeModel1.setUpgradeText("Send Unlimited Messages!");
//        upgradeModel1.setUpgradeImage(getActivity().getResources().getDrawable(R.drawable.premium));
        upgradeModelArrayList.add(upgradeModel1);

        UpgradeModel upgradeModel2=new UpgradeModel();
        upgradeModel2.setUpgradeText("Free users can message you first");
//        upgradeModel2.setUpgradeImage(getActivity().getResources().getDrawable(R.drawable.chat));
        upgradeModelArrayList.add(upgradeModel2);

        UpgradeModel upgradeModel3=new UpgradeModel();
        upgradeModel3.setUpgradeText("Stand out from the regular crowd");
//        upgradeModel3.setUpgradeImage(getActivity().getResources().getDrawable(R.drawable.appreciation));
        upgradeModelArrayList.add(upgradeModel3);

        UpgradeModel upgradeModel4=new UpgradeModel();
        upgradeModel4.setUpgradeText("Go Invisible");
//        upgradeModel4.setUpgradeImage(getActivity().getResources().getDrawable(R.drawable.hide));
        upgradeModelArrayList.add(upgradeModel4);

        UpgradeModel upgradeModel5=new UpgradeModel();
        upgradeModel5.setUpgradeText("Get twice the profile views");
//        upgradeModel5.setUpgradeImage(getActivity().getResources().getDrawable(R.drawable.profit));
        upgradeModelArrayList.add(upgradeModel5);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.upgrade_new, container, false);

        init();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("Upgrade Screen");

        //mActivity.hideCurrentTab();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {


            CURRENTTABTAG = getArguments().getString(
                    AlbanianConstants.EXTRA_CURRENTTAB_TAG);
        }

    }

    private void init() {
        initViews();

    }


    private void initViews() {

        plan_pager = (ViewPager)mView.findViewById(R.id.plan_pager);
        titlePageIndicator = (CirclePageIndicator)mView.findViewById(R.id.indicator);

        adapter = new DataAdapter(getFragmentManager(), upgradeModelArrayList);
        plan_pager.setAdapter(adapter);
        titlePageIndicator.setViewPager(plan_pager);

        headertext = (TextView)mView.findViewById(R.id.txt_headerlogo);
        headertextright = (TextView)mView.findViewById(R.id.txt_header_right);
        headertextright.setVisibility(View.VISIBLE);
        headertextright.setText("Terms");
        headertextright.setTypeface(null, Typeface.BOLD);

        oneyearSubs = (LinearLayout)mView.findViewById(R.id.rl_oneyear);
        sixmonthSubs = (LinearLayout)mView.findViewById(R.id.rl_sixmonth);
        threemonthSubs = (LinearLayout)mView.findViewById(R.id.rl_threemonth);

        back = (RelativeLayout) mView.findViewById(R.id.rl_back_header);
        headertextright.setOnClickListener(this);
        oneyearSubs.setOnClickListener(this);
        sixmonthSubs.setOnClickListener(this);
        threemonthSubs.setOnClickListener(this);

        back.setOnClickListener(this);
        headertext.setText(getResources().getString(R.string.nav_item_upgrade));

        adapter = new DataAdapter(getFragmentManager(), upgradeModelArrayList);
    }


    // set adapter for sliding images

    class DataAdapter extends FragmentStatePagerAdapter {

        protected ArrayList<UpgradeModel> result;

        public DataAdapter(FragmentManager fm, ArrayList<UpgradeModel> result) {
            super(fm);

            this.result = result;

        }

        @Override
        public Fragment getItem(int position) {
            // return CalenderFragment.newInstance(position);

            UpgradePagerFragment fragment = new UpgradePagerFragment();

            if (result != null) {

                {

                  Log.d("sumit", "image to send is= " + position);
                  Log.d("sumit", "image to send is= " + result.get(position).getUpgradeText());
//                  Log.d("sumit", "image to send is= " + result.get(position).getUpgradeImage());

                    Bundle b = new Bundle();
                    b.putParcelable("upgradestring", result.get(position));
                    b.putInt("upgradeimageposition", position);
                    b.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG, CURRENTTABTAG);
                    fragment.setArguments(b);

                }

                return fragment;
            }
            else {

                Log.d("sumit", "return new Fragment() " + position);

                return new Fragment();
            }

        }


        @Override
        public int getCount() {

            return result.size();

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back_header:


                FragmentManager fm = getFragmentManager();

                for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++)
                {
                    Log.d("sumit", "upgrade no of frag.= " + fm.getBackStackEntryCount());
                    Log.d("sumit", "upgrade frag.= " + fm.getBackStackEntryAt(entry).getId());
                }

                getActivity().onBackPressed();

                break;

            case R.id.rl_oneyear:

                AlbanianApplication.getInstance().trackEvent("119.40 / 1 Year Plan", "119.40 / 1 Year Plan", "119.40 / 1 Year Plan");

                loadPaymentPage(119.40);
                break;

            case R.id.rl_sixmonth:

                AlbanianApplication.getInstance().trackEvent("71.70 / 6 Month Plan", "71.70 / 6 Month Plan", "71.70 / 6 Month Plan");

                loadPaymentPage(71.70);
                break;

            case R.id.rl_threemonth:

                AlbanianApplication.getInstance().trackEvent("44.85 / 3 Month Plan", "44.85 / 3 Month Plan", "44.85 / 3 Month Plan");

                loadPaymentPage(44.85);
                break;

            case R.id.txt_header_right:

                loadTosPage("http://albaniancircle.com/terms.html");

                break;
        }
    }

    private void loadTosPage(String extra) {


        String url = extra;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);


    }



    private void loadPaymentPage(double payment) {


//        Bundle bundle=new Bundle();
//        bundle.putDouble(AlbanianConstants.EXTRA_PAYMENTPRICE, payment);
//        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);
//
//        mActivity.pushFragments(CURRENTTABTAG,
//                new PaymentFragment(), false, true, bundle);

    }



}
