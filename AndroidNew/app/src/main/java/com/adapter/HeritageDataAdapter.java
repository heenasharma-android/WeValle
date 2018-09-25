package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaniancircle.R;
import com.albaniancircle.RoundedCornersTransformationCustom;
import com.models.Heritage;
import com.models.HeritageModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by Sumit on 04-Feb-18.
 */

public class HeritageDataAdapter extends
        RecyclerView.Adapter<HeritageDataAdapter.ViewHolder> {

    private List<Heritage> heritageList;
    Context context;

//    DisplayImageOptions options;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public HeritageDataAdapter(List<Heritage> heritageList, Context context) {
        this.heritageList = heritageList;
        this.context=context;

//        options = new DisplayImageOptions.Builder()
//                .displayer(new RoundedBitmapDisplayer(1))
////                    .showStubImage(R.drawable.profile_male_btn)
////                    .showImageForEmptyUri(R.drawable.profile_male_btn)
////                    .showImageOnFail(R.drawable.profile_male_btn)
//                .cacheInMemory()
//                .cacheOnDisc()
//
//                .build();

    }

    // Create new views
    @Override
    public HeritageDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.heritage_item, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.tvName.setText(heritageList.get(position).getHeritageName());

//        imageLoader
//                .displayImage(heritageList.get(position).getFlag(),
//                        viewHolder.img_flag, options, animateFirstListener);

        final int radius = 5;
        final int margin = 3;
        final Transformation transformation = new RoundedCornersTransformationCustom(radius, margin);

        Picasso.with(context).load(heritageList.get(position).getFlag()).fit().centerCrop()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
                .transform(transformation)
                .into(viewHolder.img_flag);


        viewHolder.chkSelected.setChecked(heritageList.get(position).isInterested());

        viewHolder.chkSelected.setTag(heritageList.get(position));


        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                HeritageModel heritageModel = (HeritageModel) cb.getTag();

                heritageModel.setInterested(cb.isChecked());
                heritageList.get(pos).setInterested(cb.isChecked());

//                Toast.makeText(
//                        v.getContext(),
//                        "Clicked on Checkbox: " + cb.getText() + " is "
//                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return heritageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public ImageView img_flag;
        public CheckBox chkSelected;

        public HeritageModel singlestudent;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = (TextView) itemLayoutView.findViewById(R.id.tvName);
            img_flag = (ImageView) itemLayoutView.findViewById(R.id.img_flag);


            chkSelected = (CheckBox) itemLayoutView
                    .findViewById(R.id.chkSelected);

        }

    }

    // method to access in activity after updating selection
    public List<Heritage> getHeritageList() {
        return heritageList;
    }

}
