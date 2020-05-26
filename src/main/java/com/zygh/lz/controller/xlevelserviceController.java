package com.zygh.lz.controller;

import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class xlevelserviceController {
    @Autowired
    private com.zygh.lz.service.xlevelserviceService xlevelserviceService;

    //按大队统计等级勤务所有应到人数
    @GetMapping("selectorderlyAllqw")
    @ViLog(logType = "1", module = "按大队统计等级勤务所有应到人数")
    public ResultBean selectorderlyAll(String sectionName){
        return xlevelserviceService.selectorderlyAll(sectionName);
    }
    //
    @GetMapping("selectorderlydjyd")
    @ViLog(logType = "1", module = "查询等级勤务")
    public ResultBean selectorderlydjyd(String sectionName){
        return xlevelserviceService.selectorderlydjyd(sectionName);
    }

    //按等级按大队查询等级勤务
    @GetMapping("selectxleveBydj")
    @ViLog(logType = "1", module = "按等级按大队查询等级勤务")
    public ResultBean selectxleveBydj(Integer hierarchy, String sectionName){
        return xlevelserviceService.selectxleveBydj(hierarchy,sectionName);
    }


}
