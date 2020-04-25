package com.learn.all_electric.exam;


import com.learn.all_electric.base.BaseApplication;
import com.learn.all_electric.R;
import com.learn.all_electric.bean.ExamListBean;
import com.learn.all_electric.bean.ExperimentBean;
import com.learn.all_electric.utils.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 实验2
 */
public class ExamUtils {

    public static String Y_VALUES = "Y";
    public static String N_VALUES = "N";

    public static String is_Battery = "是否正确组装电池";//
    public static String selection_Situation = "实物选材情况";
    public static String connection_Situation = "实物连接情况";

    public static String is_True_Ammeter = "是否正确接入电压表";

    public static String UI= "12";
    public static String U2 = "1";
    public static String U3 = "14";
    public static String U1_2 = "12";
    public static String U2_2 = "14";
    public static String U3_2 = "12";

    public static String connt_position = "接入位置错误";
    public static String connt_direction = "正负极方向错误";
    public static String range_direction = "量程错误";
    public static int connt_position_score = 2;
    public static int connt_direction_score = 2;
    public static int range_direction_score = 1;

    public static int colse_No_Effect_score = 2;
    public static int colse_No_Effect_score2 = 3;
    public static String colse_No_Effect = "开关不能起作用";


    public static String ammeter_number = "电压表读数";
    public static String ammeter_number_good = "数据有效分";
    public static String ammeter_number_duol = "小数点得分";
    public static int ammeter_number_good_score = 3;
    public static int ammeter_number_duol_score = 2;

    public static int colse_open_go_score = 2;
    public static String colse_open_go = "开关断开后电路仍有电流通过";


    public static String switch_Is_Close = "是否闭合开关";
    public static String switch_Is_Tobreakoff = "是否断开开关";
    public static String is_dismantle = "是否拆除线路和电池";

    public static String is_eques = "评判选择题";

    public static String is_Close_Tobreakoff_Situation = "是否在断开开关的情况下接入、拆除元器件";
    public static String is_no_open = "没有开关";
    public static String is_finish_num = "不规范操作超过3次";
    public static int is_no_open_score = 5;
    public static int is_finish_num_score = 5;

    public static String max1 = "max1";//出现有效线数和实际节点数与标准线数、节点相同，但实际电路图与标准电路图不相符的情况
    public static String max3 = "max3";//出现电池短路的情况


    public static ExperimentBean setExperimentBean(int... ints) {
        return setExperimentBean(true, ints);
    }

