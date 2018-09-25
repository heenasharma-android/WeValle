package com.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.albaniancircle.R;

/**
 * Created by Sumit on 9/14/2015.
 */
public class ProfileViewedHolder implements View.OnClickListener {

    private View mView;
    private ProfileViewedHolder holder;
    private Activity mActivity;
    private String viewers;
    private LayoutInflater mLayoutInflater;
    private TextView txtSourceHistory;
    private LinearLayout llListItem;
    private ImageView ivCheck;

    public ProfileViewedHolder(Activity activity) {
        this.mActivity = activity;
    }

    public View getConvertView() {
        mView = mActivity.getLayoutInflater().inflate(R.layout.viewedme_list_item, null);
        txtSourceHistory = (TextView) mView.findViewById(R.id.txt_source_history);
        llListItem = (LinearLayout) mView.findViewById(R.id.ll_list_item);
        ivCheck = (ImageView) mView.findViewById(R.id.iv_test);
        return mView;
    }

    public void applyData() {
        txtSourceHistory.setText(viewers.toString());
    }
    public void initializeData(Object data) {
        viewers = (String) data;
    }

    @Override
    public void onClick(View view) {

    }

    public void setCheckVisibility(boolean isCheck) {
        if(isCheck)
            ivCheck.setVisibility(View.VISIBLE);
        else
            ivCheck.setVisibility(View.INVISIBLE);
    }
}
