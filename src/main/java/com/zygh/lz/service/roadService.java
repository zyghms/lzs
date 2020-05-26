package com.zygh.lz.service;

import com.zygh.lz.admin.Road;
import com.zygh.lz.vo.ResultBean;

public interface roadService {
    //新增
    ResultBean addRoad(Road road);
    //修改
    ResultBean updateRoad(Road road);
    //删除
    ResultBean delRoadById(Integer id);
    //根据道路类型、区域模糊查询
    ResultBean selectRoadByCondition(String keyword,String roadType,String urbanName);
    //查询所有道路
    ResultBean selectAllRoad();

}
