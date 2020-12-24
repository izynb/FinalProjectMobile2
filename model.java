package com.example.finalprojectmobile2;

import java.util.Date;

public class model {
    private String publishId;
    String subject;
    Date date;
    String text;
    public model(String subject,String text,Date date){
        this.subject=subject;
        this.text=text;
        this.date=date;
    }
    public model(){}

    public String getText() {
        return text;
    }

    public String getSubject() {
        return subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }
}
