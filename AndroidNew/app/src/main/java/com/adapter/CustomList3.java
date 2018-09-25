package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaniancircle.R;
import com.bumptech.glide.Glide;
import com.models.Mprofile;

import java.util.List;

public class CustomList3 extends RecyclerView.Adapter<CustomList3.ItemViewHolder> {

    Context context;
    private List<Mprofile> Flist;
    public CustomList3(Context context, List data) {
        this.Flist=data;
        this.context = context;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_items3, null);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Mprofile detail = Flist.get(position);

        //binding the data with the viewholder views

        // holder.textView.setText(Flist.get(position).getName());

        holder.imageView.setClipToOutline(true);
        Glide.with(context)
                .load(detail.getUserImages())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return Flist.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView =  (ImageView) itemView.findViewById(R.id.F2avimg);
        }
    }
}