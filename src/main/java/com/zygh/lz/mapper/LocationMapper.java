package com.zygh.lz.mapper;

import com.zygh.lz.admin.Location;

import java.util.List;

public interface LocationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Location record);

    int insertSelective(Location record);

    Location selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Location record);

    int updateByPrimaryKey(Location record);

    List<Location> selectLocationByStaffId(Integer staffId);

    //实时修改警员坐标
    int updateByStaffId(Location record);

    //实时获取警员坐标
    List<Location> selectLocationByNowday();
}