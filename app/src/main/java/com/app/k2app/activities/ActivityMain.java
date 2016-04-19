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
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;

public class ActivityMain extends AppCompatActivity  implements
        ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    protected static final String TAG = "K2GPS";

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
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;

    /**
     * Time when the location was updated represented as a String.
     */
    protected String mLastUpdateTime;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    /**
     * Distancia em metros
     */
    private static int DISPLACEMENT = 5; // x in meters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(Config.TAG, "ActivityMain onCreate");

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
        Log.i(TAG, "ActivityMain checkPlayServices()");
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
        Log.i(TAG, "ActivityMain buildGoogleApiClient()");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        createLocationRequest();
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    protected void createLocationRequest() {
        Log.i(TAG, "ActivityMain createLocationRequest()");
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
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

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {

        Log.i(TAG, "ActivityMain onConnect()");

        // If the initial location was never previously requested, we use
        // FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
        // its value in the Bundle and check for it in onCreate(). We
        // do not request it again unless the user specifically requests location updates by pressing
        // the Start Updates button.
        //
        // Because we cache the value of the initial location in the Bundle, it means that if the
        // user launches the activity,
        // moves to a new location, and then changes the device orientation, the original location
        // is displayed as the activity is re-created.
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            updateLocationInWebServer(mCurrentLocation, mLastUpdateTime);
        }else{
            Toast.makeText(this, "Nenhuma localização detectada", Toast.LENGTH_SHORT).show();
        }

        /**
         *  Importante para o LocationChanged();
         */
        startLocationUpdates();

    }

    /**
     * Callback that fires when the location changes.
     */
    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "ActivityMain onLocationChanged()");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateLocationInWebServer(mCurrentLocation, mLastUpdateTime);
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Activity Connection suspended");
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Activity Connection failed: " + result.getErrorCode());
        Toast.makeText(this, "Connection failed: " + result.getErrorCode(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
        Log.i(TAG, "ActivityMain startLocationUpdates()");
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        Log.i(TAG, "ActivityMain stopLocationUpdates()");
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }


    protected void updateLocationInWebServer(Location location, String date){

        Location loc = location;
        String Date = date;
        Toast.makeText(this, "Lat: " + loc.getLatitude()+"\nLong: " + loc.getLongitude()+"\nData: "+Date, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Config.TAG, "ActivityMain onStart");

        if(mGoogleApiClient!= null) {
            mGoogleApiClient.connect();
        }else{
            Toast.makeText(this, "Not connected...", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(Config.TAG, "ActivityMain onResume");
        //Log.i(Config.TAG, "ActivityMain mGoogleApiClient.isConnected() : " + ActivityLogin.mGoogleApiClient.isConnected());

        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(Config.TAG, "ActivityMain onPause");

        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
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
     * IMPORTANTE:
     * Não fecha a aplicação quando o botão "Voltar" é pressionado
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
