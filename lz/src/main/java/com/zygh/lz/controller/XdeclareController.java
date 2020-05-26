package com.zygh.lz.controller;

import com.zygh.lz.admin.Xdeclare;
import com.zygh.lz.service.xdeclareService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XdeclareController {
    @Autowired
    private xdeclareService xdelclareService;

    @PostMapping("insertXdeclare")
    @ViLog(logType = "2", module = "新增报备")
    public ResultBean insertXdeclare(Xdeclare xdeclare){
        return xdelclareService.insertXdeclare(xdeclare);
    }

}
