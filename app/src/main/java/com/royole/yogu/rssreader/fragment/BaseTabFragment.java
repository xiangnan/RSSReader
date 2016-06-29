package com.royole.yogu.rssreader.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.royole.yogu.rssreader.R;
import com.royole.yogu.rssreader.activity.ArticleDetaisActivity;
import com.royole.yogu.rssreader.adapter.SwipeRefreshListAdapter;
import com.royole.yogu.rssreader.app.Constants;
import com.royole.yogu.rssreader.db.ArticleManager;
import com.royole.yogu.rssreader.db.RssContentProvider;
import com.royole.yogu.rssreader.http.HttpRequest;
import com.royole.yogu.rssreader.http.HttpRequestTask;
import com.royole.yogu.rssreader.http.HttpResponse;
import com.royole.yogu.rssreader.http.NetWorkUtil;
import com.royole.yogu.rssreader.model.Article;
import com.royole.yogu.rssreader.utils.XMLUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;


/**
 * All the tab fragment should implement from this base
 * Created by xiangnan on 16/6/22.
 */
public class BaseTabFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private String TAG = BaseTabFragment.class.getSimpleName();

    /**
     * Enums often require more than twice as much memory as static constants. You should strictly avoid using enums on Android.
     */
    //1 define the constant
    public static final int NEWS_TAB = 0;
    public static final int CAR_TAB = 1;
    public static final int FINANCE_TAB = 2;
    public static final int TECH_TAB = 3;

    // 2 @IntDef include the constant
    @IntDef({CAR_TAB, FINANCE_TAB, NEWS_TAB, TECH_TAB})
    // 3 @Retention define the policy
    @Retention(RetentionPolicy.SOURCE)
    // 4 declare the constructor
    public @interface Tab {
    }
    // End @IntDef Enum

    private String mUrl;// Tab URL
    private String mTab;// Tab Name
    private Uri mUri;// Tab Content Provider Uri
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private SwipeRefreshListAdapter mAdapter;
    private List<Article> mArticles = new ArrayList<Article>();

    public void setUrlAndTitle(String url, @Tab int tab) {
        this.mUrl = url;
        switch (tab) {
            case CAR_TAB:
                mTab = getResources().getStringArray(R.array.tabs)[CAR_TAB];
                mUri = RssContentProvider.CAR_CONTENT_URI;
                break;
            case FINANCE_TAB:
                mTab = getResources().getStringArray(R.array.tabs)[FINANCE_TAB];
                mUri = RssContentProvider.FINANCE_CONTENT_URI;
                break;
            case NEWS_TAB:
                mTab = getResources().getStringArray(R.array.tabs)[NEWS_TAB];
                mUri = RssContentProvider.NEWS_CONTENT_URI;
                break;
            case TECH_TAB:
                mTab = getResources().getStringArray(R.array.tabs)[TECH_TAB];
                mUri = RssContentProvider.TECH_CONTENT_URI;
                break;
            default:
                break;
        }
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
        // already avoid call onCreateView when switch the tab in MainActivity
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
                                         getFromLocalCache();
                                         fetchArticles();
                                     }
                                 }
        );
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BaseTabFragment.this.getActivity(), ArticleDetaisActivity.class);
                Bundle data = new Bundle();
                data.putString("url", mArticles.get(position).getLink());
                data.putString("title", mTab);
                intent.putExtras(data);
                BaseTabFragment.this.getActivity().startActivity(intent);
            }
        });
    }

    private void updateLocalCache() {
        ArticleManager.newInstance(getActivity()).deleteAll(mUri);
        ArticleManager.newInstance(getActivity()).create(mArticles, mUri);
    }

    /**
     * get articles list from database first
     */
    private void getFromLocalCache() {
        mArticles.addAll(ArticleManager.newInstance(getActivity()).getAllArticles(mUri));
        mAdapter.notifyDataSetChanged();
    }

    /**
     * !!! To optimize !!!
     */
    private void fetchArticles() {
        if (NetWorkUtil.isNetwork(this.getContext())) {
            // showing refresh animation before making http call
            mSwipeRefreshLayout.setRefreshing(true);
            new HttpRequestTask(
                    new HttpRequest(mUrl, HttpRequest.GET),
                    new HttpRequest.Handler() {
                        @Override
                        public void response(HttpResponse response) {
                            if (response.code == 200) {
                                try {
                                    mArticles.clear();
                                    Log.d(TAG, "response:" + response.body);
                                    if (!TextUtils.isEmpty(response.body)) {
                                        mArticles.addAll(XMLUtils.streamToArticles(new ByteArrayInputStream(response.body.getBytes("gb2312"))));
                                    }
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                            mAdapter.notifyDataSetChanged();
                            // stopping swipe refresh
                            mSwipeRefreshLayout.setRefreshing(false);
                            updateLocalCache();

                        }
                    }).execute();
        }
    }

    // Private - M

    // Implement SwipeRefreshLayout.OnRefreshListener
    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh...");
        fetchArticles();
    }
    // End SwipeRefreshLayout.OnRefreshListener
}
