package com.zygh.lz.admin;

public class Urban {
    private Integer sysUrbanId;

    private Integer sysSectionId;

    private String urbanName;

    private String sectionName;         //部门名称

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Integer getSysUrbanId() {
        return sysUrbanId;
    }

    public void setSysUrbanId(Integer sysUrbanId) {
        this.sysUrbanId = sysUrbanId;
    }

    public Integer getSysSectionId() {
        return sysSectionId;
    }

    public void setSysSectionId(Integer sysSectionId) {
        this.sysSectionId = sysSectionId;
    }

    public String getUrbanName() {
        return urbanName;
    }

    public void setUrbanName(String urbanName) {
        this.urbanName = urbanName == null ? null : urbanName.trim();
    }
}