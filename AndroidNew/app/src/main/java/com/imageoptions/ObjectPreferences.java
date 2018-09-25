package com.imageoptions;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;

public class ObjectPreferences {
	
	private SharedPreferences pref;
    private String PREF_NAME = "testing";
    private Context context;

    public ObjectPreferences(Context context) {

                    this.context = context;
                    pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

   
    public void saveImageUri(Uri mUri) {
                    try {

                                    Editor edit = pref.edit();
                                    edit.putString("uri", mUri + "");
                                    edit.commit();
                    } catch (Exception exp) {
                                    //Debug.debug(exp);
                    }
    }

    public String getImageUri() {
                    return pref.getString("uri", "");
    }

    public void savevideoUri(Uri vUri){
                    try {

                                    Editor edit = pref.edit();
                                    edit.putString("vuri", vUri + "");
                                    edit.commit();
                    } catch (Exception exp) {
                                    //Debug.debug(exp);
                    }
    }
    public String getvideoUri(){
                    return pref.getString("vuri","");
    }

}
