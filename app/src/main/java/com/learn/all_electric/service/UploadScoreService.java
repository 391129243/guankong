package com.learn.all_electric.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.learn.all_electric.bean.ExamScoreBean;
import com.learn.all_electric.constants.SharedConstants;
import com.learn.all_electric.myinterface.ExamDetailCallback;
import com.learn.all_electric.utils.AndroidFileUtils;
import com.learn.all_electric.utils.LogUtil;
import com.learn.all_electric.utils.PreferenceUtil;
import com.learn.all_electric.utils.RequestManager;
import com.learn.all_electric.utils.StringUtils;

import java.io.IOException;

public class UploadScoreService extends IntentService {

    private static final String TAG = "UploadScoreService";

    public UploadScoreService() {
        super("");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        // 根据 Intent的不同，进行不同的事务处理
        int taskName = intent.getExtras().getInt("taskName");

        switch (taskName){
            case 1:
                String fileName =intent.getStringExtra("upload_score_file");
                if(!StringUtils.isEmpty(fileName)){
                    readUploadScoreFile(fileName);
                }
                break;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG,"onCreate");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        LogUtil.i(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG,"onDestroy");
    }

    //从sdcard中读取保存的实验成绩
    private void readUploadScoreFile(String filename){

        //判断设备是否有sdcard
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String sdcard_upload_dir = Environment.getExternalStorageDirectory()+"/UploadScore/";
            String path = sdcard_upload_dir + filename;
            try{
                String score_body = AndroidFileUtils.readJsonData(path);
                Gson gson = new Gson();
                ExamScoreBean examScoreBean = gson.fromJson(score_body,ExamScoreBean.class);
                if(null != examScoreBean){
                    String token = PreferenceUtil.getInstance(getApplicationContext())
                            .getStringValue(SharedConstants.TOKEN,"");
                    RequestManager.getInstance(getApplicationContext()).uploadScore(examScoreBean,token,path);
                }
            }catch (IOException e){
                e.printStackTrace();;
            }


        }else{
            //
        }
    }
}
