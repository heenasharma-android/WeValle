package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.OldScreens.OldChatActivity;
import com.albaniancircle.R;
import com.albaniancircle.RoundedCornersTransformationCustom;
import com.models.ChatData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class ChatDataAdapter extends RecyclerView.Adapter<ChatDataAdapter.ViewHolder> {

    private List<ChatData> chatDataList;
    Context context;

    public ChatDataAdapter(List<ChatData> chatDataList, Context context) {
        this.chatDataList = chatDataList;
        this.context=context;
    }


    @Override
    public ChatDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, null);
        ChatDataAdapter.ViewHolder viewHolder = new ChatDataAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatDataAdapter.ViewHolder viewHolder, int position) {

        final int pos = position;
        final int radius = 15;
        final int margin = 3;
        final Transformation transformation = new RoundedCornersTransformationCustom(radius, margin);

        Picasso.with(context).load(chatDataList.get(pos).getuImageUrl()).fit().centerCrop()
                .transform(transformation)
                .into(viewHolder.ivImage);
        if (chatDataList.get(pos).getuPresence().equalsIgnoreCase("1"))
        {
            viewHolder.ivOnline.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.ivOnline.setVisibility(View.GONE);
        }

        viewHolder.tvName.setText(chatDataList.get(pos).getuName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,OldChatActivity.class);
                intent.putExtra("id",chatDataList.get(pos).getuId());
                intent.putExtra("name",chatDataList.get(pos).getuName());
                intent.putExtra("image",chatDataList.get(pos).getuImageUrl());
                context.startActivity(intent);
            }
        });

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return chatDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName,tvLoc;
        public ImageView ivImage,ivOnline;

        public ViewHolder(View view) {
            super(view);
            ivImage = (ImageView) view.findViewById(R.id.img);
            tvName=(TextView)view.findViewById(R.id.txt);
            tvLoc=(TextView)view.findViewById(R.id.loc);
            ivOnline = (ImageView) view.findViewById(R.id.online);


        }

    }
}