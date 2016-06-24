package com.royole.yogu.rssreader.utils;

import com.royole.yogu.rssreader.model.Article;
import com.royole.yogu.rssreader.model.BaseObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Android provides three types of XML parsers which are Simple API for XML(SAX)【解析速度快并且占用内存少的xml解析器】 、
 * Document Object Model(DOM)【所有内容读取到内存中遍历XML树】 and Android Native XMLPullParser.【SAX解析器的工作方式是自动将事件推入事件处理器进行处理，不能控制事件的处理主动结束；
 * 而Pull解析器主动从解析器中获取事件，满足了需要的条件后可结束解析。pull是一个while循环，随时可以跳出，而sax是只要解析了，就必须解析完成。】
 * Among all of them android recommend XMLPullParser because it is efficient and easy to use.
 * This class use XMLPullParser for parsing XML
 * Author  yogu
 * Since  2016/6/23
 */


public class XMLUtils {
    public static XmlPullParserFactory xmlFactoryObject;
    /**
     * volatile 变量可以被看作是一种 “程度较轻的 synchronized”；
     * 与 synchronized 块相比，volatile 变量所需的编码较少，并且运行时开销也较少，但是它所能实现的功能也仅是 synchronized 的一部分（只有synchronize的可见性）。
     * 可见性：对一个volatile变量的读，总是能看到（任意线程）对这个volatile变量最后的写入。
     * 原子性：对任意单个volatile变量的读/写具有原子性，但类似于volatile++这种复合操作不具有原子性。
     */
    public static volatile boolean parsingComplete = true;

//    public static <T extends BaseObject> void streamText2Model(T obj, XmlPullParser pullParser) {
//        int eventType;
//        try {
//            eventType = pullParser.getEventType();
//            String[] nodes = obj.getNodes();
//            String nodeName = null;
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                switch (eventType) {
//                    case XmlPullParser.START_DOCUMENT:
//                        break;
//                    case XmlPullParser.START_TAG:
//                        nodeName = pullParser.getName();
//                        break;
//                    case XmlPullParser.TEXT:
//                        for (int i = 0; i < nodes.length; i++) {
//                            if (nodes[i].equals(nodeName)) {
//                                obj.setParamater(nodeName, pullParser.getText());
//                            }
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        break;
//                }
//                eventType = pullParser.next();
//            }
//            parsingComplete = false;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static <T extends BaseObject> List<T> streamParam2Model(T obj, XmlPullParser pullParser) {
        int eventType;
        try {
            eventType = pullParser.getEventType();

            ArrayList<T> objs = null;
            ArrayList<String> nodes = obj.getNodes();
            String beginNode = obj.getBeginNodes();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    // init the data
                    case XmlPullParser.START_DOCUMENT:
                        objs = new ArrayList<T>();
                        break;
                    case XmlPullParser.START_TAG:
                        String name = pullParser.getName();
                        if (null == name || name.equals("")) {
                            break;
                        }
                        if (name.equals(beginNode)) {
                            Constructor<T> constructor = (Constructor<T>) obj.getClass().getConstructor();
                            obj = constructor.newInstance();
                            eventType = pullParser.next();
                            while (!(eventType == XmlPullParser.END_TAG && pullParser.getName().equals(beginNode))) {
                                if (eventType == XmlPullParser.START_TAG) {
                                    int i = nodes.indexOf(pullParser.getName());
                                    if (i != -1) {
                                        obj.setParamater(nodes.get(i), pullParser.nextText());//nextText() get the text between tag,while the eventType must be START_TAG
                                    }
                                }
                                eventType = pullParser.next();
                            }
                            objs.add(obj);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = pullParser.next();
            }
            parsingComplete = false;
            return objs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getArticleXML(final String urlString, final List<Article> articles) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream stream = conn.getInputStream();
                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, "gb2312");

                    articles.addAll(streamParam2Model(new Article(), myparser));
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();
    }
}
