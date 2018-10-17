package com.MainFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adapter.PlacesAdapter;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.models.PlaceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesFragment extends Fragment {

    PlacesAdapter placesAdapter;
    AlbanianPreferances albanianPreferances;
    List<PlaceData> placeDataList =new ArrayList<PlaceData>();
    private OnFragmentInteractionListener mListener;

    public PlacesFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_places, container, false);
        ButterKnife.bind(this,view);
        albanianPreferances = new AlbanianPreferances(getContext());
        placeDataList.clear();
        sendJsonRequest();
        return view;
    }

    public void sendJsonRequest() {
        //  AlbanianApplication.showProgressDialog(getActivity(),"","Loading..");
        String webAddress = "http://culturalsinglesapps.com/WeValleAPICalls.php";
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JSONObject object = new JSONObject();

        try {

            object.put("user_id", albanianPreferances.getUserData().getUserId());
            object.put("api_name", "list_places");

        } catch (JSONException e) {
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, webAddress, object, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //  AlbanianApplication.hideProgressDialog(getActivity());
                Log.d("RESPONSE", response.toString());
                try {
                    JSONArray jsonArray=response.getJSONArray("places");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        PlaceData placeData = new PlaceData();
                        placeData.setId(jsonObject.getString("id"));
                        placeData.setTitle(jsonObject.getString("title"));
                        placeData.setDescription(jsonObject.getString("description"));
                        placeData.setAddress(jsonObject.getString("address"));
                        placeData.setLatitude(jsonObject.getString("latitude"));
                        placeData.setLongitude(jsonObject.getString("longitude"));
                        placeData.setStatus(jsonObject.getString("status"));
                        placeData.setCreated_at(jsonObject.getString("created_at"));
                        placeData.setUpdated_at(jsonObject.getString("updated_at"));
                        placeData.setImages(Collections.singletonList(jsonObject.getString("images")));
                        placeDataList.add(placeData);
                    }
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    rvGallery.setLayoutManager(llm);
                    placesAdapter = new PlacesAdapter(getActivity(), placeDataList);
                    rvGallery.setAdapter(placesAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  AlbanianApplication.hideProgressDialog(getActivity());
                Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request); }

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

    @BindView(R.id.rv_gallery)
    RecyclerView rvGallery;


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
