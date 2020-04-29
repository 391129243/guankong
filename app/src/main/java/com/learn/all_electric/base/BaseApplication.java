package com.learn.all_electric.base;

import android.app.Application;
import android.content.Context;

import com.learn.all_electric.R;
import com.learn.all_electric.bean.ExamListBean;
import com.learn.all_electric.bean.ExperimentBean;
import com.learn.all_electric.constants.SharedConstants;
import com.learn.all_electric.utils.CrashHandler;
import com.learn.all_electric.utils.CtrlAppManager;
import com.learn.all_electric.utils.PreferenceUtil;


public final class BaseApplication extends Application {

    public static String app_name = "";

    public static Boolean RS232_exist = false;

    public static String userName = "Admin";

    public static int duration = 0;

    public static ExamListBean examListBean = null;

    public static  ExperimentBean experimentBean = null;

    public String getUserName() {
        return userName;
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
        //启动故障收集
/*        CrashHandler crashHandler = CrashHandler.getInstance(this);
        crashHandler.init(this);*/
        restoreVariable();
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
    }

   private void restoreVariable(){
        CtrlAppManager.getInstance(getApplicationContext()).restoreVariable();
   }

}
