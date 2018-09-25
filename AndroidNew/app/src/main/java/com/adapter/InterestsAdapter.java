package com.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sumit on 9/14/2015.
 */
public class InterestsAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<String> viewrs;
    private HightHolder holder;
    public int selectedPosition = -1;

    private HashMap<Integer, Boolean> myChecked;

    public InterestsAdapter(Activity activity, ArrayList<String> viewrsList) {
        this.activity = activity;
        this.viewrs = viewrsList;

        myChecked = new HashMap<Integer, Boolean>();

        for(int i = 0; i < viewrs.size(); i++){
            myChecked.put(i, false);

        }
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
            holder = (HightHolder) convertView.getTag();
        } else {
            holder = new HightHolder(activity);
            convertView = holder.getConvertView();
            convertView.setTag(holder);
        }
        holder.initializeData(viewrs.get(position));
        holder.applyData();


        Boolean checked = myChecked.get(position);
//        if (checked != false)
//        {
////		    checkedTextView.setChecked(checked);
//            holder.layout_upper.setBackgroundResource(R.drawable.textbackground);
//        }
//        else
//        {
//            holder.layout_upper.setBackgroundResource(0);
//        }



        holder.setCheckVisibility(checked);
        return convertView;
    }



    public List<String> getCheckedServicesName()
    {
        List<String> checkedItems = new ArrayList<String>();

        for(int i = 0; i < myChecked.size(); i++)
        {
            if (myChecked.get(i))
            {
                (checkedItems).add(viewrs.get(i));
            }

        }

        return checkedItems;
    }


    public void toggleChecked(int position){

        if(myChecked.get(position))
        {
            myChecked.put(position, false);
        }
        else
        {
            myChecked.put(position, true);
        }

//        if (getCheckedServicesName().size()>=5) {
//
//            if(myChecked.get(selectedPosition))
//            {
//                myChecked.put(selectedPosition, false);
//            }
//        }

        selectedPosition=position;

        notifyDataSetChanged();




//        for (int i = 0; i < viewrs.size(); i++) {
//            if(myChecked.get(i))
//            {
//                nextButton.setVisibility(View.VISIBLE);
//                break;
//            }
//            else
//            {
//                nextButton.setVisibility(View.GONE);
//            }
//
//        }

    }
}
