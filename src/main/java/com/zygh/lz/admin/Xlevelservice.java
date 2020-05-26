package com.zygh.lz.admin;

public class Xlevelservice {
    private Integer id;

    private String callsign;

    private String place;

    private Integer subofficeId;

    private Integer number;

    private Integer sectionId;

    private String ondutytime;

    private String location;

    private String hierarchy;

    private String conment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign == null ? null : callsign.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public Integer getSubofficeId() {
        return subofficeId;
    }

    public void setSubofficeId(Integer subofficeId) {
        this.subofficeId = subofficeId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getOndutytime() {
        return ondutytime;
    }

    public void setOndutytime(String ondutytime) {
        this.ondutytime = ondutytime == null ? null : ondutytime.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy == null ? null : hierarchy.trim();
    }

    public String getConment() {
        return conment;
    }

    public void setConment(String conment) {
        this.conment = conment == null ? null : conment.trim();
    }
}