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
import com.models.PlaceData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {
    private List<PlaceData> placeData;;
    private Context context;

    public PlacesAdapter(Context context, List<PlaceData> placeData) {
        this.placeData = placeData;
        this.context = context;
    }

    @Override
    public PlacesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place_list, viewGroup, false);
        return new PlacesAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final PlacesAdapter.ViewHolder viewHolder, final int position) {
        final int pos = position;
        viewHolder.tvTitle.setText(placeData.get(pos).getTitle());
        viewHolder.tvDes.setText(placeData.get(pos).getDescription());
        viewHolder.tvView.setText(placeData.get(pos).getAddress());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, WebviewActivity.class);
                intent.putExtra("link","http://www.littleitalysoho.co.uk/");
                intent.putExtra("title",placeData.get(pos).getTitle());
                context.startActivity(intent);
            }
        });

        final int radius = 15;
        final int margin = 3;
        final Transformation transformation = new RoundedCornersTransformationCustom(radius, margin);

        Picasso.with(context).load(R.drawable.place)
                .transform(transformation)
                .into(viewHolder.ivImage);
    }



    @Override
    public int getItemCount() {
        if (placeData == null)
            return 0;
        else
            return  placeData.size();
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

