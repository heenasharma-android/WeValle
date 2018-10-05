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
import com.holders.GalleryData;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(final GalleryAdapter.ViewHolder viewHolder, final int i) {
        final GalleryData galleryData1 =galleryData.get(i);
        viewHolder.tvTitle.setText(galleryData1.getTitle());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, WebviewActivity.class);
                intent.putExtra("link","https://www.youtube.com/watch?v=lY6-ryYcuRM");
                context.startActivity(intent);
            }
        });
        Picasso.with(context).load(galleryData1.getImage()).into(viewHolder.imageView);
    }



    @Override
    public int getItemCount() {
        if (galleryData == null)
            return 0;
        else
            return  galleryData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            tvTitle= (TextView)view.findViewById(R.id.tv_title);
            imageView= (ImageView)view.findViewById(R.id.iv_image);
        }
    }


}
