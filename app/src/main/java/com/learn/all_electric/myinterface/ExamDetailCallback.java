package com.learn.all_electric.myinterface;

import com.learn.all_electric.bean.ExamDetailReponse;

import okhttp3.Callback;
import okhttp3.Response;

public interface ExamDetailCallback {
    void onFailExamDetail();
    void onSuccessExamDetail(ExamDetailReponse examDetailReponse);
}
