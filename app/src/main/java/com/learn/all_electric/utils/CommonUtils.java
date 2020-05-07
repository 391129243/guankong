package com.learn.all_electric.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import com.learn.all_electric.bean.AppInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /**
     * 获取机台序列号
     */
    @SuppressLint({"NewApi", "MissingPermission"})
    public static String getSerialNumber(){
        String serialNo = "";
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {//9.0+
                serialNo = Build.getSerial();

            }else if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {//8.0+

                serialNo = Build.SERIAL;
            }else {
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);
                serialNo = (String) get.invoke(c, "ro.serialno");
            }
        }catch (Exception e){
            e.printStackTrace();;
            LogUtil.i("getSerialNumber","获取设备序列号异常"+e.toString());
        }
        return serialNo;
    }


    /***
     * 获取版本号
     * versionCode
     */
    public static int getAppVersionCode(Context context){
        int versionCode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            versionCode = pi.versionCode;

        } catch (Exception e) {
            // TODO: handle exception
        }
        return versionCode;
    }

    /**
     * 获取版本名称
     * versionName
     */
    public static String getAppVersionName(Context context){
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if(null == versionName || versionName.length()<=0){
                versionName = "";
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return versionName;
    }

    //获取APP列表
    public static ArrayList<AppInfo> getAppInfoList(Context context){
        try{
            PackageManager packageManager = context.getPackageManager();
            ArrayList<AppInfo> items = new ArrayList<AppInfo>();
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (PackageInfo pInfo : packageInfos) {

                if(pInfo.packageName.startsWith("com.winters")){
                    AppInfo appInfo = new AppInfo();
                    appInfo.setAppName(pInfo.applicationInfo.loadLabel(packageManager).toString());
                    appInfo.setPackageName(pInfo.packageName);
                    appInfo.setVersionName(pInfo.versionName);
                    appInfo.setVersionCode(pInfo.versionCode);
                    items.add(appInfo);
                }
            }

            return items;

        }catch (Exception e){
            return null;
        }

    }
}
