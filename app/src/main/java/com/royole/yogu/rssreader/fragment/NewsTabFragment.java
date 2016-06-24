package com.royole.yogu.rssreader.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.royole.yogu.rssreader.R;

/**
 * News Tab
 * Author  yogu
 * Since  2016/6/22
 */


public class NewsTabFragment extends BaseTabFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmUrl("http://news.qq.com/newsgn/rss_newsgn.xml");
    }
}
