package com.learn.all_electric.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* 多个实验
* @author zuoliji
* @time 2020/2/22 11:39
*/
public class ExamListBean {
    private String examName;//实验名称
    private List<ExplainBean> explainBeans;
    private List<ExperimentBean> beanLists=new ArrayList<>();

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public List<ExperimentBean> getBeanLists() {
        return beanLists;
    }

    public void setBeanLists(List<ExperimentBean> beanLists) {
        this.beanLists = beanLists;
    }

    public List<ExplainBean> getExplainBeans() {
        return explainBeans;
    }

    public void setExplainBeans(List<ExplainBean> explainBeans) {
        this.explainBeans = explainBeans;
    }

    @Override
    public String toString() {
        return "ExamListBean{" +
                "examName='" + examName + '\'' +
                ", explainBeans=" + explainBeans +
                ", beanLists=" + beanLists +
                '}';
    }

    public static class ExplainBean implements Serializable {
        //实验说明
        private String explain;//文字说明
        private String imagePath;//图片
        private int imageMip;//资源图片

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public int getImageMip() {
            return imageMip;
        }

        public void setImageMip(int imageMip) {
            this.imageMip = imageMip;
        }
    }

}
