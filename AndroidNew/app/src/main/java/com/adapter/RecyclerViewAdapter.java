package com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.albaniancircle.R;
import com.albaniancircle.RoundedCornersTransform;
import com.models.EventModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sumit on 18-Feb-18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView> {

    private List<EventModel> list;

//    DisplayImageOptions options;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public class MyView extends RecyclerView.ViewHolder {

        public ImageView eventImage;

        public MyView(View view) {
            super(view);

            eventImage = (ImageView) view.findViewById(R.id.textview1);

        }
    }


    public RecyclerViewAdapter(List<EventModel> horizontalList) {
        this.list = horizontalList;

//        options = new DisplayImageOptions.Builder()
//                .displayer(new RoundedBitmapDisplayer(14))
////                .showStubImage(R.drawable.profile_male_btn)
////                .showImageForEmptyUri(R.drawable.profile_male_btn)
////                .showImageOnFail(R.drawable.profile_male_btn)
//
//                .cacheInMemory()
//                .cacheOnDisc()
//
//                .build();
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item, parent, false);

        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

//        imageLoader
//                .displayImage(list.get(position).getEventImageUrl(),
//                        holder.eventImage, options, animateFirstListener);

//        Picasso.with().load(list.get(position).getEventImageUrl()).fit().centerCrop()
////                    .placeholder(R.drawable.user_placeholder)
////                    .error(R.drawable.user_placeholder_error)
//                .transform(new RoundedCornersTransform())
//                .into(holder.eventImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
