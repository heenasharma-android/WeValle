package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.albaniancircle.R;
import com.albaniancircle.RoundedCornersTransform;
import com.models.UserImagesModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Sumit on 9/14/2015.
 */
public class UserImagesAdapter extends BaseAdapter {


    /**
     * ********************
     * Adapter class members
     */

    private LayoutInflater inflator;
    private Context context;

    private String profileVisitedID;
    private String userId;
    final int radius = 5;
    final int margin = 3;

//    private OnClickListener onclick_checkin;
//    private OnClickListener onclick_fav;
//    private OnClickListener onclick_getdirection;

//    DisplayImageOptions options;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private ArrayList<UserImagesModel> messagesArrayList = new ArrayList<>();

    public UserImagesAdapter(Context c,
                             ArrayList<UserImagesModel> localarray,
                             String profileVisitedID, String userId)

    {
        this.context = c;
        this.messagesArrayList = localarray;

        this.userId = userId;
        this.profileVisitedID = profileVisitedID;


//        options = new DisplayImageOptions.Builder()
////               .displayer(new RoundedBitmapDisplayer(200))
////                 .showStubImage(R.drawable.profile_male_btn)
////                 .showImageForEmptyUri(R.drawable.profile_male_btn)
////                 .showImageOnFail(R.drawable.profile_male_btn)
//                 .cacheInMemory()
////               .cacheOnDisc()
//
//                .build();


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (messagesArrayList.size() == 0) {
            return 0;
        } else {

            return messagesArrayList.size();

        }

    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        viewholder holder;

        if (convertView == null) {

            inflator = LayoutInflater.from(context);
            convertView = inflator.inflate(R.layout.home_gallery_item, null);
            holder = new viewholder();




            holder.userimage = (ImageView) convertView
                    .findViewById(R.id.img_online_user);


//
                holder.progressBar = (ProgressBar) convertView
                        .findViewById(R.id.progressBar);

            convertView.setTag(holder);

        } else
        {
            holder = (viewholder) convertView.getTag();
        }




//        holder.username.setText(changeDate(messagesArrayList.get(position).getUserName()));
//        holder.date.setText(changeDate(messagesArrayList.get(position).getUserDOB()));

//        holder.addtofav.setOnClickListener(onclick_fav);
//        holder.addtofav.setTag(position);

        if (profileVisitedID.equals(userId))

        {

            if (position == 0)
            {

                holder.userimage.setImageResource(R.drawable.newimage);
                holder.progressBar.setVisibility(View.GONE);
            }
            else
            {

//                imageLoader
//                        .displayImage(messagesArrayList.get(position).getUserImageUrl(),
//                                holder.userimage, options, animateFirstListener);

                Picasso.with(context).load(messagesArrayList.get(position).getUserImageUrl()).fit().centerCrop()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
                        .transform(new RoundedCornersTransform(radius,margin))
                        .into(holder.userimage);

            }
        }
        else
        {

//            imageLoader
//                    .displayImage(messagesArrayList.get(position).getUserImageUrl(),
//                            holder.userimage, options, animateFirstListener);

            Picasso.with(context).load(messagesArrayList.get(position).getUserImageUrl()).fit().centerCrop()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
                    .transform(new RoundedCornersTransform(radius,margin))
                    .into(holder.userimage);


        }



        if (messagesArrayList.get(position).getIsUserProfileImage().equals("1")) {

              final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.userimage.setBackgroundDrawable( context.getResources()
                        .getDrawable(R.drawable.grid_image_bg) );
            } else {
                holder.userimage.setBackground( context.getResources().
                        getDrawable(R.drawable.grid_image_bg));
            }
        }








        return convertView;
    }

    public static class viewholder {

        ImageView userimage;
        ProgressBar progressBar;

    }




    public String changeDate(String strDate) {
        try {

            DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = inputDateFormat.parse(strDate);//"2014-12-31 19:30:00"

            DateFormat outputDateFormat = new SimpleDateFormat("MMMM dd,yyyy  hh:mm");
            String outputDate = outputDateFormat.format(date);

            return outputDate;
        } catch (Exception e) {
            e.printStackTrace();
            return strDate;
        }
    }

    public static class ButtonClick {

        public int position;

    }





}
