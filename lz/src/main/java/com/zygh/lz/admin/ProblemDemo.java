package com.zygh.lz.admin;

/**
 * 公司：河南中裕广恒科技股份有限公司
 * 项目：知识库管理系统
 * 编程人员：研发部柯博
 * 时间：2018/10/26 14:25
 */
public class ProblemDemo {
    private Integer count;              //数量
    private String sectionName;         //部门名称
    private String problemResove;       //问题是否解决

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getProblemResove() {
        return problemResove;
    }

    public void setProblemResove(String problemResove) {
        this.problemResove = problemResove;
    }
}
