package com.zygh.lz.admin;

import java.util.List;

public class Subsystem {
    private Integer sysSubsystemId;

    private String subsystemName;

    private String icon;

    private String sref;

    private Integer sysProjectId;

    private Integer title;

    private Integer subsystemSort;


    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public List<Menu> menuList ;

    public Integer getSysSubsystemId() {
        return sysSubsystemId;
    }

    public void setSysSubsystemId(Integer sysSubsystemId) {
        this.sysSubsystemId = sysSubsystemId;
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName == null ? null : subsystemName.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getSref() {
        return sref;
    }

    public void setSref(String sref) {
        this.sref = sref == null ? null : sref.trim();
    }

    public Integer getSysProjectId() {
        return sysProjectId;
    }

    public void setSysProjectId(Integer sysProjectId) {
        this.sysProjectId = sysProjectId;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public Integer getSubsystemSort() {
        return subsystemSort;
    }

    public void setSubsystemSort(Integer subsystemSort) {
        this.subsystemSort = subsystemSort;
    }
}