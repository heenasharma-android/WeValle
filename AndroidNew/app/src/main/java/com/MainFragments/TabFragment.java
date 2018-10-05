package com.MainFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adapter.GalleryAdapter;
import com.albaniancircle.R;
import com.holders.GalleryData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TabFragment extends Fragment {

    private String parameter;
    List<GalleryData>galleryDataList=new ArrayList<>();
    GalleryAdapter galleryAdapter;
    private OnFragmentInteractionListener mListener;

    public TabFragment() {
        // Required empty public constructor
    }


    public static TabFragment newInstance(String parameter) {
        Bundle args = new Bundle();
        args.putString("parameter", parameter);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this,view);
        if (getArguments() != null) {
            parameter = getArguments().getString("parameter");
        }
        getImages();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getImages() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvGallery.setLayoutManager(layoutManager);
        for (int i=0;i<4;i++) {
            GalleryData galleryData = new GalleryData("Event 1", R.drawable.youtube);
            galleryDataList.add(galleryData);
        }
        galleryAdapter = new GalleryAdapter(getActivity(), galleryDataList);
        rvGallery.setAdapter(galleryAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @BindView(R.id.rv_gallery)
    RecyclerView rvGallery;
}
