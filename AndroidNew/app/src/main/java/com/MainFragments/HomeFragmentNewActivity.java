package com.MainFragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.OldScreens.FilterActivity;
import com.OldScreens.NewProfileActivity;
import com.SigninSignup.ChatTab;
import com.SigninSignup.ListActivity;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.locationmanager.LocationManagerFragmentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Sumit on 28/08/2015.
 */
public class HomeFragmentNewActivity extends LocationManagerFragmentActivity implements FeaturedFragment.OnFragmentInteractionListener, PlaceSelectionListener ,PlaceTabFragment.OnFragmentInteractionListener,TabFragment.OnFragmentInteractionListener{


    private static String TAG = HomeFragmentNewActivity.class.getSimpleName();


    private FragmentRefreshListener fragmentRefreshListener;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private static final int REQUEST_SELECT_PLACE = 1000;

    /* Your Tab host */
   // private TabHost mTabHost;
    String mStringLatitude, mStringLongitude;

    /* A HashMap of stacks, where we use tab identifier as keys.. */
    public HashMap<String, Stack<Fragment>> mStacks;

    /* Save current tabs identifier in this.. */
    private String mCurrentTab;


    /*Variables*/
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    private AlbanianPreferances pref;

    public String messageBadge;
    private Handler mHandler;
    public static boolean fabVisible;
    FloatingActionButton fab1,fab2;
    ViewPagerAdapter viewPagerAdapter;
    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 5)
                {

                    getMessageCount();
                }

            }

        };


        pref = new AlbanianPreferances(this);
        Bundle bundle = new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, pref.getUserData().getUserId());
        /*
		 * Navigation stacks for each tab gets created.. tab identifier is used
		 * as key to get respective stack for each tab
		 */
        mStacks = new HashMap<String, Stack<Fragment>>();
        mStacks.put(AlbanianConstants.TAB_1_TAG, new Stack<Fragment>());
        mStacks.put(AlbanianConstants.TAB_2_TAG, new Stack<Fragment>());
        mStacks.put(AlbanianConstants.TAB_3_TAG, new Stack<Fragment>());
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        fab1=(FloatingActionButton)findViewById(R.id.fab1);
        fab2=(FloatingActionButton)findViewById(R.id.fab2);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),FilterActivity.class);
                startActivity(intent);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder
                            (PlaceAutocomplete.MODE_FULLSCREEN)
                            .setBoundsBias(BOUNDS_MOUNTAIN_VIEW)
                            .build(HomeFragmentNewActivity.this);
                    startActivityForResult(intent, REQUEST_SELECT_PLACE);
                } catch (GooglePlayServicesRepairableException |
                        GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        if (fabVisible)
        {
            fab1.show();
            fab2.show();
        }
        else {
            fab1.hide();
            fab2.hide();
        }
        setTitle(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 1:
                        fab1.show();
                        fab2.show();
                        fabVisible = true;
                        break;
                    default:
                        fab1.hide();
                        fab2.hide();
                        fabVisible = false;
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 1:
                        fab1.show();
                        fab2.show();
                        fabVisible = true;
                        break;
                    default:
                        fab1.hide();
                        fab2.hide();
                        fabVisible = false;
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purp));

//        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
//        mTabHost.setOnTabChangedListener(listener);
//        mTabHost.setup();
//
//        initializeTabs("0");
//
//
//        Bundle extras = getIntent().getExtras();
//        if(extras != null && extras.containsKey(AlbanianConstants.EXTRA_NOTIFICATIONTYPE)){
//
//            // extract the extra-data in the Notification
//            String notiType = extras.getString(AlbanianConstants.EXTRA_NOTIFICATIONTYPE);
//
//            Log.d("sumit","noti from type= "+notiType);
//
//                if (notiType.equals("1")) {
//
//                    displayView(1);
//
//                }
//            else if (notiType.equals("2"))
//                {
//                    displayView(2);
//                }
//            else
//                {
////                  displayView(6);
//                }
//
//
//        }

