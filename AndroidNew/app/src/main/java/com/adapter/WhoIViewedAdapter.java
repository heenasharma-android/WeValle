package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaniancircle.R;
import com.albaniancircle.RoundedCornersTransform;
import com.models.Heritage;
import com.models.WhoIViewedModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sumit on 9/14/2015.
 */
public class WhoIViewedAdapter extends BaseAdapter {


    /**
     * ********************
     * Adapter class members
     */

    private LayoutInflater inflator;
    private Context context;
   int radius=5;
   int margin=3;

//    private OnClickListener onclick_checkin;
//    private OnClickListener onclick_fav;
//    private OnClickListener onclick_getdirection;

//    DisplayImageOptions options;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private ArrayList<WhoIViewedModel> historyArrayList = new ArrayList<WhoIViewedModel>();

    public WhoIViewedAdapter(Context c,
                             ArrayList<WhoIViewedModel> localarray)

    {
        this.context = c;
        this.historyArrayList = localarray;


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
        if (historyArrayList.size() == 0) {
            return 0;
        } else {

            return historyArrayList.size();

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
            convertView = inflator.inflate(R.layout.whoiviewed_list_item, null);
            holder = new viewholder();




            holder.userimage = (ImageView) convertView
                    .findViewById(R.id.img_user);

            holder.username = (TextView) convertView
                    .findViewById(R.id.txt_username);

            holder.addres = (TextView) convertView
                    .findViewById(R.id.txt_address_user);

            holder.quote = (TextView) convertView
                    .findViewById(R.id.txt_quote_user);

            convertView.setTag(holder);

        } else
        {
            holder = (viewholder) convertView.getTag();
        }




        holder.username.setText((historyArrayList.get(position).getUserName()));
        holder.addres.setText(historyArrayList.get(position).getAge()+", "
        +historyArrayList.get(position).getUserCity()+", "+historyArrayList.get(position)
                .getUserCountry());
        holder.quote.setText((historyArrayList.get(position).getUserFavQuote()));
        holder.quote.setVisibility(View.GONE);

//        float tempamount=Float.parseFloat(historyArrayList.get(position).getPayment_amount());
//        holder.fare.setText("$"+String.valueOf(round(tempamount,2)));


//        holder.addtofav.setOnClickListener(onclick_fav);
//        holder.addtofav.setTag(position);


//        imageLoader
//                .displayImage(historyArrayList.get(position).getUserImageUrl(),
//                        holder.userimage, options, animateFirstListener);


        Picasso.with(context).load(historyArrayList.get(position).getUserImageUrl()).fit().centerCrop()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
                .transform(new RoundedCornersTransform(radius,margin))
                .into(holder.userimage);




        return convertView;
    }

    public static class viewholder {
        TextView username;
        TextView addres;
        TextView quote,onlinestatus;
        ImageView userimage;


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


}
