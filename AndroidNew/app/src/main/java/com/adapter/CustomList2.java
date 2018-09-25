package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.OldScreens.NewProfileActivity;
import com.albaniancircle.R;
import com.bumptech.glide.Glide;
import com.models.Fdetail;

import java.util.List;

public class CustomList2 extends RecyclerView.Adapter<CustomList2.ItemViewHolder> {

    Context context;
    private List<Fdetail> Flist;
    public CustomList2(Context context, List data) {
        this.Flist=data;
        this.context = context;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_items2, null);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Fdetail detail = Flist.get(position);

        //binding the data with the viewholder views

        // holder.textView.setText(Flist.get(position).getName());

        holder.imageView.setClipToOutline(true);
        Glide.with(context)
                .load(detail.getUimage())
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,NewProfileActivity.class);
                intent.putExtra("userId",detail.getId());
                context.startActivity(intent);
            }
        });

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
            imageView =  (ImageView) itemView.findViewById(R.id.Favimg);

            //    textView=(TextView)itemView.findViewById(R.id.ftxt);
        }
    }
}
