package com.learn.all_electric;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learn.all_electric.adapter.ExperimentAdapter;
import com.learn.all_electric.adapter.SeatAdapter;
import com.learn.all_electric.base.BaseActivity;
import com.learn.all_electric.bean.ChooseExperimentBean;

import java.util.ArrayList;

/**用户实验选择**/
public class UserChooseExperimentActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout mTitleLayout;
    private Button mLogoutBtn;
    private ImageView mBackImg;
    private RecyclerView mPhysical_View;
    private String subject;
    private ArrayList<ChooseExperimentBean> mExperimentList;
    private ExperimentAdapter mExperimentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.module_activity_user_chooseexperiment;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        mTitleLayout = (RelativeLayout)findViewById(R.id.user_choose_title_layout);
        mLogoutBtn = (Button)mTitleLayout.findViewById(R.id.back_btn);
        mBackImg = (ImageView)findViewById(R.id.back_img);
        mPhysical_View = (RecyclerView)findViewById(R.id.user_choose_experiment_recycler);
    }

    @Override
    protected void initListener() {
        mLogoutBtn.setOnClickListener(this);

    }

    private void inivariables(){
        mExperimentList = new ArrayList<ChooseExperimentBean>();
        mExperimentList.clear();

        //获取实验名称
        getAllExperimentName();
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this,2);
        mPhysical_View.setLayoutManager(mGridLayoutManager);
        mExperimentAdapter = new ExperimentAdapter(getApplicationContext(),mExperimentList);
        mPhysical_View.setAdapter(mExperimentAdapter);
        mExperimentAdapter.notifyDataSetChanged();

    }

    private void getAllExperimentName(){
        ChooseExperimentBean bean1 = new ChooseExperimentBean("1、连接串联电路和并联电路","V1.2.1",true,false,1);
        ChooseExperimentBean bean2 = new ChooseExperimentBean("2、串联并联电路中电流的关系","V1.2.1",false,false,2);
        ChooseExperimentBean bean3 = new ChooseExperimentBean("3、串联并联电路的电压的关系","V1.2.1",false,false,3);
        ChooseExperimentBean bean4 = new ChooseExperimentBean("4、测量小灯泡的电阻","V1.2.1",false,false,4);
        ChooseExperimentBean bean5 = new ChooseExperimentBean("5、测量小灯泡的电功率","V1.2.1",false,false,5);
        ChooseExperimentBean bean6 = new ChooseExperimentBean("6、探究影响摩擦力大小的因素","V1.2.1",false,false,6);
        ChooseExperimentBean bean7 = new ChooseExperimentBean("7、探究杠杆的平衡条件","V1.2.1",false,false,7);
        mExperimentList.add(bean1);
        mExperimentList.add(bean2);
        mExperimentList.add(bean3);
        mExperimentList.add(bean4);
        mExperimentList.add(bean5);
        mExperimentList.add(bean6);
        mExperimentList.add(bean7);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        inivariables();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
        }
    }


}
