package com.football.domain;

import java.util.Map;

/**
 * Created by lenovo on 2018-3-23.
 */
public class LoginData {
    private Map<String,String> data;
    private String code;
    private String msg;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
