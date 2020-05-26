package com.zygh.lz.admin;

public class Road {
    private Integer sysRoadId;

    private Integer sysUrbanId;

    private String roadName;

    private String roadBegin;

    private String roadOver;

    private String roadType;

    private String roadGps;

    private String roadGpsBegin;

    private String roadGpsEnd;

    public Integer getSysRoadId() {
        return sysRoadId;
    }

    public void setSysRoadId(Integer sysRoadId) {
        this.sysRoadId = sysRoadId;
    }

    public Integer getSysUrbanId() {
        return sysUrbanId;
    }

    public void setSysUrbanId(Integer sysUrbanId) {
        this.sysUrbanId = sysUrbanId;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName == null ? null : roadName.trim();
    }

    public String getRoadBegin() {
        return roadBegin;
    }

    public void setRoadBegin(String roadBegin) {
        this.roadBegin = roadBegin == null ? null : roadBegin.trim();
    }

    public String getRoadOver() {
        return roadOver;
    }

    public void setRoadOver(String roadOver) {
        this.roadOver = roadOver == null ? null : roadOver.trim();
    }

    public String getRoadType() {
        return roadType;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType == null ? null : roadType.trim();
    }

    public String getRoadGps() {
        return roadGps;
    }

    public void setRoadGps(String roadGps) {
        this.roadGps = roadGps == null ? null : roadGps.trim();
    }

    public String getRoadGpsBegin() {
        return roadGpsBegin;
    }

    public void setRoadGpsBegin(String roadGpsBegin) {
        this.roadGpsBegin = roadGpsBegin == null ? null : roadGpsBegin.trim();
    }

    public String getRoadGpsEnd() {
        return roadGpsEnd;
    }

    public void setRoadGpsEnd(String roadGpsEnd) {
        this.roadGpsEnd = roadGpsEnd == null ? null : roadGpsEnd.trim();
    }
}