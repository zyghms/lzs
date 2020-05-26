package com.zygh.lz.admin;

public class Basic {
    private Integer sysBasicId;             //基础设施id

    private String basicCategory;           //大类

    private String basicAttribute;          //小类

    private String basicIp;                 //ip地址

    private String basicModel;              //品牌型号

    private String basicBrandname;          //品牌名称

    private String basicEnvironment;        //部署环境

    private String basicNumber;             //序号

    private String basicDate;               //建设时间

    private String basicWay;                //发布方式

    private String basicNum;                //模块数量

    private String basicElectricity;        //供电方式

    private String basicDatasource;         //数据源

    private String basicPrecision;          //经度

    private String basicLatitude;           //纬度

    private String basicLocation;           //地理位置

    private String basicUse;                //主要用途

    private String basicNormal;             //是否正常

    private String basicEnable;             //是否启用

    private Integer sysDutyId;              //道路责任明细id

    private String basicAccount;            //账号

    private String basicPassword;           //密码

    private Road road;                      //道路

    private Duty duty;                      //道路责任明细

    private Staff staff;                    //路长

    private Section section;                //部门

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Integer getSysBasicId() {
        return sysBasicId;
    }

    public void setSysBasicId(Integer sysBasicId) {
        this.sysBasicId = sysBasicId;
    }

    public String getBasicCategory() {
        return basicCategory;
    }

    public void setBasicCategory(String basicCategory) {
        this.basicCategory = basicCategory == null ? null : basicCategory.trim();
    }

    public String getBasicAttribute() {
        return basicAttribute;
    }

    public void setBasicAttribute(String basicAttribute) {
        this.basicAttribute = basicAttribute == null ? null : basicAttribute.trim();
    }

    public String getBasicIp() {
        return basicIp;
    }

    public void setBasicIp(String basicIp) {
        this.basicIp = basicIp == null ? null : basicIp.trim();
    }

    public String getBasicModel() {
        return basicModel;
    }

    public void setBasicModel(String basicModel) {
        this.basicModel = basicModel == null ? null : basicModel.trim();
    }

    public String getBasicBrandname() {
        return basicBrandname;
    }

    public void setBasicBrandname(String basicBrandname) {
        this.basicBrandname = basicBrandname == null ? null : basicBrandname.trim();
    }

    public String getBasicEnvironment() {
        return basicEnvironment;
    }

    public void setBasicEnvironment(String basicEnvironment) {
        this.basicEnvironment = basicEnvironment == null ? null : basicEnvironment.trim();
    }

    public String getBasicNumber() {
        return basicNumber;
    }

    public void setBasicNumber(String basicNumber) {
        this.basicNumber = basicNumber == null ? null : basicNumber.trim();
    }

    public String getBasicDate() {
        return basicDate;
    }

    public void setBasicDate(String basicDate) {
        this.basicDate = basicDate == null ? null : basicDate.trim();
    }

    public String getBasicWay() {
        return basicWay;
    }

    public void setBasicWay(String basicWay) {
        this.basicWay = basicWay == null ? null : basicWay.trim();
    }

    public String getBasicNum() {
        return basicNum;
    }

    public void setBasicNum(String basicNum) {
        this.basicNum = basicNum == null ? null : basicNum.trim();
    }

    public String getBasicElectricity() {
        return basicElectricity;
    }

    public void setBasicElectricity(String basicElectricity) {
        this.basicElectricity = basicElectricity == null ? null : basicElectricity.trim();
    }

    public String getBasicDatasource() {
        return basicDatasource;
    }

    public void setBasicDatasource(String basicDatasource) {
        this.basicDatasource = basicDatasource == null ? null : basicDatasource.trim();
    }

    public String getBasicPrecision() {
        return basicPrecision;
    }

    public void setBasicPrecision(String basicPrecision) {
        this.basicPrecision = basicPrecision == null ? null : basicPrecision.trim();
    }

    public String getBasicLatitude() {
        return basicLatitude;
    }

    public void setBasicLatitude(String basicLatitude) {
        this.basicLatitude = basicLatitude == null ? null : basicLatitude.trim();
    }

    public String getBasicLocation() {
        return basicLocation;
    }

    public void setBasicLocation(String basicLocation) {
        this.basicLocation = basicLocation == null ? null : basicLocation.trim();
    }

    public String getBasicUse() {
        return basicUse;
    }

    public void setBasicUse(String basicUse) {
        this.basicUse = basicUse == null ? null : basicUse.trim();
    }

    public String getBasicNormal() {
        return basicNormal;
    }

    public void setBasicNormal(String basicNormal) {
        this.basicNormal = basicNormal == null ? null : basicNormal.trim();
    }

    public String getBasicEnable() {
        return basicEnable;
    }

    public void setBasicEnable(String basicEnable) {
        this.basicEnable = basicEnable == null ? null : basicEnable.trim();
    }

    public Integer getSysDutyId() {
        return sysDutyId;
    }

    public void setSysDutyId(Integer sysDutyId) {
        this.sysDutyId = sysDutyId;
    }

    public String getBasicAccount() {
        return basicAccount;
    }

    public void setBasicAccount(String basicAccount) {
        this.basicAccount = basicAccount == null ? null : basicAccount.trim();
    }

    public String getBasicPassword() {
        return basicPassword;
    }

    public void setBasicPassword(String basicPassword) {
        this.basicPassword = basicPassword == null ? null : basicPassword.trim();
    }

    public Road getRoad() {
        return road;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}