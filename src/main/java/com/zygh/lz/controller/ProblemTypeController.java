package com.zygh.lz.controller;

import com.zygh.lz.service.problemTypeService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ProblemTypeController {
    @Autowired
    private problemTypeService problemTypeService;

     //查询所有问题类型
    @GetMapping("selectAllByProblemtype")
    @ViLog(logType = "1",module = "问题类型>查询所有问题类型")
    public ResultBean selectAllByProblemtype(HttpServletRequest request){
        return problemTypeService.selectAllByProblemtype();
    }


}
