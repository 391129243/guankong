package com.learn.all_electric.myinterface;

import com.learn.all_electric.bean.ExperimentBean;

/**
 * 保存数据接口
 *
 * @author zuoliji
 * @time 2020/2/22 14:29
 */
public interface SaveClickListener {
    void onSaveClick(ExperimentBean.StepBean stepBean, int position,int index);
    void onChange(int position,int index);
    void onSaveChiose(ExperimentBean.StepBean stepBean, int position,int index,int number);
    void onSaveAnswer(ExperimentBean.StepBean stepBean, int position,int index,String answer);
}
