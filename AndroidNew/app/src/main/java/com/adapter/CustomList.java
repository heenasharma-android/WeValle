package com.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaniancircle.R;
import com.bumptech.glide.Glide;
import com.models.Detail;

import java.util.List;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private List<Detail> data;
    public CustomList(Activity context, List data) {
        super(context, R.layout.list_items, data);
        this.context = context;
        this.data=data;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_items, null, true);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView textView=(TextView)rowView.findViewById(R.id.txt);
        TextView textView1=(TextView)rowView.findViewById(R.id.loc);
        ImageView online = (ImageView) rowView.findViewById(R.id.online);
        if (data.get(position).getVpresence().equalsIgnoreCase("1"))
        {
            online.setVisibility(View.VISIBLE);
        }
        else {
            online.setVisibility(View.GONE);
        }


        // ImageView imageView1=(ImageView)rowView.findViewById(R.id.Favimg);
        // imageView.setImageResource(imageId.get(position));

        imageView.setClipToOutline(true);
        //   Picasso.with(context).load(furls.get(position)).into(imageView1);

        Glide.with(context)
                .load(data.get(position).getVimage())
                .into(imageView);
        textView.setText(data.get(position).getVame());
        textView1.setText(data.get(position).getVlocation());


        return rowView;
    }


}
