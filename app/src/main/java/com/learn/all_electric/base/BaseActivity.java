package com.learn.all_electric.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;


import com.kongqw.permissionslibrary.PermissionsManager;
import com.learn.all_electric.constants.Constant;
import com.learn.all_electric.receiver.NetworkChangeReceiver;
import com.learn.all_electric.utils.ToastUtil;


public abstract class BaseActivity extends AppCompatActivity {


    public static BaseApplication mApplication;
    protected abstract int getLayoutId();
    //抽象函数用于继承
    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        mApplication = (BaseApplication) getApplication();
        BaseApplication.RS232_exist = false;
        initViews(savedInstanceState);
        initListener();

    }

    /**
     * 显示缺少权限的对话框
     */
    protected void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请求权限");
        builder.setMessage("Android 6.0+ 动态请求相机权限");
        builder.setPositiveButton("去设置权限", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                PermissionsManager.startAppSettings(getApplicationContext());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 显示没有安装OpenCV Manager的对话框
     */
    protected void showInstallDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("您还没有安装OpenCV Manager");
        builder.setMessage("是否下载安装？");
        builder.setPositiveButton("去下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "去下载", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://github.com/kongqw/FaceDetectLibrary/tree/opencv3.2.0/OpenCVManager")));
            }
        });
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void displayToast(int resId){
        ToastUtil.showToast(getApplicationContext(),getResources().getString(resId));
    }

    public void displayToast(String message){
        ToastUtil.showToast(getApplicationContext(),message);
    }
}
