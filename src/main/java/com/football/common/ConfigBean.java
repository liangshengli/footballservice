package com.football.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by mac on 17/8/22.
 */
@ConfigurationProperties(prefix = "footballconfig")
public class ConfigBean {

        private String urlandport;  //地址和端口
        private String loginurl; //登录接口地址
        private String todaymess ; //今日推荐数据接口地址
        private String historymess ; //历史推荐数据接口地址

        private String mqUserName;
        private String mqPassword;
        private String mqHost;
        private String exchangeName;
        private String queueName;

    public String getUrlandport() {
        return urlandport;
    }

    public void setUrlandport(String urlandport) {
        this.urlandport = urlandport;
    }

    public String getLoginurl() {
        return loginurl;
    }

    public void setLoginurl(String loginurl) {
        this.loginurl = loginurl;
    }

    public String getTodaymess() {
        return todaymess;
    }

    public void setTodaymess(String todaymess) {
        this.todaymess = todaymess;
    }

    public String getHistorymess() {
        return historymess;
    }

    public void setHistorymess(String historymess) {
        this.historymess = historymess;
    }

    public String getMqUserName() {
        return mqUserName;
    }

    public void setMqUserName(String mqUserName) {
        this.mqUserName = mqUserName;
    }

    public String getMqPassword() {
        return mqPassword;
    }

    public void setMqPassword(String mqPassword) {
        this.mqPassword = mqPassword;
    }

    public String getMqHost() {
        return mqHost;
    }

    public void setMqHost(String mqHost) {
        this.mqHost = mqHost;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
