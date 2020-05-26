package com.zygh.lz.admin;

import java.util.Date;

public class Problem {
    private Integer problemId;

    private String problemNum;

    private Integer problemType;

    private String problemDescribe;

    private String problemState;

    private String problemPicture;

    private String problemVideo;

    private String problemGpsX;

    private String problemGpsY;

    private Date problemDate;

    private String problemCheck;

    private String problemDetail;

    private Integer sysPatrolRecordId;

    private Integer sysStaffId;

    private String roadName;

    private Integer acceptId;

    private Staff staff;

    private String sectionName;

    private String sectionPid;

    public String getSectionPid() {
        return sectionPid;
    }

    public void setSectionPid(String sectionPid) {
        this.sectionPid = sectionPid;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Road getRoad() {

        return road;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    private Road road;                      //道路

    private String section;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public String getProblemNum() {
        return problemNum;
    }

    public void setProblemNum(String problemNum) {
        this.problemNum = problemNum;
    }

    public Integer getProblemType() {
        return problemType;
    }

    public void setProblemType(Integer problemType) {
        this.problemType = problemType;
    }

    public String getProblemDescribe() {
        return problemDescribe;
    }

    public void setProblemDescribe(String problemDescribe) {
        this.problemDescribe = problemDescribe == null ? null : problemDescribe.trim();
    }

    public String getProblemState() {
        return problemState;
    }

    public void setProblemState(String problemState) {
        this.problemState = problemState == null ? null : problemState.trim();
    }

    public String getProblemPicture() {
        return problemPicture;
    }

    public void setProblemPicture(String problemPicture) {
        this.problemPicture = problemPicture == null ? null : problemPicture.trim();
    }

    public String getProblemVideo() {
        return problemVideo;
    }

    public void setProblemVideo(String problemVideo) {
        this.problemVideo = problemVideo == null ? null : problemVideo.trim();
    }

    public String getProblemGpsX() {
        return problemGpsX;
    }

    public void setProblemGpsX(String problemGpsX) {
        this.problemGpsX = problemGpsX == null ? null : problemGpsX.trim();
    }

    public String getProblemGpsY() {
        return problemGpsY;
    }

    public void setProblemGpsY(String problemGpsY) {
        this.problemGpsY = problemGpsY == null ? null : problemGpsY.trim();
    }

    public Date getProblemDate() {
        return problemDate;
    }

    public void setProblemDate(Date problemDate) {
        this.problemDate = problemDate;
    }

    public String getProblemCheck() {
        return problemCheck;
    }

    public void setProblemCheck(String problemCheck) {
        this.problemCheck = problemCheck == null ? null : problemCheck.trim();
    }

    public String getProblemDetail() {
        return problemDetail;
    }

    public void setProblemDetail(String problemDetail) {
        this.problemDetail = problemDetail == null ? null : problemDetail.trim();
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

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName == null ? null : roadName.trim();
    }

    public Integer getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(Integer acceptId) {
        this.acceptId = acceptId;
    }
}