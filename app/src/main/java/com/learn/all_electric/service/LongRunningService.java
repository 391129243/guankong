package com.learn.all_electric.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import com.learn.all_electric.bean.ExamDetailReponse;
import com.learn.all_electric.bean.UserLoginFailResponse;
import com.learn.all_electric.bean.UserLoginResponse;
import com.learn.all_electric.constants.SharedConstants;
import com.learn.all_electric.myinterface.LoginResponseCallback;
import com.learn.all_electric.utils.LogUtil;
import com.learn.all_electric.utils.PreferenceUtil;
import com.learn.all_electric.utils.RequestManager;
import com.learn.all_electric.utils.StringUtils;


public class LongRunningService extends Service implements LoginResponseCallback{
    private Binder mBinder = new LongRunningBinder();
    private int time = 1000 * 3;
    private boolean isLogin;
    private Handler mUpdateTokenHandler;
    private HandleUpdateTokenThread mUpdateTokenThread;
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("1111","service onCreate");
        mUpdateTokenThread = new HandleUpdateTokenThread();
        mUpdateTokenThread.start();
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("1111","service onDestroy");
        if(null != mUpdateTokenThread){
            mUpdateTokenThread.interrupt();
            mUpdateTokenThread = null;
        }
        if(null != mUpdateTokenHandler){
            mUpdateTokenHandler.removeCallbacksAndMessages(null);
            mUpdateTokenHandler = null;
        }
    }





    public class LongRunningBinder extends Binder {
        public LongRunningBinder getService(){
            return LongRunningBinder.this;
        }


    }

    private class HandleUpdateTokenThread extends Thread{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            Looper.prepare();
            mUpdateTokenHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    updateTocken();
                    sendEmptyMessageDelayed(0,3000*1000);
                }
            };
            mUpdateTokenHandler.sendEmptyMessageDelayed(0,3000*1000);
            Looper.loop();
        }

    }

    //登录成功后开启定时线程刷新更新token避免token过期
    private void updateTocken(){
        boolean isLogin = PreferenceUtil.getInstance(getApplicationContext())
                .getBooleanValue(SharedConstants.IS_LOGIN,false);
        String userType = PreferenceUtil.getInstance(getApplicationContext())
                .getStringValue(SharedConstants.LOGIN_USERTPYE,"device-teacher");
        if(isLogin){
            LogUtil.i("1111","updateTocken"+ System.currentTimeMillis());
            String refresh_token = PreferenceUtil.getInstance(getApplicationContext())
                    .getStringValue(SharedConstants.REFRESH_TOKEN,"");
            if(!StringUtils.isEmpty(refresh_token)){
                RequestManager.getInstance(getApplicationContext()).refleshRequest(refresh_token,userType,this);
            }
        }
    }

    //用于接收更新token的回调
    @Override
    public void onLoginSuccess(UserLoginResponse successResponse) {

        String role_name = successResponse.getRole_name();
        String account = successResponse.getAccount();
        String user_name = successResponse.getUser_name();
        String reflesh_token = successResponse.getRefresh_token();
        String access_token = successResponse.getAccess_token();
        saveLoginParams(role_name,account,user_name,reflesh_token,access_token);

        //保存新的
    }

    @Override
    public void onLoginFailure(UserLoginFailResponse failResponse) {


    }

    public void onFail(ExamDetailReponse reponse){

    }


    private void saveLoginParams(String roleName,String account,String user_name,String reflesh_token, String access_token){

        String token = "bearer " + access_token;
        PreferenceUtil.getInstance(getApplicationContext())
                .setValueByName(SharedConstants.LOGIN_USERNAME,user_name);
        PreferenceUtil.getInstance(getApplicationContext())
                .setValueByName(SharedConstants.LOGIN_ACCOUNT,account);
        PreferenceUtil.getInstance(getApplicationContext())
                .setValueByName(SharedConstants.REFRESH_TOKEN,reflesh_token);
        PreferenceUtil.getInstance(getApplicationContext())
                .setValueByName(SharedConstants.ACCESS_TOKEN,access_token);
        PreferenceUtil.getInstance(getApplicationContext())
                .setValueByName(SharedConstants.TOKEN, token);
    }
}
