package com.holders;

import android.view.View;

/**
 * Created by Sumit on 25-Feb-18.
 */

public interface ClickListener{
    public void onClick(View view, int position);
    public void onLongClick(View view,int position);
}
