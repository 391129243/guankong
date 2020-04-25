package com.learn.all_electric.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.Map;

public class CommonUtils {

    public static Spanned changeText(String content) {
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//androidN+之后废除了Html.fromHtml(String),用Html.fromHtml(String,flag)代替
            result = Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(content);
        }
        return result;
    }


    public static  boolean isNull(Map<String, Object> map, String key) {
        if (map.get(key) == null) {
            return false;
        }
        return true;
    }

    public static  boolean isNullDouble(Map<String, Double> map, String key) {
        if (map.get(key) == null) {
            return false;
        }
        return true;
    }
}