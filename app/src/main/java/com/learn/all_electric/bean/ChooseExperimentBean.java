package com.learn.all_electric.bean;

/**
 * 选择实验
 */
public class ChooseExperimentBean {
    String experiment_name;//学科名称
    String version;
    boolean isUpdate;//是否需要更新
    boolean isCheck;//是否被选中


    public ChooseExperimentBean(String experiment_name,
            String version,
            boolean isUpdate,
            boolean isCheck){
        this.experiment_name = experiment_name;
        this.version = version;
        this.isUpdate = isUpdate;
        this.isCheck = isCheck;
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
}
