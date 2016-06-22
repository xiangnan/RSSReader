package com.royole.yogu.rssreader.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.royole.yogu.rssreader.R;

/**
 * Entertainment Tab
 * Author  yogu
 * Since  2016/6/22
 */


public class EntertainmentTabFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entertainment_tab, container, false);
    }
}
