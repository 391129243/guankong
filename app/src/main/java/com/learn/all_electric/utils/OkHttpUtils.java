package com.learn.all_electric.utils;

import com.google.gson.Gson;
import com.learn.all_electric.R;
import com.learn.all_electric.bean.ExamDetailReponse;
import com.learn.all_electric.bean.UserLoginFailResponse;
import com.learn.all_electric.bean.UserLoginResponse;
import com.learn.all_electric.myinterface.ExamDetailCallback;
import com.learn.all_electric.myinterface.LoginResponseCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

    private static OkHttpClient okHttpClient;
    private static String client_device = "Basic ZGV2aWNlOkMybWZZd3lNdlhiNHE1eGpHc3JwOTNOUm9lVU9kMEZ0";

    //get请求
    public static void doGetByHeaders(String url, String token, ExamDetailCallback callback)
    {
        final ExamDetailCallback mCallBack = callback;
        OkHttpClient okHttpClient = new OkHttpClient();

        //创建Request
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization",client_device)
                .addHeader("Asit-Auth",token)
                .build();
        okhttp3.Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                if(null != mCallBack){
                    mCallBack.onFailExamDetail();
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                LogUtil.i("1111" , 3333 + response.code()+"");
                if(response.code() == 200){
                    Gson gson = new Gson();
                    String responeBody = response.body().string();
                    LogUtil.i("room" , responeBody);
                    ExamDetailReponse examDetailReponse = gson.fromJson(responeBody,ExamDetailReponse.class);
                    if(null != mCallBack){

                        mCallBack.onSuccessExamDetail(examDetailReponse);
                    }
                }

            }
        });
    }

    /**
     * 带参数的Get请求
     * @param url
     * @param params
     * @param token
     * @param callback
     */

    public static void doGetByParams(String url, HashMap<String ,String> params, String token, ExamDetailCallback callback) {
        final ExamDetailCallback mCallBack = callback;
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个HttpUrl
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for (String key : params.keySet()) {
                urlBuilder.setQueryParameter(key, params.get(key));
            }
        }
        Request request = new Request.Builder().url(urlBuilder.build())
                .addHeader("Authorization",client_device)
                .addHeader("Asit-Auth",token)
                .get().build();

        okhttp3.Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                if(null != mCallBack){
                    mCallBack.onFailExamDetail();
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                LogUtil.i("1111" ,  "getdetail "+ response.code()+"");

                if(response.code() == 200){
                    Gson gson = new Gson();
                    String responeBody = response.body().string();
                    LogUtil.i("1111" , responeBody);
                    ExamDetailReponse examDetailReponse = gson.fromJson(responeBody,ExamDetailReponse.class);
                    if(null != mCallBack){
                        LogUtil.i("1111" ,  "getdetail "+ examDetailReponse.getMsg()+"");
                        mCallBack.onSuccessExamDetail(examDetailReponse);
                    }
                }

            }
        });
    }

    public static void doGet(String url)
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建Request
        Request request = new Request.Builder()
                .url(url)
                .build();
        okhttp3.Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

            }
        });
    }
    //post请求
    public static void doPost(String url, Map<String, String> params, final LoginResponseCallback callback)
    {
        final LoginResponseCallback mCallback = callback;
        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody.Builder builder=new FormBody.Builder();
        if(params!=null)
        {
            for(String key:params.keySet())
            {
                builder.add(key,params.get(key));
            }
        }

        Request request=new Request.Builder()
                .post(builder.build())
                .url(url)
                .addHeader("Authorization",client_device)
                .addHeader("User-Type","device-student")
                .build();

        okhttp3.Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {


            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                String responseBody = response.body().string();
                LogUtil.i("1111" , "1111---"+ response.code() + "");
                if(response.code() == 200){
                    if(responseBody.contains("error_code")){
                        Gson gson = new Gson();
                        UserLoginFailResponse failResponse = gson.fromJson(responseBody,UserLoginFailResponse.class);
                        if(null != mCallback){
                            mCallback.onLoginFailure(failResponse);
                        }
                    }else{

                        Gson gson = new Gson();
                        UserLoginResponse successResponse = gson.fromJson(responseBody,UserLoginResponse.class);
                        if(null != mCallback){
                            mCallback.onLoginSuccess(successResponse);
                        }
                    }

                }

            }
        });
    }


    //post请求
    public static void doPostByBody(String url, String body, String token,ExamDetailCallback callback)
    {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, body);
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request=new Request.Builder()
                .url(url)
                .addHeader("Authorization",client_device)
                .addHeader("Asit-Auth",token)
                .post(requestBody)
                .build();

        okhttp3.Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {


            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                LogUtil.i("1111" , "2222---"+ response.code() + "");
                String responseData = response.body().string();
                LogUtil.i("1111" , "3333---"+ responseData + "");
                if(response.code() == 200){
                    //服务器有回应，例如tokn过期回应请求未授权//回应都在msg内
                    Gson gson = new Gson();
                    String responeBody =  response.body().string();
                    ExamDetailReponse examDetailReponse = gson.fromJson(responeBody,ExamDetailReponse.class);
                    if(null != callback){
                        callback.onSuccessExamDetail(examDetailReponse);
                    }
                }

            }
        });
    }




}
