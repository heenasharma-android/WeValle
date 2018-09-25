package com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaniancircle.R;

/**
 * Created by Sumit on 25/09/15.
 */
public class LocalRecyclerViewHolder extends RecyclerView.ViewHolder  {
    // View holder for gridview recycler view as we used in listview
    public TextView onlinetext,distance;
    public ImageView userimage;




    public LocalRecyclerViewHolder(View view) {
        super(view);
        // Find all views ids




        this.userimage = (ImageView) view
                .findViewById(R.id.img_search_user);
        this.onlinetext = (TextView) view
                .findViewById(R.id.txt_onlinenow);
        this.distance = (TextView) view
                .findViewById(R.id.txt_distance);

    }



}