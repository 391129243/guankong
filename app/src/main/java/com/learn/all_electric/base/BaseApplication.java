package com.learn.all_electric.base;

import android.app.Application;
import android.content.Context;

import com.learn.all_electric.R;
import com.learn.all_electric.bean.ExamListBean;
import com.learn.all_electric.bean.ExperimentBean;


public final class BaseApplication extends Application {

    public static String app_name = "";

    public static ExamListBean examListBean;//多个实验

    public static ExperimentBean experimentBean;//全局

    public static int duration = 0;

    public static Boolean RS232_exist = false;

    public static String userName = "Admin";

    public static Boolean BalanceBarExist = false;

    public static Boolean BalanceBarPrepareBalance = false;

    public static Boolean BalanceBarClean = false;


    public static Integer inputNum = 0;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private static Context context;

    public static Context getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        app_name = getResources().getString(R.string.app_name);
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
    }

    public static void clear() {

        BalanceBarExist = false;

        BalanceBarPrepareBalance = false;

        BalanceBarClean = false;

        inputNum = 0;
        examListBean=null;

    }

    public static void clear_step2() {

        BalanceBarClean = false;
        inputNum = 0;
        examListBean=null;

    }

}
