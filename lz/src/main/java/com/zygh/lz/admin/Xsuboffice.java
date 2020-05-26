package com.zygh.lz.admin;

public class Xsuboffice {
    private Integer id;

    private String name;

    private String conment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getConment() {
        return conment;
    }

    public void setConment(String conment) {
        this.conment = conment == null ? null : conment.trim();
    }
}