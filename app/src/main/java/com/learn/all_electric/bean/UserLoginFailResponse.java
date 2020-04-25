package com.learn.all_electric.bean;

public class UserLoginFailResponse {
    String error_code;
    String error_description;

    public UserLoginFailResponse(String errorcode, String errordescription){
        this.error_code = errorcode;
        this.error_description = errordescription;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }
}
