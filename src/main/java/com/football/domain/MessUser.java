package com.football.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by lenovo on 2018-3-28.
 */

@Entity
@Table(name = "mess_user")
public class MessUser {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String mess_user_id;
    private String username;
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "messlog_id")
    private MessageLog mess_id;
    private String read_status; //0 "未读"  ; 1 "已读"
    private String cdate;

    public String getMess_user_id() {
        return mess_user_id;
    }

    public void setMess_user_id(String mess_user_id) {
        this.mess_user_id = mess_user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MessageLog getMess_id() {
        return mess_id;
    }

    public void setMess_id(MessageLog mess_id) {
        this.mess_id = mess_id;
    }

    public String getRead_status() {
        return read_status;
    }

    public void setRead_status(String read_status) {
        this.read_status = read_status;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }
}
