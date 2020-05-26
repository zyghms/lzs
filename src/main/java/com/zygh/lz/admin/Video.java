package com.zygh.lz.admin;

public class Video {
    private Integer id;

    private String basicid;

    private String computerip;

    private String host;

    private String basicip;

    private String accout;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBasicid() {
        return basicid;
    }

    public void setBasicid(String basicid) {
        this.basicid = basicid == null ? null : basicid.trim();
    }

    public String getComputerip() {
        return computerip;
    }

    public void setComputerip(String computerip) {
        this.computerip = computerip == null ? null : computerip.trim();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    public String getBasicip() {
        return basicip;
    }

    public void setBasicip(String basicip) {
        this.basicip = basicip == null ? null : basicip.trim();
    }

    public String getAccout() {
        return accout;
    }

    public void setAccout(String accout) {
        this.accout = accout == null ? null : accout.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}