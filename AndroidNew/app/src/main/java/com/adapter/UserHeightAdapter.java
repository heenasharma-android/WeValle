package com.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;


/**
 * Created by Sumit on 9/14/2015.
 */
public class UserHeightAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<String> viewrs;
    private ProfileViewedHolder holder;
    public int selectedPosition = -1;

    public UserHeightAdapter(Activity activity, ArrayList<String> viewrs) {
        this.activity = activity;
        this.viewrs = viewrs;
    }

    @Override
    public int getCount() {
        return viewrs.size();
    }

    @Override
    public Object getItem(int position) {
        return viewrs.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private boolean isCheck = false;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            holder = (ProfileViewedHolder) convertView.getTag();
        } else {
            holder = new ProfileViewedHolder(activity);
            convertView = holder.getConvertView();
            convertView.setTag(holder);
        }
        holder.initializeData(viewrs.get(position));
        holder.applyData();
        if(selectedPosition == position)
            isCheck = true;
        else
            isCheck = false;

        holder.setCheckVisibility(isCheck);
        return convertView;
    }

}
