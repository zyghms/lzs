package com.zygh.lz.controller;

import com.zygh.lz.admin.Road;
import com.zygh.lz.service.roadService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RoadController {
    @Autowired
    private roadService roadService;

    //新增
    @PostMapping("addRoad")
    @ViLog(logType = "2",module = "道路列表>道路新增")
    public ResultBean addRoad(Road road, HttpServletRequest request) {
        return roadService.addRoad(road);
    }


    //修改
    @GetMapping("updateRoad")
    @ViLog(logType = "3",module = "道路列表>道路修改")
    public ResultBean updateRoad(Road road, HttpServletRequest request) {
        return roadService.updateRoad(road);
    }


    //删除
    @GetMapping("delRoadById")
    @ViLog(logType = "4",module = "道路列表>道路删除")
    public ResultBean delRoadById(Integer id, HttpServletRequest request) {
        return roadService.delRoadById(id);
    }

    //根据道路类型、区域模糊查询
    @GetMapping("selectRoadByCondition")
    @ViLog(logType = "1",module = "道路列表>根据道路类型、区域模糊查询")
    public ResultBean selectRoadByCondition(String keyword,String roadType, String urbanName, HttpServletRequest request) {
        return roadService.selectRoadByCondition(keyword,roadType, urbanName);
    }

    //查询所有道路列表
    @GetMapping("selectAllRoad")
    @ViLog(logType = "1",module = "道路列表>查询所有道路列表")
    public ResultBean selectAllRoad(HttpServletRequest request){
        return roadService.selectAllRoad();
    }

}
