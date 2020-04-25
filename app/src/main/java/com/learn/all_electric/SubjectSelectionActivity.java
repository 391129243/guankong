package com.learn.all_electric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learn.all_electric.base.BaseActivity;
import com.learn.all_electric.view.SubjectView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 学科选择
 */
public class SubjectSelectionActivity extends BaseActivity {

    private RelativeLayout mTitleLayout;
    private Button mLogoutBtn;
    private TextView mUserNameTv;
    private SubjectView mChemical;
    private SubjectView mPhysics;
    private SubjectView mBiological;

     @Override
    protected int getLayoutId() {

        return R.layout.module_activity_subject_selection;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mTitleLayout= (RelativeLayout)findViewById(R.id.subject_selection_title_layout);
        mLogoutBtn = (Button)mTitleLayout.findViewById(R.id.back_btn);
        mUserNameTv = (TextView)findViewById(R.id.subject_selection_user_tv);
        mChemical = (SubjectView)findViewById(R.id.subject_selection_chemical_tx);
        mPhysics = (SubjectView)findViewById(R.id.subject_selection_physical_tx);
        mBiological = (SubjectView)findViewById(R.id.subject_selection_biological_tx);

    }

    @Override
    protected void initListener() {
        mUserNameTv.setText(mApplication.getUserName());
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        // 物理
        mPhysics.setOnImageButtonClickListener(new SubjectView.OnSubjectBtnClickListener() {
            @Override
            public void customSubjectButtonClicked(View v) {
                Intent intent = new Intent(SubjectSelectionActivity.this,UserChooseExperimentActivity.class);
                intent.putExtra("subject","physical");
                startActivity(intent);
            }
        });

        mChemical.setOnImageButtonClickListener(new SubjectView.OnSubjectBtnClickListener() {
            @Override
            public void customSubjectButtonClicked(View v) {
                mPhysics.setSelected(false);
                mChemical.setSelected(true);
                mBiological.setSelected(false);
            }
        });
        mBiological.setOnImageButtonClickListener(new SubjectView.OnSubjectBtnClickListener() {
            @Override
            public void customSubjectButtonClicked(View v) {
                mPhysics.setSelected(false);
                mChemical.setSelected(false);
                mBiological.setSelected(true);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
