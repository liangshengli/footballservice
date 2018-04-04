package com.football.domain;

/**
 * Created by lenovo on 2018-3-28.
 */
public class Message {
    private String datetime;
    private String sendString;
    private String mess_users;//推送消息的所有人

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSendString() {
        return sendString;
    }

    public void setSendString(String sendString) {
        this.sendString = sendString;
    }

    public String getMess_users() {
        return mess_users;
    }

    public void setMess_users(String mess_users) {
        this.mess_users = mess_users;
    }
}
