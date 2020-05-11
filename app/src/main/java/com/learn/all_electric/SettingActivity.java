package com.learn.all_electric;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.learn.all_electric.base.BaseActivity;
import com.learn.all_electric.utils.CommonUtils;
import com.learn.all_electric.utils.InternetUtils;
import com.learn.all_electric.utils.LogUtil;
import com.learn.all_electric.utils.StringUtils;
import com.learn.all_electric.view.CustomSettingItem;

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    private Button mBackBtn;
    private CustomSettingItem mDevInfoItem;
    private CustomSettingItem mWifiItem;
    private WifiManager wifiManager;

    @Override
    protected int getLayoutId() {
        return R.layout.module_activity_setting;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        RelativeLayout mTitleLayout = (RelativeLayout)findViewById(R.id.setting_title_layout);
        mBackBtn = (Button)mTitleLayout.findViewById(R.id.back_btn);
        ImageView mSettingImg = (ImageView)mTitleLayout.findViewById(R.id.setting_img);
        mSettingImg.setVisibility(View.INVISIBLE);
        mBackBtn.setText(R.string.back);
        mWifiItem = (CustomSettingItem)findViewById(R.id.setting_wifi);
        mDevInfoItem = (CustomSettingItem)findViewById(R.id.setting_device_info);
    }

    @Override
    protected void initListener() {
        mBackBtn.setOnClickListener(this);
        mWifiItem.setOnSettingItemClickListener(new CustomSettingItem.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick() {
                Intent intent = new Intent(SettingActivity.this,WlanConnectActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initVariables();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wifiManager = null;
    }

    private void initVariables(){
        String serialNum = CommonUtils.getSerialNumber();
        mDevInfoItem.setItemContent(getResources().getString(R.string.setting_device_serialnumber) + serialNum);
        //wifi打开，未连接显示未连接，wifi未打开显示不可用
        boolean isWifiEnable = wifiManager.isWifiEnabled();
        if(isWifiEnable){
            boolean isConnect = InternetUtils.isConnect(getApplicationContext());
            if(isConnect){
                //显示连接的wifi名称
                String connect_wifi_name = getConnectWifiInfo();
                if(!StringUtils.isEmpty(connect_wifi_name)){
                    mWifiItem.setItemContent(connect_wifi_name);
                }

            }else{
                mWifiItem.setItemContent(getResources().getString(R.string.setting_wifi_disconnect));
            }
        }else{
            mWifiItem.setItemContent(getResources().getString(R.string.setting_wifi_disable));
        }
    }

    private String getConnectWifiInfo(){
        WifiInfo info = wifiManager.getConnectionInfo();
        String connect_wifi_name = info.getSSID();
        return connect_wifi_name;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            default:
                break;
        }
    }
}
