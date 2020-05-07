package com.learn.all_electric.bean;

import java.util.List;

/**
 * 固件列表详情
 */
public class DeviceDetailReponse {
    private int code;

    private boolean success;

    private List<Data> data;

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
    public void setData(List<Data> data){
        this.data = data;
    }
    public List<Data> getData(){
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

    public static class Data
    {
        private String id;

        private String createUser;

        private String createDept;

        private String createTime;

        private String updateUser;

        private String updateTime;

        private int status;

        private int isDeleted;

        private String questionNo;

        private String firewareVersion;

        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
        public void setCreateUser(String createUser){
            this.createUser = createUser;
        }
        public String getCreateUser(){
            return this.createUser;
        }
        public void setCreateDept(String createDept){
            this.createDept = createDept;
        }
        public String getCreateDept(){
            return this.createDept;
        }
        public void setCreateTime(String createTime){
            this.createTime = createTime;
        }
        public String getCreateTime(){
            return this.createTime;
        }
        public void setUpdateUser(String updateUser){
            this.updateUser = updateUser;
        }
        public String getUpdateUser(){
            return this.updateUser;
        }
        public void setUpdateTime(String updateTime){
            this.updateTime = updateTime;
        }
        public String getUpdateTime(){
            return this.updateTime;
        }
        public void setStatus(int status){
            this.status = status;
        }
        public int getStatus(){
            return this.status;
        }
        public void setIsDeleted(int isDeleted){
            this.isDeleted = isDeleted;
        }
        public int getIsDeleted(){
            return this.isDeleted;
        }
        public void setQuestionNo(String questionNo){
            this.questionNo = questionNo;
        }
        public String getQuestionNo(){
            return this.questionNo;
        }
        public void setFirewareVersion(String firewareVersion){
            this.firewareVersion = firewareVersion;
        }
        public String getFirewareVersion(){
            return this.firewareVersion;
        }
    }
}
