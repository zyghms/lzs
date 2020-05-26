package com.zygh.lz.service;

import com.zygh.lz.admin.Location;
import com.zygh.lz.vo.ResultBean;

public interface locationService {
    ResultBean addLocation(Location location);
    ResultBean delLocation(Integer id);
    ResultBean selectLocation(Integer staffId);
    //实时获取警员坐标
    ResultBean selectLocationByNowday();
}
