package com.royole.yogu.rssreader.model;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * BaseObject used for reflecting
 * Author  yogu
 * Since  2016/6/23
 */


public abstract class BaseObject {
    public abstract ArrayList<String> getNodes();
    public abstract String getBeginNodes();
    public void setParamater(String tag, Object value) {
        try {
            //Field field = getClass().getField(tag);//warning: getField(String name) get the public fields only.
            Field field = getClass().getDeclaredField(tag);
            field.setAccessible(true);//required if field is not normally accessible
            field.set(this, value);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
