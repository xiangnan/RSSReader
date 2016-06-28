package com.royole.yogu.rssreader.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Copyright (C) 2015, Royole Corporation all rights reserved.
 * Author  yogu
 * Since  2016/6/28
 */


public class IO {
    public static void write(OutputStream os, byte[] body) throws IOException {
        os.write(body);
        os.close();
    }

    public static String read(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "gb2312"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
