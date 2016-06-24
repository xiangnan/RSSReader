package com.royole.yogu.rssreader.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.royole.yogu.rssreader.R;

public class ArticleDetaisActivity extends BaseActivity {

    private static final String TAG = ArticleDetaisActivity.class.getName();

    private WebView mDetailsWebView;
    private FrameLayout mWebViewLayout;
    private String mURL;

    // lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detais);
        Intent intent = getIntent();
        mURL = intent.getStringExtra("url");
        initTitleBar(intent.getStringExtra("title"), true);
        initView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        mWebViewLayout.removeAllViews();
        mDetailsWebView.stopLoading();
        mDetailsWebView.removeAllViews();
        mDetailsWebView.destroy();
        mDetailsWebView = null;
        mWebViewLayout = null;
        super.onDestroy();
    }
    // End lifecycle

    private void initView(Bundle savedInstanceState) {
        mWebViewLayout = (FrameLayout) findViewById(R.id.webViewContainer);
        mDetailsWebView = new WebView(getApplicationContext());
        WebSettings setting = mDetailsWebView.getSettings();
        setting.setJavaScriptEnabled(true);// js enable
        setting.setBuiltInZoomControls(true);
        setting.setAppCacheEnabled(true);// h5 cache enable
        setting.setDisplayZoomControls(true);
        setting.setUseWideViewPort(true);// fit screen automatically
        mWebViewLayout.addView(mDetailsWebView);

        if (null != savedInstanceState) {
            mDetailsWebView.restoreState(savedInstanceState);
        } else {
            mDetailsWebView.loadUrl(mURL);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            // chromium, enable hardware acceleration
            mDetailsWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            mDetailsWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mDetailsWebView.setWebViewClient(new ArticleWebViewClient());
    }

    private class ArticleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }


    }


}
