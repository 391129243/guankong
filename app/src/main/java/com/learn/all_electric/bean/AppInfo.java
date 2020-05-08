package com.learn.all_electric.bean;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.learn.all_electric.utils.StringUtils;

public class AppInfo implements Comparable<AppInfo>{
    public String appName = "";
    public String packageName = "";
    public String versionName = "";
    public int versionCode = 0;

    public void print(){
        Log.v("app","Name:"+appName+" Package:"+packageName);
        Log.v("app","Name:"+appName+" versionName:"+versionName);
        Log.v("app","Name:"+appName+" versionCode:"+versionCode);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }


    @Override
    public int compareTo(AppInfo appInfo) {
        int i = StringUtils.convertToInt(this.getAppName().substring(0,1),0) - StringUtils.convertToInt(appInfo.getAppName().substring(0,1),0);//先按照年龄排序
        return i;
    }
}
