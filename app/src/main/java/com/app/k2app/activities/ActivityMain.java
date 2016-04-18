package com.app.k2app.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.k2app.R;
import com.app.k2app.adapters.AdapterViewPagerMain;

import com.app.k2app.config.Config;
import com.app.k2app.fragments.FragmentNavigationDrawer;
import com.app.k2app.views.SlidingTabLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

public class ActivityMain extends AppCompatActivity  implements
        ConnectionCallbacks, OnConnectionFailedListener {
    public static Activity activityMain;
    private static Integer userId;

    private Toolbar mToolbar;
    private FragmentNavigationDrawer fragmentNavigationDrawer;

    // Declaring Your View and Variables
    ViewPager viewpager;
    SlidingTabLayout tabs;

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMain = this;

        setupTabs();
        setupToolbar();
        setupDrawer();

        userId = 1;

        // First we need to check availability of play services
        if (checkPlayServices()) {
            buildGoogleApiClient();
        }
    }

    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }


    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public Integer getUserId(){
        return userId;
    }

    private void setupToolbar() {
        // Creating The Toolbar and setting it as the Toolbar for the activity
        mToolbar = (Toolbar) findViewById(R.id.tb_main);
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
        mGoogleApiClient.connect();
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
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(Config.TAG, "ActivityMain onDESTROY");
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Toast.makeText(this, "Lat: "+ mLastLocation.getLatitude()+"\nLong: "+mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nenhuma localização detectada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(Config.TAG, "Connection failed: " + result.getErrorCode());
        Toast.makeText(this, "Connection failed: " + result.getErrorCode(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(Config.TAG, "Connection suspended");
        mGoogleApiClient.connect();
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
