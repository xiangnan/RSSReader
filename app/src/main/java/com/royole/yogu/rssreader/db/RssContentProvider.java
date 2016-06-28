package com.royole.yogu.rssreader.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

import com.royole.yogu.rssreader.app.Constants;

/**
 * 1.SharedPreferences– this tools is used to save primitive data in Android. Primitive data types are data that has been decomposed into units that are native to the programming language (Java in this case).
 * 2.Device Storage – if you want to save the pictures to your device
 * 3.SQLite
 * 4.File
 * 5.Network
 * Author  yogu
 * Since  2016/6/24
 */


public class RssContentProvider extends ContentProvider {
    private DatabaseHelper dbHelper;

    private static final String CAR_PATH_ARTICLE = "car_article";
    private static final String FINANCE_PATH_ARTICLE = "finance_article";
    private static final String NEWS_PATH_ARTICLE = "news_article";
    private static final String TECH_PATH_ARTICLE = "tech_article";
    //The <authority> is the name of the provider.The package and the name of the class (made that way to be sure that it's a unique address).
    private static final String AUTHORITY = "com.royole.yogu.rssreader.db";
    public static final Uri CAR_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CAR_PATH_ARTICLE);
    public static final Uri FINANCE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + FINANCE_PATH_ARTICLE);
    public static final Uri NEWS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + NEWS_PATH_ARTICLE);
    public static final Uri TECH_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TECH_PATH_ARTICLE);
    private static final int CAR = 100;
    private static final int CARS = 101;
    private static final int FINANCE = 102;
    private static final int FINANCES = 103;
    private static final int NEW = 104;
    private static final int NEWS = 105;
    private static final int TECH = 106;
    private static final int TECHS = 107;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, CAR_PATH_ARTICLE, CARS);
        URI_MATCHER.addURI(AUTHORITY, CAR_PATH_ARTICLE + "/#", CAR);
        URI_MATCHER.addURI(AUTHORITY, FINANCE_PATH_ARTICLE, FINANCES);
        URI_MATCHER.addURI(AUTHORITY, FINANCE_PATH_ARTICLE + "/#", FINANCE);
        URI_MATCHER.addURI(AUTHORITY, NEWS_PATH_ARTICLE, NEWS);
        URI_MATCHER.addURI(AUTHORITY, NEWS_PATH_ARTICLE + "/#", NEW);
        URI_MATCHER.addURI(AUTHORITY, TECH_PATH_ARTICLE, TECHS);
        URI_MATCHER.addURI(AUTHORITY, TECH_PATH_ARTICLE + "/#", TECH);

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
        switch (type) {
            case CARS:
                queryBuilder.setTables(Constants.CARS_TABLE);
                break;
            case CAR:
                queryBuilder.setTables(Constants.CARS_TABLE);
                queryBuilder.appendWhere(Constants.COLUMN_ID + " = " + uri.getLastPathSegment());
                break;
            case FINANCES:
                queryBuilder.setTables(Constants.FINANCES_TABLE);
                break;
            case FINANCE:
                queryBuilder.setTables(Constants.FINANCES_TABLE);
                queryBuilder.appendWhere(Constants.COLUMN_ID + " = " + uri.getLastPathSegment());
                break;
            case NEWS:
                queryBuilder.setTables(Constants.NEWS_TABLE);
                break;
            case NEW:
                queryBuilder.setTables(Constants.NEWS_TABLE);
                queryBuilder.appendWhere(Constants.COLUMN_ID + " = " + uri.getLastPathSegment());
                break;
            case TECHS:
                queryBuilder.setTables(Constants.TECHS_TABLE);
                break;
            case TECH:
                queryBuilder.setTables(Constants.TECHS_TABLE);
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
        Uri mUri = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Long id;
        switch (type) {
            case CARS:
                id = db.insert(Constants.CARS_TABLE, null, values);
                if (id > 0) {
                    mUri = ContentUris.withAppendedId(CAR_CONTENT_URI, id);
                }
                break;
            case FINANCES:
                id = db.insert(Constants.FINANCES_TABLE, null, values);
                if (id > 0) {
                    mUri = ContentUris.withAppendedId(FINANCE_CONTENT_URI, id);
                }
                break;
            case NEWS:
                id = db.insert(Constants.NEWS_TABLE, null, values);
                if (id > 0) {
                    mUri = ContentUris.withAppendedId(NEWS_CONTENT_URI, id);
                }
                break;
            case TECHS:
                id = db.insert(Constants.TECHS_TABLE, null, values);
                if (id > 0) {
                    mUri = ContentUris.withAppendedId(TECH_CONTENT_URI, id);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(mUri, null);
        return mUri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int type = URI_MATCHER.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int affectedRows;
        String id = uri.getLastPathSegment();
        switch (type) {
            case CARS:
                affectedRows = db.delete(Constants.CARS_TABLE, selection, selectionArgs);
                break;
            case CAR:
                if (TextUtils.isEmpty(selection)) {
                    affectedRows = db.delete(Constants.CARS_TABLE, Constants.COLUMN_ID + "=" + id, null);
                } else {
                    affectedRows = db.delete(Constants.CARS_TABLE, Constants.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            case FINANCES:
                affectedRows = db.delete(Constants.FINANCES_TABLE, selection, selectionArgs);
                break;

            case FINANCE:
                if (TextUtils.isEmpty(selection)) {
                    affectedRows = db.delete(Constants.FINANCES_TABLE, Constants.COLUMN_ID + "=" + id, null);
                } else {
                    affectedRows = db.delete(Constants.FINANCES_TABLE, Constants.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            case NEWS:
                affectedRows = db.delete(Constants.NEWS_TABLE, selection, selectionArgs);
                break;
            case NEW:
                if (TextUtils.isEmpty(selection)) {
                    affectedRows = db.delete(Constants.NEWS_TABLE, Constants.COLUMN_ID + "=" + id, null);
                } else {
                    affectedRows = db.delete(Constants.NEWS_TABLE, Constants.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            case TECHS:
                affectedRows = db.delete(Constants.TECHS_TABLE, selection, selectionArgs);
                break;
            case TECH:
                if (TextUtils.isEmpty(selection)) {
                    affectedRows = db.delete(Constants.TECHS_TABLE, Constants.COLUMN_ID + "=" + id, null);
                } else {
                    affectedRows = db.delete(Constants.TECHS_TABLE, Constants.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
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
        String id = uri.getLastPathSegment();
        switch (type) {
            case CARS:
                affectedRows = db.update(Constants.CARS_TABLE, values, selection, selectionArgs);
                break;
            case CAR:
                if (TextUtils.isEmpty(selection)) {
                    affectedRows = db.update(Constants.CARS_TABLE, values, Constants.COLUMN_ID + "=" + id, null);
                } else {
                    affectedRows = db.update(Constants.CARS_TABLE, values, Constants.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            case FINANCES:
                affectedRows = db.update(Constants.FINANCES_TABLE, values, selection, selectionArgs);
                break;
            case FINANCE:
                if (TextUtils.isEmpty(selection)) {
                    affectedRows = db.update(Constants.FINANCES_TABLE, values, Constants.COLUMN_ID + "=" + id, null);
                } else {
                    affectedRows = db.update(Constants.FINANCES_TABLE, values, Constants.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            case NEWS:
                affectedRows = db.update(Constants.NEWS_TABLE, values, selection, selectionArgs);
                break;
            case NEW:
                if (TextUtils.isEmpty(selection)) {
                    affectedRows = db.update(Constants.NEWS_TABLE, values, Constants.COLUMN_ID + "=" + id, null);
                } else {
                    affectedRows = db.update(Constants.NEWS_TABLE, values, Constants.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            case TECHS:
                affectedRows = db.update(Constants.TECHS_TABLE, values, selection, selectionArgs);
                break;
            case TECH:
                if (TextUtils.isEmpty(selection)) {
                    affectedRows = db.update(Constants.TECHS_TABLE, values, Constants.COLUMN_ID + "=" + id, null);
                } else {
                    affectedRows = db.update(Constants.TECHS_TABLE, values, Constants.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affectedRows;
    }
}
