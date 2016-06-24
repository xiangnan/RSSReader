package com.royole.yogu.rssreader.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.royole.yogu.rssreader.R;

/**
 * Entertainment Tab
 * Author  yogu
 * Since  2016/6/22
 */


public class FinanceTabFragment extends BaseTabFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmUrl("http://finance.qq.com/financenews/breaknews/rss_finance.xml");
    }

}
