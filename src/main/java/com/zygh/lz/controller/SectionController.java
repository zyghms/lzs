package com.zygh.lz.controller;

import com.zygh.lz.admin.Section;
import com.zygh.lz.service.roleService;
import com.zygh.lz.service.sectionService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SectionController {
    @Autowired
    private sectionService sectionService;

    //新增部门
    @PostMapping("addSection")
    @ViLog(logType = "2", module = "部门模块>新增部门")
    public ResultBean addSection(Section section, HttpServletRequest request) {
        return sectionService.addSection(section);
    }

    //修改部门
    @GetMapping("modefiSectionById")
    @ViLog(logType = "3", module = "部门模块>修改部门")
    public ResultBean modefiSectionById(Section section, HttpServletRequest request) {
        return sectionService.modefiSectionById(section);
    }

    //根据id删除部门
    @GetMapping("delSectionById")
    @ViLog(logType = "4", module = "部门模块>根据id删除部门")
    public ResultBean delSectionById(Integer id, HttpServletRequest request) {
        return sectionService.delSectionById(id);
    }

    //根据姓名，性别，职位，部门，领导模糊查询所有部门人员(查询人员的模糊查询)
    @GetMapping("selectAllBySection")
    @ViLog(logType = "1", module = "部门模块>根据姓名，性别，职位，部门，领导模糊查询所有部门人员(查询人员的模糊查询)")
    public ResultBean selectAllBySection(String sex, String staffPost, String staffName, String staffPid, String sectionName, HttpServletRequest request) {
        return sectionService.selectAllBySection(sex, staffPost, staffName, staffPid, sectionName);
    }

    //按层级推送
    @GetMapping("selectAllSection")
    @ViLog(logType = "1", module = "部门模块>按层级推送")
    public ResultBean selectAllSection(HttpServletRequest request) {
        return sectionService.selectAllSection();
    }

    //查询所有部门
    @GetMapping("selectSection")
    @ViLog(logType = "1", module = "部门模块>查询所有部门")
    public ResultBean selectSection(HttpServletRequest request) {
        return sectionService.selectAllSectionInfo();
    }

    //门人员信息查询那里的模糊查询
    @GetMapping("selectSectionByCt")
    @ViLog(logType = "1", module = "部门模块>门人员信息查询那里的模糊查询")
    public ResultBean selectSectionByCt(String sex, String staffHierarchy, String sectionName, HttpServletRequest request) {
        return sectionService.selectSectionByCt(sex, staffHierarchy, sectionName);
    }

    //根据部门id查询所有部门人员
    @GetMapping("selectBySectionId")
    @ViLog(logType = "1", module = "部门模块>根据部门id查询所有部门人员")
    public ResultBean selectBySectionId(Integer sectionId, String staffHierarchy, HttpServletRequest request) {
        return sectionService.selectBySectionId(sectionId, staffHierarchy);
    }

    //根据大队id查询大队下面的中队
    @GetMapping("selectDetachmentByid")
    @ViLog(logType = "1", module = "部门模块>根据大队id查询大队下面的中队")
    public ResultBean selectDetachmentByid(Integer id){
        return sectionService.selectDetachmentByid(id);
    }
}
