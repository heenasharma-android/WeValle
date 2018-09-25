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
import com.albaniancircle.RoundedCornersTransform;
import com.models.Heritage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class HeritageAdapter extends RecyclerView.Adapter<HeritageAdapter.ViewHolder> {

    private List<Heritage> heritageList;
    Context context;
    String tag;

    public HeritageAdapter(List<Heritage> heritageList, Context context, String tag) {
        this.heritageList = heritageList;
        this.context=context;
        this.tag=tag;
    }

    // Create new views
    @Override
    public HeritageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.heritage_item_new, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.tvName.setText(heritageList.get(position).getHeritageName());
        final int radius = 5;
        final int margin = 3;
        if (tag.equalsIgnoreCase("heritage")){
            viewHolder.line.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.line.setVisibility(View.GONE);
        }
        final Transformation transformation = new RoundedCornersTransform(radius, margin);

        Picasso.with(context).load(heritageList.get(position).getFlag()).fit().centerCrop()
                .transform(transformation)
                .into(viewHolder.img_flag);


        viewHolder.chkSelected.setChecked(heritageList.get(position).isInterested());

        viewHolder.chkSelected.setTag(heritageList.get(position));


        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Heritage heritageModel = (Heritage) cb.getTag();

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
        View line;

        public Heritage singlestudent;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = (TextView) itemLayoutView.findViewById(R.id.tvHeritage);
            img_flag = (ImageView) itemLayoutView.findViewById(R.id.ivFlag);
            chkSelected = (CheckBox) itemLayoutView.findViewById(R.id.cbHeritage);
            line=(View)itemLayoutView.findViewById(R.id.v1);

        }

    }

    // method to access in activity after updating selection
    public List<Heritage> getHeritageList() {
        return heritageList;
    }

}
