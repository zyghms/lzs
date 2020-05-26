package com.zygh.lz.admin;

import java.util.Date;

public class Notice {
    private Integer sysNoticeId;

    private String noticeContent;

    private String noticeUrl;

    private Integer noticeSend;

    private String noticeAccept;

    private String noticeTitle;

    private Date noticeTime;

    private String noticeDetail;

    private String staffName;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    private String noticeSectionName;//接收大队

    public String getNoticeSectionName() {
        return noticeSectionName;
    }

    public void setNoticeSectionName(String noticeSectionName) {
        this.noticeSectionName = noticeSectionName;
    }


    public Integer getSysNoticeId() {
        return sysNoticeId;
    }

    public void setSysNoticeId(Integer sysNoticeId) {
        this.sysNoticeId = sysNoticeId;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent == null ? null : noticeContent.trim();
    }

    public String getNoticeUrl() {
        return noticeUrl;
    }

    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl == null ? null : noticeUrl.trim();
    }

    public Integer getNoticeSend() {
        return noticeSend;
    }

    public void setNoticeSend(Integer noticeSend) {
        this.noticeSend = noticeSend;
    }

    public String getNoticeAccept() {
        return noticeAccept;
    }

    public void setNoticeAccept(String noticeAccept) {
        this.noticeAccept = noticeAccept == null ? null : noticeAccept.trim();
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle == null ? null : noticeTitle.trim();
    }

    public Date getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getNoticeDetail() {
        return noticeDetail;
    }

    public void setNoticeDetail(String noticeDetail) {
        this.noticeDetail = noticeDetail == null ? null : noticeDetail.trim();
    }
}