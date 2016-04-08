package com.app.k2app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import com.app.k2app.JSON.ParserFeed;
import com.app.k2app.R;
import com.app.k2app.activities.ActivityMain;
import com.app.k2app.adapters.AdapterFeedItem;
import com.app.k2app.application.MyApplication;
import com.app.k2app.classes.ClassFilter;
import com.app.k2app.config.Config;
import com.app.k2app.network.VolleySingleton;
import com.app.k2app.pojo.PojoFeedItem;

import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentTab1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;

    //Swipe Refresh Layout
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String filtroPosts;

    private Integer userId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_tab1,container,false);

        // Swipe Refresh Layout
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.tab1_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setSize(0);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.cl_srl_background);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.cl_srl_primary,
                R.color.cl_srl_second,
                R.color.cl_srl_third,
                R.color.cl_srl_four);

        Log.i(Config.TAG,"Fragment 1");

        mRecyclerView = (RecyclerView) v.findViewById(R.id.rvTab1);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Carrega dados na inicializaçao do fragment
        showFeedPosts();

        //set Animation
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return v;
    }

    public void showFeedPosts(){

        try{

            ActivityMain am = new ActivityMain();
            userId=am.getUserId();

        Integer idMin = ClassFilter.getIdMin();
        Integer idMax = ClassFilter.getIdMax();
        Integer sexo = ClassFilter.getSexo();
        Integer oriSex = ClassFilter.getOrientacaoSexual();

        Integer getFilterPosts = MyApplication.sharedPrefs.getInt(Config.SHARED_FILTERS_POSTS,0);
        Log.i(Config.TAG,"getFilterPosts: " + getFilterPosts);
        if (getFilterPosts==0){
            filtroPosts = "/posts/"+userId+"/all/"+idMin+"/"+idMax+"/"+sexo+"/"+oriSex;
        }else{
            filtroPosts = "/posts/"+userId+"/contacts/"+idMin+"/"+idMax+"/"+sexo+"/"+oriSex;
        }

        String URI = MyApplication.getWsUrlApp() + filtroPosts;
        Log.i(Config.TAG,"URL: " + URI);

        // pass second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(URI, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        ArrayList<PojoFeedItem> arrayDataset = new ArrayList<PojoFeedItem>();
                        arrayDataset.clear();
                            arrayDataset = ParserFeed.ParserJSONFeed(response);
                            AdapterFeedItem mAdapter = new AdapterFeedItem(arrayDataset);
                            mRecyclerView.setAdapter(mAdapter);
                            if (arrayDataset.size() == 0){
                            Toast.makeText(getActivity(),R.string.noDataFeed,Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                if (error instanceof TimeoutError){
                    Toast.makeText(MyApplication.getAppContext(), "Timeout", Toast.LENGTH_LONG).show();
                }else if (error instanceof NoConnectionError) {
                    Toast.makeText(MyApplication.getAppContext(),"No Connection",Toast.LENGTH_LONG).show();
                }else if (error instanceof AuthFailureError) {
                    Toast.makeText(MyApplication.getAppContext(),"Auth Failure",Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(MyApplication.getAppContext(),"Server Error",Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(MyApplication.getAppContext(),"Network Error",Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(MyApplication.getAppContext(),"Parse Error",Toast.LENGTH_LONG).show();
                }
            }
        });

        // add the request object to the queue to be executed
        VolleySingleton.getInstance().addToRequestQueue(req);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopSwipeRefreshLayout(){
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(Config.TAG, "FragmentTab1 onViewCreated");
    }

    @Override
    public void onRefresh() {
        showFeedPosts();
        stopSwipeRefreshLayout();
        Log.i(Config.TAG, "FragmentTab1 onRefresh");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(Config.TAG, "FragmentTab1 onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        //showFeedPosts();
        Log.i(Config.TAG, "FragmentTab1 onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(Config.TAG, "FragmentTab1 onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(Config.TAG, "FragmentTab1 onStop");
    }


}