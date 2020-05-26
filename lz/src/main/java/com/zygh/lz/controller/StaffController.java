package com.zygh.lz.controller;

import com.zygh.lz.admin.Xarea;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaffController {
    @Autowired
    private com.zygh.lz.service.staffService staffService;


    //默认人
    @GetMapping("selectdefault")
    @ViLog(logType = "1", module = "查询默认人")
    public ResultBean selectdefault(String probleDetail) {
        return staffService.selectdefault(probleDetail);
    }

    //默认大队
    @GetMapping("selectStaffBySectionName")
    @ViLog(logType = "1", module = "查询默认大队")
    public ResultBean selectStaffBySectionName(Integer staffid) {
        return staffService.selectStaffBySectionName(staffid);
    }

    //查询在岗人数
    @GetMapping("selectStaffByzg")
    @ViLog(logType = "1", module = "查询在岗人数")
    public ResultBean selectStaffByzg(Integer sectionId, Integer sectionPid, String sectionName) {
        return staffService.selectStaffByzg(sectionId, sectionPid, sectionName);
    }

    @GetMapping("selectStaffByName")
    @ViLog(logType = "1", module = "根据名字模糊查询")
    public ResultBean selectStaffByName(String name, String staffHierarchy) {
        return staffService.selectStaffByName(name, staffHierarchy);
    }

    //分层级查询在岗人数
    @GetMapping("selectStaffInfoByid")
    @ViLog(logType = "1", module = "分层级查询在岗人数")
    public ResultBean selectStaffInfoByid(Integer id) {
        return staffService.selectStaffInfoByid(id);
    }

    //应到民警
    @GetMapping("selectStaffYdByAll")
    @ViLog(logType = "1", module = "根据部门查询应到民警")
    public ResultBean selectStaffYdByAll(Integer SectionId) {
        return staffService.selectStaffYdByAll(null, SectionId);
    }

    //在线民警
    @GetMapping("selectpoliceZx")
    @ViLog(logType = "1", module = "根据部门查询在线民警")
    public ResultBean selectpoliceZx(String station) throws Exception {
        return staffService.selectpoliceZx(station);
    }

    //查询该人的直系领导
    @GetMapping("selectStaffByid")
    @ViLog(logType = "1", module = "查询该人的直系领导")
    public ResultBean selectStaffByid(Integer id) {
        return staffService.selectStaffByid(id);
    }

    //根据民警id查询该民警所负责的路段里程，上班时长
    @GetMapping("selectStaffXareaByid")
    @ViLog(logType = "1", module = "根据民警id查询该民警所负责的路段里程，上班时长")
    public ResultBean selectStaffXareaByid(Integer id) throws Exception {
        return staffService.selectStaffXareaByid(id);
    }

    //根据民警id查询该民警所负责的路段
    @GetMapping("selectXareaByid")
    @ViLog(logType = "1", module = "根据民警id查询该民警所负责的路段")
    public ResultBean selectXareaByid(Integer id) throws Exception {
        return staffService.selectXareaByid(id);
    }

    //返回在线民警GPS
    @GetMapping("selectStaffZx")
    @ViLog(logType = "1", module = "返回在线民警GPS")
    public ResultBean selectStaffZx(Integer id) {
        return staffService.selectStaffZx(id);
    }

    //查询昨日总警力
    @GetMapping("selecttotalforces")
    @ViLog(logType = "1", module = "查询昨日总警力")
    public ResultBean selecttotalforces() {
        return staffService.selecttotalforces();
    }

    //按大队查询昨日总警力
    @GetMapping("selecttotalforceszr")
    @ViLog(logType = "1", module = "按大队查询昨日总警力")
    public ResultBean selecttotalforceszr() {
        return staffService.selecttotalforceszr();
    }

    //根据不同岗位查询在线人数
    @GetMapping("selectzaBystation")
    @ViLog(logType = "1", module = "根据不同岗位查询在线人数")
    public ResultBean selectzaBystation(String station, String conment, String grid) {
        return staffService.selectzaBystation(station, conment, grid);
    }

    //查询其他在岗人
    @GetMapping("selectStaffByqita")
    @ViLog(logType = "1", module = "查询其他在岗人")
    public ResultBean selectStaffByqita() {
        return staffService.selectStaffByqita();
    }

    //以单位为结构显示信息列表
    @GetMapping("selectAllbytype")
    public ResultBean selectAllbytype(String battalion) {
        return staffService.selectAllbytype(battalion);
    }


    //根据岗位查询各大队在线民警
    @GetMapping("selectcountBysection")
    @ViLog(logType = "1", module = "根据岗位查询各大队在线民警")
    public ResultBean selectcountBysection(Xarea xarea) {
        return staffService.selectcountBysection(xarea);
    }

    //根据岗位查询各大队下各中队在线民警
    @GetMapping("selectcountBydetachment")
    @ViLog(logType = "1", module = "根据岗位查询各大队下各中队在线民警")
    public ResultBean selectcountBydetachment(Xarea xarea) {
        return staffService.selectcountBydetachment(xarea);
    }

    //根据岗位查询各大队在线民警详情
    @GetMapping("selectAllBysection")
    @ViLog(logType = "1", module = "根据岗位查询各大队在线民警详情")
    public ResultBean selectAllBysection(Xarea xarea) {
        return staffService.selectAllBysection(xarea);
    }
}
