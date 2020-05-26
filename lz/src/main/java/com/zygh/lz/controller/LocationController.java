package com.zygh.lz.controller;

import com.zygh.lz.admin.Location;
import com.zygh.lz.service.locationService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LocationController {
    @Autowired
    private locationService locationService;

    @PostMapping("addLocation")
    public ResultBean addLocation(Location location){
        return locationService.addLocation(location);
    }

    @GetMapping("delLocation")
    public ResultBean delLocation(Integer id){
        return locationService.delLocation(id);
    }

    @GetMapping("selectLocation")
    @ViLog(logType = "1",module = "实时跟新警员坐标>查询")
    public ResultBean selectLocation(Integer staffId, HttpServletRequest request) {
        return locationService.selectLocation(staffId);
    }

    //实时获取警员坐标
    @GetMapping("selectLocationByNowday")
    @ViLog(logType = "1",module = "实时跟新警员坐标>实时获取警员坐标")
    public ResultBean selectLocationByNowday(HttpServletRequest request){
        return locationService.selectLocationByNowday();
    }
}
