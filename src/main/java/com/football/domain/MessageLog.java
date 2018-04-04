package com.football.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lenovo on 2018-3-28.
 */


@Entity
@Table(name = "messagelog")
public class MessageLog {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String messlog_id;

    private String messagecontent ;

    private String messagesenddate;

    public String getMesslog_id() {
        return messlog_id;
    }

    public void setMesslog_id(String messlog_id) {
        this.messlog_id = messlog_id;
    }

    public String getMessagecontent() {
        return messagecontent;
    }

    public void setMessagecontent(String messagecontent) {
        this.messagecontent = messagecontent;
    }

    public String getMessagesenddate() {
        return messagesenddate;
    }

    public void setMessagesenddate(String messagesenddate) {
        this.messagesenddate = messagesenddate;
    }
}
