package com.learn.all_electric.bean;

import java.util.List;

/**
 * 上传成绩
 */
public class ExamScoreBean {
    private int examId;

    private String questionNo;

    private int operateTime;

    private double examScore;

    private double percentScore;

    private List<ExperimentList> experimentList;

    private List<SubList> subList;

    public void setExamId(int examId){
        this.examId = examId;
    }
    public int getExamId(){
        return this.examId;
    }

    public double getPercentScore() {
        return percentScore;
    }

    public void setPercentScore(double percentScore) {
        this.percentScore = percentScore;
    }

    public void setQuestionNo(String questionNo){
        this.questionNo = questionNo;
    }
    public String getQuestionNo(){
        return this.questionNo;
    }
    public void setOperateTime(int operateTime){
        this.operateTime = operateTime;
    }
    public int getOperateTime(){
        return this.operateTime;
    }
    public void setExamScore(double examScore){
        this.examScore = examScore;
    }
    public double getExamScore(){
        return this.examScore;
    }
    public void setExperimentList(List<ExperimentList> experimentList){
        this.experimentList = experimentList;
    }
    public List<ExperimentList> getExperimentList(){
        return this.experimentList;
    }
    public void setSubList(List<SubList> subList){
        this.subList = subList;
    }
    public List<SubList> getSubList(){
        return this.subList;
    }

    public static class ExperimentList{
        private int experimentSeq;

        private String experimentNo;

        private double examScore;

        private List<StepList> stepList;

        private List<SubList> subList;

        public void setExperimentSeq(int experimentSeq){
            this.experimentSeq = experimentSeq;
        }
        public int getExperimentSeq(){
            return this.experimentSeq;
        }
        public void setExperimentNo(String experimentNo){
            this.experimentNo = experimentNo;
        }
        public String getExperimentNo(){
            return this.experimentNo;
        }
        public void setExamScore(double examScore){
            this.examScore = examScore;
        }
        public double getExamScore(){
            return this.examScore;
        }
        public void setStepList(List<StepList> stepList){
            this.stepList = stepList;
        }
        public List<StepList> getStepList(){
            return this.stepList;
        }
        public void setSubList(List<SubList> subList){
            this.subList = subList;
        }
        public List<SubList> getSubList(){
            return this.subList;
        }




    }

    public static class SubList {

        private int subSeq;

        private String subNo;

        private int inputValue;

        private double examScore;

        private String scoreRemark;

        public void setSubSeq(int subSeq) {
            this.subSeq = subSeq;
        }

        public int getSubSeq() {
            return this.subSeq;
        }

        public void setSubNo(String subNo) {
            this.subNo = subNo;
        }

        public String getSubNo() {
            return this.subNo;
        }

        public void setInputValue(int inputValue) {
            this.inputValue = inputValue;
        }

        public int getInputValue() {
            return this.inputValue;
        }

        public void setExamScore(double examScore) {
            this.examScore = examScore;
        }

        public double getExamScore() {
            return this.examScore;
        }

        public void setScoreRemark(String scoreRemark) {
            this.scoreRemark = scoreRemark;
        }

        public String getScoreRemark() {
            return this.scoreRemark;
        }
    }

    public static class ItemList
    {
        private int itemSeq;

        private String itemNo;

        private double examScore;

        private List<SubList> subList;

        public void setItemSeq(int itemSeq){
            this.itemSeq = itemSeq;
        }
        public int getItemSeq(){
            return this.itemSeq;
        }
        public void setItemNo(String itemNo){
            this.itemNo = itemNo;
        }
        public String getItemNo(){
            return this.itemNo;
        }
        public void setExamScore(double examScore){
            this.examScore = examScore;
        }
        public double getExamScore(){
            return this.examScore;
        }
        public void setSubList(List<SubList> subList){
            this.subList = subList;
        }
        public List<SubList> getSubList(){
            return this.subList;
        }
    }

    public static class StepList
    {
        private int stepSeq;

        private String stepNo;

        private double examScore;

        private List<ItemList> itemList;

        public void setStepSeq(int stepSeq){
            this.stepSeq = stepSeq;
        }
        public int getStepSeq(){
            return this.stepSeq;
        }
        public void setStepNo(String stepNo){
            this.stepNo = stepNo;
        }
        public String getStepNo(){
            return this.stepNo;
        }
        public void setExamScore(double examScore){
            this.examScore = examScore;
        }
        public double getExamScore(){
            return this.examScore;
        }
        public void setItemList(List<ItemList> itemList){
            this.itemList = itemList;
        }
        public List<ItemList> getItemList(){
            return this.itemList;
        }
    }
}
