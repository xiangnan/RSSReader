package com.royole.yogu.rssreader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.royole.yogu.rssreader.app.Constants;

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
        db.execSQL(createTableSQL(Constants.CARS_TABLE, Constants.COLUMNS));
        db.execSQL(createTableSQL(Constants.FINANCES_TABLE, Constants.COLUMNS));
        db.execSQL(createTableSQL(Constants.NEWS_TABLE, Constants.COLUMNS));
        db.execSQL(createTableSQL(Constants.TECHS_TABLE, Constants.COLUMNS));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.CARS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.FINANCES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.NEWS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TECHS_TABLE);
        onCreate(db);
    }

    private String createTableSQL(String tableName, String[] columns) {
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(tableName);
        sb.append(" (");
        sb.append(columns[0]);
        sb.append(" integer primary key autoincrement ");

        for (int i = 1; i < columns.length; i++) {
            sb.append(", ");
            sb.append(columns[i]);
        }
        sb.append(" ) ");
        String sql = sb.toString();
        return sql;
    }
}

