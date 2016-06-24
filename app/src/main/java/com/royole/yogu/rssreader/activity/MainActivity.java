package com.royole.yogu.rssreader.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.royole.yogu.rssreader.R;
import com.royole.yogu.rssreader.adapter.PagerTabAdapter;

public class MainActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerTabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTitleBar("RSSReader", false);
        initTabView();
    }

    /**
     * ViewPager + FragmentStatePagerAdapter + TabLayout implement TabHost
     */
    private void initTabView() {

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        String[] tabs = getResources().getStringArray(R.array.tabs);
        for (String tab : tabs) {
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerTabAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        /**
         * Set the number of pages that should be retained to either side of the current page in the view hierarchy in an idle state. Pages beyond this limit will be recreated from the adapter when needed.
         This is offered as an optimization. If you know in advance the number of pages you will need to support or have lazy-loading mechanisms in place on your pages,
         tweaking this setting can have benefits in perceived smoothness of paging animations and interaction. If you have a small number of pages (3-4) that you can keep active all at once,
         less time will be spent in layout for newly created view subtrees as the user pages back and forth.
         You should keep this limit low, especially if your pages have complex layouts. This setting defaults to 1.
         */
        viewPager.setOffscreenPageLimit(4);
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
