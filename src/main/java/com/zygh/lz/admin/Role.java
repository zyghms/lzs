package com.zygh.lz.admin;

public class Role {
    private Integer sysRoleId;

    private String roleName;

    private String roleDetails;

    private String subsystemUsetype;

    private String menuUsetype;

    private String controlUsetype;          //控件使用状态

    private String controlUsetypeName ;

    public String getMenuUsetypeName() {
        return menuUsetypeName;
    }

    public void setMenuUsetypeName(String menuUsetypeName) {
        this.menuUsetypeName = menuUsetypeName;
    }

    private String menuUsetypeName ;        //菜单使用状态名称

    public String getSubsystemUsetypeName() {

        return subsystemUsetypeName;
    }

    public void setSubsystemUsetypeName(String subsystemUsetypeName) {
        this.subsystemUsetypeName = subsystemUsetypeName;
    }

    private String subsystemUsetypeName ;

    public Integer getSysRoleId() {
        return sysRoleId;
    }

    public String getControlUsetype() {
        return controlUsetype;
    }

    public void setControlUsetype(String controlUsetype) {
        this.controlUsetype = controlUsetype;
    }

    public String getControlUsetypeName() {
        return controlUsetypeName;
    }

    public void setControlUsetypeName(String controlUsetypeName) {
        this.controlUsetypeName = controlUsetypeName;
    }

    public void setSysRoleId(Integer sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleDetails() {
        return roleDetails;
    }

    public void setRoleDetails(String roleDetails) {
        this.roleDetails = roleDetails == null ? null : roleDetails.trim();
    }

    public String getSubsystemUsetype() {
        return subsystemUsetype;
    }

    public void setSubsystemUsetype(String subsystemUsetype) {
        this.subsystemUsetype = subsystemUsetype == null ? null : subsystemUsetype.trim();
    }

    public String getMenuUsetype() {
        return menuUsetype;
    }

    public void setMenuUsetype(String menuUsetype) {
        this.menuUsetype = menuUsetype == null ? null : menuUsetype.trim();
    }
}