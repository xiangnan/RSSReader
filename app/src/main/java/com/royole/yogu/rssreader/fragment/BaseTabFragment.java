package com.royole.yogu.rssreader.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.royole.yogu.rssreader.R;
import com.royole.yogu.rssreader.adapter.SwipeRefreshListAdapter;
import com.royole.yogu.rssreader.model.Article;
import com.royole.yogu.rssreader.utils.XMLUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * All the tab fragment should implement from this base
 * Created by xiangnan on 16/6/22.
 */
public class BaseTabFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private String TAG = BaseTabFragment.class.getSimpleName();

    private String mUrl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private SwipeRefreshListAdapter mAdapter;
    private List<Article> mArticles = new ArrayList<Article>();

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    // lifecycle
    // onCreate before onCreateView in fragment's lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView...");
        View rootView = inflater.inflate(R.layout.fragment_base, container, false);
        initView(rootView);
        return rootView;
    }
    // End lifecycle

    // Private Method
    private void initView(View v) {
        mListView = (ListView) v.findViewById(R.id.lv_articles);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.layout_swipe_refresh);

        mArticles = new ArrayList<>();
        mAdapter = new SwipeRefreshListAdapter(this.getActivity(), mArticles);
        mListView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {
                                     @Override
                                     public void run() {
                                         mSwipeRefreshLayout.setRefreshing(true);

                                         fetchArticles();
                                     }
                                 }
        );
    }

    private void fetchArticles() {
        // showing refresh animation before making http call
        mSwipeRefreshLayout.setRefreshing(true);
        XMLUtils.getArticleXML(mUrl, mArticles);
        mAdapter.notifyDataSetChanged();
        // stopping swipe refresh
        mSwipeRefreshLayout.setRefreshing(false);
    }

    // Private - M

    // Implement SwipeRefreshLayout.OnRefreshListener
    @Override
    public void onRefresh() {
        Log.d(TAG,"onRefresh...");
        fetchArticles();
    }
    // End SwipeRefreshLayout.OnRefreshListener
}