    public static ExperimentBean setExperimentBean(boolean isAdd, int... ints) {
        ExperimentBean experimentBean = new ExperimentBean();
        experimentBean.setTitle("串联电路部分");
        experimentBean.setScore_total(86.0);
        List<Integer> steps = new ArrayList<>();
        int size = 7;
        for (int i = 0; i < size; i++) {
            boolean flag = true;
            for (int j = 0; j < ints.length; j++) {
                if (i == j) {
                    flag = false;
                }
            }
            if (flag) {
                steps.add(i);
            }
        }
        //实验说明
        List<ExamListBean.ExplainBean> explainBeans = new ArrayList<>();
        ExamListBean.ExplainBean explainBean1 = new ExamListBean.ExplainBean();
        explainBean1.setExplain("实验说明");
        explainBeans.add(explainBean1);

        ExamListBean.ExplainBean explainBean2 = new ExamListBean.ExplainBean();
        explainBean2.setExplain("本实验包含两个分实验，两个实验都需要完成");
//        explainBean2.setImageMip(R.mipmap.ex_1);
        explainBeans.add(explainBean2);

        ExamListBean.ExplainBean explainBean3 = new ExamListBean.ExplainBean();
        explainBean3.setExplain("实验总时长为15分钟，请按提示完成实验步骤");
        explainBeans.add(explainBean3);

        List<ExperimentBean.StepBean> stepBeans = new ArrayList<>();
        Random random = new Random();//以系统当前时间作为随机数生成的种子
        int[] temp = BeanUtils.rands();
        int num = 0;
        //步骤
        for (int j = 0; j < steps.size(); j++) {
            ExperimentBean.StepBean stepBean = new ExperimentBean.StepBean();
            Map<String, List<ExperimentBean.StepBean.StepChioseBean>> listMap = new HashMap<>();
            int i = steps.get(j);
            if (i == 0) {
                stepBean.setStepTitle("下图为串联电路中的关系原理图，请根据原理图选择相应的实验器材并连线。");
                int[] images = {R.mipmap.s1_step_1};
                stepBean.setBtnStr("确认");
                stepBean.setImages(images);
                stepBean.setType(0);
                stepBean.getAnswerScores().put(is_Battery, 5.0);
                stepBean.getAnswerScores().put(selection_Situation, 2.0);
                stepBean.getAnswerScores().put(connection_Situation, 2.0);


                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans2 = new ArrayList<>();
                chioseBeans2.add(setStepChioseBean("电源", "", Y_VALUES));
                chioseBeans2.add(setStepChioseBean("开关", "", Y_VALUES));
                chioseBeans2.add(setStepChioseBean("灯泡1", "", Y_VALUES));
                chioseBeans2.add(setStepChioseBean("灯泡2", "", Y_VALUES));
                listMap.put(selection_Situation, chioseBeans2);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans = new ArrayList<>();
                chioseBeans.add(setStepChioseBean("A、M两端是否连接", "", Y_VALUES));
                chioseBeans.add(setStepChioseBean("K 、L两端是否连接", "", Y_VALUES));
                chioseBeans.add(setStepChioseBean("J、K2两端是否连接", "", Y_VALUES));
                chioseBeans.add(setStepChioseBean("B、J2两端是否连接", "", Y_VALUES));

                listMap.put(connection_Situation, chioseBeans);

            }
            if (i == 1) {
                stepBean.setStepTitle("请断开开关，将电压表与小灯泡L1并联，闭合开关并读取电压表读数。");
                stepBean.setBtnStr("保存数据");
                stepBean.setType(3);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans = new ArrayList<>();
                chioseBeans.add(setStepChioseBean("UI=", "", UI));
                listMap.put(ammeter_number, chioseBeans);

                stepBean.getAnswerScores().put(is_True_Ammeter, 5.0);
                stepBean.getAnswerScores().put(switch_Is_Close, 5.0);
                stepBean.getAnswerScores().put(ammeter_number, 5.0);
            }
            if (i == 2) {
                stepBean.setStepTitle("请断开开关，将电压表与小灯泡L2并联，闭合开关并读取电压表读数。");
                stepBean.setBtnStr("保存数据");
                stepBean.setType(3);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans = new ArrayList<>();
                chioseBeans.add(setStepChioseBean("U2=", "", U2));
                listMap.put(ammeter_number, chioseBeans);

                stepBean.getAnswerScores().put(is_True_Ammeter, 5.0);
                stepBean.getAnswerScores().put(switch_Is_Close, 5.0);
                stepBean.getAnswerScores().put(ammeter_number, 5.0);
            }
            if (i == 3) {
                stepBean.setStepTitle("请断开开关，将电压表接在电源两端，闭合开关并读取电压表读数。");
                stepBean.setBtnStr("保存数据");
                stepBean.setType(3);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans = new ArrayList<>();
                chioseBeans.add(setStepChioseBean("U=", "", U3));
                listMap.put(ammeter_number, chioseBeans);

                stepBean.getAnswerScores().put(is_True_Ammeter, 5.0);
                stepBean.getAnswerScores().put(switch_Is_Close, 5.0);
                stepBean.getAnswerScores().put(ammeter_number, 5.0);
            }
            if (i == 4) {
                stepBean.setStepTitle("断开开关，并将线路和电池拆除。");
                stepBean.setBtnStr("确认");
                stepBean.setType(1);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans = new ArrayList<>();
                chioseBeans.add(setStepChioseBean("电源", "是否拆除", Y_VALUES));
                chioseBeans.add(setStepChioseBean("开关", "是否拆除", Y_VALUES));
                chioseBeans.add(setStepChioseBean("灯泡1", "是否拆除", Y_VALUES));
                chioseBeans.add(setStepChioseBean("灯泡2", "是否拆除", Y_VALUES));
                chioseBeans.add(setStepChioseBean("电池", "是否拆除", Y_VALUES));
                listMap.put(is_dismantle, chioseBeans);

                stepBean.getAnswerScores().put(switch_Is_Tobreakoff, 5.0);
                stepBean.getAnswerScores().put(is_dismantle, 5.0);
            }
            if (i == 5) {
                stepBean.setStepTitle("结论：通过对U、U1+U2的分析发现，在实验误差允许的范围内，串联电路的总电压_______各部分电压之和。");
                stepBean.setBtnStr("");
                stepBean.setType(4);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans = new ArrayList<>();
                chioseBeans.add(setStepChioseBean("结论：通过对U、U1+U2的分析发现，在实验误差允许的范围内，串联电路的总电压", "各部分电压之和。", "1", "小于","等于", "大于"));
                listMap.put(is_eques, chioseBeans);

                stepBean.getAnswerScores().put(is_eques, 5.0);

            }
            if (i == 6) {
                stepBean.setStepTitle("若已完成本实验所有步骤，可以点击下方“下一个实验”按钮，开始下一个实验");
                stepBean.setBtnStr("下一个实验");
                stepBean.setType(2);
                stepBean.getAnswerScores().put(is_Close_Tobreakoff_Situation, 5.0);
            }
            stepBean.setChioseMaps(listMap);
            stepBeans.add(stepBean);
        }
        experimentBean.setStepBeans(stepBeans);
//        experimentBean.setEndStep("若已经完成上述步骤1-" + stepBeans.size() + "，请点击“交卷”按钮。若不满意且时间允许，你可以点击“重做”按钮，之前所做的将全部清零，并重新开始实验。");

        if (BaseApplication.examListBean == null) {
            BaseApplication.examListBean = new ExamListBean();
            BaseApplication.examListBean.setExplainBeans(explainBeans);
            BaseApplication.examListBean.setExamName("串、并联电路中的电压关系");
        }
        if (isAdd) {
            BaseApplication.examListBean.getBeanLists().add(experimentBean);
        }
        return experimentBean;
    }

