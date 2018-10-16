package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.OldScreens.NewProfileActivity;
import com.albaniancircle.R;
import com.albaniancircle.RoundedCornersTransformationCustom;
import com.models.FavData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private List<FavData> internationals;
    Context context;

    public FavAdapter(List<FavData> internationals, Context context) {
        this.internationals = internationals;
        this.context=context;
    }


    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item, null);
        FavAdapter.ViewHolder viewHolder = new FavAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavAdapter.ViewHolder viewHolder, int position) {

        final int pos = position;
        final int radius = 15;
        final int margin = 3;
        final Transformation transformation = new RoundedCornersTransformationCustom(radius, margin);

        Picasso.with(context).load(internationals.get(pos).getuImageUrl()).fit().centerCrop()
                .transform(transformation)
                .into(viewHolder.ivImage);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,NewProfileActivity.class);
                intent.putExtra("userId",internationals.get(pos).getuId());
                context.startActivity(intent);
            }
        });

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return internationals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImage;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ivImage = (ImageView) itemLayoutView.findViewById(R.id.iv_image);

        }

    }
}