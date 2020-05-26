package com.zygh.lz.admin;

public class Menu {
    private Integer sysMenuId;

    private Integer parentId;

    private String menuUrl;

    private String menuIcon;

    private Integer menuSort;

    private String menuName;

    private Integer sysSubsystemId;

    public Integer getSysMenuId() {
        return sysMenuId;
    }

    public void setSysMenuId(Integer sysMenuId) {
        this.sysMenuId = sysMenuId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
    }

    public Integer getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public Integer getSysSubsystemId() {
        return sysSubsystemId;
    }

    public void setSysSubsystemId(Integer sysSubsystemId) {
        this.sysSubsystemId = sysSubsystemId;
    }
}