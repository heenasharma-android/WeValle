package com.holders;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.albaniancircle.R;

/**
 * Created by Sumit on 9/14/2015.
 */
public class ProfileViewedHolder {

    private View mView;
    private ProfileViewedHolder hr;
    private Activity mActivity;
    private String viewers;
    private LayoutInflater mLayoutInflater;
    private TextView txtSourceHistory;

    public ProfileViewedHolder(Activity activity) {
        this.mActivity = activity;
    }


    public View getConvertView() {
        mView = mActivity.getLayoutInflater().inflate(R.layout.recievedmessages_item, null);
        txtSourceHistory = (TextView) mView.findViewById(R.id.txt_source_history);
        return mView;
    }

    public void applyData() {

        txtSourceHistory.setText(viewers.toString());
    }

    public void initializeData(Object data) {
        viewers = (String) data;
    }
}
