package com.zygh.lz.admin;

public class Asset {
    private Integer sysAssetId;

    private Integer sysSectionId;

    private Integer sysStaffId;

    private String assetName;

    private String assetType;

    private String assetGpsx;

    private String assetGpsy;

    public Integer getSysAssetId() {
        return sysAssetId;
    }

    public void setSysAssetId(Integer sysAssetId) {
        this.sysAssetId = sysAssetId;
    }

    public Integer getSysSectionId() {
        return sysSectionId;
    }

    public void setSysSectionId(Integer sysSectionId) {
        this.sysSectionId = sysSectionId;
    }

    public Integer getSysStaffId() {
        return sysStaffId;
    }

    public void setSysStaffId(Integer sysStaffId) {
        this.sysStaffId = sysStaffId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName == null ? null : assetName.trim();
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType == null ? null : assetType.trim();
    }

    public String getAssetGpsx() {
        return assetGpsx;
    }

    public void setAssetGpsx(String assetGpsx) {
        this.assetGpsx = assetGpsx == null ? null : assetGpsx.trim();
    }

    public String getAssetGpsy() {
        return assetGpsy;
    }

    public void setAssetGpsy(String assetGpsy) {
        this.assetGpsy = assetGpsy == null ? null : assetGpsy.trim();
    }
}