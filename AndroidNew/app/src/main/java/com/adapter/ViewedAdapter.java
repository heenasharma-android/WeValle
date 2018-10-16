package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.OldScreens.NewProfileActivity;
import com.albaniancircle.R;
import com.albaniancircle.RoundedCornersTransformationCustom;
import com.models.ViewData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class ViewedAdapter extends RecyclerView.Adapter<ViewedAdapter.ViewHolder> {

    private List<ViewData> viewData;
    Context context;
    String userId;

    public ViewedAdapter(List<ViewData> viewData, Context context,String userId) {
        this.viewData = viewData;
        this.context=context;
        this.userId=userId;
    }


    @Override
    public ViewedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, null);
        ViewedAdapter.ViewHolder viewHolder = new ViewedAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewedAdapter.ViewHolder viewHolder, int position) {
        final int pos = position;
        final int radius = 15;
        final int margin = 3;
        final Transformation transformation = new RoundedCornersTransformationCustom(radius, margin);
        Picasso.with(context).load(viewData.get(pos).getuImageUrl()).fit().centerCrop()
                .transform(transformation)
                .into(viewHolder.imageView);

        if (viewData.get(pos).getuPresence().equalsIgnoreCase("1"))
        {
            viewHolder.online.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.online.setVisibility(View.GONE);
        }

        viewHolder.tvName.setText(viewData.get(pos).getuName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,NewProfileActivity.class);
                intent.putExtra("Seid",userId);
                intent.putExtra("userId",viewData.get(pos).getuId());
                context.startActivity(intent);
            }
        });


    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return viewData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName,tvLoc;
        public ImageView online,imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.img);
            tvName=(TextView)view.findViewById(R.id.txt);
            tvLoc=(TextView)view.findViewById(R.id.loc);
            online = (ImageView) view.findViewById(R.id.online);


        }

    }
}
