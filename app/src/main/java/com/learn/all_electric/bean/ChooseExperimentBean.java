package com.learn.all_electric.bean;

import android.support.annotation.NonNull;

import com.learn.all_electric.utils.StringUtils;

import java.util.Comparator;

/**
 * 选择实验
 */
public class ChooseExperimentBean implements Comparable<ChooseExperimentBean>{
    String experiment_name;//学科名称
    String version;
    boolean isUpdate;//是否需要更新
    boolean isCheck;//是否被选中
    int experiment_number;


    public ChooseExperimentBean(){

    }
    public ChooseExperimentBean(String experiment_name,
            String version,
            boolean isUpdate,
            boolean isCheck,
            int number){
        this.experiment_name = experiment_name;
        this.version = version;
        this.isUpdate = isUpdate;
        this.isCheck = isCheck;
        this.experiment_number = number;
    }


    public String getExperiment_name() {
        return experiment_name;
    }

    public void setExperiment_name(String experiment_name) {
        this.experiment_name = experiment_name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getExperiment_number() {
        return experiment_number;
    }

    public void setExperiment_number(int experiment_number) {
        this.experiment_number = experiment_number;
    }





/*
    @Override
    public int compare(ChooseExperimentBean o1, ChooseExperimentBean o2) {
        if (o1.getExperimentNumber()>o2.getExperimentNumber()){
            return -1;
        }
        return 1;
    }
*/

    @Override
    public int compareTo(@NonNull ChooseExperimentBean bean) {
        if (this.experiment_number>bean.getExperiment_number()) {
            return (this.experiment_number - bean.getExperiment_number());
        }
        if (this.experiment_number<bean.getExperiment_number()) {
            return (this.experiment_number - bean.getExperiment_number());
        }
        return 0;

    }
}
