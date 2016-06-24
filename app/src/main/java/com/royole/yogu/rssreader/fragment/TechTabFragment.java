package com.royole.yogu.rssreader.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.royole.yogu.rssreader.R;

/**
 * Tech Tab
 * Author  yogu
 * Since  2016/6/22
 */


public class TechTabFragment extends BaseTabFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUrlAndTitle("http://tech.qq.com/web/webnews/rss_11.xml",getResources().getStringArray(R.array.tabs)[3]);
    }
}
