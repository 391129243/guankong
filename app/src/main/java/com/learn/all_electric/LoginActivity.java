package com.learn.all_electric;

import android.Manifest;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jay.easykeyboard.SystemKeyBoardEditText;
import com.jay.easykeyboard.action.KeyBoardActionListener;
import com.learn.all_electric.base.BaseActivity;
import com.learn.all_electric.bean.ExamDetailReponse;
import com.learn.all_electric.bean.UserLoginFailResponse;
import com.learn.all_electric.bean.UserLoginResponse;
import com.learn.all_electric.bean.UserN;
import com.learn.all_electric.constants.Constant;
import com.learn.all_electric.constants.PackageConstants;
import com.learn.all_electric.constants.SharedConstants;
import com.learn.all_electric.myinterface.ExamDetailCallback;
import com.learn.all_electric.myinterface.LoginResponseCallback;
import com.learn.all_electric.myinterface.OnNetWorkChangedListener;
import com.learn.all_electric.myinterface.OnReceiveScoreListener;
import com.learn.all_electric.receiver.NetworkChangeReceiver;
import com.learn.all_electric.receiver.UploadScoreReceiver;
import com.learn.all_electric.service.LongRunningService;
import com.learn.all_electric.service.UploadScoreService;
import com.learn.all_electric.utils.CommonUtils;
import com.learn.all_electric.utils.EncrypUtils;
import com.learn.all_electric.utils.InternetUtils;
import com.learn.all_electric.utils.LogUtil;
import com.learn.all_electric.utils.PackageUtils;
import com.learn.all_electric.utils.PreferenceUtil;
import com.learn.all_electric.utils.ProgressDialogUtil;
import com.learn.all_electric.utils.RequestManager;
import com.learn.all_electric.utils.StringUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class LoginActivity extends BaseActivity implements View.OnClickListener ,OnNetWorkChangedListener, LoginResponseCallback,ExamDetailCallback, OnReceiveScoreListener {

    private final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private UIHandler mUIHandler;
    private NetworkChangeReceiver netWorkChangedReceiver;
    private UploadScoreReceiver uploadScoreReceiver;
    private TextView btn_login;
   // private SystemKeyBoardEditText et_login_user_name;
   // private SystemKeyBoardEditText et_login_pass_word;
    private EditText et_login_user_name;
    private EditText et_login_pass_word;
    private SeekBar mVertify_sb;
    private TextView mVertify_tv;
    private RelativeLayout mVertify_layout;
    private RadioGroup userTypeRg;
    private RadioButton userTeachRb;
    private Dialog mProgressDialog;
    private boolean isSuccess_vertify = false;
    private static final int MSG_LOGIN_FAIL = 0;
    private static final int MSG_LOGIN_SUCCESS = 1;
    private static final int MSG_EXAMDETAIL_SUCCESS = 3;
    private static final int MSG_EXAMDETAIL_FAIL = 4;
    private static final int MSG_FAIL = 5;
    private Context mContext;
    private String userName;
    private String account;
    private String roleName;
    private String token;
    private String questionNo;
    private String userType ;

    @Override
    protected int getLayoutId() {
        return R.layout.module_activity_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        btn_login = (TextView) findViewById(R.id.btn_login);
        et_login_user_name = (EditText) findViewById(R.id.et_login_user_name);
        et_login_pass_word = (EditText) findViewById(R.id.et_login_passeord);
        mVertify_sb = (SeekBar)findViewById(R.id.login_vertify_sb);
        mVertify_tv= (TextView)findViewById(R.id.login_vertify_tv);
        mVertify_layout = (RelativeLayout)findViewById(R.id.login_vertify_layout);
        userTypeRg = (RadioGroup)findViewById(R.id.user_type_gp);
        userTeachRb = (RadioButton)findViewById(R.id.user_type_teacher);



    }

    @Override
    protected void initListener() {
/*        et_login_user_name.setOnKeyboardActionListener(new KeyBoardActionListener() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onTextChange(Editable editable) {


            }

            @Override
            public void onClear() {

            }

            @Override
            public void onClearAll() {

            }
        });
        et_login_pass_word.setOnKeyboardActionListener(new KeyBoardActionListener() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onTextChange(Editable editable) {

            }

            @Override
            public void onClear() {

            }

            @Override
            public void onClearAll() {

            }
        });*/
        btn_login.setOnClickListener(this);
        mVertify_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (seekBar.getProgress() == seekBar.getMax()) {
                    mVertify_layout.setBackgroundColor(getResources()
                            .getColor(R.color.login_seekbar_progress));
                    mVertify_tv.setVisibility(View.VISIBLE);
                    mVertify_tv.setTextColor(Color.WHITE);
                    mVertify_tv.setText(R.string.login_vertify_success);
                    mVertify_sb.setEnabled(false);
                    isSuccess_vertify = true;
                } else {
                    mVertify_tv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mVertify_layout.setBackgroundColor(getResources()
                        .getColor(R.color.login_seekbar_progress));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() != seekBar.getMax()) {
                    seekBar.setProgress(0);
                    mVertify_sb.setEnabled(true);
                    mVertify_tv.setVisibility(View.VISIBLE);
                    mVertify_tv.setTextColor(Color.GRAY);
                    mVertify_tv.setText(R.string.login_vertify_start);
                    isSuccess_vertify = false;
                    mVertify_layout.setBackgroundColor(getResources()
                            .getColor(R.color.login_seekbar_background));
                }else {
                    mVertify_sb.setEnabled(false);
                    isSuccess_vertify = true;
                    mVertify_layout.setBackgroundColor(getResources()
                            .getColor(R.color.login_seekbar_progress));
                }

            }
        });

        userTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                switch (id){
                    case R.id.user_type_teacher:
                        userType = "device-teacher";
                        PreferenceUtil.getInstance(getApplicationContext()).setValueByName(SharedConstants.LOGIN_USERTPYE,userType);
                        break;
                    case R.id.user_type_student:
                        userType = "device-student";
                        PreferenceUtil.getInstance(getApplicationContext()).setValueByName(SharedConstants.LOGIN_USERTPYE,userType);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 获取试验台设置的实验编号
     */
    private void iniVariables(){
        mContext = this;
        mUIHandler = new UIHandler(this);
        RequestManager.getInstance(getApplicationContext()).setOnUploadExamScoreListener(new RequestManager.OnUploadExamScoreListener() {
            @Override
            public void onCompleteUpload() {
                stopKeepTokenAlive();
            }
        });

    }

    private void initAccount(){
        questionNo = PreferenceUtil.getInstance(getApplicationContext())
                .getStringValue(SharedConstants.EXPERIMENT_SN,"");
        et_login_user_name.setText("");
        et_login_pass_word.setText("");
        userType = PreferenceUtil.getInstance(getApplicationContext())
                .getStringValue(SharedConstants.LOGIN_USERTPYE,"device-teacher");
        if(userType.equals("device-teacher")){
            userTeachRb.setChecked(true);
        }else {
            userTeachRb.setChecked(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniVariables();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(PERMISSIONS, 102);
        }
        registerReceivers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAccount();
        clearseekbarStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceivers();
        stopKeepTokenAlive();
        mContext = null;
        if(null != mProgressDialog){
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        if(null != mUIHandler){
            mUIHandler.removeCallbacksAndMessages(null);
            mUIHandler = null;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:

                onLogin();

                break;
            default:
                break;
        }
    }


    private static class UIHandler extends Handler {

        WeakReference<LoginActivity> mActivityReference;

        public UIHandler(LoginActivity mActivity){
            mActivityReference = new WeakReference<LoginActivity>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final LoginActivity mActivity = mActivityReference.get();
            if(null == mActivity){
                return;
            }
            switch (msg.what) {
                case MSG_LOGIN_FAIL:
                    UserLoginFailResponse failResponse = (UserLoginFailResponse)msg.obj;
                    if(null != failResponse){
                        String error_code = failResponse.getError_code();
                        String error_discription = failResponse.getError_description();
                        mActivity.displayToast(error_discription);
                    }
                    mActivity.clearseekbarStatus();
                    mActivity.hideProgressDialog();
                    break;

                case MSG_LOGIN_SUCCESS:
                    //下载失败，提示
                    UserLoginResponse successResponse = (UserLoginResponse)msg.obj;
                    if(null != successResponse){
                        String role_name = successResponse.getRole_name();
                        LogUtil.i("login" , role_name);

                        String account = successResponse.getAccount();
                        String user_name = successResponse.getUser_name();
                        String reflesh_token = successResponse.getRefresh_token();
                        String access_token = successResponse.getAccess_token();
                        mActivity.saveLoginParams(role_name,account,user_name,reflesh_token,access_token);
                        mActivity.displayToast(R.string.login_success);
                        PreferenceUtil.getInstance(mActivity.getApplicationContext())
                                .setValueByName(SharedConstants.IS_LOGIN, true);
                        if(role_name.contains("student")){
                           mActivity.onExamingLogin();
                        }else if(role_name.contains("teacher-device")||role_name.contains("teacher-monitor")){
                            mActivity.onAdminLogin();
                        }

                    }

                    break;
                case MSG_EXAMDETAIL_SUCCESS:
                    ExamDetailReponse examDetailReponse = (ExamDetailReponse)msg.obj;
                    if(null != examDetailReponse){
                        if(examDetailReponse.getCode() == 200 && examDetailReponse.getSuccess()){
                            String question_no = examDetailReponse.getData().getQuestionNo();
                            String question_name = examDetailReponse.getData().getQuestionName();
                            ExamDetailReponse.Data data = examDetailReponse.getData();
                            mActivity.onStudentExamLogin(examDetailReponse,question_no);


                        }else {
                            mActivity.displayToast(examDetailReponse.getMsg());
                            mActivity.clearseekbarStatus();
                            mActivity.hideProgressDialog();
                            mActivity.stopKeepTokenAlive();
                        }

                    }

                    break;

                case MSG_EXAMDETAIL_FAIL:
                    mActivity.displayToast(R.string.exam_login_fail);
                    mActivity.clearseekbarStatus();
                    mActivity.hideProgressDialog();
                    mActivity.stopKeepTokenAlive();
                    break;

                case MSG_FAIL:
                    ExamDetailReponse response = (ExamDetailReponse)msg.obj;
                    if(null != response){
                        String message = response.getMsg();
                        mActivity.displayToast(message);
                    }
                    mActivity.clearseekbarStatus();
                    mActivity.hideProgressDialog();
                    break;
                default:
                    break;
            }
        }
    }

    private void registerReceivers(){
        registerReceiver(netWorkChangedReceiver = new NetworkChangeReceiver(), new IntentFilter(Constant.NETWORK_CONNECTION_CHANGE));
        netWorkChangedReceiver.registerReceiver(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.BROACAST_UPLOAD_SCORE);
        registerReceiver(uploadScoreReceiver = new UploadScoreReceiver(),intentFilter);
        uploadScoreReceiver.registerReceiver(this);
    }

    private void unregisterReceivers(){
        if(null != netWorkChangedReceiver) {
            netWorkChangedReceiver.unregisterReceiver(this);
            unregisterReceiver(netWorkChangedReceiver);
            netWorkChangedReceiver = null;
        }

        if(null != uploadScoreReceiver){
            unregisterReceiver(uploadScoreReceiver);
            uploadScoreReceiver = null;
        }

    }

    @Override
    public void onNetworkChange(boolean isConnected, int type) {

/*        LogUtil.i("1111","onNetworkChange" + isConnected);
        if(isConnected){
            //扫描一下sdcard中是否存在文件如果存在则上传
            ArrayList<String> uploadFileList = new ArrayList<String>();
            uploadFileList = checkSdcardUploadFile();
            if(uploadFileList != null && uploadFileList.size() > 0){
                for(String fileName : uploadFileList){
                    onUploadExamScore(fileName);
                }
            }
        }*/

    }

    //监听收到需要上传广播的消息
    //收到需要上传的分数的文档名称
    @Override
    public void onReceiveFileName(String fileName) {

        onUploadExamScore(fileName);
    }



    private void onLogin(){
        String account = et_login_user_name.getText().toString();
        String password = et_login_pass_word.getText().toString();
        if(InternetUtils.isConnect(this)){

            if(checkInput(account,password)){
                showProgressDialog(getResources().getString(R.string.login_network_hint));
                if(userTeachRb.isChecked()){
                    //onAdminLogin();
                    onLoginRequest(account,password,userType);
                }else{
                    if(StringUtils.isEmpty(questionNo)){
                        hideProgressDialog();
                        displayToast(R.string.admin_choose_experiment_null);
                        clearseekbarStatus();
                        initAccount();
                        return;
                    }else{
                        onLoginRequest(account,password,userType);
                    }

                }


            }

        }else{
            if(account.equals("Admin")&& password.equals("123456")){
                onAdminLogin();
            }else{
                displayToast(R.string.network_disconnect);
                clearseekbarStatus();
                hideProgressDialog();
            }

        }

    }

    private void onLoginRequest(String account,String password,String user_type){
        String pwdByMd5 = EncrypUtils.md5Decode32(password);
        RequestManager.getInstance(getApplicationContext()).LoginRequest(account,pwdByMd5, user_type,LoginActivity.this);
    }

    private boolean checkInput(String account, String password){
        if(StringUtils.isEmpty(account)){
            displayToast(R.string.login_username_empty);
        }else if(StringUtils.isEmpty(password)){
            displayToast(R.string.login_password_empty);
        }else if(password.length() >6){
            displayToast(R.string.login_password_length);
        }else if(!isSuccess_vertify){
            displayToast(R.string.login_vertify_error);
        }else{
            return true;
        }
        return false;
    }

    private void saveLoginParams(String roleName,String account,String user_name,String reflesh_token, String access_token){
        this.account = account;
        this.userName = user_name;
        this.roleName = roleName;
        token = "bearer " + access_token;
        PreferenceUtil.getInstance(getApplicationContext())
                .setValueByName(SharedConstants.LOGIN_USERNAME,user_name);
        PreferenceUtil.getInstance(getApplicationContext())
                .setValueByName(SharedConstants.LOGIN_ACCOUNT,account);
        PreferenceUtil.getInstance(getApplicationContext())
                .setValueByName(SharedConstants.REFRESH_TOKEN,reflesh_token);
        PreferenceUtil.getInstance(getApplicationContext())
                .setValueByName(SharedConstants.ACCESS_TOKEN,access_token);
        PreferenceUtil.getInstance(getApplicationContext())
                .setValueByName(SharedConstants.TOKEN, token);
    }



    /**考试类登录**/
    private void onExamingLogin(){
        //获取考试信息详情
        LogUtil.i("1111","questionNo" + " " +questionNo);
        startKeepTokenAlive();
        getExamDetail(questionNo);
    }

    /**管理员登录**/
    private void onAdminLogin(){
        //跳转考场设置
        hideProgressDialog();
        startActivity(new Intent(LoginActivity.this, ExamRoomSettingsActivity.class));
    }


    /**获取考试详情
     * 使用token作为请求头
     * @param questionNo 考试编号
     * **/
    private void getExamDetail(String questionNo){
        if(InternetUtils.isConnect(this)){
            token = PreferenceUtil.getInstance(getApplicationContext())
                    .getStringValue(SharedConstants.TOKEN,"");
            String serialNum = CommonUtils.getSerialNumber();
            LogUtil.i("1111" ,"serialNum"  + serialNum);
            if(!StringUtils.isEmpty(token)){

                RequestManager.getInstance(getApplicationContext()).getExamDetail(token,serialNum,questionNo,LoginActivity.this);
            }else{
                String access_token = PreferenceUtil.getInstance(getApplicationContext())
                        .getStringValue(SharedConstants.ACCESS_TOKEN,"");
                token = "bearer " + access_token;

                RequestManager.getInstance(getApplicationContext()).getExamDetail(token,serialNum,questionNo,LoginActivity.this);
            }
        }
    }


    /**拉起考试跳转学生实验登录**/
    private void onStudentExamLogin(ExamDetailReponse examDetailReponse,String questionNo){
        LogUtil.i("1111","onStudentExamLogin");

        hideProgressDialog();
        String packageName = PackageUtils.getPackageName(questionNo);
        //String packageName = PackageConstants.SN14_TEMPERUTURE_PACKAGENAME;
        if(PackageUtils.isApkInstalled(this,packageName)){
            //存在则拉起
            UserN userN = new UserN(account,userName,roleName,examDetailReponse);
            Gson gson = new Gson();
            String user_info = gson.toJson(userN);
            ComponentName componentName = new ComponentName(packageName,
                    packageName+".LoginActivity");
            Intent intent = new Intent();
            intent.setComponent(componentName);;
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("user",user_info);
            startActivity(intent);
        }else{
            displayToast(R.string.login_userexam_app_null);
            hideProgressDialog();
        }

    }


    //启动读取分数文件
    private void onUploadExamScore(String fileName){
        Intent intent = new Intent(this,UploadScoreService.class);
        intent.putExtra("taskName",1);
        intent.putExtra("upload_score_file",fileName);
        startService(intent);
    }

    private void startKeepTokenAlive(){
        //定时刷新token。保存token的状态
        LogUtil.i("1111","startKeepTokenAlive");
        Intent intent = new Intent(LoginActivity.this, LongRunningService.class);
        startService(intent);
    }

    private void stopKeepTokenAlive(){
        //定时刷新token。保存token的状态
        LogUtil.i("1111","stopKeepTokenAlive");
        Intent intent = new Intent(LoginActivity.this, LongRunningService.class);
        stopService(intent);
    }

    private void clearseekbarStatus(){
        mVertify_sb.setProgress(0);
        mVertify_sb.setEnabled(true);
        mVertify_tv.setVisibility(View.VISIBLE);
        mVertify_tv.setTextColor(Color.GRAY);
        mVertify_tv.setText(R.string.login_vertify_start);
        isSuccess_vertify = false;
        mVertify_layout.setBackgroundColor(getResources()
                .getColor(R.color.login_seekbar_background));
    }

    private ArrayList<String> checkSdcardUploadFile(){
        ArrayList<String> fileNameList = new ArrayList<String>();
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String sdcard_upload_dir = Environment.getExternalStorageDirectory()+"/UploadScore/";
            File dir = new File(sdcard_upload_dir);
            if(dir.isDirectory()){
                File[] array = dir.listFiles();
                if(array.length > 0){
                    for (File file: array){
                        fileNameList.add(file.getName());
                    }
                }
            }

        }
        return  fileNameList;
    }


    /**进度对话框，可提示登陆加载中**/
    private void showProgressDialog(String message){
        if(null == mProgressDialog){
            mProgressDialog = ProgressDialogUtil.createLoadingDialog(mContext, message);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(true);
            mProgressDialog.show();
        }
    }

    /*
     * 隐藏提示加载
     */
    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void onLoginSuccess(UserLoginResponse successResponse) {

        Message msg = Message.obtain();
        msg.obj = successResponse;
        msg.what = MSG_LOGIN_SUCCESS;
        if(null != mUIHandler){
            mUIHandler.sendMessage(msg);
        }
    }

    @Override
    public void onLoginFailure(UserLoginFailResponse failResponse) {

        Message msg = Message.obtain();
        msg.obj = failResponse;
        msg.what = MSG_LOGIN_FAIL;
        if(null != mUIHandler){
            mUIHandler.sendMessage(msg);
        }
    }

    //失败
    public void onFail(ExamDetailReponse reponse){
        Message msg = Message.obtain();
        msg.obj = reponse;
        msg.what = MSG_FAIL;
        if(null != mUIHandler){
            mUIHandler.sendMessage(msg);
        }
    }


    @Override
    public void onFailExamDetail() {
        if(null != mUIHandler) {
            mUIHandler.sendEmptyMessage(MSG_EXAMDETAIL_FAIL);
        }
    }

    @Override
    public void onSuccessExamDetail(ExamDetailReponse examDetailReponse) {

        Message msg = Message.obtain();
        msg.obj = examDetailReponse;
        msg.what = MSG_EXAMDETAIL_SUCCESS;
        if(null != mUIHandler) {
            mUIHandler.sendMessage(msg);
        }
    }

    //空白处隐藏软键盘
    private void hideSoftInputMethod(){
        InputMethodManager im = (InputMethodManager)LoginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(getCurrentFocus()!= null && getCurrentFocus().getWindowToken() != null){
            im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            hideSoftInputMethod();
        }
        return super.onTouchEvent(event);
    }


}
