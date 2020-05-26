package com.zygh.lz.admin;

public class Xrelation {
    private Integer id;

    private Integer staffId;

    private Integer xareaId;

    private String conmnet;

    private Integer pcty;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getXareaId() {
        return xareaId;
    }

    public void setXareaId(Integer xareaId) {
        this.xareaId = xareaId;
    }

    public String getConmnet() {
        return conmnet;
    }

    public void setConmnet(String conmnet) {
        this.conmnet = conmnet == null ? null : conmnet.trim();
    }

    public Integer getPcty() {
        return pcty;
    }

    public void setPcty(Integer pcty) {
        this.pcty = pcty;
    }
}