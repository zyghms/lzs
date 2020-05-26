package com.zygh.lz.controller;

import com.zygh.lz.admin.Gps;
import com.zygh.lz.admin.Xlevelservice;
import com.zygh.lz.mapper.XlevelserviceMapper;
import com.zygh.lz.service.GpsService;
import com.zygh.lz.util.GPSTransformMars;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class GPSController {
    @Autowired
    private GpsService gpsService;
    @Autowired
    private XlevelserviceMapper xlevelserviceMapper;

    //实时上传gps
    @PostMapping("addGps")
    @ViLog(logType = "1",module = "GPS>实时上传gps")
    public ResultBean addGps(Gps gps, HttpServletRequest request){
        request.setAttribute("result",gpsService.addGps(gps));
        return gpsService.addGps(gps);
    }

    //测试使用，改变数据库表坐标
   /* @GetMapping("gps")
    public void gps(){
        List<Xlevelservice> xareas = xlevelserviceMapper.selectXleveAll();
        for (int i=0;i<xareas.size();i++){
            System.out.println("====="+xareas.get(i).getLocation());
            if(xareas.get(i).getLocation()!=null){
                Xlevelservice xarea=new Xlevelservice();
                String s = GPSTransformMars.GCj2TOWGS(xareas.get(i).getLocation());

                xarea.setId(xareas.get(i).getId());
                xarea.setLocation(s);
                xlevelserviceMapper.updateByPrimaryKeySelective(xarea);
            }
        }

    }*/
}
