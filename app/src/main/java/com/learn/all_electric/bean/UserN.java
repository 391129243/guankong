package com.learn.all_electric.bean;

public class UserN {
    private String  account;//学生ID account
    private String user_name;//学生名称
    private String role_name;
    private ExamDetailReponse examDetailReponse;

    public UserN(String account, String user_name,  String role_name,  ExamDetailReponse examDetailReponse) {
        this.account = account;
        this.user_name = user_name;
        this.role_name = role_name;
        this.examDetailReponse = examDetailReponse;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public ExamDetailReponse getExamDetailReponse() {
        return examDetailReponse;
    }

    public void setExamDetailReponse(ExamDetailReponse examDetailReponse) {
        this.examDetailReponse = examDetailReponse;
    }

    @Override
    public String toString() {
        return "UserN{" + "account='" + account + '\'' + ", user_name='" + user_name + '\'' + ", role_name='" + role_name + '\'' + ", examDetailReponse=" + examDetailReponse + '}';
    }
}
