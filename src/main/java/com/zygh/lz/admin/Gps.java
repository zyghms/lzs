package com.zygh.lz.admin;

public class Gps {
    private Integer sysGpsId;

    private String gpsX;

    private String gpsY;

    private String gpsZ;

    private String gpsTime;

    private Integer sysStaffId;

    private Integer sysPatrolRecordId;

    public Integer getSysGpsId() {
        return sysGpsId;
    }

    public void setSysGpsId(Integer sysGpsId) {
        this.sysGpsId = sysGpsId;
    }

    public String getGpsX() {
        return gpsX;
    }

    public void setGpsX(String gpsX) {
        this.gpsX = gpsX == null ? null : gpsX.trim();
    }

    public String getGpsY() {
        return gpsY;
    }

    public void setGpsY(String gpsY) {
        this.gpsY = gpsY == null ? null : gpsY.trim();
    }

    public String getGpsZ() {
        return gpsZ;
    }

    public void setGpsZ(String gpsZ) {
        this.gpsZ = gpsZ == null ? null : gpsZ.trim();
    }

    public String getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(String gpsTime) {
        this.gpsTime = gpsTime == null ? null : gpsTime.trim();
    }

    public Integer getSysStaffId() {
        return sysStaffId;
    }

    public void setSysStaffId(Integer sysStaffId) {
        this.sysStaffId = sysStaffId;
    }

    public Integer getSysPatrolRecordId() {
        return sysPatrolRecordId;
    }

    public void setSysPatrolRecordId(Integer sysPatrolRecordId) {
        this.sysPatrolRecordId = sysPatrolRecordId;
    }

    @Override
    public String toString() {
        return "Gps{" +
                "sysGpsId=" + sysGpsId +
                ", gpsX='" + gpsX + '\'' +
                ", gpsY='" + gpsY + '\'' +
                ", gpsZ='" + gpsZ + '\'' +
                ", gpsTime='" + gpsTime + '\'' +
                ", sysStaffId=" + sysStaffId +
                ", sysPatrolRecordId=" + sysPatrolRecordId +
                '}';
    }
}