package com.zygh.lz.mapper;

import com.zygh.lz.admin.Road;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoadMapper {
    int deleteByPrimaryKey(Integer sysRoadId);

    int insert(Road record);

    int insertSelective(Road record);

    Road selectByPrimaryKey(Integer sysRoadId);

    int updateByPrimaryKeySelective(Road record);

    int updateByPrimaryKey(Road record);

    //根据道路类型、区域模糊查询
    List<Road> selectRoadByCondition(@Param("keyword") String keyword,
                                     @Param("roadType") String roadType,
                                     @Param("urbanName") String urbanName);

    //查询所有道路列表
    List<Road> selectAllRoad();

}