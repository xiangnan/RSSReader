package com.royole.yogu.rssreader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.royole.yogu.rssreader.R;

/**
 * All the Activity should implement from this Base
 * Created by xiangnan on 16/6/22.
 */
public class BaseActivity extends AppCompatActivity {
    protected Toolbar titleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * init TitleBar( This titleBar use Android Toolbar )
     * @param title
     * @param isBackBtnShowing if the navigation back Button showing
     */
    protected void initTitleBar(String title,Boolean isBackBtnShowing) {
        titleBar = (Toolbar) findViewById(R.id.toolbar);
        titleBar.setTitle(title);
        setSupportActionBar(titleBar);
        if(isBackBtnShowing) {
            titleBar.setNavigationIcon(R.mipmap.back);
            titleBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseActivity.this.finish();
                }
            });
        }
    }


    /**
     *
     * @param aTargetClass
     * @param aBundle
     */
    public void startOtherActivity(Class<?> aTargetClass, Bundle aBundle) {
        Intent intent = new Intent(this, aTargetClass);
        if (aBundle != null) {
            intent.putExtras(aBundle);
        }
        startActivity(intent);
    }

    /**
     * @param aTargetClass
     * @param aBundle
     * @param aRequestCode
     */
    protected void startOtherActivity(Class<?> aTargetClass, Bundle aBundle,
                                      int aRequestCode) {
        Intent i = new Intent(this, aTargetClass);
        if (aBundle != null) {
            i.putExtras(aBundle);
        }
        startActivityForResult(i, aRequestCode);
    }
}
