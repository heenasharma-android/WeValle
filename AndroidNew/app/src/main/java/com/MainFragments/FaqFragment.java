package com.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;

/**
 * Created by Sumit on 24/10/2015.
 */
public class FaqFragment extends Fragment implements View.OnClickListener {


    /**
     * UI..
     */

    private View mView;
    private WebView mWebview;
    private RelativeLayout Back;
    private TextView header;

    /**
     * Variables..
     */
    private AlbanianPreferances pref;
    private String pagestring;
//	private RelativeLayout header;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        pref = new AlbanianPreferances(getActivity().getApplicationContext());

    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();



    }


    @Override
    public void onResume() {
        super.onResume();
        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("Faq Screen");

       // mActivity.hideCurrentTab();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.localwebview, container, false);


        initview(mView);

        return mView;
    }

    private void initview(View mView) {
        // TODO Auto-generated method stub
        Back=(RelativeLayout)mView.findViewById(R.id.rl_back_header);
        Back.setOnClickListener(this);

        header=(TextView)mView.findViewById(R.id.txt_headerlogo);
//		header.setOnClickListener(this);


        mWebview=(WebView)mView.findViewById(R.id.webview_help);
        mWebview.setWebViewClient(new MyBrowser());

        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setBuiltInZoomControls(true);
//		mWebview.getSettings().setPluginsEnabled(true);
        mWebview.getSettings().setUseWideViewPort(true);

        //pagestring=getArguments().getString(AlbanianConstants.EXTRA_LOCALPAGENAME);
        pagestring="TOS";

        if(pagestring.equals(AlbanianConstants.EXTRA_TOS))
        {
            header.setText("TOS");
            mWebview.loadUrl("http://wevalle.com/terms/");


        }else if(pagestring.equals(AlbanianConstants.EXTRA_PRIVACY))
        {
            header.setText("Privacy policy");
            mWebview.loadUrl("file:///android_asset/privacypolicy.html");


        }
        else if(pagestring.equals(AlbanianConstants.EXTRA_WEVALLE))
        {
            header.setText("We are rebranding");
            mWebview.loadUrl("http://wevalle.com");


        }
        else
        {
//            header.setText("FAQ");
//            mWebview.loadUrl("file:///android_asset/faq.html");

        }


    }


    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();

    }



    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.rl_back_header:

                   getActivity().onBackPressed();

                break;

            default:
                break;
        }
    }





}
