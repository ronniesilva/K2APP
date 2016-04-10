package com.app.k2app.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.k2app.R;

public class ActivityAbout extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //movimenta a intent horizontalmente
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

        setContentView(R.layout.activity_about);

        // Creating The Toolbar and setting it as the Toolbar for the activity
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tb_about);
        try{
            mToolbar.setLogo(R.mipmap.ic_action_k2pio);
        }catch (Exception e){

        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_about, menu);
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
            //movimenta a intent horizontalmente
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //movimenta a intent horizontalmente
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
}
