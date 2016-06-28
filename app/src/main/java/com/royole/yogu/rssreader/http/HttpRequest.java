package com.royole.yogu.rssreader.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Copyright (C) 2015, Royole Corporation all rights reserved.
 * Author  yogu
 * Since  2016/6/28
 */


public class HttpRequest {
    public interface Handler {
        void response(HttpResponse response);
    }

    public final static String OPTIONS = "OPTIONS";
    public final static String GET = "GET";
    public final static String HEAD = "HEAD";
    public final static String POST = "POST";
    public final static String PUT = "PUT";
    public final static String DELETE = "DELETE";
    public final static String TRACE = "TRACE";

    private final String url;
    private final String method;
    private final String json;
    private final String authorization;

    public HttpRequest(String url, String method) {
        this(url, method, null, null);
    }

    public HttpRequest(String url, String method, String json) {
        this(url, method, json, null);
    }

    public HttpRequest(String url, String method, String json, String authorization) {
        this.url = url;
        this.method = method;
        this.json = json;
        this.authorization = authorization;
    }

    public HttpResponse request()  {

        HttpResponse response = new HttpResponse();

        HttpURLConnection con;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }

        try {
            if(method != null){
                con.setRequestMethod(method);
            }

            if(authorization != null) {
                con.setRequestProperty("Authorization", this.authorization);
            }

            if(json != null){
                con.setDoInput(true);// can use con.getInputStream().read(); Default is true
                con.setDoOutput(true);//can use con.getOutputStream().write(); Default is false
                final byte[] bytes = json.getBytes("UTF-8");
                /**
                 * multipart/form-data 用于发送二进制的文件，不对字符编码，其他两种类型不能用于发送文件
                 * text/plain 用于发送纯文本内容，空格转换为 "+" 加号，但不对特殊字符编码，一般用于email之类的
                 * application/x-www-form-urlencoded 用于发送html内容，在发送前编码所有字符（默认）
                 */
                con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                /**
                 * text/html是html格式的正文
                 * text/plain是无格式正文
                 * text/xml忽略xml头所指定编码格式而默认采用us-ascii编码
                 * application/xml会根据xml头指定的编码格式来编码：
                 */
                con.setRequestProperty("Accept", "application/xml");
                con.setRequestProperty("Content-Length", "" + bytes.length);

                IO.write(con.getOutputStream(), bytes);
            }

            response.code = con.getResponseCode();
            response.body = IO.read(con.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
        return response;
    }
}