//        else
//        {
//                 displayView(6);
//        }
        // display the first navigation drawer view on app launch
        sendJsonRequest();
    }

    public void sendJsonRequest() {
        AlbanianApplication.showProgressDialog(HomeFragmentNewActivity.this,"","Loading..");
        String webAddress = "http://culturalsinglesapps.com/WeValleAPICalls.php";
        RequestQueue queue = Volley.newRequestQueue(HomeFragmentNewActivity.this);

        JSONObject object = new JSONObject();

        try {
            object.put("user_id", pref.getUserData().getUserId());
            object.put("api_name", "list_tabs");

        } catch (JSONException e) {
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, webAddress, object, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                AlbanianApplication.hideProgressDialog(HomeFragmentNewActivity.this);
                Log.d("RESPONSE", response.toString());

                try {

                    JSONArray jsonArray=response.getJSONArray("tabs");
                    String ErrorCode = response.optString("ErrorCode");
                    Log.d("ErrorCode", ErrorCode);
                    if (ErrorCode.equalsIgnoreCase("1")) {
                        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.d("RESPONSE", jsonArray.get(i).toString());
                            if (jsonArray.get(i).equals("Feed")) {
                                viewPagerAdapter.addFragment(TabFragment.newInstance(jsonArray.get(i).toString()), jsonArray.get(i).toString());
                            }
                            else if (jsonArray.get(i).equals("People"))
                            {
                                viewPagerAdapter.addFragment(new SearchNewFragment(), jsonArray.get(i).toString());
                            }
                            else if (jsonArray.get(i).equals("Featured"))
                            {
                                viewPagerAdapter.addFragment(new FeaturedFragment(), jsonArray.get(i).toString());
                            }
                            else if (jsonArray.get(i).equals("Places"))
                            {
                                viewPagerAdapter.addFragment(new PlaceTabFragment(), "Places");
                            }
                            else
                            {
                                viewPagerAdapter.addFragment(new PlaceTabFragment(), jsonArray.get(i).toString());
                            }
                            viewPager.setAdapter(viewPagerAdapter);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlbanianApplication.hideProgressDialog(HomeFragmentNewActivity.this);
                Toast.makeText(HomeFragmentNewActivity.this,error.toString().trim(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }


//    private void setupViewPager(ViewPager viewPager) {
//        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        viewPagerAdapter.addFragment(new SearchNewFragment(), "PEOPLE");
//        viewPagerAdapter.addFragment(new FeaturedFragment(), "FEATURED");
//        viewPagerAdapter.addFragment(new PlaceTabFragment(), "PLACES");
//        viewPager.setAdapter(viewPagerAdapter);
//    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }




    @Override
    public void onPlaceSelected(Place place) {
        if (place != null) {
        LatLng latLng = place.getLatLng();
         mStringLatitude = String.valueOf(latLng.latitude);
         mStringLongitude = String.valueOf(latLng.longitude);
            if (null != fragmentRefreshListener) {
                fragmentRefreshListener.onRefresh(mStringLatitude,mStringLongitude,place);
            }

        Log.d("guggu", "Place Selected: " + "lat"+mStringLatitude+","+"lng"+mStringLongitude);
    }


    }

    @Override
    public void onError(Status status) {

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private BroadcastReceiver mChatMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent

//            receivedMessage = intent.getStringExtra("message");

//            Log.d("sumit", "MESSAGE= " + receivedMessage);


            Message msg5 = new Message();
            msg5.what = 5;
            mHandler.sendMessage(msg5);




        }
    };

    private void getMessageCount() {


        StringRequest messageCountRequest = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "updateTokenOnServer= "+response);

                parseResponse(response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

//                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {

            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "new_messagecount");
                params.put("user_id", pref.getUserData().getUserId());

                return params;
            }

        };


        AlbanianApplication.getInstance().addToRequestQueue(messageCountRequest);
    }

    private void parseResponse(String Result) {


        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
                    String messageCount = jObj.getString("messageCount");


                    Log.d(AlbanianConstants.TAG, "messageCount- " + messageCount);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        messageBadge=messageCount;

                    }

                    else
                    {

                        messageBadge="0";

                        String mTitle = getResources().getString(R.string.app_name);

//                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
//                                ErrorMessage, false);
                    }

                    //refreshTab(messageBadge);



                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","profile exception= "+e);
                }

            }

        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(mChatMessageReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        viewPagerAdapter.notifyDataSetChanged();
        getMessageCount();

        registerReceiver(mChatMessageReceiver, new IntentFilter(
                AlbanianConstants.EXTRA_Chat_NonMessageTab_alert));

        AlbanianApplication.getInstance().trackScreenView("Home");
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile) {
            Intent intent= new Intent(HomeFragmentNewActivity.this,NewProfileActivity.class);
            intent.putExtra("userId",pref.getUserData().getUserId());
            startActivity(intent);
            return true;
        }else if(id==R.id.list){
            Intent intent=new Intent(HomeFragmentNewActivity.this,ListActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id==R.id.chat)
        {
            startActivity(new Intent(HomeFragmentNewActivity.this, ChatTab.class));
            return true;

        }

    return super.onOptionsItemSelected(item);
}

    private void displayView(int position) {

//        if (position==1) {
//
//            mTabHost.setCurrentTab(1);
//        }
//        else if (position==2) {
//
//            mTabHost.setCurrentTab(2);
//        }
        // 3 for Messages
//        else if (position==3) {
//
//            FragmentManager mFragmentManager = getSupportFragmentManager();
//            FragmentTransaction mFragmentTransaction = mFragmentManager
//                    .beginTransaction();
//            MessagesFragment mFragment = new MessagesFragment();
//            mFragmentTransaction.replace(R.id.container_body, mFragment);
////        mFragmentTransaction.addToBackStack(null);
//            mFragmentTransaction.commit();
//        }
        // 4 for locals
//        else if (position==4) {
//
//            FragmentManager mFragmentManager = getSupportFragmentManager();
//            FragmentTransaction mFragmentTransaction = mFragmentManager
//                    .beginTransaction();
//            LocalNWorldFragment mFragment = new LocalNWorldFragment();
//            mFragmentTransaction.replace(R.id.container_body, mFragment);
////        mFragmentTransaction.addToBackStack(null);
//            mFragmentTransaction.commit();
//        }
//        else {
//
//            FragmentManager mFragmentManager = getSupportFragmentManager();
//            FragmentTransaction mFragmentTransaction = mFragmentManager
//                    .beginTransaction();
//            HomeNewFragment mFragment = new HomeNewFragment();
//            mFragmentTransaction.replace(R.id.container_body, mFragment);
////           mFragmentTransaction.addToBackStack(null);
//            mFragmentTransaction.commit();
//        }



            // set the toolbar title
//            getSupportActionBar().setTitle(title);

    }



