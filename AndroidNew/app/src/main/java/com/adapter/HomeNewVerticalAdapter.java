package com.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MainFragments.HomeNewFragment;
import com.albaniancircle.R;
import com.models.UserModel;

import java.util.ArrayList;

/**
 * Created by Sumit on 01-Aug-17.
 */

public class HomeNewVerticalAdapter extends RecyclerView.Adapter<HomeNewVerticalAdapter.SimpleViewHolder>{

    private final Context mContext;
    private static ArrayList<ArrayList<UserModel>> mData;
    private static RecyclerView horizontalList;
    private static HomeNewFragment.UserClick userClick;


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView nodata;
        private HomeNewHorizontalRVAdapter horizontalAdapter;

        public SimpleViewHolder(View view) {
            super(view);
            Context context = itemView.getContext();
            title = (TextView) view.findViewById(R.id.txt_item_name_type);
            nodata = (TextView) view.findViewById(R.id.txt_no_data);
            horizontalList = (RecyclerView) itemView.findViewById(R.id.horizontal_list);

            horizontalAdapter = new HomeNewHorizontalRVAdapter();
            horizontalList.setAdapter(horizontalAdapter);

            final RecyclerItemClickListener.OnItemClickListener listener=new RecyclerItemClickListener.
                    OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

//                    loadProfile(onlineuser_arraylist.get(position).getUserId());

                    userClick.onImageClick(position);


                }


            };

            horizontalList.addOnItemTouchListener(
                    new RecyclerItemClickListener(context, listener));
        }
    }

    public HomeNewVerticalAdapter(Context context, ArrayList<ArrayList<UserModel>> data,
                                  HomeNewFragment.UserClick userClick) {
        mContext = context;

        this.userClick=userClick;

        if (data != null)
            this.mData = (data);
        else mData = new ArrayList<>();
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.homenew_verticalitem, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {


        if (mData.get(position).size()>0) {
            Log.d("sumit","hometype- "+mData.get(position).get(0).getUsertypeHomePage());

            holder.title.setText((mData.get(position).get(0).getUsertypeHomePage()).toUpperCase());

            if (mData.get(position).get(0).getUserId().equals("0")) {

                horizontalList.setVisibility(View.GONE);
                holder.nodata.setVisibility(View.VISIBLE);
                if (mData.get(position).get(0).getUsertypeHomePage().equals("Viewed you"))
                {
                    holder.nodata.setText("No one has viewed your profile yet");
                }
                else if(mData.get(position).get(0).getUsertypeHomePage().equals("Your Favourites"))
                {
                    holder.nodata.setText("You haven’t added anyone to your favorites yet");
                }
                else
                {
                    holder.nodata.setText("");
                }

            }
            else {

//                if (position == 1)
                {

                    horizontalList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                }
//                else {
//                    horizontalList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
//                }

                holder.horizontalAdapter.setData(mData.get(position)); // List of Strings
                holder.horizontalAdapter.setRowIndex(position);
            }


        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
