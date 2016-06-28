package com.royole.yogu.rssreader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.royole.yogu.rssreader.model.Article;

import java.util.ArrayList;
import java.util.List;

import com.royole.yogu.rssreader.app.Constants;


/**
 * Copyright (C) 2015, Royole Corporation all rights reserved.
 * Author  yogu
 * Since  2016/6/24
 */


public class ArticleManager {
    private static String TAG = "ArticleManager";
    private Context mContext;
    private static ArticleManager mArticleManagerInstance = null;

    public static ArticleManager newInstance(Context context) {
        if (mArticleManagerInstance == null) {
            mArticleManagerInstance = new ArticleManager(context.getApplicationContext());
        }

        return mArticleManagerInstance;
    }

    private ArticleManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public long create(Article article, Uri contentURI) {
        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_TITLE, article.getTitle());
        values.put(Constants.COLUMN_DESCRIPTION, article.getDescription());
        values.put(Constants.COLUMN_PUB_DATE, article.getPubDate());
        values.put(Constants.COLUMN_LINK, article.getLink());
        values.put(Constants.COLUMN_AUTHOR, article.getAuthor());
        Uri result = mContext.getContentResolver().insert(contentURI, values);
        long id = Long.parseLong(result.getLastPathSegment());
        return id;
    }
    public void create(List<Article> articles,Uri contentURI){
        for (Article a:articles){
            create(a, contentURI);
        }
    }

    public void update(Article article, Uri contentURI) {
        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_TITLE, article.getTitle());
        values.put(Constants.COLUMN_DESCRIPTION, article.getDescription());
        values.put(Constants.COLUMN_PUB_DATE, article.getPubDate());
        values.put(Constants.COLUMN_LINK, article.getLink());
        values.put(Constants.COLUMN_AUTHOR, article.getAuthor());
        mContext.getContentResolver().update(contentURI,
                values, Constants.COLUMN_ID + "=" + article.getaID(), null);
    }

    public void delete(Article article, Uri contentURI) {
        mContext.getContentResolver().delete(
                contentURI, Constants.COLUMN_ID + "=" + article.getaID(), null);
    }

    public void deleteAll(Uri contentURI) {
        mContext.getContentResolver().delete(
                contentURI,null, null);
    }


    public List<Article> getAllArticles(Uri contentURI) {
        List<Article> articles = new ArrayList<Article>();
        Cursor cursor = mContext.getContentResolver().query(contentURI, Constants.COLUMNS, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                articles.add(Article.getNotefromCursor(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        Log.d(TAG, "fetch " + contentURI + "from database");
        return articles;
    }

    public Article getArticle(Long id, Uri contentURI) {
        Article article;
        Cursor cursor = mContext.getContentResolver().query(contentURI,
                Constants.COLUMNS, Constants.COLUMN_ID + " = " + id, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            article = Article.getNotefromCursor(cursor);
            return article;
        }
        return null;
    }
}
