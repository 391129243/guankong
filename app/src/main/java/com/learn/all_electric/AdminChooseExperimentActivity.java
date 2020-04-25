package com.learn.all_electric;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.learn.all_electric.adapter.ExperimentAdapter;
import com.learn.all_electric.adapter.TestAdapter;
import com.learn.all_electric.base.BaseActivity;
import com.learn.all_electric.bean.ChooseExperimentBean;
import com.learn.all_electric.bean.TestBean;
import com.learn.all_electric.bean.UserN;
import com.learn.all_electric.constants.PackageConstants;
import com.learn.all_electric.constants.SharedConstants;
import com.learn.all_electric.utils.LogUtil;
import com.learn.all_electric.utils.PackageUtils;
import com.learn.all_electric.utils.PreferenceUtil;
import com.learn.all_electric.utils.StringUtils;
import com.learn.all_electric.view.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * 管理员实验选择
 */
public class AdminChooseExperimentActivity extends BaseActivity{

    private RelativeLayout mTitleLayout;
    private Button mBackBtn;
    private TextView mExamNameTv;
    private TextView mSchoolTv;
    private TextView mCentreNoTv;
    private TextView mSeatNoTv;
    private RecyclerView mExperimentView;
    private ArrayList<ChooseExperimentBean> mExperimentList;
    private ExperimentAdapter mExperimentAdapter;
    private CustomDialog mChooseDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.module_activity_choose_experiment;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mTitleLayout = (RelativeLayout)findViewById(R.id.admin_choose_experient_title_layout);
        mBackBtn = (Button)mTitleLayout.findViewById(R.id.back_btn);
        mExamNameTv = (TextView)findViewById(R.id.admin_chooce_exam_name_txt);
        mSchoolTv = (TextView)findViewById(R.id.admin_chooce_exam_school_txt);
        mCentreNoTv = (TextView)findViewById(R.id.admin_chooce_exam_roomnum_txt);
        mSeatNoTv = (TextView)findViewById(R.id.admin_chooce_exam_seatnum_txt);
        mExperimentView = (RecyclerView)findViewById(R.id.admin_choose_experient_recycler);


    }

    @Override
    protected void initListener() {
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inivariables();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initExperimentStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mChooseDialog){
            mChooseDialog.dismiss();;
            mChooseDialog.cancel();;
        }
    }

    private void inivariables(){
        // 考试名称
        mExamNameTv.setText("广东省-肇庆市-端州区2020年物理实验操作考试");
        // 学校
        mSchoolTv.setText("实验中学");
        // 考场编号
        mCentreNoTv.setText("1");
        // 实验
        mExperimentList = new ArrayList<ChooseExperimentBean>();
        mExperimentList.clear();
        //获取实验名称
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        mExperimentView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this,2);
        mExperimentView.setLayoutManager(mGridLayoutManager);
        mExperimentList = getAllExperimentName();
        mExperimentAdapter = new ExperimentAdapter(mExperimentList);
        mExperimentView.setAdapter(mExperimentAdapter);
        mExperimentAdapter.notifyDataSetChanged();
        mExperimentAdapter.setOnItemClickListener(new ExperimentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                //弹出对话框确认是否将此实验作为保存的实验
                ChooseExperimentBean chooseExperimentBean = mExperimentList.get(position);
                String experimentName = chooseExperimentBean.getExperiment_name();
                String experimentNum = PackageUtils.getQuestionNumber(getApplicationContext(),experimentName);
                if(!StringUtils.isEmpty(experimentNum)){
                    showChooseExperimentNumDialog(0,experimentNum,experimentName);
                }
            }

            @Override
            public void onItemLongClick(int position) {
                //跳转至实验的设置参数
                ChooseExperimentBean chooseExperimentBean = mExperimentList.get(position);
                String experimentName = chooseExperimentBean.getExperiment_name();
                String experimentNum = PackageUtils.getQuestionNumber(getApplicationContext(),experimentName);
                if(!StringUtils.isEmpty(experimentNum)){
                    showChooseExperimentNumDialog(1,experimentNum,experimentName);
                }
            }

            @Override
            public void onItemUpdateVersion(int position) {
                //判断是否存在此实验，存在提示升级
            }
        });

    }

    private void initExperimentStatus(){
        String qustionNo = PreferenceUtil.getInstance(getApplicationContext())
                .getStringValue(SharedConstants.EXPERIMENT_SN,"SN1");
        String experimentName = PackageUtils.getExperimentName(getApplicationContext(),qustionNo);
        if(!StringUtils.isEmpty(experimentName)){
            settingExperimentChecked(experimentName);
        }

    }

    private ArrayList<ChooseExperimentBean> getAllExperimentName(){
        ArrayList<ChooseExperimentBean> list = new ArrayList<>();
        list.clear();
        ChooseExperimentBean bean1 = new ChooseExperimentBean(getResources().getString(R.string.admin_name_experiment_sn1)
                ,"V1.2.1",true,false);
        ChooseExperimentBean bean2 = new ChooseExperimentBean(getResources().getString(R.string.admin_name_experiment_sn2)
                ,"V1.2.1",false,false);
        ChooseExperimentBean bean3 = new ChooseExperimentBean(getResources().getString(R.string.admin_name_experiment_sn3)
                ,"V1.2.1",false,false);
        ChooseExperimentBean bean4 = new ChooseExperimentBean(getResources().getString(R.string.admin_name_experiment_sn4)
                ,"V1.2.1",false,false);
        ChooseExperimentBean bean5 = new ChooseExperimentBean(getResources().getString(R.string.admin_name_experiment_sn5)
                ,"V1.2.1",false,false);
        ChooseExperimentBean bean6 = new ChooseExperimentBean(getResources().getString(R.string.admin_name_experiment_sn6)
                ,"V1.2.1",false,false);
        ChooseExperimentBean bean7 = new ChooseExperimentBean(getResources().getString(R.string.admin_name_experiment_sn7)
                ,"V1.2.1",false,false);
        ChooseExperimentBean bean8 = new ChooseExperimentBean(getResources().getString(R.string.admin_name_experiment_sn8)
                ,"V1.2.1",false,false);
        ChooseExperimentBean bean9 = new ChooseExperimentBean(getResources().getString(R.string.admin_name_experiment_sn9)
                ,"V1.2.1",false,false);
        ChooseExperimentBean bean10 = new ChooseExperimentBean(getResources().getString(R.string.admin_name_experiment_sn10)
                ,"V1.2.1",false,false);
        ChooseExperimentBean bean11 = new ChooseExperimentBean(getResources().getString(R.string.admin_name_experiment_sn11)
                ,"V1.2.1",false,false);
        ChooseExperimentBean bean12 = new ChooseExperimentBean(getResources().getString(R.string.admin_name_experiment_sn12)
                ,"V1.2.1",false,false);
        ChooseExperimentBean bean13= new ChooseExperimentBean(getResources().getString(R.string.admin_name_experiment_sn13)
                ,"V1.2.1",false,false);

        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        list.add(bean6);
        list.add(bean7);
        list.add(bean8);
        list.add(bean9);
        list.add(bean10);
        list.add(bean11);
        list.add(bean12);
        list.add(bean13);

        return  list;
    }

    // 自定义条目修饰类
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.bottom = space;
            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.left = 0;
            }
        }
    }



    /**
     * 显示确认实验台设置实验考试题目对话框
     * @param questionNo 考试题目
     * @param experimentName 实验名称
     */

    private void showChooseExperimentNumDialog(final int index,final String questionNo,final String experimentName){

        if(null != mChooseDialog){
            mChooseDialog.dismiss();
            mChooseDialog = null;
        }
        if(null == mChooseDialog){
            mChooseDialog = new CustomDialog(this);
            LayoutInflater mInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = (View)mInflater.inflate(R.layout.dialog_common_hint_layout, null);
            TextView hintText = (TextView)view.findViewById(R.id.common_dialog_hint_txt);
            TextView comonTxt = (TextView)view.findViewById(R.id.common_hint_txt);
            comonTxt.setVisibility(View.INVISIBLE);
            if(index == 0){
                hintText.setText("确定选择"+ " " + experimentName+" 作为考试实验吗？");
            }else if(index == 1){
                hintText.setText("确定进行"+ " " + experimentName+" 的实验参数设置吗？");
            }

            CustomDialog.Builder builder = new CustomDialog.Builder(this);
            builder.setContentView(view);
            builder.setPositiveButton(R.string.ok,  new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub

                    if(index == 0 ){
                        settingExperimentChecked(experimentName);
                        PreferenceUtil.getInstance(getApplicationContext())
                                .setValueByName(SharedConstants.EXPERIMENT_SN,questionNo);
                    }else if(index == 1){
                        //跳转
                        settingExperimentParam(questionNo);
                    }

                    mChooseDialog.dismiss();
                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    mChooseDialog.dismiss();
                    mChooseDialog = null;
                }
            });
            mChooseDialog = builder.create();
            mChooseDialog.show();
        }
    }


    private void settingExperimentChecked(String experimentName){
        for(ChooseExperimentBean bean : mExperimentList){
            if(bean.getExperiment_name().equals(experimentName)){
                bean.setCheck(true);
            }else{
                bean.setCheck(false);
            }
        }
        mExperimentAdapter.notifyDataSetChanged();

    }


    /***
     * Admin进行选择实验的参数设置
     */
    private void settingExperimentParam(String questionNo){
        // String packageName = PackageUtils.getPackageName(questionNo);
        String packageName = PackageConstants.SN14_TEMPERUTURE_PACKAGENAME;
        if(PackageUtils.isApkInstalled(this,packageName)){
            //存在则拉起
            UserN userN = new UserN("Admin","Admin","Admin",null);
            Gson gson = new Gson();
            String user_info = gson.toJson(userN);
            ComponentName componentName = new ComponentName(PackageConstants.SN14_TEMPERUTURE_PACKAGENAME,
                    "com.winters.fourteen_temperature.LoginActivity");
            Intent intent = new Intent();
            intent.setComponent(componentName);;
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("user",user_info);
            startActivity(intent);
        }else{
            LogUtil.i("1111","不存在拉起的应用");
        }
    }

}
