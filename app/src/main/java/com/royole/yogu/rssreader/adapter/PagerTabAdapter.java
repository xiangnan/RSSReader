package com.royole.yogu.rssreader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.royole.yogu.rssreader.fragment.EntertainmentTabFragment;
import com.royole.yogu.rssreader.fragment.NewsTabFragment;
import com.royole.yogu.rssreader.fragment.SportsTabFragment;
import com.royole.yogu.rssreader.fragment.TechTabFragment;

/**
 * Tab adapter
 * Author  yogu
 * Since  2016/6/22
 */


public class PagerTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerTabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                NewsTabFragment newsTab = new NewsTabFragment();
                return newsTab;
            case 1:
                SportsTabFragment sportsTab = new SportsTabFragment();
                return sportsTab;
            case 2:
                EntertainmentTabFragment entertainmentTab = new EntertainmentTabFragment();
                return entertainmentTab;
            case 3:
                TechTabFragment techTab = new TechTabFragment();
                return techTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
