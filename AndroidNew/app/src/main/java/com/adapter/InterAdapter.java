package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaniancircle.R;
import com.albaniancircle.RoundedCornersTransformationCustom;
import com.models.International;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class InterAdapter extends RecyclerView.Adapter<InterAdapter.ViewHolder> {

    private List<International> internationals;
    Context context;

    public InterAdapter(List<International> internationals, Context context) {
        this.internationals = internationals;
        this.context=context;
    }


    @Override
    public InterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_item, null);
        InterAdapter.ViewHolder viewHolder = new InterAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InterAdapter.ViewHolder viewHolder, int position) {

        final int pos = position;
        viewHolder.tvUdes.setText(internationals.get(pos).getName());
        viewHolder.tvUbatch.setText(internationals.get(pos).getCategory());
        viewHolder.tvUadd.setText(internationals.get(pos).getAddress());
        final int radius = 10;
        final int margin = 3;
        final Transformation transformation = new RoundedCornersTransformationCustom(radius, margin);

        Picasso.with(context).load(internationals.get(pos).getImageUrl()).fit().centerCrop()
                .transform(transformation)
                .into(viewHolder.ivImage);

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return internationals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvUdes,tvUbatch,tvUadd;
        public ImageView ivImage;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvUdes = (TextView) itemLayoutView.findViewById(R.id.tv_user_des);
            ivImage = (ImageView) itemLayoutView.findViewById(R.id.iv_image);
            tvUbatch= (TextView) itemLayoutView.findViewById(R.id.tv_ubatch);
            tvUadd = (TextView) itemLayoutView.findViewById(R.id.tv_uadd);


        }

    }
}
