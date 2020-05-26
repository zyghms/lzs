package com.zygh.lz.controller;

import com.zygh.lz.admin.Xarea;
import com.zygh.lz.service.xareaService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class buttJointController {
    @Autowired
    private xareaService xareaServce;
    @Autowired
    private com.zygh.lz.service.staffService staffService;

    /**
     * 根据点线面查询区域
     * @param xarea
     * @return
     */
    @GetMapping("selectXareaByInfo")
    @ViLog(logType = "1", module = "与市局对接模块》根据点线面查询区域")
    public ResultBean selectXareaByInfo(Xarea xarea) {
        return xareaServce.selectXareaByInfo(xarea);
    }

    /**
     *根据区域查询相应警力
     * @param xarea
     * @return
     */
    @GetMapping("selectPoliceNumber")
    @ViLog(logType = "1", module = "与市局对接模块》根据区域查询相应警力")
    public ResultBean selectPoliceNumber(Xarea xarea) {
        return xareaServce.selectPoliceNumber(xarea);
    }

    //根据区域查询相应警力
    @GetMapping("selctStrengthById")
    @ViLog(logType = "1", module = "与市局对接模块》根据区域查询相应警力")
    public ResultBean selctStrengthById(Xarea xarea) {
        return xareaServce.selctStrengthById(xarea);
    }

    //人员信息列表
    @GetMapping("selectStaffByInfo")
    @ViLog(logType = "1", module = "与市局对接模块》人员信息列表")
    public ResultBean selectStaffByInfo() {
        return staffService.selectStaffByInfo();
    }

    //全部在线人
    @GetMapping("selectInformantAll")
    @ViLog(logType = "1", module = "与市局对接模块》全部在线人")
    public ResultBean selectInformantAll() {
        return xareaServce.selectInformant();
    }

    //任务组信息
    @GetMapping("selectTaskSetInfo")
    @ViLog(logType = "1", module = "与市局对接模块》任务组信息")
    public ResultBean selectTaskSetInfo(){return xareaServce.selectTaskSetInfo();}
    //任务信息
    @GetMapping("selectTaskInfo")
    @ViLog(logType = "1", module = "与市局对接模块》任务信息")
    public ResultBean selectTaskInfo(){
        return xareaServce.selectTaskInfo();}


}
