package com.learn.all_electric.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* 实验 */
public class ExperimentBean implements Serializable {
    private String title;//实验标题
    private String endStep;//最后步骤说明
    private Double Score_total;//总分数
    private Double own_total;//答题总分数
    private List<StepBean> stepBeans;
    private boolean isShow=false;//是否显示分数详情

    public static class StepBean implements Serializable {//实验步骤
        private String stepTitle;//步骤标题
        private int type;//体型
        private String btnStr;//按钮
        private List<String> answers;//答案
        private boolean balance = false;//平衡
        private int[] images;//图片
        private String objectNum_left;
        private int objectWeight_left;
        private int length_left;
        private Map<String, Double> ownScores = new LinkedHashMap<>();//我的答案分数
        private Map<String, Double> answerScores = new LinkedHashMap<>();//题目分数
        private Map<String,Object> ownAnswers=new LinkedHashMap<>();//我的答案
        private Map<String,List<StepChioseBean>> chioseMaps = new HashMap<>();//选择题-填空题文字

        private int selCount=0;//0未选，1选择 ，2不可做

        public StepBean() {
            //初始化避免查询错误
        }

        public int getSelCount() {
            return selCount;
        }

        public void setSelCount(int selCount) {
            this.selCount = selCount;
        }

        public String getObjectNum_left() {
            return objectNum_left;
        }

        public void setObjectNum_left(String objectNum_left) {
            this.objectNum_left = objectNum_left;
        }

        public int getObjectWeight_left() {
            return objectWeight_left;
        }

        public void setObjectWeight_left(int objectWeight_left) {
            this.objectWeight_left = objectWeight_left;
        }

        public int getLength_left() {
            return length_left;
        }

        public void setLength_left(int length_left) {
            this.length_left = length_left;
        }

        public List<String> getAnswers() {
            return answers;
        }

        public void setAnswers(List<String> answers) {
            this.answers = answers;
        }

        public String getStepTitle() {
            return stepTitle;
        }

        public void setStepTitle(String stepTitle) {
            this.stepTitle = stepTitle;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getBtnStr() {
            return btnStr;
        }

        public void setBtnStr(String btnStr) {
            this.btnStr = btnStr;
        }


        public boolean isBalance() {
            return balance;
        }

        public void setBalance(boolean balance) {
            this.balance = balance;
        }

        public Map<String, Double> getOwnScores() {
            return ownScores;
        }

        public void setOwnScores(Map<String, Double> ownScores) {
            this.ownScores = ownScores;
        }

        public Map<String, Double> getAnswerScores() {
            return answerScores;
        }

        public void setAnswerScores(Map<String, Double> answerScores) {
            this.answerScores = answerScores;
        }

        public Map<String, Object> getOwnAnswers() {
            return ownAnswers;
        }

        public void setOwnAnswers(Map<String, Object> ownAnswers) {
            this.ownAnswers = ownAnswers;
        }

        public Map<String, List<StepChioseBean>> getChioseMaps() {
            return chioseMaps;
        }

        public void setChioseMaps(Map<String, List<StepChioseBean>> chioseMaps) {
            this.chioseMaps = chioseMaps;
        }

        public int[] getImages() {
            return images;
        }

        public void setImages(int[] images) {
            this.images = images;
        }

        public static class StepChioseBean implements Serializable {
            private String chioseStart;
            private String chioseEnd;
            private String answer="";
            private String timi_answer="";
            private String lr_answer="";//录入值
            private double score=0;//单条分数
            private boolean iscolseNoEffect=true;
            private boolean isShow=false;
            private List<String> chiose=new ArrayList<>();//选项

            public StepChioseBean() {
            }

            public StepChioseBean(String chioseStart, String chioseEnd) {
                this.chioseStart = chioseStart;
                this.chioseEnd = chioseEnd;
            }

            public String getChioseStart() {
                return chioseStart;
            }

            public void setChioseStart(String chioseStart) {
                this.chioseStart = chioseStart;
            }

            public String getChioseEnd() {
                return chioseEnd;
            }

            public void setChioseEnd(String chioseEnd) {
                this.chioseEnd = chioseEnd;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public String getTimi_answer() {
                return timi_answer;
            }

            public void setTimi_answer(String timi_answer) {
                this.timi_answer = timi_answer;
            }

            public boolean isShow() {
                return isShow;
            }

            public void setShow(boolean show) {
                isShow = show;
            }

            public List<String> getChiose() {
                return chiose;
            }

            public String getLr_answer() {
                return lr_answer;
            }

            public void setLr_answer(String lr_answer) {
                this.lr_answer = lr_answer;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public boolean isIscolseNoEffect() {
                return iscolseNoEffect;
            }

            public void setIscolseNoEffect(boolean iscolseNoEffect) {
                this.iscolseNoEffect = iscolseNoEffect;
            }

            public void setChiose(List<String> chiose) {
                this.chiose = chiose;
            }

            @Override
            public String toString() {
                return "StepChioseBean{" +
                        "chioseStart='" + chioseStart + '\'' +
                        ", chioseEnd='" + chioseEnd + '\'' +
                        ", answer='" + answer + '\'' +
                        ", timi_answer='" + timi_answer + '\'' +
                        ", isShow=" + isShow +
                        ", chiose=" + chiose +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "StepBean{" +
                    "stepTitle='" + stepTitle + '\'' +
                    ", type=" + type +
                    ", btnStr='" + btnStr + '\'' +
                    ", answers=" + answers +
                    ", balance=" + balance +
                    ", objectNum_left='" + objectNum_left + '\'' +
                    ", objectWeight_left=" + objectWeight_left +
                    ", length_left=" + length_left +
                    ", ownScores=" + ownScores.toString() +
                    ", answerScores=" + answerScores.toString() +
                    ", chioseBeans=" + chioseMaps.toString() +
                    '}';
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEndStep() {
        return endStep;
    }

    public void setEndStep(String endStep) {
        this.endStep = endStep;
    }

    public Double getScore_total() {
        return Score_total;
    }

    public void setScore_total(Double score_total) {
        Score_total = score_total;
    }

    public Double getOwn_total() {
        return own_total;
    }

    public void setOwn_total(Double own_total) {
        this.own_total = own_total;
    }

    public List<StepBean> getStepBeans() {
        return stepBeans;
    }

    public void setStepBeans(List<StepBean> stepBeans) {
        this.stepBeans = stepBeans;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    public String toString() {
        return "ExperimentBean{" +
                "title='" + title + '\'' +
                ", endStep='" + endStep + '\'' +
                ", Score_total=" + Score_total +
                ", stepBeans=" + stepBeans.toString() +
                '}';
    }

}
