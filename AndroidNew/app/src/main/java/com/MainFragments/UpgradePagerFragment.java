package com.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.models.UpgradeModel;

/**
 * Created by Sumit on 16-Mar-18.
 */

public class UpgradePagerFragment extends Fragment implements View.OnClickListener {


    /**
     * Image Loader
     */


//    DisplayImageOptions options;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();


    private View mainView;

    private ImageView image;
//	private ImageView back;


    private UpgradeModel imageModel;
    private AlbanianPreferances pref;
    private String CURRENTTABTAG;
    private TextView text;
    private int upgradeimageposition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        pref = new AlbanianPreferances(getActivity().getApplicationContext());

        imageModel = getArguments().getParcelable("upgradestring");
        upgradeimageposition = getArguments().getInt("upgradeimageposition");
        CURRENTTABTAG= getArguments().getString(AlbanianConstants.EXTRA_CURRENTTAB_TAG);

//        Log.d("sumit","image string= "+imageModel.getUpgradeImage());


//		Log.d("sumit", "images received before === " + imagestring);

//        options = new DisplayImageOptions.Builder()
////		.showStubImage(R.drawable.logo)
////		.showImageForEmptyUri(R.drawable.logo)
////		.showImageOnFail(R.drawable.logo)
//                .cacheInMemory()
//                .cacheOnDisc()
//                .build();

    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // super.onCreateView(inflater, container, savedInstanceState);


        mainView = inflater.inflate(R.layout.upgradefragmentitem, container, false);


        image=(ImageView)mainView.findViewById(R.id.img_upgradeimage);
        text=(TextView)mainView.findViewById(R.id.txt_upgradetext);





        try
        {


            if (imageModel != null) {


                text.setText(imageModel.getUpgradeText());

                if (upgradeimageposition == 0) {

                    image.setImageResource((R.drawable.chat));

//                    imageLoader
//                        .displayImage("drawable://" +R.drawable.premium,
//                                image, options, animateFirstListener);
                }
                else if (upgradeimageposition == 1)
                {
                    image.setImageResource((R.drawable.speechbubble));

//                    imageLoader
//                            .displayImage("drawable://" +R.drawable.chat,
//                                    image, options, animateFirstListener);
                }
                else if (upgradeimageposition == 2)
                {
                    image.setImageResource((R.drawable.appreciation));

//                    imageLoader
//                            .displayImage("drawable://" +R.drawable.appreciation,
//                                    image, options, animateFirstListener);
                }
                else if (upgradeimageposition == 3)
                {
                    image.setImageResource((R.drawable.hide));

//                    imageLoader
//                            .displayImage("drawable://" +R.drawable.hide,
//                                    image, options, animateFirstListener);
                }
                else if (upgradeimageposition == 4)
                {
                    image.setImageResource((R.drawable.profit));

//                    imageLoader
//                            .displayImage("drawable://" +R.drawable.profit,
//                                    image, options, animateFirstListener);
                }

            }


        } catch (Exception ex)
        {

        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
            }
        });


        return mainView;

    }


    public static Image_Profile_Fragment newInstance(int position) {
        Image_Profile_Fragment fragment = new Image_Profile_Fragment();

        return fragment;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_close_image:


                getActivity().onBackPressed();

                break;

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("View Image Screen");
    }
}
