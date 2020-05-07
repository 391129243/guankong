package com.learn.all_electric.myinterface;

import com.learn.all_electric.bean.ExamDetailReponse;

public interface RequestCallBack {
    void onFailure();
    void onSuccess(String response);
}
