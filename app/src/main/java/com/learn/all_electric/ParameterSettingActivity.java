package com.learn.all_electric;

import android.os.Bundle;
import android.view.View;

import com.learn.all_electric.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 版本参考与预设置
 */
public class ParameterSettingActivity extends BaseActivity implements View.OnClickListener{



    @Override
    protected int getLayoutId() {
        return R.layout.activity_parameter_setting;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.back)
    public void back(){
        // 返回
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View view) {

    }
}
