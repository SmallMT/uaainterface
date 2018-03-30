package com.bov.mt.entity;

public class Token {

    private int code;//请求状态码
    private String token;//请求成功后获取的状态码
    private String error;//请求错误提示信息

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
