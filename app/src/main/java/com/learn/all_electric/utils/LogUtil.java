package com.learn.all_electric.utils;

import android.util.Log;

/**
 * 管理Log的工具列，通过设置mCurLevel控制Log的输出级别
 * 项目上线后可将mCurLevel设置成LEVEL_NONE,禁止Log输出
 */
public class LogUtil {
    private static final int LEVEL_NONE = 0;
    //输出错误界别的日志
    private static final int LEVEL_ERROR = 1;
    //日志输出界别警告
    private static final int LEVEL_WARN = 2;
    //日志输出级别信息i
    private static final int LEVEL_INFO = 3;
    //日志输出级别调试debug
    private static final int LEVEL_DEBUG = 4;
    //日志输出级别V
    private static final int LEVEL_VERBOSE = 5;
    //日志输出的TAG
    private static String TAG = "LogUtil";
    //当前日志输出界别
    private static int mCurrentLevel = LEVEL_VERBOSE;

    public static void v(String tag,String msg){
        if(mCurrentLevel >= LEVEL_VERBOSE){
            Log.v(tag,msg);
        }
    }

    public static void d(String tag,String msg){
        if(mCurrentLevel >= LEVEL_DEBUG){
            Log.d(tag,msg);
        }
    }

    public static void i(String tag,String msg){
        if(mCurrentLevel >= LEVEL_INFO){
            Log.i(tag,msg);
        }
    }

    public static void w(String tag,String msg){
        if(mCurrentLevel >= LEVEL_INFO){
            Log.w(tag,msg);
        }
    }


    public static void e(String tag,String msg){
        if(mCurrentLevel >= LEVEL_ERROR){
            Log.e(tag,msg);
        }
    }

    public static void w(String msg, Throwable tr) {
        if (mCurrentLevel >= LEVEL_WARN && null != msg) {
            Log.w(TAG, msg, tr);
        }
    }

    /**
     * 以级别为 e 的形式输出Throwable
     */
    public static void e(Throwable tr) {
        if (mCurrentLevel >= LEVEL_ERROR) {
            Log.e(TAG, "", tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG信息和Throwable
     */
    public static void e(String msg, Throwable tr) {
        if (mCurrentLevel >= LEVEL_ERROR && null != msg) {
            Log.e(TAG, msg, tr);
        }
    }
}
