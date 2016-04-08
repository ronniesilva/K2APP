package com.app.k2app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import com.app.k2app.JSON.ParserInReach;
import com.app.k2app.R;
import com.app.k2app.activities.ActivityMain;
import com.app.k2app.adapters.AdapterInReachItem;
import com.app.k2app.application.MyApplication;
import com.app.k2app.classes.ClassFilter;
import com.app.k2app.config.Config;
import com.app.k2app.network.VolleySingleton;
import com.app.k2app.pojo.PojoInReachItem;

import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentTab2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;

    //Swipe Refresh Layout
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String filtroPosts;

    private Integer userId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab2,container,false);

        // Swipe Refresh Layout
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.tab2_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setSize(0);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.cl_srl_background);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.cl_srl_primary,
                R.color.cl_srl_second,
                R.color.cl_srl_third,
                R.color.cl_srl_four);

        Log.i(Config.TAG, "Fragment 2");

        mRecyclerView = (RecyclerView) v.findViewById(R.id.rvTab2);

        //set Animation
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a GRID layout manager
        //mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //mRecyclerView.setAdapter(new NumberedAdapter(30));

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(manager);

        // specify an adapter
        //ArrayList<String> initArray = new ArrayList<String>();
        //initArray.add("Approaching those near");
        //AdapterInReachItem mAdapter = new AdapterInReachItem(initArray);
        //mRecyclerView.setAdapter(mAdapter);

        //Carrega dados na inicializaçao do fragment
        showUsersInReach();

        return v;
    }

    public void showUsersInReach(){

        try{

            ActivityMain am = new ActivityMain();
            userId=am.getUserId();

            Integer idMin = ClassFilter.getIdMin();
            Integer idMax = ClassFilter.getIdMax();
            Integer sexo =  ClassFilter.getSexo();
            Integer oriSex = ClassFilter.getOrientacaoSexual();

        Integer getFilterPosts = MyApplication.sharedPrefs.getInt(Config.SHARED_FILTERS_POSTS,0);
        Log.i(Config.TAG,"getFilterPosts: " + getFilterPosts);
        if (getFilterPosts==0){
            filtroPosts = "/inreach/"+userId+"/all/"+idMin+"/"+idMax+"/"+sexo+"/"+oriSex;
        }else{
            filtroPosts = "/inreach/"+userId+"/contacts/"+idMin+"/"+idMax+"/"+sexo+"/"+oriSex;
        }

        String URI = MyApplication.getWsUrlApp() + filtroPosts;

        Log.i(Config.TAG,"URL: " + URI);

        // pass second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(URI, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<PojoInReachItem> arrayDataset = new ArrayList<PojoInReachItem>();
                        arrayDataset.clear();
                        arrayDataset = ParserInReach.ParserJSONInReach(response);

                        // specify an adapter
                        AdapterInReachItem mAdapter = new AdapterInReachItem(arrayDataset);
                        mRecyclerView.setAdapter(mAdapter);
                        if (arrayDataset.size() == 0){
                            Toast.makeText(getActivity(),R.string.noDataInReach,Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                if (error instanceof TimeoutError){
                    Toast.makeText(getActivity(), "Timeout", Toast.LENGTH_LONG).show();
                }else if (error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(),"No Connection",Toast.LENGTH_LONG).show();
                }else if (error instanceof AuthFailureError) {
                    Toast.makeText(getActivity(),"Auth Failure",Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getActivity(),"Network Error",Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getActivity(),"Parse Error",Toast.LENGTH_LONG).show();
                }
            }
        });

        // add the request object to the queue to be executed
        VolleySingleton.getInstance().addToRequestQueue(req);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        showUsersInReach();
        stopSwipeRefreshLayout();
    }

    public void stopSwipeRefreshLayout(){
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //showUsersInReach();
        Log.i(Config.TAG, "FragmentTab2 onResume");

    }

}