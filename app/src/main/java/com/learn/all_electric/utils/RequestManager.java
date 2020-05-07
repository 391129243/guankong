package com.learn.all_electric.utils;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.learn.all_electric.base.BaseApplication;
import com.learn.all_electric.bean.ExamDetailReponse;
import com.learn.all_electric.bean.ExamScoreBean;
import com.learn.all_electric.bean.UserLoginFailResponse;
import com.learn.all_electric.bean.UserLoginResponse;
import com.learn.all_electric.constants.Constant;
import com.learn.all_electric.myinterface.ExamDetailCallback;
import com.learn.all_electric.myinterface.LoginResponseCallback;
import com.learn.all_electric.myinterface.RequestCallBack;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class RequestManager implements ExamDetailCallback{
    private static String USER_LOGIN_URL = "http://kkclass-api.asit.me/asit-auth/oauth/token?";

    private static String REFRESH_URL = "http://kkclass-api.asit.me/asit-auth/oauth/token?";

    private static String EXAM_DETAIL_URL = "http://kkclass-api.asit.me/asit-device/exam/detail?";

    private static String UPLOAD_SCORE_URL = "http://kkclass-api.asit.me/asit-device/exam/score";
    //固件列表
    private static String DEVICE_LIST_URL = "http://kkclass-api.asit.me/asit-device/fireware/list";

    private static RequestManager mInstance = null;
    private RequestHander mRequestHandler = null;
    private LoginResponseCallback mCallBack = null;
    private ExamDetailCallback examCallBack = null;
    private static Context mContext;

    private String upload_file_path;

    public static RequestManager getInstance(Context context){
        if(null == mInstance){
            synchronized (RequestManager.class) {
                if(null == mInstance){
                    mInstance = new RequestManager(context);
                }
            }
        }
        return mInstance;
    }

    public RequestManager(Context context){
        mRequestHandler = new RequestHander(this);
        mContext = context;


    }


    private static class RequestHander extends Handler {
        private WeakReference<RequestManager> mRequestReference;
        public RequestHander(RequestManager updateManager) {
            mRequestReference = new WeakReference<RequestManager>(updateManager);
        }

        public void handleMessage(Message msg){
        // TODO Auto-generated method stub
            super.handleMessage(msg);
            final RequestManager mUpdateHandler = mRequestReference.get();
            if(null == mUpdateHandler){
                return;
            }
            switch (msg.what){
                //上传结果，上传成功或成绩已存在则删除文件
                case  Constant.MSG_UPLOAD_SCORE:
                    String upload_result = (String) msg.getData().get("upload_result");
                    int code = (int) msg.getData().get("code");
                    String upload_path = (String) msg.getData().get("upload_path");
                    ToastUtil.showToast(mContext,upload_result);
                    if(code == 200){
                        AndroidFileUtils.deleteFile(upload_path);
                    }
                    CtrlAppManager.getInstance(mContext.getApplicationContext())
                            .restoreVariable();
                    if(null != mUpdateHandler.onUploadExamScoreListener){
                        mUpdateHandler.onUploadExamScoreListener.onCompleteUpload();
                    }
                    break;
            }
        }

    }



    /**
     * 登录请求
     * @param account
     * @param password
     * @param callback
     */
    public void LoginRequest(String account,String password,String user_type,final LoginResponseCallback callback){
        this.mCallBack = callback;
        HashMap<String, String> parameter = new HashMap<String, String>();
        parameter.put("tenantId","000000");
        parameter.put("username", account);
        parameter.put("password",password);
        parameter.put("grant_type","password");

        OkHttpUtils.doPost(USER_LOGIN_URL,parameter,user_type,mCallBack);
    }

    /**
     * 刷新请求
     */
    public void refleshRequest(String refresh_token,String user_type,final LoginResponseCallback callback)
    {
        this.mCallBack = callback;
        String refreshToken = refresh_token;

        HashMap<String, String> parameter = new HashMap<String, String>();
        parameter.put("tenantId","000000");
        parameter.put("refresh_token", refreshToken);
        parameter.put("grant_type","refresh_token");
        OkHttpUtils.doPost(REFRESH_URL,parameter,user_type,mCallBack);
    }

    /**
     * 获取考试详情
     * 方法:GET
     * serialNo 机台序列号
     * questionNo 试题题号 SN1
     */
    public void getExamDetail(String token,String serialNo,String questionNo ,ExamDetailCallback callback){

        this.examCallBack = callback;
        try {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("serialNo",serialNo);
            params.put("questionNo", questionNo);
            OkHttpUtils.doGetByParams(EXAM_DETAIL_URL,params,token,examCallBack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传成绩
     * 使用resuqestBody提交数据
     */
    public void uploadScore(ExamScoreBean examScoreBean,String token,String upload_file_path) {

        this.upload_file_path = upload_file_path;
        Gson gson = new Gson();
        if (null != examScoreBean) {
            String questScoreBody = gson.toJson(examScoreBean);
            OkHttpUtils.doPostByBody(UPLOAD_SCORE_URL,questScoreBody,token,this);
        }
    }

    /**
     * 获取设备列表信息
     */
    public void getDeviceListInfo(String token, RequestCallBack callBack){

        OkHttpUtils.doGetByHeaders(DEVICE_LIST_URL,token,callBack);
    }

    @Override
    public void onFailExamDetail() {

    }

    @Override
    public void onSuccessExamDetail(ExamDetailReponse examDetailReponse) {
        LogUtil.i("1111" , "RequstManager--onSuccessExamDetail:" + examDetailReponse.getMsg());
        int code = examDetailReponse.getCode();
        String upload_result = examDetailReponse.getMsg();
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putInt("code",code);
        bundle.putString("upload_result",upload_result);
        bundle.putString("upload_path",upload_file_path);
        message.setData(bundle);
        message.what = Constant.MSG_UPLOAD_SCORE;
        if(null != mRequestHandler){
            mRequestHandler.sendMessage(message);
        }
    }

    public interface OnUploadExamScoreListener{
        void onCompleteUpload();
    }
    private OnUploadExamScoreListener onUploadExamScoreListener;
    public void setOnUploadExamScoreListener(OnUploadExamScoreListener listener){
        this.onUploadExamScoreListener = listener;
    }

}
