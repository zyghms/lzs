package com.zygh.lz.admin;

public class Controlsystem {
    private Integer id;

    private String ip;

    private String systemname;

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

    public String getSystemname() {
        return systemname;
    }

    public void setSystemname(String systemname) {
        this.systemname = systemname == null ? null : systemname.trim();
    }

    public String getConment() {
        return conment;
    }

    public void setConment(String conment) {
        this.conment = conment == null ? null : conment.trim();
    }
}