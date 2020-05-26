package com.zygh.lz.controller;

import com.zygh.lz.admin.Duty;
import com.zygh.lz.service.dutyService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DutyController {
    @Autowired
    private dutyService dutyService;

    //责任明细
    @GetMapping("selectAllDuty")
    @ViLog(logType = "1",module = "责任明细>责任明细查询")
    public ResultBean selectAllDuty(HttpServletRequest request){
        return dutyService.selectAllDuty();
    }


    //模糊查询
    @GetMapping("seleteDimAsset")
    @ViLog(logType = "1",module = "责任明细>责任明细模糊查询")
    public ResultBean seleteDimAsset(Duty duty,HttpServletRequest request){
        return dutyService.seleteDimAsset(duty);
    }

    //删除
    @GetMapping("deleteSomeDuty")
    @ViLog(logType = "4",module = "责任明细>道路责任明细删除")
    public ResultBean deleteSomeDuty(Integer id,HttpServletRequest request){
        return dutyService.delDuty(id);
    }

    //修改
    @GetMapping("updateDuty")
    @ViLog(logType = "3",module = "责任明细>道路责任明细修改")
    public ResultBean updateDuty(Duty duty,HttpServletRequest request){
        return dutyService.updateDuty(duty);
    }
    //新增
    @PostMapping("insertDuty")
    @ViLog(logType = "2",module = "责任明细>道路责任明细新增")
    public ResultBean insertDuty(Duty duty,HttpServletRequest request){
        return dutyService.addDuty(duty);
    }

}
