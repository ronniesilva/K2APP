package com.app.k2app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.app.k2app.R;
import com.app.k2app.application.MyApplication;
import com.app.k2app.config.Config;
import com.app.k2app.config.Params;
import com.app.k2app.utils.RangeSeekBar;

public class ActivityFilters extends AppCompatActivity {

    private Toolbar mToolbar;
    private RadioButton rbPostAll;
    private RadioButton rbPostMyContacts;
    private ToggleButton tbFilterMens;
    private ToggleButton tbFilterGirls;
    private ToggleButton tbFilterHetero;
    private ToggleButton tbFilterHomo;
    private ToggleButton tbFilterBi;
    private RangeSeekBar<Integer> rangeSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //movimenta a intent horizontalmente
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

        setContentView(R.layout.activity_filters);

        // Creating The Toolbar and setting it as the Toolbar for the activity
        mToolbar = (Toolbar) findViewById(R.id.tb_filters);
        mToolbar.setTitle("K2PIO");
        mToolbar.setSubtitle("Post add");
        mToolbar.setLogo(R.mipmap.ic_action_k2pio);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setup the new range seek bar
        rangeSeekBar = new RangeSeekBar<Integer>(this);
        // Set the range
        int idMin = MyApplication.sharedPrefs.getInt(Config.SHARED_FILTERS_ID_MIN, Params.ID_MIN_VALUE_DEFAULT);
        int idMax = MyApplication.sharedPrefs.getInt(Config.SHARED_FILTERS_ID_MAX, Params.ID_MAX_VALUE_DEFAULT);
        rangeSeekBar.setRangeValues(Params.ID_MIN_TOTAL, Params.ID_MAX_TOTAL); // Valor máximo para idade
        rangeSeekBar.setSelectedMinValue(idMin);
        rangeSeekBar.setSelectedMaxValue(idMax);
        //rangeSeekBar.setBackgroundColor(getResources().getColor(R.color.cl_textColor));

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                Log.i(Config.TAG, "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
                        /* Age */
                MyApplication.shEditor.putInt(Config.SHARED_FILTERS_ID_MIN, minValue);
                MyApplication.shEditor.putInt(Config.SHARED_FILTERS_ID_MAX, maxValue);
            }
        });

        // Add to layout
        LinearLayout layout = (LinearLayout) findViewById(R.id.rangeSeekbar_placeholder);
        layout.addView(rangeSeekBar);

        rbPostAll = (RadioButton) findViewById(R.id.rbPostAll);
        rbPostMyContacts = (RadioButton) findViewById(R.id.rbPostMyContacts);
        tbFilterMens = (ToggleButton) findViewById(R.id.tbFilterMens);
        tbFilterGirls = (ToggleButton) findViewById(R.id.tbFilterGirls);

        tbFilterHetero= (ToggleButton) findViewById(R.id.tbFilterHetero);
        tbFilterHomo= (ToggleButton) findViewById(R.id.tbFilterHomo);
        tbFilterBi= (ToggleButton) findViewById(R.id.tbFilterBi);

        initGetSharedPreferences();
    }

    // recupera os valores em SharedPreferences e seta as views
    private void initGetSharedPreferences(){

        //Show posts
        Integer getSpPosts = MyApplication.sharedPrefs.getInt(Config.SHARED_FILTERS_POSTS,0);
        if (getSpPosts==0){
            rbPostAll.setChecked(true) ;
        }else{
            rbPostMyContacts.setChecked(true) ;
        }
        //Range Seek Bar
        //rangeSeekBar.setSelectedMinValue(MyApplication.sharedPrefs.getInt(Config.SHARED_FILTERS_ID_MIN,18));
        //rangeSeekBar.setSelectedMaxValue(MyApplication.sharedPrefs.getInt(Config.SHARED_FILTERS_ID_MAX,100));
        //Sex
        tbFilterMens.setChecked(MyApplication.sharedPrefs.getBoolean(Config.SHARED_FILTERS_MAN, true));
        tbFilterGirls.setChecked(MyApplication.sharedPrefs.getBoolean(Config.SHARED_FILTERS_GIRL, true));
        //Orientation Sexual
        tbFilterHetero.setChecked(MyApplication.sharedPrefs.getBoolean(Config.SHARED_FILTERS_HETERO, true));
        tbFilterHomo.setChecked(MyApplication.sharedPrefs.getBoolean(Config.SHARED_FILTERS_HOMO, true));
        tbFilterBi.setChecked(MyApplication.sharedPrefs.getBoolean(Config.SHARED_FILTERS_BI, true));

    }

    //salva alteracoes em SharedPreferences
    public void setSharedPreferences() {

        /* Filter All or Contacts */
        if(rbPostAll.isChecked()){
            MyApplication.shEditor.putInt(Config.SHARED_FILTERS_POSTS,0);
            //Log.i(Config.TAG,"shEditor: " + 0);
        } else{
            MyApplication.shEditor.putInt(Config.SHARED_FILTERS_POSTS,1);
            //Log.i(Config.TAG,"shEditor: " + 1);
        }

        /* Filter Sexo */
        if (tbFilterMens.isChecked()) {
            MyApplication.shEditor.putBoolean(Config.SHARED_FILTERS_MAN, true);
        }else{
            MyApplication.shEditor.putBoolean(Config.SHARED_FILTERS_MAN, false);
        }

        if (tbFilterGirls.isChecked()) {
            MyApplication.shEditor.putBoolean(Config.SHARED_FILTERS_GIRL, true);
        }else{
            MyApplication.shEditor.putBoolean(Config.SHARED_FILTERS_GIRL, false);
        }

        /* Filter Orientacao Sexual */
        if (tbFilterHetero.isChecked()) {
            MyApplication.shEditor.putBoolean(Config.SHARED_FILTERS_HETERO, true);
        }else{
            MyApplication.shEditor.putBoolean(Config.SHARED_FILTERS_HETERO, false);
        }

        if (tbFilterHomo.isChecked()) {
            MyApplication.shEditor.putBoolean(Config.SHARED_FILTERS_HOMO, true);
        }else{
            MyApplication.shEditor.putBoolean(Config.SHARED_FILTERS_HOMO, false);
        }

        if (tbFilterBi.isChecked()) {
            MyApplication.shEditor.putBoolean(Config.SHARED_FILTERS_BI, true);
        }else{
            MyApplication.shEditor.putBoolean(Config.SHARED_FILTERS_BI, false);
        }

        /* salva as alteracoes */
        MyApplication.shEditor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_filters, menu);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_accept_filter) {

            if (!tbFilterMens.isChecked() && !tbFilterGirls.isChecked()) {
                Toast.makeText(getBaseContext(), R.string.error_filter_sex_blank, Toast.LENGTH_SHORT).show();
            } else if (!tbFilterHetero.isChecked() && !tbFilterHomo.isChecked() && !tbFilterBi.isChecked()) {
                Toast.makeText(getBaseContext(), R.string.error_filter_oriSex_blank, Toast.LENGTH_SHORT).show();
            } else{
                this.setSharedPreferences();
                Toast.makeText(getBaseContext(), R.string.save_message, Toast.LENGTH_SHORT).show();
                finish();
            }
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
