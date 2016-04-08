package com.app.k2app.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.k2app.fragments.FragmentPerfil;

public class AdapterViewPagerPerfil extends FragmentStatePagerAdapter {

    //CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    CharSequence Titles[] = {"PERFIL"};
    //int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    int NUM_TABS = 1;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public AdapterViewPagerPerfil(FragmentManager fm) {
        super(fm);
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentPerfil();
            default:
                return new FragmentPerfil();

        }
    }

    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return NUM_TABS;
    }
}