    public static ExperimentBean setExperimentBeanTwo(int... ints) {
        return setExperimentBeanTwo(true, ints);
    }

    public static ExperimentBean setExperimentBeanTwo(boolean isAdd, int... ints) {
        ExperimentBean experimentBean = new ExperimentBean();
        experimentBean.setTitle("并联电路部分");
        experimentBean.setScore_total(99.0);
        List<Integer> steps = new ArrayList<>();
        int size = 7;
        for (int i = 0; i < size; i++) {
            boolean flag = true;
            for (int j = 0; j < ints.length; j++) {
                if (i == j) {
                    flag = false;
                }
            }
            if (flag) {
                steps.add(i);
            }
        }

        //实验说明
        List<ExamListBean.ExplainBean> explainBeans = new ArrayList<>();
        ExamListBean.ExplainBean explainBean1 = new ExamListBean.ExplainBean();
        explainBean1.setExplain("实验说明");
        explainBeans.add(explainBean1);

        ExamListBean.ExplainBean explainBean2 = new ExamListBean.ExplainBean();
        explainBean2.setExplain("本实验包含两个分实验，两个实验都需要完成");
//        explainBean2.setImageMip(R.mipmap.ex_1);
        explainBeans.add(explainBean2);

        ExamListBean.ExplainBean explainBean3 = new ExamListBean.ExplainBean();
        explainBean3.setExplain("实验总时长为15分钟，请按提示完成实验步骤");
        explainBeans.add(explainBean3);

        List<ExperimentBean.StepBean> stepBeans = new ArrayList<>();
        //步骤
        for (int j = 0; j < steps.size(); j++) {
            ExperimentBean.StepBean stepBean = new ExperimentBean.StepBean();
            Map<String, List<ExperimentBean.StepBean.StepChioseBean>> listMap = new HashMap<>();
            int i = steps.get(j);
            if (i == 0) {
                stepBean.setStepTitle("下图为并联电路中的关系原理图，一个开关控制整个电路，另外2个开关分别控制每一个小灯泡，请根据原理图选择相应的实验器材并连线。");
                int[] images = {R.mipmap.s1_cl_1};
                stepBean.setBtnStr("确认");
                stepBean.setImages(images);
                stepBean.setType(0);
                stepBean.getAnswerScores().put(is_Battery, 5.0);
                stepBean.getAnswerScores().put(selection_Situation, 2.0);
                stepBean.getAnswerScores().put(connection_Situation, 2.0);


                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans2 = new ArrayList<>();
                chioseBeans2.add(setStepChioseBean("电源", "", Y_VALUES));
                chioseBeans2.add(setStepChioseBean("开关1", "", Y_VALUES));
                chioseBeans2.add(setStepChioseBean("开关2", "", Y_VALUES));
                chioseBeans2.add(setStepChioseBean("开关3", "", Y_VALUES));
                chioseBeans2.add(setStepChioseBean("灯泡1", "", Y_VALUES));
                chioseBeans2.add(setStepChioseBean("灯泡2", "", Y_VALUES));
                listMap.put(selection_Situation, chioseBeans2);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans = new ArrayList<>();
                chioseBeans.add(setStepChioseBean("电源正极-总开关", "", Y_VALUES));
                chioseBeans.add(setStepChioseBean("总开关-支路开关1", "", Y_VALUES));
                chioseBeans.add(setStepChioseBean("支路开关1-支路开关2", "", Y_VALUES));
                chioseBeans.add(setStepChioseBean("支路开关1-小灯泡1", "", Y_VALUES));
                chioseBeans.add(setStepChioseBean("支路开关2-小灯泡2", "", Y_VALUES));
                chioseBeans.add(setStepChioseBean("小灯泡-小灯泡", "", Y_VALUES));
                chioseBeans.add(setStepChioseBean("小灯泡-电源负极", "", Y_VALUES));

                listMap.put(connection_Situation, chioseBeans);

            }
            if (i == 1) {
                stepBean.setStepTitle("请断开开关，将电压表与小灯泡L1并联，闭合开关并读取电压表读数。");
                stepBean.setBtnStr("保存数据");
                stepBean.setType(3);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans = new ArrayList<>();
                chioseBeans.add(setStepChioseBean("U1=", "", U1_2));
                listMap.put(ammeter_number, chioseBeans);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans2 = new ArrayList<>();
                chioseBeans2.add(setStepChioseBean("开关1", "", Y_VALUES));
                chioseBeans2.add(setStepChioseBean("开关2", "", Y_VALUES));
                chioseBeans2.add(setStepChioseBean("开关3", "", Y_VALUES));
                listMap.put(switch_Is_Close, chioseBeans2);

                stepBean.getAnswerScores().put(is_True_Ammeter, 5.0);
                stepBean.getAnswerScores().put(switch_Is_Close, 2.0);
                stepBean.getAnswerScores().put(ammeter_number, 5.0);
            }
            if (i == 2) {
                stepBean.setStepTitle("请断开开关，将电压表与小灯泡L2并联，闭合开关并读取电压表读数。");
                stepBean.setBtnStr("保存数据");
                stepBean.setType(3);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans = new ArrayList<>();
                chioseBeans.add(setStepChioseBean("U2=", "", U2_2));
                listMap.put(ammeter_number, chioseBeans);

                stepBean.getAnswerScores().put(is_True_Ammeter, 5.0);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans2 = new ArrayList<>();
                chioseBeans2.add(setStepChioseBean("开关1", "", Y_VALUES));
                chioseBeans2.add(setStepChioseBean("开关2", "", Y_VALUES));
                chioseBeans2.add(setStepChioseBean("开关3", "", Y_VALUES));
                listMap.put(switch_Is_Close, chioseBeans2);

                stepBean.getAnswerScores().put(switch_Is_Close, 2.0);
                stepBean.getAnswerScores().put(ammeter_number, 5.0);
            }
            if (i == 3) {
                stepBean.setStepTitle("请断开开关，将电压表接在电源两端，再闭合开关，读取电压表读数。");
                stepBean.setBtnStr("保存数据");
                stepBean.setType(3);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans = new ArrayList<>();
                chioseBeans.add(setStepChioseBean("U=", "", U3_2));
                listMap.put(ammeter_number, chioseBeans);

                stepBean.getAnswerScores().put(is_True_Ammeter, 5.0);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans2 = new ArrayList<>();
                chioseBeans2.add(setStepChioseBean("开关1", "", Y_VALUES));
                chioseBeans2.add(setStepChioseBean("开关2", "", Y_VALUES));
                chioseBeans2.add(setStepChioseBean("开关3", "", Y_VALUES));
                listMap.put(switch_Is_Close, chioseBeans2);

                stepBean.getAnswerScores().put(switch_Is_Close, 2.0);
                stepBean.getAnswerScores().put(ammeter_number, 5.0);
            }
            if (i == 4) {
                stepBean.setStepTitle("断开开关。");
                stepBean.setBtnStr("确认");
                stepBean.setType(1);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans = new ArrayList<>();
                chioseBeans.add(setStepChioseBean("电源", "是否拆除", Y_VALUES));
                chioseBeans.add(setStepChioseBean("开关", "是否拆除", Y_VALUES));
                chioseBeans.add(setStepChioseBean("灯泡1", "是否拆除", Y_VALUES));
                chioseBeans.add(setStepChioseBean("灯泡2", "是否拆除", Y_VALUES));
                chioseBeans.add(setStepChioseBean("电池", "是否拆除", Y_VALUES));
                listMap.put(is_dismantle, chioseBeans);

                stepBean.getAnswerScores().put(switch_Is_Tobreakoff, 5.0);
//                stepBean.getAnswerScores().put(is_dismantle, 5.0);
            }
            if (i == 5) {
                stepBean.setStepTitle("结论：通过对U、U1、U2的分析发现，在实验误差允许的范围内，并联电路的总电压_______各部分电压。");
                stepBean.setBtnStr("");
                stepBean.setType(4);

                List<ExperimentBean.StepBean.StepChioseBean> chioseBeans = new ArrayList<>();
                chioseBeans.add(setStepChioseBean("结论：通过对U、U1、U2的分析发现，在实验误差允许的范围内，并联电路的总电压", "各部分电压。", "1", "大于", "等于", "小于"));
                listMap.put(is_eques, chioseBeans);

                stepBean.getAnswerScores().put(is_eques, 5.0);

            }
            if (i == 6) {
                stepBean.setStepTitle("若已经完成上述步骤1—6，请点击右侧“交卷”按钮，若不满意且时间允许，你可以点击“重做”按钮，之前所做的将全部清零，并重新开始实验");
                stepBean.setBtnStr("");
                stepBean.setType(2);
                stepBean.getAnswerScores().put(is_Close_Tobreakoff_Situation, 5.0);
            }
            stepBean.setChioseMaps(listMap);
            stepBeans.add(stepBean);
        }
        experimentBean.setStepBeans(stepBeans);

        if (BaseApplication.examListBean == null) {
            BaseApplication.examListBean = new ExamListBean();
            BaseApplication.examListBean.setExplainBeans(explainBeans);
            BaseApplication.examListBean.setExamName("串、并联电路中的电压关系");
        }
        if (isAdd) {
            BaseApplication.examListBean.getBeanLists().add(experimentBean);
        }
        return experimentBean;
    }