//    public void setMesageCount(String messageCount) {
////        for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
////            //      unselected
////            tabhost.getTabWidget().getChildAt(i)
////                    .setBackgroundResource(R.drawable.tab_unselected);
////
////            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(R.id.tvTabTitle);
////            tv.setTextColor(getResources().getColor(R.color.text_white));
////        }
////        if (position > 0)
//        {
//            //      selected
////            tabhost.getTabWidget().setCurrentTab(position);
////            mTabHost.getTabWidget().getChildAt(2)
////                    .setBackgroundResource(R.drawable.tab_selected);
//            TextView txt_messagescount = (TextView) mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).findViewById(R.id.txt_messagescount);
//            RelativeLayout rl_messagesbadge = (RelativeLayout) mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).findViewById(R.id.rl_messagesbadge);
//
//
//
//            if (messageCount.equals("0")) {
//                rl_messagesbadge.setVisibility(View.GONE);
////            imageView.setVisibility(View.VISIBLE);
//
//            }
//
//            else
//            {
//                rl_messagesbadge.setVisibility(View.VISIBLE);
//                txt_messagescount.setText(messageCount);
////            imageView.setVisibility(View.GONE);
//            }
////            tv.setTextColor(getResources().getColor(R.color.tab_color));
//        }
//
//    }



//    public void initializeTabs(String messageCount) {
//		/* Setup your tab icons and content views.. Nothing special in this.. */
//
//		mTabHost.clearAllTabs();
//
//        TabHost.TabSpec spec = mTabHost.newTabSpec(AlbanianConstants.TAB_1_TAG);
//        mTabHost.setCurrentTab(-3);
//        spec.setContent(new TabHost.TabContentFactory() {
//            public View createTabContent(String tag) {
//                return findViewById(R.id.container_body);
//            }
//        });
//        spec.setIndicator(createTabView(R.drawable.localstab_selector, "Local","0"));
//        mTabHost.addTab(spec);
//
//        spec = mTabHost.newTabSpec(AlbanianConstants.TAB_2_TAG);
//        spec.setContent(new TabHost.TabContentFactory() {
//            public View createTabContent(String tag) {
//                return findViewById(R.id.container_body);
//            }
//        });
//
//        spec.setIndicator(createTabView(R.drawable.hometab_selector, "Activity","0"));
//        mTabHost.addTab(spec);
//
//        spec = mTabHost.newTabSpec(AlbanianConstants.TAB_3_TAG);
//        spec.setContent(new TabHost.TabContentFactory() {
//            public View createTabContent(String tag) {
//                return findViewById(R.id.container_body);
//            }
//        });
//
//        spec.setIndicator(createTabView(R.drawable.messagestab_selector, "Messages",messageCount));
//        mTabHost.addTab(spec);
//
//        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
//            mTabHost.getTabWidget().getChildAt(i).
//                    setBackgroundColor(Color.parseColor("#e0e0e0"));
//        }
//
//    }


