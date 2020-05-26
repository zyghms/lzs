package com.zygh.lz.admin;

import java.util.Date;

public class Location {
    private Integer id;

    private Integer sysStaffId;

    private String gpsX;

    private String gpsY;

    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysStaffId() {
        return sysStaffId;
    }

    public void setSysStaffId(Integer sysStaffId) {
        this.sysStaffId = sysStaffId;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}