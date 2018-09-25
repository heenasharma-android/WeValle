package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaniancircle.CircleTransform;
import com.albaniancircle.R;
import com.albaniancircle.RoundedCornersTransform;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.models.Fdetail;
import com.models.UserImagesModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sumit on 9/14/2015.
 */
public class UserImagesGalleryAdapter extends RecyclerView.Adapter<UserImagesGalleryAdapter.ItemViewHolder> {

    Context context;
    int radius=20;
    int margin=1;
    List<UserImagesModel> userImagesModels;
    public UserImagesGalleryAdapter(Context context, List<UserImagesModel> userImagesModels) {
        this.userImagesModels=userImagesModels;
        this.context = context;
    }
    @Override
    public UserImagesGalleryAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.profile_gallery_item, null);
        return new UserImagesGalleryAdapter.ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(UserImagesGalleryAdapter.ItemViewHolder holder, int position) {
        UserImagesModel detail = userImagesModels.get(position);

        //binding the data with the viewholder views

        // holder.textView.setText(Flist.get(position).getName());

//        holder.imageView.setClipToOutline(true);
//        Glide.with(context)
//                .load(detail.getUserImageUrl())
//                .into(holder.imageView);
        Picasso.with(context).load(detail.getUserImageUrl()).fit().centerCrop().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return userImagesModels.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CircularImageView imageView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView =  (CircularImageView) itemView.findViewById(R.id.img_online_user);
        }
    }
}