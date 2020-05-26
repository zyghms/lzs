package com.zygh.lz.admin;

public class Infrastructure {
    private Integer sysInfrastructureId;

    private Integer sysRoadId;

    private String infrastructureName;

    private String infrastructureType;

    private String damage;

    private String capital;

    private String department;

    private String locationdetails;

    public Integer getSysInfrastructureId() {
        return sysInfrastructureId;
    }

    public void setSysInfrastructureId(Integer sysInfrastructureId) {
        this.sysInfrastructureId = sysInfrastructureId;
    }

    public Integer getSysRoadId() {
        return sysRoadId;
    }

    public void setSysRoadId(Integer sysRoadId) {
        this.sysRoadId = sysRoadId;
    }

    public String getInfrastructureName() {
        return infrastructureName;
    }

    public void setInfrastructureName(String infrastructureName) {
        this.infrastructureName = infrastructureName == null ? null : infrastructureName.trim();
    }

    public String getInfrastructureType() {
        return infrastructureType;
    }

    public void setInfrastructureType(String infrastructureType) {
        this.infrastructureType = infrastructureType == null ? null : infrastructureType.trim();
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage == null ? null : damage.trim();
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital == null ? null : capital.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getLocationdetails() {
        return locationdetails;
    }

    public void setLocationdetails(String locationdetails) {
        this.locationdetails = locationdetails == null ? null : locationdetails.trim();
    }
}