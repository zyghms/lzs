package com.zygh.lz.service.impl;

import com.zygh.lz.admin.Location;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.LocationMapper;
import com.zygh.lz.service.locationService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class locationServiceImpl implements locationService {
    @Autowired
    private LocationMapper locationMapper;


    @Override
    public ResultBean addLocation(Location location) {
        return ResultUtil.execOp(locationMapper.insertSelective(location), "新增");
    }

    @Override
    public ResultBean delLocation(Integer id) {
        return ResultUtil.execOp(locationMapper.deleteByPrimaryKey(id), "删除");
    }

    @Override
    public ResultBean selectLocation(Integer staffId) {
        List<Location> locations = locationMapper.selectLocationByStaffId(staffId);
        if (locations.size() > 0 || locations.size() == 0) {
            return ResultUtil.setOK("success",locations);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }
    /**
     * 实时获取警员坐标
     * @return
     */
    @Override
    public ResultBean selectLocationByNowday() {
        List<Location> locations = locationMapper.selectLocationByNowday();
        if (locations.size() > 0 || locations.size() == 0) {
            return ResultUtil.setOK("success",locations);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }
}
