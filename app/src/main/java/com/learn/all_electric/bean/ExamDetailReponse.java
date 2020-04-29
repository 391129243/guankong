package com.learn.all_electric.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import java.util.List;
import java.util.Map;

/**
 * context 实现接口Parcelable
 */
public class ExamDetailReponse {

      private int code;

      private boolean success; //是否成功

      private Data data;

      private String msg;

      private int time;

      public void setCode(int code){
         this.code = code;
      }
      public int getCode(){
         return this.code;
      }
      public void setSuccess(boolean success){
         this.success = success;
      }
      public boolean getSuccess(){
         return this.success;
      }
      public void setData(Data data){
         this.data = data;
      }
      public Data getData(){
         return this.data;
      }
      public void setMsg(String msg){
         this.msg = msg;
      }
      public String getMsg(){
         return this.msg;
      }
      public void setTime(int time){
         this.time = time;
      }
      public int getTime(){
         return this.time;
      }

      public ExamDetailReponse(int code,boolean success,Data data
              ,String msg, int time){
          this.code = code;
          this.success = success;
          this.data = data;
          this.msg = msg;
          this.time = time;
      }




    public static class Data  {

        private long examId;

        private int gradeType;//年级

        private int subjectType;//学科

        private int branchType;//物理分支

        private int examType;//考试类型

        private String questionNo;//实验题号

        private String questionName;//实验名称

        private int experimentDuration;//考试时长

        private int hardDegree;//难度类，1：easy；2：general;3:hard;4:difficult

        private int isPercent;//百分制，0：非百分制，1：百分制

        private int totalScore;//课题总分

        private List<ExperimentList> experimentList;

        private List<SubList> subList;

        public void setExamId(long examId) {
            this.examId = examId;
        }

        public long getExamId() {
            return this.examId;
        }

        public void setGradeType(int gradeType) {
            this.gradeType = gradeType;
        }

        public int getGradeType() {
            return this.gradeType;
        }

        public void setSubjectType(int subjectType) {
            this.subjectType = subjectType;
        }

        public int getSubjectType() {
            return this.subjectType;
        }

        public void setBranchType(int branchType) {
            this.branchType = branchType;
        }

        public int getBranchType() {
            return this.branchType;
        }

        public void setExamType(int examType) {
            this.examType = examType;
        }

        public int getExamType() {
            return this.examType;
        }

        public void setQuestionNo(String questionNo) {
            this.questionNo = questionNo;
        }

        public String getQuestionNo() {
            return this.questionNo;
        }

        public void setQuestionName(String questionName) {
            this.questionName = questionName;
        }

        public String getQuestionName() {
            return this.questionName;
        }

        public void setExperimentDuration(int experimentDuration) {
            this.experimentDuration = experimentDuration;
        }

        public int getExperimentDuration() {
            return this.experimentDuration;
        }

        public void setHardDegree(int hardDegree) {
            this.hardDegree = hardDegree;
        }

        public int getHardDegree() {
            return this.hardDegree;
        }

        public void setIsPercent(int isPercent) {
            this.isPercent = isPercent;
        }

        public int getIsPercent() {
            return this.isPercent;
        }

        public void setTotalScore(int totalScore) {
            this.totalScore = totalScore;
        }

        public int getTotalScore() {
            return this.totalScore;
        }

        public void setExperimentList(List<ExperimentList> experimentList) {
            this.experimentList = experimentList;
        }

        public List<ExperimentList> getExperimentList() {
            return this.experimentList;
        }

        public void setSubList(List<SubList> subList) {
            this.subList = subList;
        }

        public List<SubList> getSubList() {
            return this.subList;
        }

        public Data(int examId, int gradeType, int subjectType, int branchType, int examType, String questionNo, String questionName, int experimentDuration, int hardDegree, int isPercent, int totalScore, List<ExperimentList> experimentList, List<SubList> subList) {
            this.examId = examId;
            this.gradeType = gradeType;
            this.subjectType = subjectType;
            this.branchType = branchType;
            this.examType = examType;
            this.questionNo = questionNo;//实验题号
            this.questionName = questionName;//实验名称
            this.experimentDuration = experimentDuration;
            this.hardDegree = hardDegree;
            this.isPercent = isPercent;
            this.totalScore = totalScore;
            this.experimentList = experimentList;
            this.subList = subList;
        }

        @Override
        public String toString() {
            return "Data{" + "examId=" + examId + ", gradeType=" + gradeType + ", subjectType=" + subjectType + ", branchType=" + branchType + ", examType=" + examType + ", questionNo='" + questionNo + '\'' + ", questionName='" + questionName + '\'' + ", experimentDuration=" + experimentDuration + ", hardDegree=" + hardDegree + ", isPercent=" + isPercent + ", totalScore=" + totalScore + ", experimentList=" + experimentList + ", subList=" + subList + '}';
        }
    }


    public static class ExperimentList{

        private int experimentSeq;//实验序号

        private String experimentNo;//实验编号

        private String experimentName;//实验名称

