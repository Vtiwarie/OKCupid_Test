package com.vishaan.okcupid.adapters;

/**
 * Created by Vishaan on 2/13/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Pager Adapter class used to bind fragment views with the ViewPager
 * that is used with a tab layout.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {
    /**
     * Array of fragments.  Use array instead of lists because size is fixed
     */
    private Fragment[] mFragments;

    /**
     * Array of tab titles.  Use array instead of lists because size is fixed
     */
    private String[] mTitles;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Set tab titles
     *
     * @param titles
     */
    public void setTitles(String[] titles) {
        mTitles = titles;
    }

    /**
     * Add fragments to be used for display in the viewpager
     *
     * @param fragments to add
     */
    public void setFragments(Fragment[] fragments) {
        mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}