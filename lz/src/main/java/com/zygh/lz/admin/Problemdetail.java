package com.zygh.lz.admin;

public class Problemdetail {
    private Integer id;

    private String probledetail;

    private String repairerid;

    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProbledetail() {
        return probledetail;
    }

    public void setProbledetail(String probledetail) {
        this.probledetail = probledetail == null ? null : probledetail.trim();
    }

    public String getRepairerid() {
        return repairerid;
    }

    public void setRepairerid(String repairerid) {
        this.repairerid = repairerid == null ? null : repairerid.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}