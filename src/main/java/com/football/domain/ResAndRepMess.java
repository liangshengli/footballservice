package com.football.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lenovo on 2018-3-23.
 */

@Entity
@Table(name = "resandrepmess")
public class ResAndRepMess {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String mess_id;
    private String requestmess;
    private String responsemess;
    private String date;
    private String username;

    public String getMess_id() {
        return mess_id;
    }

    public void setMess_id(String mess_id) {
        this.mess_id = mess_id;
    }

    public String getRequestmess() {
        return requestmess;
    }

    public void setRequestmess(String requestmess) {
        this.requestmess = requestmess;
    }

    public String getResponsemess() {
        return responsemess;
    }

    public void setResponsemess(String responsemess) {
        this.responsemess = responsemess;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
