package com.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.MainFragments.HomeFragmentNewActivity;
import com.MainFragments.ProfileFragment;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.R;
import com.albaniancircle.RoundedCornersTransform;
import com.models.RecievedMessageModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


    /**
     * Created by Sumit on 9/14/2015.
     */
    public class MessagesAdapter extends BaseAdapter {


        private final boolean showReadMark;
        /**
         * ********************
         * Adapter class members
         */

        private LayoutInflater inflator;
        final int radius = 5;
        final int margin = 3;
        private Context context;
        public HomeFragmentNewActivity mActivity;

//    private OnClickListener onclick_checkin;
//    private OnClickListener onclick_fav;
//    private OnClickListener onclick_getdirection;

//        DisplayImageOptions options;
//        protected ImageLoader imageLoader = ImageLoader.getInstance();
//        private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

        private ArrayList<RecievedMessageModel> messagesArrayList = new ArrayList<>();

        public MessagesAdapter(Context c,FragmentActivity mAct,
                               ArrayList<RecievedMessageModel> localarray, boolean showReadMark,HomeFragmentNewActivity mActivity)

        {
            this.context = c;
//            this.mAct = mAct;
            this.messagesArrayList = localarray;
            this.showReadMark = showReadMark;
            this.mActivity = mActivity;


//            options = new DisplayImageOptions.Builder()
//                    .displayer(new RoundedBitmapDisplayer(18))
////                    .showStubImage(R.drawable.profile_male_btn)
////                    .showImageForEmptyUri(R.drawable.profile_male_btn)
////                    .showImageOnFail(R.drawable.profile_male_btn)
////                    .cacheInMemory()
////                .cacheOnDisc()
//
//                    .build();


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
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            viewholder holder;

            if (convertView == null) {

                inflator = LayoutInflater.from(context);
                convertView = inflator.inflate(R.layout.recievedmessages_item, null);
                holder = new viewholder();




                holder.userimage = (ImageView) convertView
                        .findViewById(R.id.img_user);

                holder.username = (TextView) convertView
                        .findViewById(R.id.txt_username);

                holder.date = (TextView) convertView
                        .findViewById(R.id.txt_date);

                holder.onlinetext = (ImageView) convertView
                        .findViewById(R.id.txt_onlinenow);
//
                holder.newmsgIcon = (RelativeLayout) convertView
                        .findViewById(R.id.img_msgicon);
//
                holder.converations = (TextView) convertView
                        .findViewById(R.id.txt_conversations);

                convertView.setTag(holder);

            } else
            {
                holder = (viewholder) convertView.getTag();
            }


            if (showReadMark) {

                if (messagesArrayList.get(position).getUnreadMessagesCount().equals("0")) {
                    holder.newmsgIcon.setVisibility(View.GONE);
                    holder.date.setVisibility(View.GONE);
                }
                else
                {
                    holder.newmsgIcon.setVisibility(View.VISIBLE);
                    holder.converations.setText(messagesArrayList.get(position).getUnreadMessagesCount());
                    holder.date.setVisibility(View.VISIBLE);
                }

            }
            else
            {
                holder.newmsgIcon.setVisibility(View.GONE);
            }

            holder.username.setText((messagesArrayList.get(position).getUserName()));
//            holder.date.setText(changeDate(messagesArrayList.get(position).getUserDOB()));
            holder.date.setText("New message!");
            holder.date.setTextColor(Color.parseColor("#ff1a5e"));


            if (messagesArrayList.get(position).getUserPresence().equals("1")) {
                holder.onlinetext.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.onlinetext.setVisibility(View.INVISIBLE);
            }

//        float tempamount=Float.parseFloat(historyArrayList.get(position).getPayment_amount());
//        holder.fare.setText("$"+String.valueOf(round(tempamount,2)));


//        holder.addtofav.setOnClickListener(onclick_fav);
//        holder.addtofav.setTag(position);


//            imageLoader
//                    .displayImage(messagesArrayList.get(position).getUserImageUrl(),
//                            holder.userimage, options, animateFirstListener);



            Picasso.with(context).load(messagesArrayList.get(position).getUserImageUrl()).fit().centerCrop()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
                    .transform(new RoundedCornersTransform(radius,margin))
                    .into(holder.userimage);

            holder.userimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loadProfileFragment(messagesArrayList.get(position).getUserId());

                }
            });







            return convertView;
        }

        public static class viewholder {
            TextView username;
            TextView date;
            TextView converations;
            ImageView userimage;
            RelativeLayout newmsgIcon;
            public ImageView onlinetext;
        }


        public static double round(double value, int places) {
            if (places < 0) throw new IllegalArgumentException();

            long factor = (long) Math.pow(10, places);
            value = value * factor;
            long tmp = Math.round(value);
            return (double) tmp / factor;
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




        private void loadProfileFragment(String userId) {

            Log.d("sumit","rec image url= "+userId);

            Bundle bundle = new Bundle();
            bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, userId);
            bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG, AlbanianConstants.TAB_3_TAG);


            mActivity.pushFragments(AlbanianConstants.TAB_3_TAG,
                    new ProfileFragment(), false, true, bundle);


        }




}
