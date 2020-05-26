package com.zygh.lz.admin;

public class Controlsubsystem {
    private Integer id;

    private String ip;

    private String subsystemname;

    private Integer systemid;

    private String conment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getSubsystemname() {
        return subsystemname;
    }

    public void setSubsystemname(String subsystemname) {
        this.subsystemname = subsystemname == null ? null : subsystemname.trim();
    }

    public Integer getSystemid() {
        return systemid;
    }

    public void setSystemid(Integer systemid) {
        this.systemid = systemid;
    }

    public String getConment() {
        return conment;
    }

    public void setConment(String conment) {
        this.conment = conment == null ? null : conment.trim();
    }
}