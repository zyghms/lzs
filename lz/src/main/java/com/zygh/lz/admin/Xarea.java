package com.zygh.lz.admin;

import java.util.List;

public class Xarea {
    private Integer id;

    private String name;

    private String station;

    private String type;

    private String battalion;

    private String detachment;

    private String gridding;

    private String rank;

    private String leader;

    private String conment;

    private String centre;

    private String gps;

    private String stafftype;

    public String getStafftype() {
        return stafftype;
    }

    public void setStafftype(String stafftype) {
        this.stafftype = stafftype;
    }

    //应到人员
    public List<Staff> staff ;

    //实到人
    public  List<Patrolrecord> number;

    private Integer sectionZid;

    public Integer getSectionZid() {
        return sectionZid;
    }

    public void setSectionZid(Integer sectionZid) {
        this.sectionZid = sectionZid;
    }

    public Integer getSectionDid() {
        return sectionDid;
    }

    public void setSectionDid(Integer sectionDid) {
        this.sectionDid = sectionDid;
    }

    private Integer sectionDid;

    public  List<Patrolrecord> getNumber() {
        return number;
    }

    public void setNumber( List<Patrolrecord> number) {
        this.number = number;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station == null ? null : station.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getBattalion() {
        return battalion;
    }

    public void setBattalion(String battalion) {
        this.battalion = battalion == null ? null : battalion.trim();
    }

    public String getDetachment() {
        return detachment;
    }

    public void setDetachment(String detachment) {
        this.detachment = detachment == null ? null : detachment.trim();
    }

    public String getGridding() {
        return gridding;
    }

    public void setGridding(String gridding) {
        this.gridding = gridding == null ? null : gridding.trim();
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank == null ? null : rank.trim();
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader == null ? null : leader.trim();
    }

    public String getConment() {
        return conment;
    }

    public void setConment(String conment) {
        this.conment = conment == null ? null : conment.trim();
    }

    public String getCentre() {
        return centre;
    }

    public void setCentre(String centre) {
        this.centre = centre == null ? null : centre.trim();
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps == null ? null : gps.trim();
    }
}