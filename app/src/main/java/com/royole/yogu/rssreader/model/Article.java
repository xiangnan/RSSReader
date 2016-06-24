package com.royole.yogu.rssreader.model;

import java.util.ArrayList;

/**
 * News Article Model
 * Author  yogu
 * Since  2016/6/23
 */


public class Article extends BaseObject{
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

    public Article(String title, String description, String pubDate, String link, String author) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
        this.author = author;
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
        return pubDate;
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
