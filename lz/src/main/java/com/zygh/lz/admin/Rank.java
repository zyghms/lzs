package com.zygh.lz.admin;

public class Rank {
    private Integer id;

    private String ranker;

    private Integer rankid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRanker() {
        return ranker;
    }

    public void setRanker(String ranker) {
        this.ranker = ranker == null ? null : ranker.trim();
    }

    public Integer getRankid() {
        return rankid;
    }

    public void setRankid(Integer rankid) {
        this.rankid = rankid;
    }
}