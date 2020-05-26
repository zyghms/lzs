package com.zygh.lz.controller;

import com.zygh.lz.admin.Infrastructure;
import com.zygh.lz.service.InfrastructureService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class InfrastructureController {
    @Autowired
    private InfrastructureService infrastructureService;

    //新增交通设施信息
    @PostMapping("addInfrastructure")
    @ViLog(logType = "2",module = "交通设施>新增交通设施信息")
    public ResultBean addInfrastructure(Infrastructure infrastructure, HttpServletRequest request) {
        return infrastructureService.addInfrastructure(infrastructure);
    }

    //修改交通设施信息
    @GetMapping("modifuInfrastructure")
    @ViLog(logType = "3",module = "交通设施>修改交通设施信息")
    public ResultBean updateInfrastructure(Infrastructure infrastructure,HttpServletRequest request) {
        return infrastructureService.updateInfrastructure(infrastructure);
    }

    //根据id删除交通设施信息
    @GetMapping("delInfrastructure")
    @ViLog(logType = "4",module = "交通设施>删除交通设施信息")
    public ResultBean delInfrastructure(Integer id,HttpServletRequest request) {
        return infrastructureService.delInfrastructure(id);
    }

    //根据交通设施类型、道路名模糊查询
    @GetMapping("selectInfrastructureByInfo")
    @ViLog(logType = "1",module = "交通设施>根据交通设施类型、道路名模糊查询")
    public ResultBean selectInfrastructureByInfo(String type, String roadName,HttpServletRequest request) {
        return infrastructureService.selectInfrastructureByInfo(type, roadName);
    }
}
