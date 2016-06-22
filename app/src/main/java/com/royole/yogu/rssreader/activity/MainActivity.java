package com.royole.yogu.rssreader.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.royole.yogu.rssreader.R;
import com.royole.yogu.rssreader.adapter.PagerTabAdapter;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerTabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("新闻"));
        tabLayout.addTab(tabLayout.newTab().setText("体育"));
        tabLayout.addTab(tabLayout.newTab().setText("娱乐"));
        tabLayout.addTab(tabLayout.newTab().setText("科技"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerTabAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
