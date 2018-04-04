package com.football.domain;

import java.util.Map;

/**
 * Created by lenovo on 2018-3-28.
 */
public class RecMessage {
    private String uuid;
    private Message message;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
