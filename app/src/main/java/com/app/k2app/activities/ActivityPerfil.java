package com.app.k2app.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.app.k2app.R;
import com.app.k2app.adapters.AdapterViewPagerPerfil;
import com.app.k2app.config.Config;
import com.app.k2app.views.SlidingTabLayout;

public class ActivityPerfil extends ActionBarActivity {

    // Declaring Your View and Variables
    ViewPager viewpager;
    SlidingTabLayout tabs;

    private Toolbar mToolbar;
    private Integer USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //movimenta a intent horizontalmente
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

        setContentView(R.layout.activity_perfil);

        //recebe o usuario que deve ser mostrado
        Integer userID = getIntent().getExtras().getInt("userId");
        USER_ID = userID;

        setupTabs();
        setupToolbar();
    }

    public Integer getUserID(){
        return USER_ID;
    }

    private void setupToolbar() {
        // Creating The Toolbar and setting it as the Toolbar for the activity
        mToolbar = (Toolbar) findViewById(R.id.tb_perfil);
        //mToolbar.setTitle("K2PIO");
        //mToolbar.setSubtitle("just a subtitle");
        mToolbar.setLogo(R.mipmap.ic_action_k2pio);
        setSupportActionBar(mToolbar);
        //button return
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private  void setupTabs(){
        // Assigning ViewPager View
        viewpager = (ViewPager) findViewById(R.id.viewpager_perfil);
        // setting the adapter and Creating The ViewPagerAdapter and Passing Fragment Manager
        viewpager.setAdapter(new AdapterViewPagerPerfil(getSupportFragmentManager()));
        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs_perfil);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //movimenta a intent horizontalmente
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Config.TAG, "ActivityPerfil onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(Config.TAG, "ActivityPerfil onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(Config.TAG, "ActivityPerfil onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(Config.TAG, "ActivityPerfil onStop");
    }
}
