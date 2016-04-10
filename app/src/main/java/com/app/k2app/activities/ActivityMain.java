package com.app.k2app.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.k2app.R;
import com.app.k2app.adapters.AdapterViewPagerMain;

import com.app.k2app.config.Config;
import com.app.k2app.fragments.FragmentNavigationDrawer;
import com.app.k2app.views.SlidingTabLayout;

public class ActivityMain extends ActionBarActivity {
    public static Activity activityMain;
    private static Integer userId;

    private Toolbar mToolbar;
    private FragmentNavigationDrawer fragmentNavigationDrawer;

    // Declaring Your View and Variables
    ViewPager viewpager;
    SlidingTabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMain = this;

        setupTabs();
        setupToolbar();
        setupDrawer();

        userId = 1;
    }

    public Integer getUserId(){
        return userId;
    }

    private void setupToolbar() {
        // Creating The Toolbar and setting it as the Toolbar for the activity
        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        //mToolbar.setTitle("K2PIO");
        //mToolbar.setSubtitle("just a subtitle");
        mToolbar.setLogo(R.mipmap.ic_action_k2pio);
        setSupportActionBar(mToolbar);

        //drawer
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private  void setupTabs(){
        // Assigning ViewPager View
        viewpager = (ViewPager) findViewById(R.id.pager);
        // setting the adapter and Creating The ViewPagerAdapter and Passing Fragment Manager
        viewpager.setAdapter(new AdapterViewPagerMain(getSupportFragmentManager()));
        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        // Set custom tab layout
        tabs.setCustomTabView(R.layout.slidingtab, 0);
        // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setDistributeEvenly(true);
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.cl_colorBarHiglight);
            }
        });

        // Sets the colors to be used for indicating the selected tab.
        //tabs.setSelectedIndicatorColors(R.color.cl_black);

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(viewpager);

    }

    private void setupDrawer(){

        fragmentNavigationDrawer = (FragmentNavigationDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        fragmentNavigationDrawer.setUp(R.id.fragment_navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout), this.mToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_postadd) {
            startActivity(new Intent(this, ActivityPostAdd.class));
        }

        FragmentNavigationDrawer.mDrawerLayout.closeDrawer(GravityCompat.START);

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Config.TAG, "ActivityMain onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(Config.TAG, "ActivityMain onResume");
        //Log.i(Config.TAG, "ActivityMain mGoogleApiClient.isConnected() : " + ActivityLogin.mGoogleApiClient.isConnected());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(Config.TAG, "ActivityMain onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(Config.TAG, "ActivityMain onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(Config.TAG, "ActivityMain onDESTROY");
    }

    /**
     * IMPORTANTE:
     * Não fecha a aplicação quando o botão "Voltar" é pressionado
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