    /* 全局算总分 */
    public static void setExamResult() {
        ExperimentBean experimentBean = new ExperimentBean();
        experimentBean.setTitle("是否拆除线路和电池");
        experimentBean.setScore_total(5.0);
        ExperimentBean.StepBean stepBean = new ExperimentBean.StepBean();
        stepBean.setStepTitle("");
        stepBean.getAnswerScores().put(is_dismantle, 5.0);
        List<ExperimentBean.StepBean> stepBeans = new ArrayList<>();
        stepBeans.add(stepBean);
        experimentBean.setStepBeans(stepBeans);
        BaseApplication.experimentBean = experimentBean;
    }

    private static List<String> setChiose(String... strings) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i]);
        }
        return list;
    }

    private static ExperimentBean.StepBean.StepChioseBean setStepChioseBean(String start, String end, String answer, boolean isShow, String... strings) {
        ExperimentBean.StepBean.StepChioseBean stepChioseBean = new ExperimentBean.StepBean.StepChioseBean(start, end);
        stepChioseBean.setTimi_answer(answer);
        stepChioseBean.setShow(isShow);
        if (strings.length > 0) {
            stepChioseBean.setChiose(setChiose(strings));
        }
        return stepChioseBean;
    }

    private static ExperimentBean.StepBean.StepChioseBean setStepChioseBean(String start, String end, String answer, String... strings) {
        return setStepChioseBean(start, end, answer, false, strings);
    }


}
