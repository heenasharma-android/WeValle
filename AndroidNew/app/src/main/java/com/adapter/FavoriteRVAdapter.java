package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.albaniancircle.R;
import com.albaniancircle.RoundedCornersTransform;
import com.models.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sumit on 01-Aug-17.
 */

public class FavoriteRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<UserModel> mDataList;
    private int mRowIndex = -1;
    Context context;

    public static int horizontalPosition;

//    DisplayImageOptions options;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public FavoriteRVAdapter() {


//        options = new DisplayImageOptions.Builder()
//                .displayer(new RoundedBitmapDisplayer(18))
////                .showStubImage(R.drawable.profile_male_btn)
////                .showImageForEmptyUri(R.drawable.profile_male_btn)
////                .showImageOnFail(R.drawable.profile_male_btn)
//
//                .cacheInMemory()
//                .cacheOnDisc()
//
//                .build();
    }

    public void setData(ArrayList<UserModel> data) {
        if (mDataList != data) {
            mDataList = data;
            notifyDataSetChanged();
        }
    }

    public void setRowIndex(int index) {
        mRowIndex = index;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView userImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            userImage = (ImageView) itemView.findViewById(R.id.img_home_horizontal);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.favorite_horizontalitem, parent, false);
        ItemViewHolder holder = new ItemViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder rawHolder, final int position) {
        ItemViewHolder holder = (ItemViewHolder) rawHolder;
        final int radius = 5;
        final int margin = 3;
//        holder.userImage.setText(mDataList.get(position));
//        imageLoader
//                .displayImage(mDataList.get(position).getUserImageUrl(),
//                        holder.userImage, options, animateFirstListener);

        Picasso.with(context).load(mDataList.get(position).getUserImageUrl()).fit().centerCrop()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
                .transform(new RoundedCornersTransform(radius,margin))
                .into(holder.userImage);


        holder.itemView.setTag(position);




        holder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                horizontalPosition=position;
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

}