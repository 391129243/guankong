package com.learn.all_electric.exam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learn.all_electric.base.BaseApplication;
import com.learn.all_electric.constants.Constant;
import com.learn.all_electric.constants.StatusBarCompat;
import com.learn.all_electric.R;
import com.learn.all_electric.bean.ExperimentBean;
import com.learn.all_electric.myinterface.DetailsClickListener;
import com.learn.all_electric.utils.BeanUtils;
import com.learn.all_electric.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static util.NumberUtils.DOUBLE_FORMAT;


public class DetailsActivity extends AppCompatActivity {
    LinearLayout content_details, moreLayout;
    TextView answer_scoce, own_scoce, top_title;
    double ownNum = 0;
    List<ExperimentBean> beanList = new ArrayList<>();
//    private DetailsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.compat(this, getResources().getColor(android.R.color.transparent));
        setContentView(R.layout.activity_detail);
        initView();

        moreLayout = findViewById(R.id.moreLayout);
        LinearLayout layoutdetail = findViewById(R.id.layoutdetail);
        if (BaseApplication.examListBean != null && BaseApplication.examListBean.getBeanLists().size() > 1) {
            top_title.setText(BaseApplication.examListBean.getExamName());
            moreLayout.setVisibility(View.VISIBLE);
            layoutdetail.setVisibility(View.GONE);
            beanList.addAll(BaseApplication.examListBean.getBeanLists());
            ExperimentBean experimentBean = new ExperimentBean();
            experimentBean.setTitle("总分");
            experimentBean.setShow(true);
            beanList.add(experimentBean);
            setData(beanList);
        } else {
            moreLayout.setVisibility(View.GONE);
            layoutdetail.setVisibility(View.VISIBLE);
            initData();
        }
    }

    private void initView() {

        content_details = findViewById(R.id.content_details);
        answer_scoce = findViewById(R.id.answer_scoce);
        own_scoce = findViewById(R.id.own_scoce);
        top_title = findViewById(R.id.top_title);
        RelativeLayout top_layout = findViewById(R.id.top_layout);
        StatusBarCompat.setPaingTop(this, top_layout);
        ImageView top_back = findViewById(R.id.top_back);
        top_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initData() {
        if (BaseApplication.examListBean == null) {
            return;
        }
//        top_title.setText(BaseApplication.experimentBean.getTitle());
//        answer_scoce.setText(BaseApplication.experimentBean.getScore_total() + "");
//        content_details.removeAllViews();
//        int size = BaseApplication.experimentBean.getStepBeans().size();
//        for (int i = 0; i < size; i++) {
//            ExperimentBean.StepBean childBean = BaseApplication.experimentBean.getStepBeans().get(i);
//            View view = View.inflate(this, R.layout.item_details, null);
//            TextView stepTitle = view.findViewById(R.id.stepTitle);
//            String hin = "步骤" + (i + 1) + "：" + childBean.getStepTitle();
//            stepTitle.setText(Html.fromHtml(hin));
//            LinearLayout scoring_items = view.findViewById(R.id.scoring_items);
//            scoring_items.removeAllViews();
//            int num = 0;
//            for (Map.Entry<String, Double> entry : childBean.getAnswerScores().entrySet()) {
//                Log.d("test", "项目:" + entry.getKey() + "  " + entry.getValue());
//                View scoring_items_views = View.inflate(this, R.layout.item_layout, null);
//                LinearLayout layout_child = scoring_items_views.findViewById(R.id.layout_child);
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
//                layout_child.setLayoutParams(params);
//                TextView scoring_items_layoutTv = scoring_items_views.findViewById(R.id.scoring_items_layoutTv);
//                scoring_items_layoutTv.setText(Html.fromHtml(entry.getKey()));
//
//                TextView scoring_values_layoutTv = scoring_items_views.findViewById(R.id.scoring_values_layoutTv);
//                scoring_values_layoutTv.setText(entry.getValue() + "");
//
//                TextView examing_operation_layoutTv = scoring_items_views.findViewById(R.id.examing_operation_layoutTv);
//                String answers = "0";
//                double score = 0;
//                if (childBean.getChioseBeans() != null && childBean.getChioseBeans().size() > 0) {
//
////                    if (entry.getKey().equals(TwoUtils.score_answer)) {
////                        ExperimentBean.StepBean.StepChioseBean chioseBean = childBean.getChioseBeans().get(0);
////                        answers = chioseBean.getChioseStart() + "<u>&nbsp;&nbsp;" + chioseBean.getAnswer() + "&nbsp;&nbsp;</u>" + chioseBean.getChioseEnd();
////                        double answer_double= NumberUtils.StringtoDouble(chioseBean.getAnswer());
////                        double Timianswer_double= NumberUtils.StringtoDouble(chioseBean.getTimi_answer());
////                        if(Math.abs(answer_double - Timianswer_double) <= 10 && childBean.getOwnAnswers().get(TwoUtils.balance).equals(TwoUtils.Y_VALUES)){
////                            score = childBean.getAnswerScores().get(entry.getKey()) - 2 ;
////                        }else{
////                            score = 0.0f;
////                        }
////                        score = score + 2 * ScoreUtils.scoreofReadWeight(chioseBean.getAnswer() );
////                        //score = chioseBean.getAnswer().equals(chioseBean.getTimi_answer()) ? childBean.getAnswerScores().get(entry.getKey()) : 0.0f;
////                    }else if (entry.getKey().equals(TwoUtils.density_calculation) && childBean.getChioseBeans().size() > 1) {//密度计算
////                        ExperimentBean.StepBean.StepChioseBean chioseBean = childBean.getChioseBeans().get(1);
////                        answers = chioseBean.getChioseStart() + "<u>&nbsp;&nbsp;" + chioseBean.getAnswer() + "&nbsp;&nbsp;</u>" + chioseBean.getChioseEnd();
////                        double answer_double= NumberUtils.StringtoDouble(chioseBean.getAnswer());
////                        double Timianswer_double= NumberUtils.StringtoDouble(chioseBean.getTimi_answer());
////                        if(Math.abs(answer_double - Timianswer_double) <= 50){
////                            score =childBean.getAnswerScores().get(entry.getKey());
////                        }else{
////                            score = 0.0f;
////                        }
////                    }else if (entry.getKey().equals(TwoUtils.graduated_cylinder_v1)) {//密度计算
////                        ExperimentBean.StepBean.StepChioseBean chioseBean = childBean.getChioseBeans().get(0);
////                        answers = chioseBean.getChioseStart() + "<u>&nbsp;&nbsp;" + chioseBean.getAnswer() + "&nbsp;&nbsp;</u>" + chioseBean.getChioseEnd();
////                        double answer_double= NumberUtils.StringtoDouble(chioseBean.getAnswer());
////                        TwoUtils.SizeofCylinder_v1 = answer_double;
////                        double Timianswer_double= NumberUtils.StringtoDouble(chioseBean.getTimi_answer());
////                        if(answer_double - Timianswer_double >= 5 || answer_double < TwoUtils.SizeofCylinder_v1_min){
////                            score = 0.0f;
////                        }else{
////                            score = childBean.getAnswerScores().get(entry.getKey()) -2 ;
////                        }
////                        score = score + 2 * ScoreUtils.scoreofReadSize(chioseBean.getAnswer() );
////                    }else if (entry.getKey().equals(TwoUtils.graduated_cylinder_v2)) {//密度计算
////                        ExperimentBean.StepBean.StepChioseBean chioseBean = childBean.getChioseBeans().get(0);
////                        answers = chioseBean.getChioseStart() + "<u>&nbsp;&nbsp;" + chioseBean.getAnswer() + "&nbsp;&nbsp;</u>" + chioseBean.getChioseEnd();
////                        double answer_double= NumberUtils.StringtoDouble(chioseBean.getAnswer());
////                        double Timianswer_double= NumberUtils.StringtoDouble(chioseBean.getTimi_answer());
////                        if(Math.abs(answer_double - Timianswer_double - TwoUtils.SizeofCylinder_v1 ) >= 5 || answer_double >= TwoUtils.Sizeofylinder + 5 ){
////                            score = 0.0f;
////                        }else{
////                            score =childBean.getAnswerScores().get(entry.getKey()) -2 ;
////                        }
////                        score = score + 2 * ScoreUtils.scoreofReadSize(chioseBean.getAnswer() );
////                    }else if (entry.getKey().equals(TwoUtils.volume_calculation)) {//密度计算
////                        ExperimentBean.StepBean.StepChioseBean chioseBean = childBean.getChioseBeans().get(0);
////                        answers = chioseBean.getChioseStart() + "<u>&nbsp;&nbsp;" + chioseBean.getAnswer() + "&nbsp;&nbsp;</u>" + chioseBean.getChioseEnd();
////                        double answer_double= NumberUtils.StringtoDouble(chioseBean.getAnswer());
////                        double Timianswer_double= NumberUtils.StringtoDouble(chioseBean.getTimi_answer());
////                        if(Math.abs(answer_double - Timianswer_double) <= 5){
////                            score =childBean.getAnswerScores().get(entry.getKey());
////                        }else{
////                            score = 0.0f;
////                        }
////                    }else {
////                        if (isNull(childBean.getOwnAnswers(), entry.getKey())) {
////                            answers = childBean.getOwnAnswers().get(entry.getKey()).toString();
////                        }
////                        if (isNullDouble(childBean.getOwnScores(), entry.getKey())) {
////                            score = childBean.getOwnScores().get(entry.getKey());
////                        }
////                    }
//
//                } else {
//                    if (isNull(childBean.getOwnAnswers(), entry.getKey())) {
//                        answers = childBean.getOwnAnswers().get(entry.getKey()).toString();
//                    }
//                    if (isNullDouble(childBean.getOwnScores(), entry.getKey())) {
//                        score = childBean.getOwnScores().get(entry.getKey());
//                    }
//                }
//                examing_operation_layoutTv.setText(Html.fromHtml(answers));
//
//                TextView examing_scoreing_layoutTv = scoring_items_views.findViewById(R.id.examing_scoreing_layoutTv);
//                examing_scoreing_layoutTv.setText(score + "");
//
//                scoring_items.addView(scoring_items_views);
//                if (num == childBean.getAnswerScores().size() - 1) {
//                    scoring_items_views.findViewById(R.id.viewLagout).setVisibility(View.GONE);
//                }
//                ownNum+=score;
//                num++;
//            }
//            content_details.addView(view);
//            Log.d("test", "i==" + i + "  size==" + BaseApplication.experimentBean.getStepBeans().size());
//            if (i == size - 1) {
//                view.findViewById(R.id.detailsLayout).setVisibility(View.GONE);
//            }
//
//        }
        own_scoce.setText(DOUBLE_FORMAT.format(ownNum));
    }


    private void setData(List<ExperimentBean> beans) {
        moreLayout.removeAllViews();
        for (int position = 0; position < beans.size(); position++) {
            ExperimentBean bean = beans.get(position);
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_recycler_details, null);
            RelativeLayout recycDetailsRl = itemView.findViewById(R.id.recycDetailsRl);
            TextView recycDetailsTv = itemView.findViewById(R.id.recycDetailsTv);
            ImageView recycDetailsIv = itemView.findViewById(R.id.recycDetailsIv);
            LinearLayout layoutdetail = itemView.findViewById(R.id.layoutdetail);
            LinearLayout zongfenItem = itemView.findViewById(R.id.zongfenItem);
            LinearLayout zfchild = itemView.findViewById(R.id.zfchild);

            content_details = itemView.findViewById(R.id.content_details);
            TextView answer_scoce = itemView.findViewById(R.id.answer_scoce);
            TextView own_scoce = itemView.findViewById(R.id.own_scoce);
            TextView zheshu = itemView.findViewById(R.id.zheshu);


            recycDetailsTv.setText(bean.getTitle());
            int finalPosition = position;
            recycDetailsRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onDetailsClick(bean, itemView, finalPosition);
                    }
                }
            });
            if (position == beans.size() - 1) {
                //最后一个
                layoutdetail.setVisibility(View.GONE);
                if (bean.isShow()) {
                    zongfenItem.setVisibility(View.VISIBLE);
                } else {
                    zongfenItem.setVisibility(View.GONE);
                }
                zfchild.removeAllViews();
                double answer = 0, own = 0;
                if (BaseApplication.experimentBean != null) {
                    for (ExperimentBean.StepBean stepBean : BaseApplication.experimentBean.getStepBeans()) {
                        View view = View.inflate(this, R.layout.item_details_score, null);
                        TextView stepTitle = view.findViewById(R.id.stepTitle);
                        TextView stepanswer_scoce = view.findViewById(R.id.stepanswer_scoce);
                        TextView stepown_scoce = view.findViewById(R.id.stepown_scoce);
                        TextView stepzheshu = view.findViewById(R.id.stepzheshu);
                        stepTitle.setText(BaseApplication.experimentBean.getTitle());
                        double answerScore = stepBean.getAnswerScores().get(ExamUtils.is_dismantle);
                        answer += answerScore;
                        String answers = "";
                        if (CommonUtils.isNull(stepBean.getOwnAnswers(), ExamUtils.is_dismantle)) {
                            answers = stepBean.getOwnAnswers().get(ExamUtils.is_dismantle).toString();
                        }
                        stepanswer_scoce.setText(stepBean.getAnswerScores().get(ExamUtils.is_dismantle) + "");
                        double score = 0.0;
                        if (CommonUtils.isNullDouble(stepBean.getOwnScores(), ExamUtils.is_dismantle)) {
                            score = stepBean.getOwnScores().get(ExamUtils.is_dismantle);
                            own += score;
                        }
                        if (score == 0) {
                            stepown_scoce.setText("器件未拆除,扣" + answerScore + "分");
                        } else {
                            stepown_scoce.setText(score + "(" + answers + ")");
                        }
                        stepzheshu.setText("/");
                        zfchild.addView(view);
                    }
                }
                for (int i = 0; i < beans.size(); i++) {
                    if (i < beans.size() - 1) {
                        ExperimentBean experimentBean = beans.get(i);
                        View view = View.inflate(this, R.layout.item_details_score, null);
                        TextView stepTitle = view.findViewById(R.id.stepTitle);
                        TextView stepanswer_scoce = view.findViewById(R.id.stepanswer_scoce);
                        TextView stepown_scoce = view.findViewById(R.id.stepown_scoce);
                        TextView stepzheshu = view.findViewById(R.id.stepzheshu);
                        stepTitle.setText(experimentBean.getTitle());
                        answer += experimentBean.getScore_total();
                        own += experimentBean.getOwn_total();
                        stepanswer_scoce.setText(experimentBean.getScore_total() + "");
                        stepown_scoce.setText(experimentBean.getOwn_total() + "");
                        stepzheshu.setText("/");
                        zfchild.addView(view);
                    }
                }
                answer_scoce.setText(answer + "");
                own_scoce.setText(own + "");
                zheshu.setText(DOUBLE_FORMAT.format(own / answer * 100));
            } else {
                if (bean.isShow()) {
                    recycDetailsIv.setImageResource(R.mipmap.icon_up);
                    layoutdetail.setVisibility(View.VISIBLE);
                } else {
                    recycDetailsIv.setImageResource(R.mipmap.icon_down);
                    layoutdetail.setVisibility(View.GONE);
                }
                setValues(bean, position);
                zongfenItem.setVisibility(View.GONE);
            }

            content_details = itemView.findViewById(R.id.content_details);
            moreLayout.addView(itemView);
        }
    }

    private void setValues(ExperimentBean bean, int position) {
        double ownNum = 0;
        content_details.removeAllViews();
        int size = bean.getStepBeans().size();
        for (int i = 0; i < size; i++) {
            ExperimentBean.StepBean childBean = bean.getStepBeans().get(i);
            View view = View.inflate(this, R.layout.item_details, null);
            TextView stepTitle = view.findViewById(R.id.stepTitle);
            String hin = "步骤" + (i + 1) + "：" + childBean.getStepTitle();
            stepTitle.setText(CommonUtils.changeText(hin));
            LinearLayout scoring_items = view.findViewById(R.id.scoring_items);
            scoring_items.removeAllViews();
            int num = 0;
            for (Map.Entry<String, Double> entry : childBean.getAnswerScores().entrySet()) {
//                Log.d("test", "项目:" + entry.getKey() + "  " + entry.getValue());
                View scoring_items_views = View.inflate(this, R.layout.item_layout, null);
                LinearLayout layout_child = scoring_items_views.findViewById(R.id.layout_child);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                layout_child.setLayoutParams(params);
                TextView scoring_items_layoutTv = scoring_items_views.findViewById(R.id.scoring_items_layoutTv);
                scoring_items_layoutTv.setText(CommonUtils.changeText(entry.getKey()));

                TextView scoring_values_layoutTv = scoring_items_views.findViewById(R.id.scoring_values_layoutTv);
                scoring_values_layoutTv.setText(entry.getValue() + "");

                TextView examing_operation_layoutTv = scoring_items_views.findViewById(R.id.examing_operation_layoutTv);
                String answers = "0";
                double score = 0;
                int numCount = 0;
                int numChu = 0;
                if (childBean.getChioseMaps() != null) {
                    List<ExperimentBean.StepBean.StepChioseBean> chioseBeans = childBean.getChioseMaps().get(entry.getKey());
                    if (chioseBeans != null && chioseBeans.size() > 0) {
                        scoring_values_layoutTv.setText(entry.getValue() * chioseBeans.size() + "");
                        score = entry.getValue();
                        answers = "";
                        int numberSorce = 0, noENum1 = 0, noClose = 0;
                        for (ExperimentBean.StepBean.StepChioseBean chioseBean : chioseBeans) {
                            if (TextUtils.isEmpty(chioseBean.getTimi_answer())) {
                                answers += "未知: " + chioseBean.getAnswer() + "(多选扣1分)<br />";
                                numChu++;
                            } else {
                                if (ExamUtils.ammeter_number.equals(entry.getKey())) {
                                    answers += "输入值: " + chioseBean.getAnswer() + " ; 检测值：" + chioseBean.getLr_answer() + " <br />";
                                } else if (ExamUtils.is_eques.equals(entry.getKey())) {
                                    String content = "";
                                    if (!TextUtils.isEmpty(chioseBean.getAnswer()) && BeanUtils.isNumeric(chioseBean.getAnswer())) {
                                        int index = Integer.parseInt(chioseBean.getAnswer());
                                        if (index < chioseBean.getChiose().size()) {
                                            content = chioseBean.getChiose().get(index);
                                        }
                                    } else {
                                        content = "未选择";
                                    }
                                    if (!chioseBean.getAnswer().equals(chioseBean.getTimi_answer())) {
                                        score = 0;
                                        content += "(错选)";
                                    }
                                    answers += "选择值: " + content + " <br />";
                                } else if (ExamUtils.switch_Is_Close.equals(entry.getKey()) || ExamUtils.connection_Situation.equals(entry.getKey())) {
                                    numCount += chioseBean.getScore();
                                    if (ExamUtils.connection_Situation.equals(entry.getKey())) {
                                        if (chioseBean.getScore() == 0) {
                                            if (numberSorce == 0) {
                                                answers += "未按要求连线（" + chioseBean.getChioseStart();
                                            } else {
                                                answers += "," + chioseBean.getChioseStart();
                                            }
                                            numberSorce += entry.getValue();
                                        }
                                        if (chioseBean.getScore() == 1) {
                                            if (numberSorce == 0) {
                                                answers += "未按要求连线（" + chioseBean.getChioseStart();
                                            } else {
                                                answers += "," + chioseBean.getChioseStart();
                                            }
                                            numberSorce += (entry.getValue() - chioseBean.getScore());
                                        }
                                    }
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
                                        numCount++;
                                    } else {
                                        if (!ExamUtils.ammeter_number.equals(entry.getKey())) {
                                            if (ExamUtils.selection_Situation.equals(entry.getKey())) {
                                                answers += "少选:" + chioseBean.getChioseStart() + ",扣" + score + "分<br />";
                                            } else {
                                                answers += chioseBean.getChioseStart() + ": " + ExamUtils.N_VALUES + "(操作错误扣" + score + "分)<br />";
                                            }

                                        }
                                    }
                                }
                            }
                        }
                        if (ExamUtils.ammeter_number.equals(entry.getKey())) {
                            boolean flag1 = false, flag2 = false;
                            if (CommonUtils.isNullDouble(childBean.getOwnScores(), ExamUtils.ammeter_number_duol)) {
                                double duol = childBean.getOwnScores().get(ExamUtils.ammeter_number_duol);
                                if (duol == ExamUtils.ammeter_number_duol_score) {
                                    flag1 = true;
                                }
                                answers += " <br />" + ExamUtils.ammeter_number_duol + ":" + duol + "分/" + ExamUtils.ammeter_number_duol_score + "分";
                                score = duol;
                            }
                            if (CommonUtils.isNullDouble(childBean.getOwnScores(), ExamUtils.ammeter_number_good)) {
                                double good = childBean.getOwnScores().get(ExamUtils.ammeter_number_good);
                                if (good == ExamUtils.ammeter_number_good_score) {
                                    flag2 = true;
                                }
                                answers += " <br />" + ExamUtils.ammeter_number_good + ":" + good + "分/" + ExamUtils.ammeter_number_good_score + "分";
                                score += good;
                            }
                            if (flag1 && flag2) {
                                answers = answers.substring(0, answers.indexOf("<br />"));
                            }
                        }
                        if (ExamUtils.switch_Is_Close.equals(entry.getKey())) {
                            score = 0;
                            if (noENum1 > 0) {
                                answers += noENum1 + "个" + ExamUtils.colse_No_Effect + ",扣" + ExamUtils.colse_No_Effect_score2 + "分";
                            } else {
                                score += ExamUtils.colse_No_Effect_score2;
                            }
                            if (noClose > 0) {
                                score = +noClose;
                                answers += noClose + "个开关未闭合,扣" + noClose + "分";
                            }
                            if (CommonUtils.isNull(childBean.getOwnAnswers(), ExamUtils.colse_No_Effect)) {
                                answers += ExamUtils.colse_No_Effect + ",扣" + ExamUtils.colse_No_Effect_score2 + "分";
                            } else {
                                numCount += ExamUtils.colse_No_Effect_score2;
                            }
                        }
                        if (ExamUtils.connection_Situation.equals(entry.getKey())) {
                            answers += ",-" + numberSorce + "分)";
                            if (CommonUtils.isNull(childBean.getOwnAnswers(), ExamUtils.max3)) {
                                if (ExamUtils.Y_VALUES.equals(childBean.getOwnAnswers().get(ExamUtils.max3))) {
                                    numCount = (int) (entry.getValue() * chioseBeans.size() - 4);
                                    answers = "未按要求连线，且电池短路，扣4分";
                                }
                            }
                            if (CommonUtils.isNull(childBean.getOwnAnswers(), ExamUtils.max1)) {
                                if (ExamUtils.Y_VALUES.equals(childBean.getOwnAnswers().get(ExamUtils.max1))) {
                                    numCount = (int) (entry.getValue() * chioseBeans.size() - 1);
                                    answers = "与标准电路图不符，扣1分";
                                }
                            }
                        }
                    } else {
                        if (CommonUtils.isNullDouble(childBean.getOwnScores(), entry.getKey())) {
                            score = childBean.getOwnScores().get(entry.getKey());
                        }
                        if (ExamUtils.is_True_Ammeter.equals(entry.getKey())) {
                            answers = "";
                            if (CommonUtils.isNullDouble(childBean.getOwnScores(), ExamUtils.connt_position)) {
                                double connt_position = childBean.getOwnScores().get(ExamUtils.connt_position);
                                if (connt_position != ExamUtils.connt_position_score) {
                                    answers += ExamUtils.connt_position + ",扣" + (ExamUtils.connt_position_score - connt_position) + "分";
                                }
                            }
                            if (CommonUtils.isNullDouble(childBean.getOwnScores(), ExamUtils.connt_direction)) {
                                double connt_direction = childBean.getOwnScores().get(ExamUtils.connt_direction);
                                if (connt_direction != ExamUtils.connt_direction_score) {
                                    answers += "<br />" + ExamUtils.connt_direction + ",扣" + (ExamUtils.connt_direction_score - connt_direction) + "分";
                                }
                            }
                            if (CommonUtils.isNullDouble(childBean.getOwnScores(), ExamUtils.range_direction)) {
                                double range_direction = childBean.getOwnScores().get(ExamUtils.range_direction);
                                if (range_direction != ExamUtils.range_direction_score) {
                                    answers += "<br />" + ExamUtils.range_direction + ",扣" + (ExamUtils.range_direction_score - range_direction) + "分";
                                }
                            }
                        } else if (ExamUtils.switch_Is_Close.equals(entry.getKey())) {
                            answers = "";
                            if (CommonUtils.isNull(childBean.getOwnAnswers(), entry.getKey())) {
                                answers = childBean.getOwnAnswers().get(entry.getKey()).toString();
                            }
                            if (TextUtils.isEmpty(answers)) {
                                answers = "没有开关,扣" + entry.getValue() + "分";
                            } else if (ExamUtils.N_VALUES.equals(answers)) {
                                answers = "没有开关断开,扣" + entry.getValue() + "分";
                            } else {
                                if (CommonUtils.isNull(childBean.getOwnAnswers(), ExamUtils.colse_No_Effect)) {
                                    answers = ExamUtils.colse_No_Effect + ",扣" + ExamUtils.colse_No_Effect_score + "分";
                                }
                            }
                            if (score == entry.getValue()) {
                                answers = "";
                            }
                        } else if (ExamUtils.switch_Is_Tobreakoff.equals(entry.getKey())) {
                            answers = "";
                            if (CommonUtils.isNull(childBean.getOwnAnswers(), entry.getKey())) {
                                answers = childBean.getOwnAnswers().get(entry.getKey()).toString();
                            }
                            if (TextUtils.isEmpty(answers)) {
                                answers = "没有开关,扣" + entry.getValue() + "分";
                            } else if (ExamUtils.N_VALUES.equals(answers)) {
                                answers = "没有开关断开,扣" + entry.getValue() + "分";
                            } else {
                                if (CommonUtils.isNull(childBean.getOwnAnswers(), ExamUtils.colse_open_go)) {
                                    answers = ExamUtils.colse_open_go + "，扣" + ExamUtils.colse_open_go_score + "分";
                                }
                            }
                            if (score == entry.getValue()) {
                                answers = "";
                            }
                        } else if (ExamUtils.is_Close_Tobreakoff_Situation.equals(entry.getKey())) {
                            answers = "";
                            if (CommonUtils.isNull(childBean.getOwnAnswers(), entry.getKey())) {
                                answers = childBean.getOwnAnswers().get(entry.getKey()).toString();
                            }
                            if (CommonUtils.isNull(childBean.getOwnAnswers(), ExamUtils.is_no_open)) {
                                answers = ExamUtils.is_no_open + "，扣" + ExamUtils.is_no_open_score + "分";
                            }
                            if (CommonUtils.isNull(childBean.getOwnAnswers(), ExamUtils.is_finish_num)) {
                                answers = ExamUtils.is_finish_num + "，扣" + ExamUtils.is_finish_num_score + "分";
                            }
                            if (score == entry.getValue()) {
                                answers = "";
                            }
                        } else {
                            if (score != entry.getValue()) {
                                answers = "";

                                if (CommonUtils.isNull(childBean.getOwnAnswers(), entry.getKey())) {
                                    answers = childBean.getOwnAnswers().get(entry.getKey()).toString();
                                }
                            }
                        }
                    }
                } else {
                    if (CommonUtils.isNullDouble(childBean.getOwnScores(), entry.getKey())) {
                        score = childBean.getOwnScores().get(entry.getKey());
                    }
                    if (score != entry.getValue()) {
                        answers = "";
                        if (CommonUtils.isNull(childBean.getOwnAnswers(), entry.getKey())) {
                            answers = childBean.getOwnAnswers().get(entry.getKey()).toString();
                        }
                    }

                }
                examing_operation_layoutTv.setText(Html.fromHtml(answers));

                TextView examing_scoreing_layoutTv = scoring_items_views.findViewById(R.id.examing_scoreing_layoutTv);
                examing_scoreing_layoutTv.setText(score + "");
                if (ExamUtils.connection_Situation.equals(entry.getKey()) || (ExamUtils.switch_Is_Close.equals(entry.getKey()) && position == 1 && numCount > 0)) {
                    ownNum += numCount;
                    examing_scoreing_layoutTv.setText(numCount + "");
                } else if (ExamUtils.selection_Situation.equals(entry.getKey())) {
                    examing_scoreing_layoutTv.setText(score * numCount + "");
                    if (numChu > 0) {
                        examing_scoreing_layoutTv.setText((score * numCount - numChu) + "");
                    }
                    if (score * numCount == 0) {
                        examing_scoreing_layoutTv.setText("0");
                    }
                    ownNum += score * numCount - numChu;
                } else {
                    ownNum += score;
                }

                scoring_items.addView(scoring_items_views);
                Log.d("test", "num==" + num);
                if (num == childBean.getAnswerScores().size() - 1) {
                    scoring_items_views.findViewById(R.id.viewLagout).setVisibility(View.GONE);
                }
                num++;
            }
            content_details.addView(view);
        }
        bean.setOwn_total(ownNum);
    }

    private DetailsClickListener clickListener = new DetailsClickListener() {
        @Override
        public void onDetailsClick(ExperimentBean bean, View itemView, int position) {
            bean.setShow(!bean.isShow());
            ImageView recycDetailsIv = itemView.findViewById(R.id.recycDetailsIv);
            LinearLayout layoutdetail = itemView.findViewById(R.id.layoutdetail);
            LinearLayout zongfenItem = itemView.findViewById(R.id.zongfenItem);
            if (position == beanList.size() - 1) {
                layoutdetail.setVisibility(View.GONE);
                if (bean.isShow()) {
                    zongfenItem.setVisibility(View.VISIBLE);
                } else {
                    zongfenItem.setVisibility(View.GONE);
                }
            } else {
                if (bean.isShow()) {
                    recycDetailsIv.setImageResource(R.mipmap.icon_up);
                    layoutdetail.setVisibility(View.VISIBLE);
                } else {
                    recycDetailsIv.setImageResource(R.mipmap.icon_down);
                    layoutdetail.setVisibility(View.GONE);
                }
            }
        }
    };

}
