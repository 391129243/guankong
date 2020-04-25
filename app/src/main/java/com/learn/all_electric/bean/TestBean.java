package com.learn.all_electric.bean;

/**
 * 实验
 */
public class TestBean {

    private String step;
    private boolean setting;

    public TestBean(String step) {
        this(step, false);
    }

    public TestBean(String step, boolean setting) {
        this.step = step;
        this.setting = setting;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public boolean isSetting() {
        return setting;
    }

    public void setSetting(boolean setting) {
        this.setting = setting;
    }
}
