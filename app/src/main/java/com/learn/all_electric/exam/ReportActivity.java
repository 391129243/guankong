package com.learn.all_electric.exam;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.learn.all_electric.base.BaseApplication;
import com.learn.all_electric.constants.Constant;
import com.learn.all_electric.constants.StatusBarCompat;
import com.learn.all_electric.LoginActivity;
import com.learn.all_electric.R;
import com.learn.all_electric.bean.ExperimentBean;
import com.learn.all_electric.utils.CommonUtils;

import java.util.List;
import java.util.Map;

import static util.NumberUtils.DOUBLE_FORMAT;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView tv_exam_name;
    private TextView tv_card_number;
    private TextView tv_subject;
    private TextView tv_operation;
    private TextView tv_operation_time;
    private TextView tv_grade;
    private TextView tv_total;
    private TextView tv_details;
    private TextView tv_report_card_exit;

    private TextView reportApply;
    private TextView reportExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.compat(this, getResources().getColor(android.R.color.transparent));
        setContentView(R.layout.activity_report_card);

        tv_exam_name = (TextView) findViewById(R.id.tv_exam_name);
        tv_card_number = (TextView) findViewById(R.id.tv_card_number);
        tv_subject = (TextView) findViewById(R.id.tv_subject);
        tv_operation = (TextView) findViewById(R.id.tv_operation);
        tv_operation_time = (TextView) findViewById(R.id.tv_operation_time);
        tv_grade = (TextView) findViewById(R.id.tv_grade);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_details = (TextView) findViewById(R.id.tv_details);
        tv_report_card_exit = (TextView) findViewById(R.id.tv_report_card_exit);
        reportApply = (TextView) findViewById(R.id.reportApply);
        reportExit = (TextView) findViewById(R.id.reportExit);
        reportApply.setOnClickListener(this);
        reportExit.setOnClickListener(this);


        Drawable drawable1 = getResources().getDrawable(R.drawable.retreat_icon);
        drawable1.setBounds(0, 0, 60, 60);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        tv_report_card_exit.setCompoundDrawables(null, drawable1, null, null);

        TextView top_title = findViewById(R.id.top_title);
        RelativeLayout top_layout = findViewById(R.id.top_layout);
        StatusBarCompat.setPaingTop(this, top_layout);
        findViewById(R.id.top_back).setVisibility(View.GONE);
        if (BaseApplication.examListBean != null) {
            top_title.setText(BaseApplication.examListBean.getExamName());
            for (int i = 0; i < BaseApplication.examListBean.getBeanLists().size(); i++) {
                ExperimentBean bean = BaseApplication.examListBean.getBeanLists().get(i);
                setValues(bean, i);
            }
        }
        if (BaseApplication.experimentBean != null) {
            for (ExperimentBean.StepBean stepBean : BaseApplication.experimentBean.getStepBeans()) {
                double answerScore = stepBean.getAnswerScores().get(ExamUtils.is_dismantle);
                answer += answerScore;
                if (CommonUtils.isNullDouble(stepBean.getOwnScores(), ExamUtils.is_dismantle)) {
                    own += stepBean.getOwnScores().get(ExamUtils.is_dismantle);
                }
            }
        }
        tv_grade.setText(DOUBLE_FORMAT.format(own) + "分/满分" + answer + "分");
        Double total_double = Double.valueOf(own);
        tv_total.setText(DOUBLE_FORMAT.format(total_double / answer * 100));
//
        tv_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ReportActivity.this, DetailsActivity.class));

            }
        });
