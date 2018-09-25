package com.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.albaniancircle.R;

/**
 * Created by Sumit on 9/14/2015.
 */
public class HightHolder implements View.OnClickListener {

    private View mView;
    private HightHolder holder;
    private Activity mActivity;
    private String viewers;
    private LayoutInflater mLayoutInflater;
    private TextView txtSourceHistory;
    private int position;
    private CheckBox checkBox;
    private LinearLayout llListItem;
    private ImageView ivCheck;

    public HightHolder(Activity activity) {
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

        ivCheck.setVisibility(View.VISIBLE);

    }

    public void setCheckVisibility(boolean isCheck) {
        if (isCheck)
            ivCheck.setVisibility(View.VISIBLE);
        else
            ivCheck.setVisibility(View.INVISIBLE);
    }
}
