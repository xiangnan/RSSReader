package com.royole.yogu.rssreader.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.royole.yogu.rssreader.R;

/**
 * Sports Tab
 * Author  yogu
 * Since  2016/6/22
 */


public class CarTabFragment extends BaseTabFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUrlAndTitle("http://auto.qq.com/gouche/hangqing09/rss.xml",getResources().getStringArray(R.array.tabs)[1]);
    }
}
