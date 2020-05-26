package com.zygh.lz.admin;

import java.util.Date;

public class Xdeclare {
    private Integer id;

    private String appear;

    private String accepter;

    private String document;

    private Date establishtime;

    private Date starttime;

    private Date stoptime;

    private String state;

    private String conmnet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppear() {
        return appear;
    }

    public void setAppear(String appear) {
        this.appear = appear == null ? null : appear.trim();
    }

    public String getAccepter() {
        return accepter;
    }

    public void setAccepter(String accepter) {
        this.accepter = accepter == null ? null : accepter.trim();
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document == null ? null : document.trim();
    }

    public Date getEstablishtime() {
        return establishtime;
    }

    public void setEstablishtime(Date establishtime) {
        this.establishtime = establishtime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getStoptime() {
        return stoptime;
    }

    public void setStoptime(Date stoptime) {
        this.stoptime = stoptime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getConmnet() {
        return conmnet;
    }

    public void setConmnet(String conmnet) {
        this.conmnet = conmnet == null ? null : conmnet.trim();
    }
}