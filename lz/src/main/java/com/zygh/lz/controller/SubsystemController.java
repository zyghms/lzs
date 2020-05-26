package com.zygh.lz.controller;

import com.zygh.lz.service.roadService;
import com.zygh.lz.service.subsystemService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SubsystemController {
    @Autowired
    private subsystemService subsystemService;

    //查询所有子系统
    @GetMapping("selectAllSubsystem")
    @ViLog(logType = "1", module = "子系统>查询所有子系统")
    public ResultBean selectAllSubsystem(HttpServletRequest request) {
        return subsystemService.selectAllSubsystem();
    }

    @GetMapping("selectControlsystem")
    @ViLog(logType = "1", module = "子系统>查询")
    public ResultBean selectControlsystem(HttpServletRequest request) {
        return subsystemService.selectControlsystem();
    }

    @GetMapping("selectControlsubsystem")
    @ViLog(logType = "1", module = "子系统>查询")
    public ResultBean selectControlsubsystem(Integer id, HttpServletRequest request) {
        return subsystemService.selectControlsubsystem(id);
    }

    @GetMapping("selectRank")
    @ViLog(logType = "1", module = "子系统>查询")
    public ResultBean selectRank(HttpServletRequest request) {
        return subsystemService.selectRank();
    }

    @GetMapping("selectRepair")
    @ViLog(logType = "1", module = "子系统>查询")
    public ResultBean selectRepair(HttpServletRequest request) {
        return subsystemService.selectRepair();
    }
}
