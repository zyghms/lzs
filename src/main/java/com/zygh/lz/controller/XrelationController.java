package com.zygh.lz.controller;

import com.zygh.lz.admin.Xrelation;
import com.zygh.lz.service.XrelationService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XrelationController {
    @Autowired
    private XrelationService xrelationService;
    //新增
    @PostMapping("insertXrelation")
    @ViLog(logType = "2", module = "新增民警与区域的关联")
    public ResultBean insertXrelation(Xrelation xrelation){
        return xrelationService.insertXrelation(xrelation);
    }
    //修改
    @GetMapping("updateXrelation")
    @ViLog(logType = "3", module = "修改民警与区域的关联")
    public ResultBean updateXrelation(Xrelation xrelation){
        return xrelationService.updateXrelation(xrelation);
    }
    //删除
    @GetMapping("deleteXrelatonByid")
    @ViLog(logType = "4", module = "删除民警与区域的关联")
    public ResultBean deleteXrelatonByid(Integer id){
        return xrelationService.deleteXrelatonByid(id);
    }
}
