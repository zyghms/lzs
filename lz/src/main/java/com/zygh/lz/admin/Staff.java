package com.zygh.lz.admin;

import java.util.Date;
import java.util.List;

public class Staff {
    private Integer sysStaffId;

    private String staffName;

    private String staffPost;

    private String staffTel;

    private Integer sysGpsId;

    private Integer sysSectionId;

    private Integer sysRoleId;

    private String staffSex;

    private String staffPhoto;

    private String staffPassword;

    private String staffHierarchy;

    private Integer stafffPid;

    private String staffNum;

    private String staffIdcard;

    private String staffOnline;

    private List<Staff> staffList;

    private String sectionName;

    private String stafftype;

    private String strength;

    private String changeShifts;

    private Gps gps;

    private Xarea xarea;

    private List<Xarea> xareaList;

    private String cellphonenumber;

    //时长
    private String duration;
    //距离
    private Double juli;

    public Double getJuli() {
        return juli;
    }

    public void setJuli(Double juli) {
        this.juli = juli;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public List<Xarea> getXareaList() {
        return xareaList;
    }

    public void setXareaList(List<Xarea> xareaList) {
        this.xareaList = xareaList;
    }

    public String getCellphonenumber() {
        return cellphonenumber;
    }

    public void setCellphonenumber(String cellphonenumber) {
        this.cellphonenumber = cellphonenumber;
    }

    public Xarea getXarea() {
        return xarea;
    }

    public void setXarea(Xarea xarea) {
        this.xarea = xarea;
    }

    //大队
    private String battalion;

    public String getBattalion() {
        return battalion;
    }

    public void setBattalion(String battalion) {
        this.battalion = battalion;
    }

    //上岗时间
    private Date patrolRecordBegintime;

    public Date getPatrolRecordBegintime() {
        return patrolRecordBegintime;
    }

    public void setPatrolRecordBegintime(Date patrolRecordBegintime) {
        this.patrolRecordBegintime = patrolRecordBegintime;
    }

    public Integer getSectionpid() {
        return sectionpid;
    }

    public void setSectionpid(Integer sectionpid) {
        this.sectionpid = sectionpid;
    }

    private Integer sectionpid;

    private Patrolrecord patrolrecord;

    public Patrolrecord getPatrolrecord() {
        return patrolrecord;
    }

    public void setPatrolrecord(Patrolrecord patrolrecord) {
        this.patrolrecord = patrolrecord;
    }

    public Gps getGps() {
        return gps;
    }

    public void setGps(Gps gps) {
        this.gps = gps;
    }


    public String getStafftype() {
        return stafftype;
    }

    public void setStafftype(String stafftype) {
        this.stafftype = stafftype;
    }


    public String getChangeShifts() {
        return changeShifts;
    }

    public void setChangeShifts(String changeShifts) {
        this.changeShifts = changeShifts;
    }


    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public Integer getSysStaffId() {
        return sysStaffId;
    }

    public void setSysStaffId(Integer sysStaffId) {
        this.sysStaffId = sysStaffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }

    public String getStaffPost() {
        return staffPost;
    }

    public void setStaffPost(String staffPost) {
        this.staffPost = staffPost == null ? null : staffPost.trim();
    }

    public String getStaffTel() {
        return staffTel;
    }

    public void setStaffTel(String staffTel) {
        this.staffTel = staffTel == null ? null : staffTel.trim();
    }

    public Integer getSysGpsId() {
        return sysGpsId;
    }

    public void setSysGpsId(Integer sysGpsId) {
        this.sysGpsId = sysGpsId;
    }

    public Integer getSysSectionId() {
        return sysSectionId;
    }

    public void setSysSectionId(Integer sysSectionId) {
        this.sysSectionId = sysSectionId;
    }

    public Integer getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(Integer sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public String getStaffSex() {
        return staffSex;
    }

    public void setStaffSex(String staffSex) {
        this.staffSex = staffSex == null ? null : staffSex.trim();
    }

    public String getStaffPhoto() {
        return staffPhoto;
    }

    public void setStaffPhoto(String staffPhoto) {
        this.staffPhoto = staffPhoto == null ? null : staffPhoto.trim();
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword == null ? null : staffPassword.trim();
    }

    public String getStaffHierarchy() {
        return staffHierarchy;
    }

    public void setStaffHierarchy(String staffHierarchy) {
        this.staffHierarchy = staffHierarchy == null ? null : staffHierarchy.trim();
    }

    public Integer getStafffPid() {
        return stafffPid;
    }

    public void setStafffPid(Integer stafffPid) {
        this.stafffPid = stafffPid;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum == null ? null : staffNum.trim();
    }

    public String getStaffIdcard() {
        return staffIdcard;
    }

    public void setStaffIdcard(String staffIdcard) {
        this.staffIdcard = staffIdcard == null ? null : staffIdcard.trim();
    }

    public String getStaffOnline() {
        return staffOnline;
    }

    public void setStaffOnline(String staffOnline) {
        this.staffOnline = staffOnline == null ? null : staffOnline.trim();
    }
}