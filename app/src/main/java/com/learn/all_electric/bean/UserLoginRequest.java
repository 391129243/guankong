package com.learn.all_electric.bean;

public class UserLoginRequest {

    String tenantid;
    String username;
    String password;
    String grant_type;

    public UserLoginRequest(String tenant_id,String user_name, String password, String grant_type){
        this.tenantid = tenant_id;
        this.username = user_name;
        this.password = password;
        this.grant_type = grant_type;
    }
}
