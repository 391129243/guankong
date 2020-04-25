package com.learn.all_electric;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.learn.all_electric.adapter.SeatAdapter;
import com.learn.all_electric.base.BaseActivity;
import com.learn.all_electric.bean.SeatBean;
import com.learn.all_electric.constants.SharedConstants;
import com.learn.all_electric.utils.PreferenceUtil;
import com.learn.all_electric.utils.StringUtils;
import com.learn.all_electric.view.CustomDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 考场设置
 */
public class ExamRoomSettingsActivity extends BaseActivity implements View.OnClickListener{

    private TextView mAdminTxt;
    //考试名称
    private TextView mExamNameTxt;
    private TextView mSchoolTxt;
    //考场编号
    private TextView mCentreNoTxt;
    private TextView mSeatNoTxt;
    private RecyclerView mSeat_RecyView;
    private SeatAdapter mSeatAdapter;
    private Button mLogoutBtn;
    private Button mSubmitBtn;

    private List<SeatBean> mSeatList;
    private String token;
    private String access_token;
    private CustomDialog mChooseDialog;


    @Override
    protected int getLayoutId() {

        return R.layout.module_activity_examroom_settings;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mAdminTxt = (TextView)findViewById(R.id.exam_admin_text);
        mExamNameTxt = (TextView)findViewById(R.id.exam_setting_name_txt);
        mSchoolTxt = (TextView)findViewById(R.id.exam_setting_school_txt);
        mCentreNoTxt = (TextView)findViewById(R.id.exam_setting_roomnum_txt);
        mLogoutBtn = (Button)findViewById(R.id.logout);
        mSeatNoTxt = (TextView)findViewById(R.id.exam_setting_choose_seat_txt);
        mSubmitBtn = (Button)findViewById(R.id.exam_setting_submit_seat_btn);
        mSeat_RecyView = (RecyclerView)findViewById(R.id.exam_room_recycler_seat);
    }

    @Override
    protected void initListener() {
        mLogoutBtn.setOnClickListener(this);
        mSubmitBtn.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mSeatList){
            mSeatList.clear();
            mSeatList = null;
        }

        if(null != mChooseDialog){
            mChooseDialog.dismiss();;
            mChooseDialog.cancel();;
        }

    }

    private void initVariables() {
        // 管理员
        mAdminTxt.setText(mApplication.getUserName());
        // 考试名称
        mExamNameTxt.setText("广东省-肇庆市-端州区2020年物理实验操作考试");
        // 学校
        mSchoolTxt.setText("实验中学");
        // 考场编号
        mCentreNoTxt.setText("1");
        String seat_Name = PreferenceUtil.getInstance(getApplicationContext())
                .getStringValue(SharedConstants.SEAT_NO,"1/1");
        showSeatNumber(seat_Name);

        mSeatList = new ArrayList<SeatBean>();
        mSeatList = getSeatList(seat_Name);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this,5);
        mSeat_RecyView.setLayoutManager(mGridLayoutManager);
        mSeatAdapter = new SeatAdapter(mSeatList);
        mSeat_RecyView.setAdapter(mSeatAdapter);
        mSeatAdapter.notifyDataSetChanged();
        mSeatAdapter.setOnItemClickListener(new SeatAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                SeatBean bean  = mSeatList.get(position);
                String seatName = bean.getSeatName();

                showChooseSeatDialog(seatName);
            }
        });

    }



    private List<SeatBean> getSeatList(final String check_seatNo) {
        List<SeatBean> beans = new ArrayList<>();
        int seat_number = 30;
        for (int i = 1; i <= seat_number; i++) {

            if(getSeatNumber(i).equals(check_seatNo)){
                beans.add(new SeatBean(getSeatNumber(i),true));
            }else{
                beans.add(new SeatBean(getSeatNumber(i),false));
            }

        }
        return beans;
    }

    //获取座位号
    private String getSeatNumber(int i){
        StringBuilder seatNum = new StringBuilder();
        int quotient = i/5;
        //余数
        int remainder = i % 5;
        if(remainder == 0){
            remainder = 5;
            seatNum.append(quotient).append("/").append(remainder);
            return seatNum.toString();
        }else{
            seatNum.append(quotient+1).append("/").append(remainder);
            return seatNum.toString();
        }
    }

    //显示座位号
    private void showSeatNumber(String seatName){
        String [] seat_number = StringUtils.splitArray(seatName);
        if(seat_number.length == 2){

            mSeatNoTxt.setText(seat_number[0] + "排" + seat_number[1]+"座");
        }
    }

    /****/
    private void showChooseSeatDialog(final String seatName){

        if(null != mChooseDialog){
            mChooseDialog.dismiss();
            mChooseDialog = null;
        }
        if(null == mChooseDialog){
            mChooseDialog = new CustomDialog(this);
            LayoutInflater mInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = (View)mInflater.inflate(R.layout.dialog_common_hint_layout, null);
            TextView hintText = (TextView)view.findViewById(R.id.common_dialog_hint_txt);

            hintText.setText("确定选择"+ seatName+"座位吗?");
            CustomDialog.Builder builder = new CustomDialog.Builder(this);
            builder.setContentView(view);
           // builder.setTitle("提示");
            builder.setPositiveButton(R.string.ok,  new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub

                    showSeatNumber(seatName);
                    PreferenceUtil.getInstance(getApplicationContext())
                            .setValueByName(SharedConstants.SEAT_NO,seatName);
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



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logout:
                finish();
                break;
            case R.id.exam_setting_submit_seat_btn:
                //确定座位号，下一步
                startActivity(new Intent(ExamRoomSettingsActivity.this, AdminChooseExperimentActivity.class));
                break;
             default:
                break;

        }
    }



}