//
        tv_report_card_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ReportActivity.this, LoginActivity.class));

                finish();

            }
        });


        tv_exam_name.setText(BaseApplication.userName);
        tv_card_number.setText("00000");
        tv_subject.setText("物理");
        tv_operation.setText(BaseApplication.app_name);
        tv_operation_time.setText((BaseApplication.duration / 60 < 10 ? "0" +
                BaseApplication.duration / 60 : BaseApplication.duration / 60) +
                ":" + (BaseApplication.duration % 60 < 10 ? "0" +
                BaseApplication.duration % 60 : BaseApplication.duration % 60));


    }

    private double answer = 0, own = 0;

    private void setValues(ExperimentBean bean, int position) {
        double ownNum = 0;
        int size = bean.getStepBeans().size();
        for (int i = 0; i < size; i++) {
            ExperimentBean.StepBean childBean = bean.getStepBeans().get(i);
            for (Map.Entry<String, Double> entry : childBean.getAnswerScores().entrySet()) {
//                Log.d("test", "项目:" + entry.getKey() + "  " + entry.getValue());
                double score = 0;
                int numCount = 0;
                int numChu = 0;
                if (childBean.getChioseMaps() != null) {
                    List<ExperimentBean.StepBean.StepChioseBean> chioseBeans = childBean.getChioseMaps().get(entry.getKey());
                    if (chioseBeans != null && chioseBeans.size() > 0) {
                        score = entry.getValue();
                        int  noENum1 = 0, noClose = 0;
                        for (ExperimentBean.StepBean.StepChioseBean chioseBean : chioseBeans) {
                            if (TextUtils.isEmpty(chioseBean.getTimi_answer())) {
                                numChu++;
                            } else {
                                if (ExamUtils.switch_Is_Close.equals(entry.getKey()) || ExamUtils.connection_Situation.equals(entry.getKey())) {
                                    numCount += chioseBean.getScore();
                                    if (ExamUtils.switch_Is_Close.equals(entry.getKey())) {
                                        if (chioseBean.isIscolseNoEffect()) {
                                            if (chioseBean.getScore() == 0) {
                                                noClose++;
                                            }
                                        } else {
                                            noENum1++;
                                        }
                                    }
                                } else {
                                    if (chioseBean.getAnswer().equals(chioseBean.getTimi_answer())) {
                                        if (!ExamUtils.ammeter_number.equals(entry.getKey()) && !ExamUtils.is_eques.equals(entry.getKey())) {
                                            numCount++;
                                        }
                                    }
                                }
                            }
                        }
                        if (ExamUtils.ammeter_number.equals(entry.getKey())) {
                            if (CommonUtils.isNullDouble(childBean.getOwnScores(), ExamUtils.ammeter_number_duol)) {
                                double duol = childBean.getOwnScores().get(ExamUtils.ammeter_number_duol);
                                score = duol;
                            }
                            if (CommonUtils.isNullDouble(childBean.getOwnScores(), ExamUtils.ammeter_number_good)) {
                                double good = childBean.getOwnScores().get(ExamUtils.ammeter_number_good);
                                score += good;
                            }
                        }
                        if (ExamUtils.switch_Is_Close.equals(entry.getKey())) {
                            score = 0;
                            if (noENum1 <= 0) {
                                score += ExamUtils.colse_No_Effect_score2;
                            }
                            if (noClose > 0) {
                                score = +noClose;
                            }
                            if (!CommonUtils.isNull(childBean.getOwnAnswers(), ExamUtils.colse_No_Effect)) {
                                numCount += ExamUtils.colse_No_Effect_score2;
                            }
                        }
                        if (ExamUtils.connection_Situation.equals(entry.getKey())) {
                            if (CommonUtils.isNull(childBean.getOwnAnswers(), ExamUtils.max3)) {
                                if (ExamUtils.Y_VALUES.equals(childBean.getOwnAnswers().get(ExamUtils.max3))) {
                                    numCount = (int) (entry.getValue() * chioseBeans.size() - 4);
                                }
                            }
                            if (CommonUtils.isNull(childBean.getOwnAnswers(), ExamUtils.max1)) {
                                if (ExamUtils.Y_VALUES.equals(childBean.getOwnAnswers().get(ExamUtils.max1))) {
                                    numCount = (int) (entry.getValue() * chioseBeans.size() - 1);
                                }
                            }
                        }
                    } else {
                        if (CommonUtils.isNullDouble(childBean.getOwnScores(), entry.getKey())) {
                            score = childBean.getOwnScores().get(entry.getKey());
                        }
                    }
                } else {
                    if (CommonUtils.isNullDouble(childBean.getOwnScores(), entry.getKey())) {
                        score = childBean.getOwnScores().get(entry.getKey());
                    }
                }
                if (ExamUtils.connection_Situation.equals(entry.getKey()) || (ExamUtils.switch_Is_Close.equals(entry.getKey()) && position == 1 && numCount > 0)) {
                    ownNum += numCount;
                } else if (ExamUtils.selection_Situation.equals(entry.getKey())) {
                    ownNum += score * numCount - numChu;
                } else {
                    ownNum += score;
                }
            }
        }
        answer += bean.getScore_total();
        own += ownNum;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //BaseApplication.clear();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reportExit:
                startActivity(new Intent(ReportActivity.this, LoginActivity.class));
                finish();

                break;
            case R.id.reportApply://对成绩有议，申请人工生活
                break;
        }
    }
}
