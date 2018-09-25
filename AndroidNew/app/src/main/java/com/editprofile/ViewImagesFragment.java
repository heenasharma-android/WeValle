package com.editprofile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.adapter.GridAdapter;
import com.albaniancircle.R;

import java.util.ArrayList;

public class ViewImagesFragment extends Fragment implements OnClickListener {
    private View mView;
    private ImageView ivAddImage;
    private GridView gvGridImage;
    private GridAdapter adapter;

    public ViewImagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_view_images, container, false);
        init();
        return mView;
    }

    private void init() {
        initViews();
    }

    ArrayList<Uri> imagesList = new ArrayList<Uri>();

    private void initViews() {
        gvGridImage = (GridView) mView.findViewById(R.id.gv_userimages);

        Uri uridrawable = Uri.parse("android.resource://com.EditProfile/" + R.drawable.user_icon);

        imagesList.add(uridrawable);
        adapter = new GridAdapter(getActivity(), imagesList);
        gvGridImage.setAdapter(adapter);

        gvGridImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
//                    openGallery(10);
                    Toast.makeText(getActivity(), "pos : " + position, Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
            }
        });
//        ivAddImage = (ImageView) mView.findViewById(R.id.iv_add_image);
//        ivAddImage.setOnClickListener(this);
    }

    private Intent cameraIntent;

    private void openCamera()
    {
        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 100);
    }

    private Uri selectedImageUri;
    private String selectedPath;


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (data.getData() != null) {
                selectedImageUri = data.getData();
            } else {
                Log.d("selectedPath1 : ", "Came here its null !");
            }
            if (requestCode == 100 && resultCode == getActivity().RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                Uri.parse( );
               // ivImage.setImageBitmap(photo);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.iv_add_image:
//                createImageViews();
//                break;
        }
    }

    private void createImageViews() {
        //RelativeLayout relativeLayout = mView
        openCamera();
    }
}
