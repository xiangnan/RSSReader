package com.royole.yogu.rssreader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.royole.yogu.rssreader.model.Article;

import java.util.ArrayList;
import java.util.List;

import app.Constants;


/**
 * Copyright (C) 2015, Royole Corporation all rights reserved.
 * Author  yogu
 * Since  2016/6/24
 */


public class ArticleManager {
    private Context mContext;
    private static ArticleManager mArticleManagerInstance = null;

    public static ArticleManager newInstance(Context context){

        if (mArticleManagerInstance == null){
            mArticleManagerInstance = new ArticleManager(context.getApplicationContext());
        }

        return mArticleManagerInstance;
    }

    private ArticleManager(Context context){
        this.mContext = context.getApplicationContext();
    }

    public long create(Article article) {
        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_TITLE, article.getTitle());
        values.put(Constants.COLUMN_DESCRIPTION, article.getDescription());
        values.put(Constants.COLUMN_PUB_DATE, article.getPubDate());
        values.put(Constants.COLUMN_LINK, article.getLink());
        values.put(Constants.COLUMN_AUTHOR, article.getAuthor());
        Uri result = mContext.getContentResolver().insert(RssContentProvider.CONTENT_URI, values);
        long id = Long.parseLong(result.getLastPathSegment());
        return id;
    }

    public void update(Article article) {
        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_TITLE, article.getTitle());
        values.put(Constants.COLUMN_DESCRIPTION, article.getDescription());
        values.put(Constants.COLUMN_PUB_DATE, article.getPubDate());
        values.put(Constants.COLUMN_LINK, article.getLink());
        values.put(Constants.COLUMN_AUTHOR, article.getAuthor());
        mContext.getContentResolver().update(RssContentProvider.CONTENT_URI,
                values, Constants.COLUMN_ID  + "=" + article.getaID(), null);
    }

    public void delete(Article article) {
        mContext.getContentResolver().delete(
                RssContentProvider.CONTENT_URI, Constants.COLUMN_ID + "=" + article.getaID(), null);
    }

    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<Article>();
        Cursor cursor = mContext.getContentResolver().query(RssContentProvider.CONTENT_URI, Constants.COLUMNS, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                articles.add(Article.getNotefromCursor(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return articles;
    }

    public Article getArticle(Long id) {
        Article article;
        Cursor cursor = mContext.getContentResolver().query(RssContentProvider.CONTENT_URI,
                Constants.COLUMNS, Constants.COLUMN_ID + " = " + id, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            article = Article.getNotefromCursor(cursor);
            return article;
        }
        return null;
    }
}
