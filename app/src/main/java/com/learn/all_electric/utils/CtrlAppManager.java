package com.learn.all_electric.utils;

import android.content.Context;

import com.learn.all_electric.constants.SharedConstants;

public class CtrlAppManager {

    private static CtrlAppManager instance;
    private Context mContext;

    public static CtrlAppManager getInstance(Context context){
        if(null == instance){
            instance = new CtrlAppManager(context);
        }
        return instance;
    }

    public CtrlAppManager(Context context){
        this.mContext = context;
    }

    public void restoreVariable(){
        PreferenceUtil.getInstance(mContext)
                .setValueByName(SharedConstants.IS_LOGIN,false);
        PreferenceUtil.getInstance(mContext)
                .setValueByName(SharedConstants.LOGIN_USERNAME,"");
        PreferenceUtil.getInstance(mContext)
                .setValueByName(SharedConstants.LOGIN_ACCOUNT,"");
        PreferenceUtil.getInstance(mContext)
                .setValueByName(SharedConstants.REFRESH_TOKEN,"");
        PreferenceUtil.getInstance(mContext)
                .setValueByName(SharedConstants.ACCESS_TOKEN,"");
        PreferenceUtil.getInstance(mContext)
                .setValueByName(SharedConstants.TOKEN,"");

    }
}
