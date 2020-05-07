package com.learn.all_electric;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.learn.all_electric.adapter.ExperimentAdapter;
import com.learn.all_electric.base.BaseActivity;
import com.learn.all_electric.bean.AppInfo;
import com.learn.all_electric.bean.ChooseExperimentBean;
import com.learn.all_electric.bean.DeviceDetailReponse;
import com.learn.all_electric.bean.UserN;
import com.learn.all_electric.constants.SharedConstants;
import com.learn.all_electric.myinterface.RequestCallBack;
import com.learn.all_electric.utils.CommonUtils;
import com.learn.all_electric.utils.LogUtil;
import com.learn.all_electric.utils.PackageUtils;
import com.learn.all_electric.utils.PreferenceUtil;
import com.learn.all_electric.utils.RequestManager;
import com.learn.all_electric.utils.StringUtils;
import com.learn.all_electric.view.CustomDialog;
import com.learn.all_electric.view.SpacesItemDecoration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 管理员实验选择
 */
public class AdminChooseExperimentActivity extends BaseActivity implements View.OnClickListener, RequestCallBack {

    private RelativeLayout mTitleLayout;
    private Button mBackBtn;
    private ImageView mSettingImg;
    private TextView mExamNameTv;
    private TextView mSchoolTv;
    private TextView mCentreNoTv;
    private TextView mSeatNoTv;
    private RecyclerView mExperimentView;
    private ArrayList<ChooseExperimentBean> mExperimentList;
    private ArrayList<AppInfo> localExperimentApps;
    private ArrayList<HashMap<String,String>> remoteVersionList;
    private ExperimentAdapter mExperimentAdapter;
    private CustomDialog mChooseDialog;
    private CustomDialog mNoticeDialog;
    private CustomDialog mUpdateDialog;
    private UIHandler mUIHandler;
    private static final int MSG_NOTIFY_EXPERIMENT_LIST = 200;

    @Override
    protected int getLayoutId() {
        return R.layout.module_activity_choose_experiment;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mTitleLayout = (RelativeLayout)findViewById(R.id.admin_choose_experient_title_layout);
        mBackBtn = (Button)mTitleLayout.findViewById(R.id.back_btn);
        //mSettingImg = (ImageView)mTitleLayout.findViewById(R.id.setting_img);
        mExamNameTv = (TextView)findViewById(R.id.admin_chooce_exam_name_txt);
        mSchoolTv = (TextView)findViewById(R.id.admin_chooce_exam_school_txt);
        mCentreNoTv = (TextView)findViewById(R.id.admin_chooce_exam_roomnum_txt);
        mSeatNoTv = (TextView)findViewById(R.id.admin_chooce_exam_seatnum_txt);
        mExperimentView = (RecyclerView)findViewById(R.id.admin_choose_experient_recycler);


    }

