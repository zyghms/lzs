package com.zygh.lz.admin;

import java.util.Date;
import java.util.List;

public class Patrolrecord {
    private Integer sysPatrolRecordId;

    private Integer sysStaffId;

    private Date patrolRecordBegintime;

    private Date patrolRecordEndtime;

    private String patrolRecordPicture;

    private String patrolRecordVideo;

    private String patrolRecordGps;

    private String patrolRecordDetail;

    private String roadName;

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

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

    private Integer sectionPid;

    private String sectionName;

    private String strength;

    private String changeShifts;
    private Gps gpsdw;

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getChangeShifts() {
        return changeShifts;
    }

    public void setChangeShifts(String changeShifts) {
        this.changeShifts = changeShifts;
    }

    public Gps getGpsdw() {
        return gpsdw;
    }

    public void setGpsdw(Gps gpsdw) {
        this.gpsdw = gpsdw;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPost() {
        return staffPost;
    }

    public void setStaffPost(String staffPost) {
        this.staffPost = staffPost;
    }

    public String getStaffTel() {
        return staffTel;
    }

    public void setStaffTel(String staffTel) {
        this.staffTel = staffTel;
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
        this.staffSex = staffSex;
    }

    public String getStaffPhoto() {
        return staffPhoto;
    }

    public void setStaffPhoto(String staffPhoto) {
        this.staffPhoto = staffPhoto;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    public String getStaffHierarchy() {
        return staffHierarchy;
    }

    public void setStaffHierarchy(String staffHierarchy) {
        this.staffHierarchy = staffHierarchy;
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
        this.staffNum = staffNum;
    }

    public String getStaffIdcard() {
        return staffIdcard;
    }

    public void setStaffIdcard(String staffIdcard) {
        this.staffIdcard = staffIdcard;
    }

    public String getStaffOnline() {
        return staffOnline;
    }

    public void setStaffOnline(String staffOnline) {
        this.staffOnline = staffOnline;
    }

    public Integer getSectionPid() {
        return sectionPid;
    }

    public void setSectionPid(Integer sectionPid) {
        this.sectionPid = sectionPid;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionPosition() {
        return sectionPosition;
    }

    public void setSectionPosition(String sectionPosition) {
        this.sectionPosition = sectionPosition;
    }

    public String getSectionTel() {
        return sectionTel;
    }

    public void setSectionTel(String sectionTel) {
        this.sectionTel = sectionTel;
    }

    public String getSectionPerson() {
        return sectionPerson;
    }

    public void setSectionPerson(String sectionPerson) {
        this.sectionPerson = sectionPerson;
    }

    private String sectionPosition;

    private String sectionTel;

    private String sectionPerson;

    public List<Gps> gpsList ;

    public List<Problem> problemList ;

    public List<Gps> getGpsList() {
        return gpsList;
    }

    public void setGpsList(List<Gps> gpsList) {
        this.gpsList = gpsList;
    }

    public List<Problem> getProblemList() {
        return problemList;
    }

    public void setProblemList(List<Problem> problemList) {
        this.problemList = problemList;
    }

    public Integer getSysPatrolRecordId() {
        return sysPatrolRecordId;
    }

    public void setSysPatrolRecordId(Integer sysPatrolRecordId) {
        this.sysPatrolRecordId = sysPatrolRecordId;
    }

    public Integer getSysStaffId() {
        return sysStaffId;
    }

    public void setSysStaffId(Integer sysStaffId) {
        this.sysStaffId = sysStaffId;
    }

    public Date getPatrolRecordBegintime() {
        return patrolRecordBegintime;
    }

    public void setPatrolRecordBegintime(Date patrolRecordBegintime) {
        this.patrolRecordBegintime = patrolRecordBegintime;
    }

    public Date getPatrolRecordEndtime() {
        return patrolRecordEndtime;
    }

    public void setPatrolRecordEndtime(Date patrolRecordEndtime) {
        this.patrolRecordEndtime = patrolRecordEndtime;
    }

    public String getPatrolRecordPicture() {
        return patrolRecordPicture;
    }

    public void setPatrolRecordPicture(String patrolRecordPicture) {
        this.patrolRecordPicture = patrolRecordPicture == null ? null : patrolRecordPicture.trim();
    }

    public String getPatrolRecordVideo() {
        return patrolRecordVideo;
    }

    public void setPatrolRecordVideo(String patrolRecordVideo) {
        this.patrolRecordVideo = patrolRecordVideo == null ? null : patrolRecordVideo.trim();
    }

    public String getPatrolRecordGps() {
        return patrolRecordGps;
    }

    public void setPatrolRecordGps(String patrolRecordGps) {
        this.patrolRecordGps = patrolRecordGps == null ? null : patrolRecordGps.trim();
    }

    public String getPatrolRecordDetail() {
        return patrolRecordDetail;
    }

    public void setPatrolRecordDetail(String patrolRecordDetail) {
        this.patrolRecordDetail = patrolRecordDetail == null ? null : patrolRecordDetail.trim();
    }


}