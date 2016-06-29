package com.royole.yogu.rssreader.model;

import android.database.Cursor;
import android.text.TextUtils;

import java.util.ArrayList;

import com.royole.yogu.rssreader.app.Constants;

/**
 * News Article Model
 * Author  yogu
 * Since  2016/6/23
 */


public class Article extends BaseObject{
    private long aID;
    /** 新闻标题 */
    private String title;
    /** 描述 */
    private String description;
    /** 发布时间 */
    private String pubDate;
    /** 新闻详情链接 */
    private String link;
    /** 作者 */
    private String author;

    public Article() {
    }
    public static Article getNotefromCursor(Cursor cursor){
        Article article = new Article();
        article.setaID(cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_ID)));
        article.setTitle(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TITLE)));
        article.setDescription(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_DESCRIPTION)));
        article.setPubDate(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_PUB_DATE)));
        article.setLink(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_LINK)));
        article.setAuthor(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_AUTHOR)));
        return article;
    }

    public Article(long aID, String title, String description, String pubDate, String link, String author) {
        this.aID = aID;
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
        this.author = author;
    }

    public void setaID(long aID) {
        this.aID = aID;
    }

    public long getaID() {
        return aID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        if (TextUtils.isEmpty(pubDate)){
            return pubDate;
        }else {
            return pubDate.substring(0, 10);
        }
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", link='" + link + '\'' +
                ", author='" + author + '\'' +
                '}';
    }


    // Override BaseObject
    @Override
    public ArrayList<String> getNodes() {
        ArrayList<String> nodes = new ArrayList<String>(){{add("title");add( "description");add("pubDate");add( "link"); add("author");}};
        return nodes;
    }

    @Override
    public String getBeginNodes() {
        return "item";
    }

    // End Override BaseObject
}