//    /* Comes here when user switch tab, or we do programmatically */
//    TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
//        public void onTabChanged(String tabId) {
//			/* Set current tab.. */
//            mCurrentTab = tabId;
//
//
//            Log.d("sumit","current tab stak= "+mStacks.get(tabId));
//            Log.d("sumit","current tab stak= "+mStacks.get(tabId).size());
//
//            if (mStacks.get(tabId).size() == 0) {
//				/*
//				 * First time this tab is selected. So add first fragment of
//				 * that tab. Dont need animation, so that argument is false. We
//				 * are adding a new fragment which is not present in stack. So
//				 * add to stack is true.
//				 */
//                if (tabId.equals(AlbanianConstants.TAB_1_TAG)) {
//                    pushFragments(tabId, new SearchNewFragment()/*People*/, false, true,null);
//                } else if (tabId.equals(AlbanianConstants.TAB_2_TAG)) {
//                    pushFragments(tabId, new HomeNewFragment(),// Activity
//                            false, true,null);
//                } else if (tabId.equals(AlbanianConstants.TAB_3_TAG)) {
//                    pushFragments(tabId, new MessagesFragment(), false, true,null);
//                }
//            } else {
//				/*
//				 * We are switching tabs, and target tab is already has atleast
//				 * one fragment. No need of animation, no need of stack pushing.
//				 * Just show the target fragment
//				 */
//                pushFragments(tabId, mStacks.get(tabId).lastElement(), false,
//                        false,null);
//            }
//        }
//    };

    /*
     * Might be useful if we want to switch tab programmatically, from inside
     * any of the fragment.
     */
