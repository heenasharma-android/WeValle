package com.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.CircleTransform;
import com.albaniancircle.R;
import com.models.ChatMessageModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Sumit on 9/14/2015.
 */
public class ChattingAdapter extends BaseAdapter {


    private final AlbanianPreferances pref;
    /**
     * ********************
     * Adapter class members
     */

    private LayoutInflater inflator;
    private Context context;
    private String profileUserImage;

//    private OnClickListener onclick_checkin;
    private View.OnClickListener onclick_image;
//    private OnClickListener onclick_getdirection;

//    DisplayImageOptions options;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private ArrayList<ChatMessageModel> messagesArrayList = new ArrayList<>();

    public ChattingAdapter(Context c,
                           ArrayList<ChatMessageModel> localarray, String profileUserModel, View.OnClickListener onclick_image)

    {
        this.context = c;
        this.messagesArrayList = localarray;
        this.profileUserImage = profileUserModel;
        pref = new AlbanianPreferances(context);

        this.onclick_image = onclick_image;
//        options = new DisplayImageOptions.Builder()
//                .displayer(new RoundedBitmapDisplayer(200))
////                .showStubImage(R.drawable.profile_male_btn)
////                .showImageForEmptyUri(R.drawable.profile_male_btn)
////                .showImageOnFail(R.drawable.profile_male_btn)
//                .cacheInMemory()
////                .cacheOnDisc()
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
    public boolean isEmpty() {
        return false;
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
            convertView = inflator.inflate(R.layout.chatitem, null);
            holder = new viewholder();




            holder.chatlayout = (LinearLayout) convertView
                    .findViewById(R.id.ll_chatmessage);

            holder.messagebg = (RelativeLayout) convertView
                    .findViewById(R.id.rl_messagebg);

            holder.messageContent = (TextView) convertView
                    .findViewById(R.id.txt_chat_message);

//            holder.senderImage = (ImageView) convertView
//                    .findViewById(R.id.img_me);

            holder.receiverImage = (ImageView) convertView
                    .findViewById(R.id.img_receiver);
//
//                holder.quote = (TextView) convertView
//                        .findViewById(R.id.txt_quote_user);

            convertView.setTag(holder);

        } else
        {
            holder = (viewholder) convertView.getTag();
        }




//        holder.msgTime.setText(changeDate(messagesArrayList.get(position).getMsgSentTime()));
        holder.messageContent.setText(changeDate(messagesArrayList.get(position).getMsgContent()));
//            holder.quote.setText(changeDate(historyArrayList.get(position).getUserFavQuote()));

//        float tempamount=Float.parseFloat(historyArrayList.get(position).getPayment_amount());
//        holder.fare.setText("$"+String.valueOf(round(tempamount,2)));


        holder.receiverImage.setOnClickListener(onclick_image);
        holder.receiverImage.setTag(position);


        if (pref.getUserData().getUserId().equals(messagesArrayList.get(position).getMsgSender())) {

//            imageLoader
//                    .displayImage(pref.getUserData().getUserImage(),
//                            holder.senderImage, options, animateFirstListener);

//            holder.senderImage.setVisibility(View.VISIBLE);
            holder.receiverImage.setVisibility(View.GONE);
            holder.chatlayout.setGravity(Gravity.RIGHT);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins((int)context.getResources().
                    getDimension(R.dimen.chatpadding), 0,
                    (int)context.getResources().
                            getDimension(R.dimen.chatpaddingright), 0);

            holder.messagebg.setLayoutParams(params);

            holder.messagebg.setBackground(context.getResources().getDrawable
                    (R.drawable.chat_right_bubble));
//            holder.messageContent.setPadding(10,10,10,10);

        }
        else
        {

            Log.d("sumit","user profileUserImage image= "+profileUserImage);

//            imageLoader
//                    .displayImage(profileUserImage,
//                            holder.receiverImage, options, animateFirstListener);

            Picasso.with(context).load(profileUserImage).fit().centerCrop()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
                    .transform(new CircleTransform())
                    .into(holder.receiverImage);

//            holder.senderImage.setVisibility(View.GONE);
            holder.receiverImage.setVisibility(View.VISIBLE);
            holder.chatlayout.setGravity(Gravity.LEFT);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0,(int)context.getResources().
                            getDimension(R.dimen.chatpadding) , 0);

            holder.messagebg.setLayoutParams(params);

            holder.messagebg.setBackground(context.getResources().getDrawable
                    (R.drawable.chat_left_bubble));

        }

//        if (holder.senderImage.isShown()) {
//
//            holder.messageContent.setTextColor(Color.parseColor("#735e5e"));
//        }
//        else
        {
            holder.messageContent.setTextColor(Color.parseColor("#000000"));
        }









        return convertView;
    }

    public static class viewholder {
        LinearLayout chatlayout;
        RelativeLayout messagebg;
        TextView /*msgTime,*/messageContent;
        ImageView /*senderImage,*/receiverImage;


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







}
