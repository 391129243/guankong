package com.learn.all_electric.myinterface;

import com.learn.all_electric.bean.UserLoginFailResponse;
import com.learn.all_electric.bean.UserLoginResponse;

import okhttp3.Callback;

public interface LoginResponseCallback{
    //成功
    public void onLoginSuccess(UserLoginResponse successResponse);
    //失败
    public void onLoginFailure(UserLoginFailResponse failResponse);
}
