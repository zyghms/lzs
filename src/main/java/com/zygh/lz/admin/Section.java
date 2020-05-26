package com.zygh.lz.admin;

import java.util.List;

public class Section {
    private Integer sysSectionId;

    private Integer sectionPid;

    private String sectionName;

    private String sectionPosition;

    private String sectionTel;

    private String sectionPerson;

    private List<Section> sectionList ;

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public Integer getSysSectionId() {
        return sysSectionId;
    }

    public void setSysSectionId(Integer sysSectionId) {
        this.sysSectionId = sysSectionId;
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
        this.sectionName = sectionName == null ? null : sectionName.trim();
    }

    public String getSectionPosition() {
        return sectionPosition;
    }

    public void setSectionPosition(String sectionPosition) {
        this.sectionPosition = sectionPosition == null ? null : sectionPosition.trim();
    }

    public String getSectionTel() {
        return sectionTel;
    }

    public void setSectionTel(String sectionTel) {
        this.sectionTel = sectionTel == null ? null : sectionTel.trim();
    }

    public String getSectionPerson() {
        return sectionPerson;
    }

    public void setSectionPerson(String sectionPerson) {
        this.sectionPerson = sectionPerson == null ? null : sectionPerson.trim();
    }
}