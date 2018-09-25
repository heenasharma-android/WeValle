package com.SigninSignup;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.MainFragments.ReportUserFragment;
import com.MainFragments.SettingsFragment;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.FragmentChangeListener;
import com.albaniancircle.R;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity implements FragmentChangeListener{
    ListView listView;
    SharedPreferences spppp2;
    String send1;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    /*UI declaration........*/
    private View mView;
    //    private ToggleButton goinvisible;
    private TextView blockedlist,deactivateaccount,terms;
//    private TextView privacy,faq;

    private RelativeLayout back;
    //    private TextView min_max_age;
    private RelativeLayout logout;
    private TextView headertext;
    private TextView submitqf;
    private TextView mymembership;

    /* Variables...... */
    LinearLayout ll_header;
    private String tag_whoiviewed_obj = "jsentmessages_req";
    private AlbanianPreferances pref;
    private String str_minage,str_maxage;
    private String CURRENTTABTAG,emailOptIn;
    ScrollView scrollView;
    private Switch emailOptInSwitch;TextView title;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purp));

        if (getIntent().getStringExtra("screen").equalsIgnoreCase("report"))
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, ReportUserFragment.newInstance(getIntent().getStringExtra("userId"))).commit();
        }
        else  {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new SettingsFragment()).commit();
        }

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void replaceFragment(Fragment fragment, String fragmentTitle) {
        FragmentManager fragmentManager = getSupportFragmentManager();;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
//        title.setText(fragmentTitle);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