        private int experimentScore;//实验总分

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
        public void setExperimentName(String experimentName){
            this.experimentName = experimentName;
        }
        public String getExperimentName(){
            return this.experimentName;
        }
        public void setExperimentScore(int experimentScore){
            this.experimentScore = experimentScore;
        }
        public int getExperimentScore(){
            return this.experimentScore;
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
    public static class StepList{


        private int stepSeq;//步骤序号

        private String stepNo;//步骤编号

        private String stepName;//步骤

        private String stepImage;

        private int stepScore;//步骤总分

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
        public void setStepName(String stepName){
            this.stepName = stepName;
        }
        public String getStepName(){
            return this.stepName;
        }
        public void setStepImage(String stepImage){
            this.stepImage = stepImage;
        }
        public String getStepImage(){
            return this.stepImage;
        }
        public void setStepScore(int stepScore){
            this.stepScore = stepScore;
        }
        public int getStepScore(){
            return this.stepScore;
        }
        public void setItemList(List<ItemList> itemList){
            this.itemList = itemList;
        }
        public List<ItemList> getItemList(){
            return this.itemList;
        }


        @Override
        public String toString() {
            return "StepList{" + "stepSeq=" + stepSeq + ", stepNo='" + stepNo + '\'' + ", stepName='" + stepName + '\'' + ", stepImage='" + stepImage + '\'' + ", stepScore=" + stepScore + ", itemList=" + itemList + '}';
        }
    }



    public static class ItemList{

        private int itemSeq;//项目序号

        private String itemNo;//项目编号

        private String itemName;//项目名称

        private int itemScore;//项目总分

        private int scoreType;//评分类型

        private List<SubList> subList;//子项类别


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
        public void setItemName(String itemName){
            this.itemName = itemName;
        }
        public String getItemName(){
            return this.itemName;
        }
        public void setItemScore(int itemScore){
            this.itemScore = itemScore;
        }
        public int getItemScore(){
            return this.itemScore;
        }
        public void setScoreType(int scoreType){
            this.scoreType = scoreType;
        }
        public int getScoreType(){
            return this.scoreType;
        }
        public void setSubList(List<SubList> subList){
            this.subList = subList;
        }
        public List<SubList> getSubList(){
            return this.subList;
        }


        @Override
        public String toString() {
            return "ItemList{" + "itemSeq=" + itemSeq + ", itemNo='" + itemNo + '\'' + ", itemName='" + itemName + '\'' + ", itemScore=" + itemScore + ", scoreType=" + scoreType + ", subList=" + subList + '}';
        }
    }

    public static class SubList {

        private int subSeq;//子项序号

        private String subNo;//子项编号

        private String subName;//子项名称

        private int subItemType;//扣分：1；计次扣分：2；分数上限：3；难度调整：4

        private int subItemClass;//子项类别，正常：0,1：计分，2：核分

        private int expectValue;//预期值

        private int subCount;//子项次数

        private int subValue;//子项数值

        private int subScore;//子项分值

        public void setSubSeq(int subSeq){
            this.subSeq = subSeq;
        }
        public int getSubSeq(){
            return this.subSeq;
        }
        public void setSubNo(String subNo){
            this.subNo = subNo;
        }
        public String getSubNo(){
            return this.subNo;
        }
        public void setSubName(String subName){
            this.subName = subName;
        }
        public String getSubName(){
            return this.subName;
        }
        public void setSubItemType(int subItemType){
            this.subItemType = subItemType;
        }
        public int getSubItemType(){
            return this.subItemType;
        }
        public void setSubItemClass(int subItemClass){
            this.subItemClass = subItemClass;
        }
        public int getSubItemClass(){
            return this.subItemClass;
        }
        public void setExpectValue(int expectValue){
            this.expectValue = expectValue;
        }
        public int getExpectValue(){
            return this.expectValue;
        }
        public void setSubCount(int subCount){
            this.subCount = subCount;
        }
        public int getSubCount(){
            return this.subCount;
        }
        public void setSubValue(int subValue){
            this.subValue = subValue;
        }
        public int getSubValue(){
            return this.subValue;
        }
        public void setSubScore(int subScore){
            this.subScore = subScore;
        }
        public int getSubScore(){
            return this.subScore;
        }

        public SubList(int subSeq,String subNo,String subName,int subItemType,int subItemClass,
                       int expectValue, int subCount,int subValue,int subScore){
            this.subSeq = subSeq;
            this.subNo = subNo;
            this.subName = subName;
            this.subItemType = subItemType;
            this.subItemClass = subItemClass;
            this.expectValue = expectValue;
            this.subCount = subCount;
            this.subValue = subValue;
            this.subScore = subScore;
        }

        @Override
        public String toString() {
            return "SubList{" + "subSeq=" + subSeq + ", subNo='" + subNo + '\'' + ", subName='" + subName + '\'' + ", subItemType=" + subItemType + ", subItemClass=" + subItemClass + ", expectValue=" + expectValue + ", subCount=" + subCount + ", subValue=" + subValue + ", subScore=" + subScore + '}';
        }
    }


    @Override
    public String toString() {
        return "ExamDetailReponse{" + "code=" + code + ", success=" + success + ", data=" + data + ", msg='" + msg + '\'' + ", time=" + time + '}';
    }
}
