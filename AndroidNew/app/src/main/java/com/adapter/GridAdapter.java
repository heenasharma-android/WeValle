package com.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Sumit on 9/14/2015.
 */
public class GridAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Uri> viewrs;
    private GridHolder holder;

    public GridAdapter(Activity activity, ArrayList<Uri> viewrsList) {
        this.activity = activity;
        this.viewrs = viewrsList;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            holder = (GridHolder) convertView.getTag();
        } else {
            holder = new GridHolder(activity);
            convertView = holder.getConvertView();
            convertView.setTag(holder);
        }
        holder.initializeData(viewrs.get(position));
        holder.applyData();
        return convertView;
    }
}
