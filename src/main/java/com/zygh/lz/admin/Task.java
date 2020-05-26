package com.zygh.lz.admin;

import java.util.Date;

public class Task {
    private Integer sysTaskId;

    private String taskNum;

    private Integer sysProblemId;

    private Integer launchStaffId;

    private Integer acceptStaffId;

    private String taskState;

    private Date taskCreatetime;

    private Date taskFinishtime;

    private String taskTitle;

    private String taskDescribe;

    private String taskMoney;

    private String taskPlanMoney;

    private String taskDetail;

    public Integer getSysTaskId() {
        return sysTaskId;
    }

    public void setSysTaskId(Integer sysTaskId) {
        this.sysTaskId = sysTaskId;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum == null ? null : taskNum.trim();
    }

    public Integer getSysProblemId() {
        return sysProblemId;
    }

    public void setSysProblemId(Integer sysProblemId) {
        this.sysProblemId = sysProblemId;
    }

    public Integer getLaunchStaffId() {
        return launchStaffId;
    }

    public void setLaunchStaffId(Integer launchStaffId) {
        this.launchStaffId = launchStaffId;
    }

    public Integer getAcceptStaffId() {
        return acceptStaffId;
    }

    public void setAcceptStaffId(Integer acceptStaffId) {
        this.acceptStaffId = acceptStaffId;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState == null ? null : taskState.trim();
    }

    public Date getTaskCreatetime() {
        return taskCreatetime;
    }

    public void setTaskCreatetime(Date taskCreatetime) {
        this.taskCreatetime = taskCreatetime;
    }

    public Date getTaskFinishtime() {
        return taskFinishtime;
    }

    public void setTaskFinishtime(Date taskFinishtime) {
        this.taskFinishtime = taskFinishtime;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle == null ? null : taskTitle.trim();
    }

    public String getTaskDescribe() {
        return taskDescribe;
    }

    public void setTaskDescribe(String taskDescribe) {
        this.taskDescribe = taskDescribe == null ? null : taskDescribe.trim();
    }

    public String getTaskMoney() {
        return taskMoney;
    }

    public void setTaskMoney(String taskMoney) {
        this.taskMoney = taskMoney == null ? null : taskMoney.trim();
    }

    public String getTaskPlanMoney() {
        return taskPlanMoney;
    }

    public void setTaskPlanMoney(String taskPlanMoney) {
        this.taskPlanMoney = taskPlanMoney == null ? null : taskPlanMoney.trim();
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail == null ? null : taskDetail.trim();
    }
}