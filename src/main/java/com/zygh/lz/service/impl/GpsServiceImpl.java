package com.zygh.lz.service.impl;

import com.zygh.lz.admin.Gps;
import com.zygh.lz.admin.Location;
import com.zygh.lz.mapper.GpsMapper;
import com.zygh.lz.mapper.LocationMapper;
import com.zygh.lz.service.GpsService;
import com.zygh.lz.util.GPSTransformMars;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GpsServiceImpl implements GpsService {
    @Autowired
    private GpsMapper gpsMapper;
    @Autowired
    private LocationMapper locationMapper;

    /**
     * 新增GPS
     * @param gps
     * @return
     */
    @Override
    public ResultBean addGps(Gps gps) {
        Location location=new Location();
        location.setSysStaffId(gps.getSysStaffId());
        location.setTime(new Date());
        GPSTransformMars gpsTransformMars = new GPSTransformMars();
        double x= Double.parseDouble(gps.getGpsX());
        double y= Double.parseDouble(gps.getGpsY());
        //double[] m=gpsTransformMars.transLatLng(x,y);
        //gps.setGpsX(String.valueOf(m[0]));
        //gps.setGpsY(String.valueOf(m[1]));
        location.setGpsX(String.valueOf(x));
        location.setGpsY(String.valueOf(y));
        locationMapper.updateByStaffId(location);
        System.out.println("gps");
        return ResultUtil.execOp(gpsMapper.insertSelective(gps),"新增");
    }
}
