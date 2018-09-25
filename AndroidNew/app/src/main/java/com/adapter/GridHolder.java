package com.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.albaniancircle.R;

/**
 * Created by Sumit on 9/14/2015.
 */
public class GridHolder implements View.OnClickListener {

    private View mView;
    private GridHolder holder;
    private Activity mActivity;
    private Uri viewers;
    private ImageView ivGridImage;

    public GridHolder(Activity activity) {
        this.mActivity = activity;
    }


    public View getConvertView() {
        mView = mActivity.getLayoutInflater().inflate(R.layout.grid_list_item, null);
        ivGridImage = (ImageView) mView.findViewById(R.id.iv_grid_image);
        return mView;
    }

    public void applyData() {
        ivGridImage.setImageURI(viewers);

    }

    public void initializeData(Object data) {
        viewers = (Uri) data;
    }

    @Override
    public void onClick(View view) {

    }
}
