package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.OldScreens.WebviewActivity;
import com.albaniancircle.R;
import com.albaniancircle.RoundedCornersTransformationCustom;
import com.holders.GalleryData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private List<GalleryData> galleryData;;
    private Context context;

    public GalleryAdapter(Context context, List<GalleryData> galleryData) {
        this.galleryData = galleryData;
        this.context = context;
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_list, viewGroup, false);
        return new GalleryAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final GalleryAdapter.ViewHolder viewHolder, final int position) {
        final int pos = position;
        viewHolder.tvTitle.setText(galleryData.get(pos).getTitle());
        viewHolder.tvDes.setText(galleryData.get(pos).getType());
        viewHolder.tvView.setText("Views: "+galleryData.get(pos).getViews());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, WebviewActivity.class);
                intent.putExtra("link",galleryData.get(pos).getUrl());
                intent.putExtra("title",galleryData.get(pos).getTitle());
                context.startActivity(intent);
            }
        });

        final int radius = 15;
        final int margin = 3;
        final Transformation transformation = new RoundedCornersTransformationCustom(radius, margin);

        Picasso.with(context).load(galleryData.get(pos).getThumbnail())
                .transform(transformation)
                .into(viewHolder.ivImage);
    }



    @Override
    public int getItemCount() {
        if (galleryData == null)
            return 0;
        else
            return  galleryData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle,tvDes,tvView;
        ImageView ivImage;

        public ViewHolder(View view) {
            super(view);
            tvTitle= (TextView)view.findViewById(R.id.tv_title);
            tvDes= (TextView)view.findViewById(R.id.tv_des);
            tvView= (TextView)view.findViewById(R.id.tv_view);
            ivImage= (ImageView)view.findViewById(R.id.iv_image);
        }
    }


}
