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

import com.adapter.InterAdapter;
import com.adapter.LocalAdapter;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.models.International;
import com.models.Local;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FeaturedFragment extends Fragment {

    RecyclerView rvLocal,rvInternationl;
    International international;
    Local local;
    List<International> internationals;
    List<Local> locals;
    List<International.PhotoList> photoLists;
    AlbanianPreferances pref;

    private OnFragmentInteractionListener mListener;

    public FeaturedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_featured, container, false);
        pref = new AlbanianPreferances(getContext());
        rvLocal = (RecyclerView) view.findViewById(R.id.rv_local);
        rvInternationl = (RecyclerView) view.findViewById(R.id.rv_inter);
        internationals=new ArrayList<>();
        locals=new ArrayList<>();
        sendJsonRequest();
        return view;
    }


    public void sendJsonRequest() {
        AlbanianApplication.showProgressDialog(getContext(),"","Loading..");
        String webAddress = "http://culturalsinglesapps.com/WeValleAPICalls.php";
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JSONObject object = new JSONObject();

        try {

            object.put("user_id", pref.getUserData().getUserId());
            object.put("api_name", "list_services");

        } catch (JSONException e) {
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, webAddress, object, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                AlbanianApplication.hideProgressDialog(getContext());
                Log.d("RESPONSE", response.toString());

                try {

//                    JSONArray jsonArray1 = response.getJSONArray("photos");
//                    for (int j = 0; j < jsonArray1.length(); j++) {
//                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
//                        International.PhotoList photoList = new International.PhotoList();
//                        photoList.setId(jsonObject1.getString("id"));
//                        photoList.setImageUrl(jsonObject1.getString("image_url"));
//                        photoLists.add(photoList);
//                    }

                    JSONArray jsonArray=response.getJSONArray("Services");
                    for(int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject.getString("is_international").equalsIgnoreCase("1")) {
                            international = new International();
                            international.setId(jsonObject.getString("id"));
                            international.setName(jsonObject.getString("name"));
                            international.setUserId(jsonObject.getString("user_id"));
                            international.setContactName(jsonObject.getString("contact_name"));
                            international.setPhoneNo(jsonObject.getString("phone_number"));
                            international.setIsInternational(jsonObject.getString("is_international"));
                            international.setAddress(jsonObject.getString("address"));
                            international.setLatitude(jsonObject.getString("latitude"));
                            international.setLongitude(jsonObject.getString("longitude"));
                            international.setCategory(jsonObject.getString("category"));
                            international.setWebsite(jsonObject.getString("website"));
                            international.setDescription(jsonObject.getString("description"));
                            international.setBusinessHr(jsonObject.getString("business_hours"));
                            international.setHeritage(jsonObject.getString("heritage"));
                            international.setStatus(jsonObject.getString("status"));
                            international.setCreateAt(jsonObject.getString("created_at"));
                            international.setUpdateAt(jsonObject.getString("updated_at"));
                            international.setUserName(jsonObject.getString("UserName"));
                            international.setUserId(jsonObject.getString("UserId"));
                            international.setUserFull(jsonObject.getString("UserFullName"));
                            international.setImageUrl(jsonObject.getString("UserImageUrl"));
                            international.setUnit(jsonObject.getString("Unit"));
                            international.setDistance(jsonObject.getString("MilesDistance"));
                            international.setPhoto(photoLists);
                            internationals.add(international);
                            LinearLayoutManager llm = new LinearLayoutManager(getContext());
                            llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                            rvInternationl.setLayoutManager(llm);
                            InterAdapter interAdapter = new InterAdapter(internationals, getContext());
                            rvInternationl.setAdapter(interAdapter);
                            //   }
                        }
                        else if (jsonObject.getString("is_international").equalsIgnoreCase("0")){
                            local = new Local();
                            local.setId(jsonObject.getString("id"));
                            local.setName(jsonObject.getString("name"));
                            local.setUserId(jsonObject.getString("user_id"));
                            local.setContactName(jsonObject.getString("contact_name"));
                            local.setPhoneNo(jsonObject.getString("phone_number"));
                            local.setIsInternational(jsonObject.getString("is_international"));
                            local.setAddress(jsonObject.getString("address"));
                            local.setLatitude(jsonObject.getString("latitude"));
                            local.setLongitude(jsonObject.getString("longitude"));
                            local.setCategory(jsonObject.getString("category"));
                            local.setWebsite(jsonObject.getString("website"));
                            local.setDescription(jsonObject.getString("description"));
                            local.setBusinessHr(jsonObject.getString("business_hours"));
                            local.setHeritage(jsonObject.getString("heritage"));
                            local.setStatus(jsonObject.getString("status"));
                            local.setCreateAt(jsonObject.getString("created_at"));
                            local.setUpdateAt(jsonObject.getString("updated_at"));
                            local.setUserName(jsonObject.getString("UserName"));
                            local.setUserId(jsonObject.getString("UserId"));
                            local.setUserFull(jsonObject.getString("UserFullName"));
                            local.setImageUrl(jsonObject.getString("UserImageUrl"));
                            local.setUnit(jsonObject.getString("Unit"));
                            local.setDistance(jsonObject.getString("MilesDistance"));
                            //international.setPhoto(photoLists);
                            locals.add(local);

                            LinearLayoutManager llm = new LinearLayoutManager(getContext());
                            llm.setOrientation(LinearLayoutManager.VERTICAL);
                            rvLocal.setLayoutManager(llm);
                            LocalAdapter interAdapter = new LocalAdapter(locals, getContext());
                            rvLocal.setAdapter(interAdapter);
                       }

                    }
                        //   Toast.makeText(ListActivity.this, response.toString().trim(), Toast.LENGTH_LONG).show();
                 //   }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlbanianApplication.hideProgressDialog(getContext());
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
