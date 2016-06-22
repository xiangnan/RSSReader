package com.royole.yogu.rssreader.fragment;

import android.support.v4.app.Fragment;

import com.royole.yogu.rssreader.activity.BaseActivity;

/**
 * Created by xiangnan on 16/6/22.
 */
public class BaseFragment extends Fragment {
    protected BaseActivity mActivity;

    public BaseFragment() {
    }

    public BaseFragment(BaseActivity mActivity) {
        this.mActivity = mActivity;
    }
}
