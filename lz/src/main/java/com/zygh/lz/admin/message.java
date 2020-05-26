package com.zygh.lz.admin;

import java.util.Date;

public class message {
    private Integer sysMessageId;

    private Integer messagePid;

    private String messageType;

    private String messageName;

    private Integer messageState;

    private Integer acceptId;

    private Integer launchId;

    private Date createTime;

    private Integer isDelete;

    public String getLaunchName() {
        return launchName;
    }

    public void setLaunchName(String launchName) {
        this.launchName = launchName;
    }

    private String launchName;

    public Integer getSysMessageId() {
        return sysMessageId;
    }

    public void setSysMessageId(Integer sysMessageId) {
        this.sysMessageId = sysMessageId;
    }

    public Integer getMessagePid() {
        return messagePid;
    }

    public void setMessagePid(Integer messagePid) {
        this.messagePid = messagePid;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType == null ? null : messageType.trim();
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName == null ? null : messageName.trim();
    }

    public Integer getMessageState() {
        return messageState;
    }

    public void setMessageState(Integer messageState) {
        this.messageState = messageState;
    }

    public Integer getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(Integer acceptId) {
        this.acceptId = acceptId;
    }

    public Integer getLaunchId() {
        return launchId;
    }

    public void setLaunchId(Integer launchId) {
        this.launchId = launchId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}