//    public void setCurrentTab(int val) {
//        mTabHost.setCurrentTab(val);
//    }
//
//    public void showCurrentTab() {
//        mTabHost.getTabWidget().setVisibility(View.VISIBLE);
//    }
//
//    public void hideCurrentTab() {
//        mTabHost.getTabWidget().setVisibility(View.GONE);
//    }
//
//    public void refreshTab(String val) {
////        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++)
////        {
////            mTabHost.getTabWidget().getChildAt(i).
////                    setBackgroundColor(Color.parseColor("#e0e0e0"));
////
////
////        }
//
//        TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(R.id.txt_messagescount);
//        RelativeLayout rl_messagesbadge = (RelativeLayout) mTabHost.getTabWidget().getChildAt(2).findViewById(R.id.rl_messagesbadge);
//
//
//        if (val.equals("0")) {
//            rl_messagesbadge.setVisibility(View.GONE);
////            imageView.setVisibility(View.VISIBLE);
//
//        }
//
//        else
//        {
//            rl_messagesbadge.setVisibility(View.VISIBLE);
//
//            tv.setText(val);
//
//        }
//    }

    /*
     * To add fragment to a tab. tag -> Tab identifier fragment -> Fragment to
     * show, in tab identified by tag shouldAnimate -> should animate
     * transaction. false when we switch tabs, or adding first fragment to a tab
     * true when when we are pushing more fragment into navigation stack.
     * shouldAdd -> Should add to fragment navigation stack (mStacks.get(tag)).
     * false when we are switching tabs (except for the first time) true in all
     * other cases.
     */
    public void pushFragments(String tag, Fragment fragment,
                              boolean shouldAnimate, boolean shouldAdd,Bundle bundle) {
        if (shouldAdd)
            mStacks.get(tag).push(fragment);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
         if(shouldAnimate)
         ft.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        ft.replace(R.id.container_body, fragment);
        ft.commit();
    }

    public void popFragments() {
		/*
		 * Select the second last fragment in current tab's stack.. which will
		 * be shown after the fragment transaction given below
		 */
        Fragment fragment = mStacks.get(mCurrentTab).elementAt(
                mStacks.get(mCurrentTab).size() - 2);

		/* pop current fragment from stack.. */
        mStacks.get(mCurrentTab).pop();

		/*
		 * We have the target fragment in hand.. Just show it.. Show a standard
		 * navigation animation
		 */
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        // ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(R.id.container_body, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (((BaseFragment) mStacks.get(mCurrentTab).lastElement())
                .onBackPressed() == false) {
			/*
			 * top fragment in current tab doesn't handles back press, we can do
			 * our thing, which is
			 *
			 * if current tab has only one fragment in stack, ie first fragment
			 * is showing for this tab. finish the activity else pop to previous
			 * fragment in stack for the same tab
			 */
            if (mStacks.get(mCurrentTab).size() == 1) {
                super.onBackPressed(); // or call finish..
            } else {
                popFragments();
            }
        } else {
            // do nothing.. fragment already handled back button press.
        }
    }

    /*
     * Imagine if you wanted to get an image selected using ImagePicker intent
     * to the fragment. Ofcourse I could have created a public function in that
     * fragment, and called it from the activity. But couldn't resist myself.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                this.onPlaceSelected(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                this.onError(status);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);


//            if (requestCode == 1) {
//                if (resultCode == RESULT_OK) {
//                    Place place = PlaceAutocomplete.getPlace(this, data);
//                    Log.i("sumit", "Place: " + place.getName());
//                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                    Status status = PlaceAutocomplete.getStatus(this, data);
//                    // TODO: Handle the error.
//                    Log.i("sumit", status.getStatusMessage());
//
//                } else if (resultCode == RESULT_CANCELED) {
//                    // The user canceled the operation.
//                }
//            }

//        Log.d("sumit","onactivity homenewactivity" +
//                "mStacks.get(mCurrentTab).size()= "+mStacks.get(mCurrentTab).size());
//
//
//        if (mStacks.get(mCurrentTab).size() == 0) {
//
//            return;
//        }
//
//        Log.d("sumit","onactivity homenewactivity" +
//                " mStacks.get(mCurrentTab).lastElement())= "+ mStacks.get(mCurrentTab).lastElement());
//		/* Now current fragment on screen gets onActivityResult callback.. */
//        mStacks.get(mCurrentTab).lastElement()
//                .onActivityResult(requestCode, resultCode, data);
    }

//    private View createTabView(final int id, String string,String messageCount) {
//        View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
//        ImageView imageView = (ImageView) view.findViewById(R.id.img_tabtxt);
//
//        if (string.equals("Local")) {
//            final float scale = getResources().getDisplayMetrics().density;
//            int dpWidthInPx  = (int) (35 * scale);
//            int dpHeightInPx = (int) (42 * scale);
//
//            imageView.getLayoutParams().width = dpWidthInPx;
//            imageView.getLayoutParams().height = dpHeightInPx;
//            imageView.requestLayout();
//
//        }
//        else
//        {
//            final float scale = getResources().getDisplayMetrics().density;
//            int dpWidthInPx  = (int) (26 * scale);
//            int dpHeightInPx = (int) (26 * scale);
//
//            imageView.getLayoutParams().width = dpWidthInPx;
//            imageView.getLayoutParams().height = dpHeightInPx;
//            imageView.requestLayout();
//        }
//
//        TextView txt_messagescount = (TextView) view.findViewById(R.id.txt_messagescount);
//        RelativeLayout rl_messagesbadge = (RelativeLayout) view.findViewById(R.id.rl_messagesbadge);
//
//        if (messageCount.equals("0")) {
//            rl_messagesbadge.setVisibility(View.GONE);
////            imageView.setVisibility(View.VISIBLE);
//
//        }
//
//        else
//        {
//            rl_messagesbadge.setVisibility(View.VISIBLE);
//            txt_messagescount.setText(messageCount);
////            imageView.setVisibility(View.GONE);
//        }
//
//
//        imageView.setImageDrawable(getResources().getDrawable(id));
//        return view;
//    }

    public interface FragmentRefreshListener{
        void onRefresh(String lat,String lng,Place place);
    }

}
