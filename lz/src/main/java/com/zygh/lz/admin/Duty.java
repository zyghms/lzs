package com.zygh.lz.admin;

public class Duty {
    private Integer sysDutyId;

    private String sutyRoad;

    private Integer sysSectionId;

    private Integer sysRoadId;

    private Integer fenguanstaffId;

    private Integer totalstaffId;

    private Integer onestaffId;

    private Integer twostaffId;

    private String fenguanName ;        //分管路长姓名

    private String totalName ;          //总路长姓名

    private String oneName ;            //一級路长姓名

    private String sectionName;         //部门名称


    private String roadName;            //道路名称

    public String getFenguanName() {
        return fenguanName;
    }

    public void setFenguanName(String fenguanName) {
        this.fenguanName = fenguanName;
    }

    public String getTotalName() {
        return totalName;
    }

    public void setTotalName(String totalName) {
        this.totalName = totalName;
    }

    public String getOneName() {
        return oneName;
    }

    public void setOneName(String oneName) {
        this.oneName = oneName;
    }

    public String getTwoName() {
        return twoName;
    }

    public void setTwoName(String twoName) {
        this.twoName = twoName;
    }

    private String twoName ;            //二级路长姓名

    public Integer getSysDutyId() {
        return sysDutyId;
    }

    public void setSysDutyId(Integer sysDutyId) {
        this.sysDutyId = sysDutyId;
    }

    public String getSutyRoad() {
        return sutyRoad;
    }

    public void setSutyRoad(String sutyRoad) {
        this.sutyRoad = sutyRoad == null ? null : sutyRoad.trim();
    }

    public Integer getSysSectionId() {
        return sysSectionId;
    }

    public void setSysSectionId(Integer sysSectionId) {
        this.sysSectionId = sysSectionId;
    }

    public Integer getSysRoadId() {
        return sysRoadId;
    }

    public void setSysRoadId(Integer sysRoadId) {
        this.sysRoadId = sysRoadId;
    }

    public Integer getFenguanstaffId() {
        return fenguanstaffId;
    }

    public void setFenguanstaffId(Integer fenguanstaffId) {
        this.fenguanstaffId = fenguanstaffId;
    }

    public Integer getTotalstaffId() {
        return totalstaffId;
    }

    public void setTotalstaffId(Integer totalstaffId) {
        this.totalstaffId = totalstaffId;
    }

    public Integer getOnestaffId() {
        return onestaffId;
    }

    public void setOnestaffId(Integer onestaffId) {
        this.onestaffId = onestaffId;
    }

    public Integer getTwostaffId() {
        return twostaffId;
    }

    public void setTwostaffId(Integer twostaffId) {
        this.twostaffId = twostaffId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }
}