package com.royole.yogu.rssreader.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.royole.yogu.rssreader.R;
import com.royole.yogu.rssreader.model.Article;

import java.util.List;

/**
 * Google Swipe-to-Refresh widget Adapter
 * Author  yogu
 * Since  2016/6/23
 */


public class SwipeRefreshListAdapter extends BaseAdapter {

    private Activity mActivity;
    private LayoutInflater mInflater;
    private List<Article> mArticles;

    public SwipeRefreshListAdapter(Activity mActivity, List<Article> mArticles) {
        this.mActivity = mActivity;
        this.mArticles = mArticles;
    }

    @Override
    public int getCount() {
        return mArticles.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (mInflater == null)
            mInflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_swipe_refresh, null);
            holder.mTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.mPubTime = (TextView) convertView.findViewById(R.id.tv_pubTime);
            // save layout in convertView in order to get by getTag()
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTitle.setText((String) mArticles.get(position).getTitle());
        holder.mPubTime.setText((String) mArticles.get(position).getPubDate());

        return convertView;
    }

    /**
     * ViewHolder pattern consists in storing a data structure in the tag of the view
     * returned by getView().This data structures contains references to the views we want to bind data to,
     * thus avoiding calling to findViewById() every time getView() is invoked
     */
    private class ViewHolder {
        private TextView mTitle;
        private TextView mPubTime;
    }
}
