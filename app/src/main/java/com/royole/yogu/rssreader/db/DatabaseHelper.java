package com.royole.yogu.rssreader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.Constants;

/**
 * Copyright (C) 2015, Royole Corporation all rights reserved.
 * Author  yogu
 * Since  2016/6/24
 */


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "rss_reader_app.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.ARTICLES_TABLE);
        onCreate(db);
    }

    private static final String CREATE_TABLE_NOTE = "create table "
            + Constants.ARTICLES_TABLE
            + "("
            + Constants.COLUMN_ID + " integer primary key autoincrement, "
            + Constants.COLUMN_TITLE + " text not null, "
            + Constants.COLUMN_DESCRIPTION + " text not null, "
            + Constants.COLUMN_PUB_DATE + " text not null, "
            + Constants.COLUMN_LINK + " text not null, "
            + Constants.COLUMN_AUTHOR + " text not null " + ")";
}
