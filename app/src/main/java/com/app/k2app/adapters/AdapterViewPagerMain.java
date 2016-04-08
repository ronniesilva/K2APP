package com.app.k2app.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.k2app.fragments.FragmentTab1;
import com.app.k2app.fragments.FragmentTab2;
import com.app.k2app.fragments.FragmentTab3;

public class AdapterViewPagerMain extends FragmentStatePagerAdapter {

    //CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    CharSequence Titles[] = {"FEED", "In REACH", "CONTACTS"};
    //int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    int NUM_TABS = 3;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public AdapterViewPagerMain(FragmentManager fm) {
        super(fm);
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentTab1();
            case 1:
                return new FragmentTab2();
            case 2:
                return new FragmentTab3();
            default:
                return new FragmentTab1();

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