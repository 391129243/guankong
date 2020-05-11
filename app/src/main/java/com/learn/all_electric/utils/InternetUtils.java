package com.learn.all_electric.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;

import com.learn.all_electric.WlanConnectActivity;

public class InternetUtils {
    //判断当前网络是否连接
    public static boolean isConnect(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {

                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {

                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static SecurityMode getSecurityMode(ScanResult scanResult){
        if (scanResult.capabilities.contains("WEP")) {
            return SecurityMode.WEP;
        } else if (scanResult.capabilities.contains("PSK")) {
            return SecurityMode.WPA;//WPA/WPA2 PSK
        } else if (scanResult.capabilities.contains("EAP")) {
            return SecurityMode.WPA2;
        }
        return SecurityMode.OPEN;
    }
    /**
     * 这个枚举用于表示网络加密模式
     */
    public enum SecurityMode {
        OPEN, WEP, WPA, WPA2
    }


}
