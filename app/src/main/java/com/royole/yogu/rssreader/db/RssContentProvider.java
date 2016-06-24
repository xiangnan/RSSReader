package com.royole.yogu.rssreader.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

import app.Constants;

/**
 * 1.SharedPreferences– this tools is used to save primitive data in Android. Primitive data types are data that has been decomposed into units that are native to the programming language (Java in this case).
 * 2.Device Storage – if you want to save the pictures to your device
 * 3.SQLite database
 * 4.File
 * 5.Network
 * Author  yogu
 * Since  2016/6/24
 */


public class RssContentProvider extends ContentProvider {
    private DatabaseHelper dbHelper;

    private static final String BASE_PATH_ARTICLE = "article";
    private static final String AUTHORITY = "com.royole.yogu.rssreader.db";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_ARTICLE);
    private static final int ARTICLE = 100;
    private static final int ARTICLES = 101;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH_ARTICLE, ARTICLES);
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH_ARTICLE + "/#", ARTICLE);

    }

    private void checkColumns(String[] projection) {
        if (projection != null) {
            HashSet<String> request = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> available = new HashSet<String>(Arrays.asList(Constants.COLUMNS));
            if (!available.containsAll(request)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        checkColumns(projection);

        int type = URI_MATCHER.match(uri);
        switch (type){
            case ARTICLES:
                queryBuilder.setTables(Constants.ARTICLES_TABLE);
                break;
            case ARTICLE:
                queryBuilder.setTables(Constants.ARTICLES_TABLE);
                queryBuilder.appendWhere(Constants.COLUMN_ID + " = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int type = URI_MATCHER.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Long id;
        switch (type){
            case ARTICLES:
                id = db.insert(Constants.ARTICLES_TABLE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH_ARTICLE + "/" + id);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int type = URI_MATCHER.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int affectedRows;
        switch (type) {
            case ARTICLES:
                affectedRows = db.delete(Constants.ARTICLES_TABLE, selection, selectionArgs);
                break;

            case ARTICLE:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    affectedRows = db.delete(Constants.ARTICLES_TABLE, Constants.COLUMN_ID + "=" + id, null);
                } else {
                    affectedRows = db.delete(Constants.ARTICLES_TABLE, Constants.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affectedRows;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int type = URI_MATCHER.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int affectedRows;
        switch (type) {
            case ARTICLES:
                affectedRows = db.update(Constants.ARTICLES_TABLE, values, selection, selectionArgs);
                break;

            case ARTICLE:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    affectedRows = db.update(Constants.ARTICLES_TABLE, values, Constants.COLUMN_ID + "=" + id, null);
                } else {
                    affectedRows = db.update(Constants.ARTICLES_TABLE, values, Constants.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affectedRows;
    }
}