    @Override
    protected void initListener() {
        mBackBtn.setOnClickListener(this);
      // mSettingImg.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUIHandler = new UIHandler(this);
        mExperimentList = new ArrayList<ChooseExperimentBean>();
        localExperimentApps = new ArrayList<AppInfo>();
        remoteVersionList = new ArrayList<HashMap<String,String>>();
        getRemoteAppInfo();
        getLocalAppInfoList();
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
        releaseDialog();
        clearList();
        if(null != mUIHandler){
            mUIHandler.removeCallbacksAndMessages(null);
            mUIHandler = null;
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

        //获取实验名称
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        mExperimentView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this,2);
        mExperimentView.setLayoutManager(mGridLayoutManager);

        mExperimentList.clear();
        mExperimentList = getAllExperimentName();
        mExperimentAdapter = new ExperimentAdapter(getApplicationContext(),mExperimentList);
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
                ChooseExperimentBean chooseExperimentBean = mExperimentList.get(position);
                String experimentName = chooseExperimentBean.getExperiment_name();
                String experimentNum = PackageUtils.getQuestionNumber(getApplicationContext(),experimentName);
                if(!StringUtils.isEmpty(experimentNum)){
                   showExperimentUpdateDialog(experimentNum,experimentName,getResources()
                           .getString(R.string.admin_choose_experiment_update));
                }
            }
        });

    }


    private void initExperimentStatus(){
        mExperimentAdapter.setmExperimentList(mExperimentList);
        mExperimentAdapter.notifyDataSetChanged();

        String qustionNo = PreferenceUtil.getInstance(getApplicationContext())
                .getStringValue(SharedConstants.EXPERIMENT_SN,"");
        String experimentName = PackageUtils.getExperimentName(getApplicationContext(),qustionNo);
        if(!StringUtils.isEmpty(experimentName)){
            settingExperimentChecked(experimentName);
        }else{

            showSetExperimentNoticeDialog(getResources().getString(R.string.admin_choose_experiment_null));
        }

    }

    private ArrayList<ChooseExperimentBean> getAllExperimentName(){
        ArrayList<ChooseExperimentBean> list = new ArrayList<>();
        list.clear();
        if(localExperimentApps.size()>0){
            for(int i = 0; i < localExperimentApps.size();i++){
                ChooseExperimentBean bean  = new ChooseExperimentBean();
                bean.setExperiment_name(localExperimentApps.get(i).appName);//app的名称
                bean.setVersion(localExperimentApps.get(i).versionName);//版本号
                bean.setCheck(false);
                bean.setUpdate(false);
                list.add(bean);
            }
        }

        return  list;
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

    /**
     * 如果實驗未設置則對話框提示進行設置
     * @param message
     */
    private void showSetExperimentNoticeDialog(String message){
        if(null != mNoticeDialog){
            mNoticeDialog.dismiss();
            mNoticeDialog = null;
        }
        if(null == mNoticeDialog){
            mNoticeDialog = new CustomDialog(this);
            LayoutInflater mInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = (View)mInflater.inflate(R.layout.dialog_common_hint_layout, null);
            TextView hintText = (TextView)view.findViewById(R.id.common_dialog_hint_txt);
            TextView comonTxt = (TextView)view.findViewById(R.id.common_hint_txt);
            comonTxt.setVisibility(View.INVISIBLE);
            hintText.setText(message);
            CustomDialog.Builder builder = new CustomDialog.Builder(this);
            builder.setContentView(view);
            builder.setPositiveButton(R.string.ok,  new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub



                    mNoticeDialog.dismiss();
                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    mNoticeDialog.dismiss();
                    mNoticeDialog = null;
                }
            });
            mNoticeDialog = builder.create();
            mNoticeDialog.show();
        }
    }

    /**
     * 顯示更新實驗更新對話框
     *
     */
    private void showExperimentUpdateDialog(final String questionNo,final String experimentName,String message){
        if(null != mUpdateDialog){
            mUpdateDialog.dismiss();
            mUpdateDialog = null;
        }
        if(null == mUpdateDialog){
            mUpdateDialog = new CustomDialog(this);
            LayoutInflater mInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = (View)mInflater.inflate(R.layout.dialog_common_hint_layout, null);
            TextView hintText = (TextView)view.findViewById(R.id.common_dialog_hint_txt);
            TextView comonTxt = (TextView)view.findViewById(R.id.common_hint_txt);
            comonTxt.setVisibility(View.INVISIBLE);
            hintText.setText(message);

            CustomDialog.Builder builder = new CustomDialog.Builder(this);
            builder.setContentView(view);
            builder.setPositiveButton(R.string.ok,  new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub


                    mUpdateDialog.dismiss();
                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    mUpdateDialog.dismiss();
                    mUpdateDialog = null;
                }
            });
            mUpdateDialog = builder.create();
            mUpdateDialog.show();
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
        String packageName = PackageUtils.getPackageName(questionNo);
        if(PackageUtils.isApkInstalled(this,packageName)){
            //存在则拉起
            UserN userN = new UserN("Admin","Admin","Admin",null);
            Gson gson = new Gson();
            String user_info = gson.toJson(userN);
            ComponentName componentName = new ComponentName(packageName,
                    packageName+".LoginActivity");
            LogUtil.i("1111" , packageName+".LoginActivity");
            Intent intent = new Intent();
            intent.setComponent(componentName);;
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("user",user_info);
            startActivity(intent);
        }else{
            LogUtil.i("1111","不存在拉起的应用");
        }
    }

    /**
     * 获取服务器固件列表信息
     * @param
     */
    private void  getRemoteAppInfo(){
        String token = PreferenceUtil.getInstance(getApplicationContext())
                .getStringValue(SharedConstants.TOKEN,"");
        RequestManager.getInstance(getApplicationContext()).getDeviceListInfo(token,this);
    }

    //获取本地实验应用的信息
    private void getLocalAppInfoList(){

        localExperimentApps = CommonUtils.getAppInfoList(getApplicationContext());

    }

    private void releaseDialog(){

        if(null != mChooseDialog){
            mChooseDialog.dismiss();;
            mChooseDialog= null;
        }

        if(null != mNoticeDialog){
            mNoticeDialog.dismiss();;
            mNoticeDialog = null;
        }

        if(null != mUpdateDialog){
            mUpdateDialog.dismiss();
            mUpdateDialog = null;
        }
    }

    private void clearList(){
        if(null != localExperimentApps){
            localExperimentApps.clear();
            localExperimentApps = null;
        }

        if(null != mExperimentList){
            mExperimentList.clear();
            mExperimentList = null;
        }

        if(null != remoteVersionList){
            remoteVersionList.clear();
            remoteVersionList = null;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.setting_img:
                //Intent intent = new Intent(AdminChooseExperimentActivity.this,WlanConnectActivity.class);
               // startActivity(intent);
                break;
        }

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onSuccess(String response) {

        remoteVersionList.clear();
        if(!StringUtils.isEmpty(response)){
            Gson gson = new Gson();
            DeviceDetailReponse deviceDetailReponse = gson.fromJson(response,DeviceDetailReponse.class);
            if(deviceDetailReponse.getCode() == 200){
                //获取每个的版本号
                List<DeviceDetailReponse.Data> dataList = deviceDetailReponse.getData();
                if(dataList.size()>0){
                    for(DeviceDetailReponse.Data data:dataList){
                        HashMap<String,String> version =  new HashMap<String,String>();
                        LogUtil.i("AdminChooseExperimentActivity" , "data.getQuestionNo()" + data.getQuestionNo() +"");
                        LogUtil.i("AdminChooseExperimentActivity" , "data.getFirewareVersion()" + data.getFirewareVersion() +"");
                        version.put(data.getQuestionNo(),data.getFirewareVersion());
                        remoteVersionList.add(version);
                    }
                }

                mUIHandler.sendEmptyMessage(MSG_NOTIFY_EXPERIMENT_LIST);
            }
        }

    }

    private static class UIHandler extends Handler {

        WeakReference<AdminChooseExperimentActivity> mActivityReference;

        public UIHandler(AdminChooseExperimentActivity mActivity){
            mActivityReference = new WeakReference<AdminChooseExperimentActivity>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final AdminChooseExperimentActivity mActivity = mActivityReference.get();
            if(null == mActivity){
                return;
            }
            switch (msg.what) {
                case MSG_NOTIFY_EXPERIMENT_LIST:
                    LogUtil.i("AdminChooseExperimentActivity","mActivity.remoteVersionList.size()" + mActivity.remoteVersionList.size());
                    if(mActivity.remoteVersionList.size() > 0 ){
                        for(HashMap<String,String> remoteAppInfo:mActivity.remoteVersionList){
                            //每一个远程的app信息
                            for(int i = 0;i< mActivity.mExperimentList.size();i++){
                                //获取本地app的questionNo；
                                LogUtil.i("AdminChooseExperimentActivity",
                                        "mExperimentList.get(i).getExperiment_name()" + mActivity.mExperimentList.get(i).getExperiment_name());
                                String questionNo = PackageUtils.getQuestionNumber(mActivity.getApplicationContext(),mActivity.mExperimentList.get(i).getExperiment_name());

                                String remoteVersion = remoteAppInfo.get(questionNo);
                                //本地app信息的与远程的app版本信息进行比较
                                if(remoteVersion != null){
                                    if(!remoteVersion.equals(mActivity.mExperimentList.get(i).getVersion())){
                                        mActivity.mExperimentList.get(i).setUpdate(true);
                                    }else{
                                        mActivity.mExperimentList.get(i).setUpdate(false);
                                    }
                                }
                            }
                        }
                    }
                    mActivity.mExperimentAdapter.setmExperimentList(mActivity.mExperimentList);
                    mActivity.mExperimentAdapter.notifyDataSetChanged();
                    break;

                default:
                    break;
            }
        }
    }
}
