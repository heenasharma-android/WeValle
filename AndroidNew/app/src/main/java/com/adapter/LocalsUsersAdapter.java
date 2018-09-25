package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albaniancircle.R;
import com.albaniancircle.RoundedCornersTransform;
import com.models.UserModel;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by Sumit on 9/14/2015.
 */
public class LocalsUsersAdapter extends RecyclerView.Adapter<LocalRecyclerViewHolder> {

    final int radius = 5;
    final int margin = 3;
    /**
     * ********************
     * Adapter class members
     */

//    private LayoutInflater inflator;
//    private Context context;
//
//
//
////    private OnClickListener onclick_checkin;
////    private OnClickListener onclick_fav;
////    private OnClickListener onclick_getdirection;
//
//    DisplayImageOptions options;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//
//    private ArrayList<UserModel> messagesArrayList = new ArrayList<>();
//
//    public LocalsUsersAdapter(Context c,
//                              ArrayList<UserModel> localarray)
//
//    {
//        this.context = c;
//        this.messagesArrayList = localarray;
//
//
//
//
//        options = new DisplayImageOptions.Builder()
////               .displayer(new RoundedBitmapDisplayer(200))
////                 .showStubImage(R.drawable.profile_male_btn)
////                 .showImageForEmptyUri(R.drawable.profile_male_btn)
////                 .showImageOnFail(R.drawable.profile_male_btn)
//                 .cacheInMemory()
////               .cacheOnDisc()
//
//                .build();
//
//
//    }
//
//    @Override
//    public int getCount() {
//        // TODO Auto-generated method stub
//        if (messagesArrayList.size() == 0) {
//            return 0;
//        } else {
//
//            return messagesArrayList.size();
//
//        }
//
//    }
//
//    @Override
//    public Object getItem(int position) {
//        // TODO Auto-generated method stub
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        // TODO Auto-generated method stub
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // TODO Auto-generated method stub
//
//
//
//        View v;
//        if (convertView == null) {  // if it's not recycled, initialize some attributes
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
//                    Context.LAYOUT_INFLATER_SERVICE );
//            v = inflater.inflate(R.layout.grid_local_item, parent, false);
//        } else {
//            v = (View) convertView;
//        }
//
//
//        ImageView userimage = (ImageView) v
//                .findViewById(R.id.img_search_user);
//
//        TextView onlinetext = (TextView) v
//                .findViewById(R.id.txt_onlinenow);
//
//        TextView distance = (TextView) v
//                .findViewById(R.id.txt_distance);
//
//
//        //        holder.username.setText(changeDate(messagesArrayList.get(position).getUserName()));
////        holder.date.setText(changeDate(messagesArrayList.get(position).getUserDOB()));
//
////        holder.addtofav.setOnClickListener(onclick_fav);
////        holder.addtofav.setTag(position);
//        DecimalFormat precision = new DecimalFormat("0.00");
//
//        distance.setText(precision.format(Double.valueOf(messagesArrayList.get(position).getRowDistance() ))
//                +" "+messagesArrayList.get(position).getUnit());
//
//        if (messagesArrayList.get(position).getUserPresence().equals("1")) {
//            onlinetext.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            onlinetext.setVisibility(View.INVISIBLE);
//        }
//
//        {
//
//            imageLoader
//                    .displayImage(messagesArrayList.get(position).getUserImageUrl(),
//                            userimage, options, animateFirstListener);
//
//        }
//
//
//
//
//        return v;






        ///////////////
//        viewholder holder;
//
//        if (convertView == null) {
//
//            inflator = LayoutInflater.from(context);
//            convertView = inflator.inflate(R.layout.grid_local_item, null);
//            holder = new viewholder();




//            holder.userimage = (ImageView) convertView
//                    .findViewById(R.id.img_search_user);
//
//            holder.onlinetext = (TextView) convertView
//                    .findViewById(R.id.txt_onlinenow);
//
//            holder.distance = (TextView) convertView
//                    .findViewById(R.id.txt_distance);


//
//                holder.quote = (TextView) convertView
//                        .findViewById(R.id.txt_quote_user);

//            convertView.setTag(holder);

//        } else
//        {
//            holder = (viewholder) convertView.getTag();
//        }




////        holder.username.setText(changeDate(messagesArrayList.get(position).getUserName()));
////        holder.date.setText(changeDate(messagesArrayList.get(position).getUserDOB()));
//
////        holder.addtofav.setOnClickListener(onclick_fav);
////        holder.addtofav.setTag(position);
//        DecimalFormat precision = new DecimalFormat("0.00");
//
//        holder.distance.setText(precision.format(Double.valueOf(messagesArrayList.get(position).getRowDistance() ))
//             +" "+messagesArrayList.get(position).getUnit());
//
//        if (messagesArrayList.get(position).getUserPresence().equals("1")) {
//            holder.onlinetext.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            holder.onlinetext.setVisibility(View.INVISIBLE);
//        }
//
//        {
//
//            imageLoader
//                    .displayImage(messagesArrayList.get(position).getUserImageUrl(),
//                            holder.userimage, options, animateFirstListener);
//
//        }

//        return convertView;
//    }

//    public static class viewholder {
//
//        ImageView userimage;
//        TextView onlinetext,distance;
//
//
//    }

/////////////////////////////



    /**
     * ********************
     * Adapter class members
     */

    private LayoutInflater inflator;
    private Context context;

//    private OnClickListener onclick_checkin;
//    private OnClickListener onclick_fav;
//    private OnClickListener onclick_getdirection;

//    DisplayImageOptions options;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private ArrayList<UserModel> messagesArrayList = new ArrayList<>();

    public LocalsUsersAdapter(Context c,
                                ArrayList<UserModel> localarray)

    {
        this.context = c;
        this.messagesArrayList = localarray;


//        options = new DisplayImageOptions.Builder()
//                .displayer(new RoundedBitmapDisplayer(200))
//                .cacheInMemory()
//                .cacheOnDisc()
//
//                .build();


    }







    @Override
    public int getItemCount() {
        return (null != messagesArrayList ? messagesArrayList.size() : 0);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(LocalRecyclerViewHolder holder, int position) {
//        final UserModel model = UserModel.get(position);

        LocalRecyclerViewHolder mainHolder = (LocalRecyclerViewHolder) holder;// holder

//        imageLoader
//                .displayImage(messagesArrayList.get(position).getUserImageUrl(),
//                        holder.userimage, options, animateFirstListener);

        Picasso.with(context).load(messagesArrayList.get(position).getUserImageUrl()).fit().centerCrop()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
                .transform(new RoundedCornersTransform(radius,margin))
                .into(holder.userimage);

        if (messagesArrayList.get(position).getUserPresence().equals("1")) {
            holder.onlinetext.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.onlinetext.setVisibility(View.INVISIBLE);
        }

        DecimalFormat precision = new DecimalFormat("0");

        holder.distance.setText(precision.format(Double.valueOf(messagesArrayList.get(position).getRowDistance() ))
                +" "+messagesArrayList.get(position).getUnit());


    }

    @Override
    public LocalRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.grid_local_item, viewGroup, false);
        LocalRecyclerViewHolder listHolder = new LocalRecyclerViewHolder(mainGroup);
        return listHolder;

    }






}
