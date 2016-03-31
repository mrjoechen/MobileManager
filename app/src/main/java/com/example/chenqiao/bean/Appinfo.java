package com.example.chenqiao.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by CHENQIAO on 2016/3/31.
 */
public class Appinfo {

    String appname;
    Drawable icon;
    boolean isSdcard;  //true 表示装在sdcard false 表示装在ROM中
    boolean isSystem;  //true 表示系统应用， false 表示用户自己安装的应用


    public Appinfo(String appname, Drawable icon, boolean isSdcard) {
        this.appname = appname;
        this.icon = icon;
        this.isSdcard = isSdcard;
    }

    public Appinfo(String appname, Drawable icon, boolean isSdcard, boolean isSystem) {
        this.appname = appname;
        this.icon = icon;
        this.isSdcard = isSdcard;
        this.isSystem = isSystem;
    }

    public String getAppname() {

        return appname;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appname='" + appname + '\'' +
                ", icon=" + icon +
                ", isSdcard=" + isSdcard +
                ", isSystem=" + isSystem +
                '}';
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isSdcard() {
        return isSdcard;
    }

    public void setIsSdcard(boolean isSdcard) {
        this.isSdcard = isSdcard;
    }
}