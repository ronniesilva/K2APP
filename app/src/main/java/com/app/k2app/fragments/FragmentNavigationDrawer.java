package com.app.k2app.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.app.k2app.JSON.ParserMenuDraw;
import com.app.k2app.R;
import com.app.k2app.activities.ActivityAbout;
import com.app.k2app.activities.ActivityFilters;
import com.app.k2app.activities.ActivityMain;
import com.app.k2app.activities.ActivitySettings;
import com.app.k2app.adapters.AdapterMenuDrawer;
import com.app.k2app.application.MyApplication;
import com.app.k2app.config.Config;
import com.app.k2app.network.VolleySingleton;
import com.app.k2app.pojo.PojoMenuDrawerOptions;
import com.app.k2app.pojo.PojoMenuDrawerUserPerfil;

import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentNavigationDrawer extends Fragment {

    //public static final String PREF_FILE_NAME = "prefs";
    //public static final String KEY_USER_LEARNED_DRAWER = "user_learner_drawer";
    public Integer userId;

    private ActionBarDrawerToggle mDrawerToggle;
    public static DrawerLayout mDrawerLayout;

    // private boolean mUserLearnedDrawer;
    // private boolean mFromSavedInstanceState;
    private View containerView;

    private AdapterMenuDrawer mAdapter;
    private RecyclerView mRecyclerDrawer;

    private PojoMenuDrawerUserPerfil dataDrawHeader;

    public FragmentNavigationDrawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mUserLearnedDrawer=Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER, "false"));
        //mFromSavedInstanceState = savedInstanceState != null ? true : false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerDrawer = (RecyclerView) view.findViewById(R.id.drawerList);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataDrawHeader = new PojoMenuDrawerUserPerfil();

        try{


            ActivityMain am = new ActivityMain();
            userId=am.getUserId();

            Log.i(Config.TAG, "Fragment userId: " + userId);
            String URI = MyApplication.getWsUrlApp() + "/users/"+userId+"/menudraw";
            Log.i(Config.TAG, "Fragment URI: " + URI);

            // pass second argument as "null" for GET requests
            JsonObjectRequest req = new JsonObjectRequest(URI, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //set Parser
                            dataDrawHeader = ParserMenuDraw.ParserJSONDrawerHeader(response);
                            mAdapter = new AdapterMenuDrawer(getActivity(), getDrawDataItem(), dataDrawHeader);
                            mRecyclerDrawer.setAdapter(mAdapter);

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

        mRecyclerDrawer.setHasFixedSize(true);
        mRecyclerDrawer.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerDrawer.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerDrawer, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                //((ActivityMain) getActivity()).onDrawerItemClicked(position - 1);

                switch (position) {
                    case 0: //Profile
                        Toast.makeText(MyApplication.getAppContext(), "Menu " + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 1: //Filters
                        startActivity(new Intent(getActivity(), ActivityFilters.class));
                        break;
                    case 2: //Settings
                        startActivity(new Intent(getActivity(), ActivitySettings.class));
                        break;
                    case 3: //About
                        startActivity(new Intent(getActivity(), ActivityAbout.class));
                        break;
                    case 4: //Disconnect
                        ActivityMain.activityMain.finish();
                        break;

                    default:
                        Toast.makeText(MyApplication.getAppContext(), "Menu " + position, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(MyApplication.getAppContext(), "Menu "+ position, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public ArrayList<PojoMenuDrawerOptions> getDrawDataItem(){

        ArrayList<PojoMenuDrawerOptions> arrayMenu = new ArrayList<PojoMenuDrawerOptions>();

        try{
            arrayMenu.clear();
            String[] menuItem = getResources().getStringArray(R.array.drawer_tabs);
            arrayMenu.add(new PojoMenuDrawerOptions(menuItem[0], R.drawable.ic_action_k2pio));
            arrayMenu.add(new PojoMenuDrawerOptions(menuItem[1], R.drawable.ic_action_k2pio));
            arrayMenu.add(new PojoMenuDrawerOptions(menuItem[2], R.drawable.ic_action_k2pio));
            arrayMenu.add(new PojoMenuDrawerOptions(menuItem[3], R.drawable.ic_action_k2pio));
        }catch (Exception e){
            e.printStackTrace();
        }

        return arrayMenu;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        containerView=getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();

            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    //clickListener
    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
